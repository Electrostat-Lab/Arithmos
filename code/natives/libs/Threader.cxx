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

int detachDispatcher(JavaVM& vm) {
    int isFinished = -1;
    
    while(isFinished != 0) {
        isFinished = vm.DetachCurrentThread();
    }

    vm.DestroyJavaVM();

    return isFinished;
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
    
    /* protect a critical section using join thread */
    join(*(args->pthread), NULL);

    JNIEnv* javaEnv;
    args->javaVM->GetEnv((void**)&(javaEnv), args->jvmArgs->version);

    /* Sanity check the inputs */
    if (javaEnv == NULL) {
        throw "Cannot find a valid java environment pointer !";
    }

    /* get the method from a java class by its name, using the classPath and the method signature */
    clazz = javaEnv->FindClass(args->classPath);
    params = args->params;
    
    if (clazz != NULL) {
        constructor = javaEnv->GetMethodID(clazz, "<init>", args->INTERFACE_CONSTRUCTOR_SIG);
    } else {
        javaEnv->ExceptionDescribe();
    }
    
    if (clazz != NULL && constructor != NULL && params != NULL) {
        object = javaEnv->NewObject(clazz, constructor, params);
    } else {
        javaEnv->ExceptionDescribe();
    }
    
    if (clazz != NULL) {
        method = javaEnv->GetMethodID(clazz, args->INTERFACING_METHOD, args->INTERFACING_METHOD_SIG);
    } else {
        javaEnv->ExceptionDescribe();
        return 0;
    }
    if (method != NULL) {
        POSIX::forceCoolDown(args->delay);
        javaEnv->CallVoidMethod(object, method, args->javaDispatcherInstance);
    } else {
        javaEnv->ExceptionDescribe();
    }

    if (args->threadType == POSIX::Threader::MUTEX) {
        //unlock the mutex Object for the other threads
        int isUnlocked = -1;
        while (isUnlocked != 0) {
            isUnlocked = unlockMutex(args->mutex);
        }
    }
    return 0;
}

bool initSyncDispatcher(void* arguments) {
    POSIX::Threader::DispatcherArgs* args = (POSIX::Threader::DispatcherArgs*) arguments;
    if (isInitialized == 0) {
        return true;
    }
    if (mutexInit(args->mutex, args->mutexAttr) != 0) {
        perror("mutex_initialize_stopped with errno EINVAL\n");                                                       
        return false;
    } else {
        // 0 -> maps a success, non-zero maps a failure (bigger than zero in NPTL API)
        while (isInitialized != 0) {
            isInitialized = setMutexAttrType(args->mutexAttr, PTHREAD_MUTEX_DEFAULT);
            return isInitialized == 0;
        }
    }
    return false;
}

void attachDispatcher(void* arguments) {
    POSIX::Threader::DispatcherArgs* args = (POSIX::Threader::DispatcherArgs*) arguments;
    /* setup jvm thread attach args info */
    int isAttached = -1;

    JNIEnv* javaEnv;
    args->javaVM->GetEnv((void**)&(javaEnv), args->jvmArgs->version);

     /* attach the current thread to the Java VM based on the platform */
    while (isAttached != 0) {
        #if (Platform == Linux_x64)
            isAttached = args->javaVM->AttachCurrentThread((void**)&(javaEnv), args->jvmArgs);
        #elif (Platform == Android_x86_x64)
            isAttached = args->javaVM->AttachCurrentThread(&(javaEnv), args->jvmArgs);
        #endif
    /* wait until the thread is attached to the jvm then start invoking the java funtions */
    }
}

int startMutex(void* arguments) {
    int result = -1;
    POSIX::Threader::DispatcherArgs* args = (POSIX::Threader::DispatcherArgs*) arguments;
     while (true) {
        //lock the mutexObject to this thread(obtain the object to this event)
        //=> once obtained the synchronized work(exclusive actions execute
        int isLocked = lockMutex(args->mutex);
        if (isLocked == 0) {
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

    /* fetch java env from java vm */
    JNIEnv* javaEnv;
    args->javaVM->GetEnv((void**)&(javaEnv), args->jvmArgs->version);
    
    /* attach the current thread to the jvm env */
    attachDispatcher(arguments);

    /* dispatch the job */
    while (result != 0) {
        if (args->threadType == POSIX::Threader::ASYNC) {
            // async threading -- not thread safe or for non-shared purposes.
            result = methodDispatcher(arguments);
            POSIX::forceCoolDown(RECYCLE_TIME);
        } else if (args->threadType == POSIX::Threader::MUTEX) {
            // mutex-based threading
            while (true) { 
                bool isInitialized = initSyncDispatcher(arguments);
                if (isInitialized) {
                    //start mutex here
                    result = startMutex(arguments);
                    break;
                }
            }
        }
    }

    // destroy the thread with its resources
    exitCurrentThread(NULL);

    while (isFinished != 0) {
        isFinished = detachDispatcher(*(args->javaVM));
    }

    return NULL;
}

void POSIX::Threader::dispatch() {   
    /* get javaVM from JNIEnv */
    if (this->args == NULL) {
        throw "IllegalStateException : Cannot proceed with NULL Args !";
    }
    this->args->pthread = this->dispatcher;
    dispatchThread(this->dispatcher, NULL, startDispatcher, (POSIX::Threader::DispatcherArgs*) this->args);
}