#include<Threader.h>

POSIX::Threader::Threader(DispatcherArgs* args) {
    this->args = args;
    this->dispatcher = new ThreadDispatcher();
}

void POSIX::Threader::destroy() {
    this->dispatcher = NULL;
    delete this->dispatcher;
    
    this->args = NULL;
    delete this->args;
}

void attachDispatcher(POSIX::Threader::DispatcherArgs* args) {
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

void detachDispatcher(JavaVM& vm) {
    int isFinished = -1;
    int isDestroyed = -1;

    while(isFinished != 0) {
        isFinished = vm.DetachCurrentThread();
    }
    while (isDestroyed != 0) {
        isDestroyed = vm.DestroyJavaVM();
    }
}

void initMutexOnce(POSIX::Threader::DispatcherArgs* args) {
    // 0 -> maps a success, non-zero maps a failure (bigger than zero in NPTL API)
    while (isInitialized != 0) {
        isInitialized = mutexInit(args->mutex, args->mutexAttr);
        setMutexAttrType(args->mutexAttr, PTHREAD_MUTEX_DEFAULT);
    }
}

void lock(Mutex* mutex) {
    int isLocked = -1;
    while (isLocked != 0) {
        isLocked = lockMutex(mutex);                
    }
}

void unlock(Mutex* mutex) {
    int isUnlocked = -1;
    while (isUnlocked != 0) {
        isUnlocked = unlockMutex(mutex);                
    }
}

void* methodDispatcher(void* arguments) {

    /* lateinit vars */
    Class clazz = NULL;
    Object params = NULL;
    Method constructor = NULL;
    Method method = NULL;
    Object object = NULL;
    
    int isFinished = -1;

    /* local pointers*/
    POSIX::Threader::DispatcherArgs* args = (POSIX::Threader::DispatcherArgs*) arguments;

    /* attach the current thread to the jvm env */
    attachDispatcher(args);

    /* protect a critical section using mutexes */
    if (args->threadType == POSIX::Threader::MUTEX) {
        /* initialize mutex only once */
        initMutexOnce(args);
        /* mutex owned by the current monitor thread */
        lock(args->mutex);
    }

    /* fetch java env from java vm */
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
        /* delay the dispatcher */
        POSIX::forceCoolDown(args->delay);
        /* reflectively execute the java method */
        javaEnv->CallVoidMethod(object, method, args->javaDispatcherInstance);
    } else {
        javaEnv->ExceptionDescribe();
    }

    if (args->threadType == POSIX::Threader::MUTEX) {
        /* unlock the mutex Object for the other threads*/
        unlockMutex(args->mutex);
    }

    // destroy the thread with its resources
    exitCurrentThread(NULL);

    detachDispatcher(*(args->javaVM));
    
    args->threader->destroy();

    return NULL;
}


void POSIX::Threader::dispatch() {   
    /* get javaVM from JNIEnv */
    if (this->args == NULL) {
        throw "IllegalStateException : Cannot proceed with NULL Args !";
    }
    this->args->threader = this;
    dispatchThread(this->dispatcher, NULL, methodDispatcher, (POSIX::Threader::DispatcherArgs*) this->args);
}