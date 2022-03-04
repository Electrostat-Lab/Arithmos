#include<Threader.h>

POSIX::Threader::Threader(DispatcherArgs* args) {
    this->dispatcher = new ThreadDispatcher();
    this->args = args;
}

POSIX::Threader::~Threader() {
    delete this->dispatcher;
    this->dispatcher = NULL;
    
    delete this->args;
    this->args = NULL;
}

int methodDispatcher(void* arguments) {
    POSIX::Threader::DispatcherArgs* args = (POSIX::Threader::DispatcherArgs*) arguments;
    
    /* get the method from a java class by its name, using the classPath and the method signature */
    Class clazz = args->javaEnv->FindClass(args->classPath);
    jobject params = args->params;
    Method constructor = args->javaEnv->GetMethodID(clazz, "<init>", "(Lpthread/model/ParameterList;)V");

    jobject object = args->javaEnv->NewObject(clazz, constructor, params);
    
    Method method = NULL;
    
    if (clazz != NULL) {
        method = args->javaEnv->GetMethodID(clazz, "invoke", "()V");
    } else {
        printf("Cannot find the class\n");
        args->javaEnv->ExceptionDescribe();
        return 0;
    }
    if (method != NULL) {
        args->javaEnv->CallVoidMethod(object, method);
    } else {
        printf("Cannot find the method \n");
        args->javaEnv->ExceptionDescribe();
    }

    if (args->threadType == POSIX::Threader::SYNC) {
        //unlock the mutex Object for the other threads
        unlockMutex(args->mutex);
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
    jvmArgs.name = (const char*) "invoke";
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
    do {
        JavaVM* javaVm;
        env->GetJavaVM(&javaVm);
        isFinished = javaVm->DetachCurrentThread();
        javaVm->DestroyJavaVM();
    } while(isFinished == -1);
    return isFinished;
}

int startMutex(void* arguments) {
    int result = -1;
    POSIX::Threader::DispatcherArgs* args = (POSIX::Threader::DispatcherArgs*) arguments;
     while(true) {
        //lock the mutexObject to this thread(obtain the object to this event)
        //=> once obtained the synchronized work(exclusive actions execute
        int lockResult = lockMutex(args->mutex);
        if (lockResult != -1) {
            result = methodDispatcher(arguments);
            return result;
        }                    
    }
    return result;
}

void* startDispatcher(void* arguments) {
    int result = -1;
    POSIX::Threader::DispatcherArgs* args = (POSIX::Threader::DispatcherArgs*) arguments;
    
    attachDispatcher(arguments);
    
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
    while(true) {
        if (result >= 1) {
            exitCurrentThread(NULL);
            detachDispatcher(args->javaEnv);
        }
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


