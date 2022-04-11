#include<pthread_ThreadDispatcher.h>
#include<Threader.h>
#include<cstdlib>

extern "C" {


    Class getClassObject(JNIEnv* env, jobject modelObj, const char* fieldName);
    jobject getParamsObject(JNIEnv* env, jobject modelObj, const char* fieldName);
    jstring getString(JNIEnv* env, jobject modelObj, const char* fieldName);
    jint getInt(JNIEnv* env, jobject modelObj, const char* fieldName);

    Mutex* mutex = new Mutex();
    MutexAttr* mutexAttr = new MutexAttr();

    JNIEXPORT void JNICALL Java_pthread_ThreadDispatcher_dispatch
    (JNIEnv *env, jobject javaDispatcherInstance, jobject modelObj){
        /* implementation of pthreads wrapper for java applications */
        POSIX::Threader::DispatcherArgs* args = new POSIX::Threader::DispatcherArgs();
        args->javaDispatcherInstance = javaDispatcherInstance;
        args->params = getParamsObject(env, modelObj, "parameterList");
        args->classPath = env->GetStringUTFChars(getString(env, modelObj, "classPath"), 0);
        args->threadType = getInt(env, javaDispatcherInstance, "OPERATION_MODE");
        args->mutex = mutex;
        args->mutexAttr = mutexAttr;
        args->delay = getInt(env, modelObj, "delay");
        args->INTERFACING_METHOD = (char*) "invoke";
        args->INTERFACING_METHOD_SIG = (char*) "(Lpthread/ThreadDispatcher;)V";
        args->INTERFACE_CONSTRUCTOR_SIG = (char*) "(Lpthread/model/ParameterList;)V";

        env->GetJavaVM(&(args->javaVM));

        JavaVMAttachArgs* jvmArgs = new JavaVMAttachArgs();
        jvmArgs->name = (char*) args->INTERFACING_METHOD;
        jvmArgs->group = NULL;

        #if (Platform == Linux_x64)
            jvmArgs->version = JNI_VERSION_1_8; 
        #elif (Platform == Android_x86_x64)
            jvmArgs->version = JNI_VERSION_1_6;
        #endif
        args->jvmArgs = jvmArgs;

        POSIX::Threader* threader = new POSIX::Threader(args);
        threader->dispatch();
        POSIX::forceCoolDown(RECYCLE_TIME);
    }

    JNIEXPORT jboolean JNICALL Java_pthread_ThreadDispatcher_finish
    (JNIEnv *env, jobject object){  
        int isFinished = destroyMutex(mutex);  
        delete mutex;
        delete mutexAttr;

        return isFinished == 0;
    }

    Class getClassObject(JNIEnv* env, jobject modelObj, const char* fieldName) {
        jclass modelClass = env->GetObjectClass(modelObj);
        jfieldID field = env->GetFieldID(modelClass, fieldName, "Ljava/lang/Class;");
        Class clazz = (Class) env->GetObjectField(modelObj, field);
        return clazz;
    }

    jobject getParamsObject(JNIEnv* env, jobject modelObj, const char* fieldName) {
        jclass modelClass = env->GetObjectClass(modelObj);
        jfieldID field = env->GetFieldID(modelClass, fieldName, "Lpthread/model/ParameterList;");
        jobject object = env->GetObjectField(modelObj, field);
        return object;
    }

    jstring getString(JNIEnv* env, jobject modelObj, const char* fieldName) {
        jclass modelClass = env->GetObjectClass(modelObj);
        jfieldID stringField = env->GetFieldID(modelClass, fieldName, "Ljava/lang/String;");
        jstring value = (jstring) env->GetObjectField(modelObj, stringField);
        return value;
    }

    jint getInt(JNIEnv* env, jobject object, const char* fieldName) {
        jclass modelClass = env->GetObjectClass(object);
        jfieldID intField = env->GetFieldID(modelClass, fieldName, "I");
        jint value = env->GetIntField(object, intField);
        return value;
    }
}
