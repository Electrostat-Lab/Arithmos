#include<Threader.h>

POSIX::Threader::Threader() {
    this->dispatcher = new ThreadDispatcher();
}

POSIX::Threader::~Threader() {
    this->dispatcher = NULL;
    delete this->dispatcher;
}

void* initDispatcher(void* arguments) {
    POSIX::Threader::DispatcherArgs* args = (POSIX::Threader::DispatcherArgs*) arguments;
    
    /* setup jvm thread attach args info */
    JavaVMAttachArgs jvmArgs;
    jvmArgs.name = (char*) args->methodName; 
    jvmArgs.group = NULL;
    int isAttached = -1;
    int isFinished = -1;
    /* attach the current thread to the Java VM based on the platform */
    do {
        #if (Platform == Linux_x64)
            jvmArgs.version = JNI_VERSION_1_8; 
            isAttached = args->javaVM->AttachCurrentThread((void**)&(args->javaEnv), &jvmArgs);
        #elif (Platform == Android_x86_x64)
            jvmArgs.version = JNI_VERSION_1_6;
            isAttached = args->javaVM->AttachCurrentThread(&(args->javaEnv), &jvmArgs);
        #endif
    /* wait until the thread is attached to the jvm then start invoking the java funtions */
    } while(isAttached == -1);
    
    /* get the method from a java class by its name, using the classPath and the method signature */
    Class clazz = args->javaEnv->FindClass(args->classRelativePath);
    Method method = NULL;
    
    if (clazz != NULL) {
        method =  args->javaEnv->GetMethodID(clazz, args->methodName, args->methodSignature);
    } else {
        printf("Cannot find the class\n");
        args->javaEnv->ExceptionDescribe();
        return 0;
    }
    if (method != NULL) {
        args->javaEnv->CallStaticVoidMethod(clazz, method);
    } else {
        printf("Cannot find the method \n");
        args->javaEnv->ExceptionDescribe();
    }
    
    do {
        isFinished = args->javaVM->DetachCurrentThread();
    } while(isFinished == -1);

    args->javaVM->DestroyJavaVM();
    return (void*) &isFinished;
}

void POSIX::Threader::dispatch(DispatcherArgs& args) {    
    /* get javaVM from JNIEnv */
    if (args.javaEnv->GetJavaVM(&(args.javaVM)) != -1) {
        dispatchThread(dispatcher, NULL, initDispatcher, (POSIX::Threader::DispatcherArgs*) &args);
    }
}

jint POSIX::Threader::finish(JNIEnv* env) {
    JavaVM* jvm;
    env->GetJavaVM(&jvm);
    jint result = jvm->DetachCurrentThread();
    jvm->DestroyJavaVM();
    pthread_detach(pthread_self());
    pthread_exit(NULL);
    return result;
}
