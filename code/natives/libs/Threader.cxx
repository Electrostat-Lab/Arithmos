#include<Threader.h>

POSIX::Threader::Threader(DispatcherArgs* args) {
    this->args = args;
    this->dispatcher = new ThreadDispatcher();
}

POSIX::Threader::~Threader() {
    this->dispatcher = NULL;
    delete this->dispatcher;
    
    this->args = NULL;
    delete this->args;
}

int methodDispatcher(void* arguments) {
    /* lateinit vars */
    Class clazz = NULL;
    Object params = NULL;
    Method constructor = NULL;
    Method method = NULL;
    Object object = NULL;
    /* local pointers*/
    POSIX::Threader::DispatcherArgs* args = (POSIX::Threader::DispatcherArgs*) arguments;
    
    if (args->javaEnv == NULL) {
        throw "Cannot find a valid java environment pointer !";
    }

    /* get the method from a java class by its name, using the classPath and the method signature */
    clazz = args->javaEnv->FindClass(args->classPath);
    params = args->params;
    
    if (clazz != NULL) {
        constructor = args->javaEnv->GetMethodID(clazz, "<init>", args->INTERFACE_CONSTRUCTOR_SIG);
    } else {
        args->javaEnv->ExceptionDescribe();
    }
    
    if (clazz != NULL && constructor != NULL && params != NULL) {
        object = args->javaEnv->NewObject(clazz, constructor, params);
    } else {
        args->javaEnv->ExceptionDescribe();
    }
    
    if (clazz != NULL) {
        method = args->javaEnv->GetMethodID(clazz, args->INTERFACING_METHOD, args->INTERFACING_METHOD_SIG);
    } else {
        args->javaEnv->ExceptionDescribe();
        return 0;
    }
    if (method != NULL) {
        args->javaEnv->CallVoidMethod(object, method);
    } else {
        args->javaEnv->ExceptionDescribe();
    }

    if (args->threadType == POSIX::Threader::SYNC) {
        //unlock the mutex Object for the other threads
        int isUnlocked = -1;
        while(isUnlocked <= 0) {
            isUnlocked = unlockMutex(args->mutex);
        }
    }

    return 1;
}

bool initSyncDispatcher(void* arguments) {
    POSIX::Threader::DispatcherArgs* args = (POSIX::Threader::DispatcherArgs*) arguments;
    if (isInitialized >= 0) {
        return true;
    }
    if (mutexInit(args->mutex, args->mutexAttr) < 0) {
        perror("mutex_initialize_stopped with errno EINVAL\n");                                                       
        return false;
    } else {
        while(true) {
            isInitialized = setMutexAttrType(args->mutexAttr, PTHREAD_MUTEX_DEFAULT);
            return isInitialized > -1;
        }
    }
    return false;
}

void attachDispatcher(void* arguments) {
    POSIX::Threader::DispatcherArgs* args = (POSIX::Threader::DispatcherArgs*) arguments;
    /* setup jvm thread attach args info */
    int isAttached = -1;
    JavaVMAttachArgs jvmArgs;
    jvmArgs.name = (char*) args->INTERFACING_METHOD;
    jvmArgs.group = NULL;
     /* attach the current thread to the Java VM based on the platform */
    while (isAttached == -1) {
        #if (Platform == Linux_x64)
            jvmArgs.version = JNI_VERSION_1_8; 
            isAttached = args->javaVM->AttachCurrentThread((void**)&(args->javaEnv), &jvmArgs);
        #elif (Platform == Android_x86_x64)
            jvmArgs.version = JNI_VERSION_1_6;
            isAttached = args->javaVM->AttachCurrentThread(&(args->javaEnv), &jvmArgs);
        #endif
    /* wait until the thread is attached to the jvm then start invoking the java funtions */
    }
}

int detachDispatcher(JNIEnv* env) {
    int isFinished = -1;
    JavaVM* javaVm;
    env->GetJavaVM(&javaVm);
    
    while(isFinished == -1) {
        isFinished = javaVm->DetachCurrentThread();
    }

    javaVm->DestroyJavaVM();

    return isFinished;
}

int startMutex(void* arguments) {
    int result = -1;
    POSIX::Threader::DispatcherArgs* args = (POSIX::Threader::DispatcherArgs*) arguments;
     while(true) {
        //lock the mutexObject to this thread(obtain the object to this event)
        //=> once obtained the synchronized work(exclusive actions execute
        int isLocked = lockMutex(args->mutex);
        if (isLocked != -1) {
            result = methodDispatcher(arguments);
            return result;
        }                    
    }
    return result;
}

void* startDispatcher(void* arguments) {
    int result = -1;
    int isFinished = -1;

    POSIX::Threader::DispatcherArgs* args = (POSIX::Threader::DispatcherArgs*) arguments;
    
    attachDispatcher(arguments);

    while (result <= 0) {
        if (args->threadType == POSIX::Threader::ASYNC) {
            result = methodDispatcher(arguments);
        } else if (args->threadType == POSIX::Threader::SYNC) {
            // mutex-based threading
            while(true) { 
                bool isInitialized = initSyncDispatcher(arguments);
                if (isInitialized) {
                    //start mutex here
                    result = startMutex(arguments);
                    break;
                }
            }
        }
    }

    exitCurrentThread(NULL);

    while (isFinished <= 0) {
        isFinished = detachDispatcher(args->javaEnv);
    }

    return NULL;
}

void POSIX::Threader::dispatch() {   
    usleep(this->args->delay);
    /* get javaVM from JNIEnv */
    if (this->args == NULL) {
        throw "IllegalStateException : Cannot proceed with NULL Args !";
    }
    if (this->args->javaEnv->GetJavaVM(&(args->javaVM)) != -1) {
        dispatchThread(dispatcher, NULL, startDispatcher, (POSIX::Threader::DispatcherArgs*) this->args);
    } else {
        throw "Cannot determine the dispatcher type, please use either DispatcherType::ASYNC or DispatcherType::SYNC";
    }
}