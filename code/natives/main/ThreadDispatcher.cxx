#include<pthread_ThreadDispatcher.h>
#include<Threader.h>
#include<cstdlib>

extern "C" {
    Class getClassObject(JNIEnv* env, jobject modelObj, const char* fieldName);
    jobject getParamsObject(JNIEnv* env, jobject modelObj, const char* fieldName);
    jstring getString(JNIEnv* env, jobject modelObj, const char* fieldName);
    jint convertInt(JNIEnv* env, jobject modelObj, const char* fieldName);

    Mutex* mutex = new Mutex();
    MutexAttr* mutexAttr = new MutexAttr();

    JNIEXPORT void JNICALL Java_pthread_ThreadDispatcher_dispatch
    (JNIEnv *env, jclass invoker, jobject modelObj){
        /* implementation of pthreads wrapper for java applications */
        POSIX::Threader::DispatcherArgs* args = new POSIX::Threader::DispatcherArgs();
        args->javaEnv = env;
        args->params = getParamsObject(env, modelObj, "parameterList");
        args->classPath = env->GetStringUTFChars(getString(env, modelObj, "classPath"), 0);
        args->threadType = POSIX::Threader::SYNC;
        args->mutex = mutex;
        args->mutexAttr = mutexAttr;

        POSIX::Threader* threader = new POSIX::Threader(args);
        threader->dispatch(); 
    }

    JNIEXPORT jint JNICALL Java_pthread_ThreadDispatcher_finish
    (JNIEnv *env, jclass _class){
        int isFinished = -1;
    
        JavaVM* javaVm;
        env->GetJavaVM(&javaVm);
        isFinished = javaVm->DetachCurrentThread();
        javaVm->DestroyJavaVM();
        destroyMutex(mutex);

        return isFinished;
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

    jint convertInt(JNIEnv* env, jobject modelObj, const char* fieldName) {
        jclass modelClass = env->GetObjectClass(modelObj);
        jfieldID intField = env->GetFieldID(modelClass, fieldName, "I");
        jint value = env->GetIntField(modelObj, intField);
        return value;
    }
}
