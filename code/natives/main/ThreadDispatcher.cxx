#include<pthread_ThreadDispatcher.h>
#include<Threader.h>
#include<cstdlib>
extern "C" {
    jstring convertString(JNIEnv* env, jobject modelObj, const char* fieldName);
    jint convertInt(JNIEnv* env, jobject modelObj, const char* fieldName);
    /* implementation of pthreads wrapper for java applications */
    POSIX::Threader* threader = new POSIX::Threader();

    JNIEXPORT void JNICALL Java_pthread_ThreadDispatcher_dispatch
    (JNIEnv *env, jclass invoker, jobject modelObj){
        
        POSIX::Threader::DispatcherArgs* args = new POSIX::Threader::DispatcherArgs();
        args->javaEnv = env;
        args->classRelativePath = env->GetStringUTFChars(convertString(env, modelObj, "classRelativePath"), 0);
        args->methodName = env->GetStringUTFChars(convertString(env, modelObj, "methodName"), 0);
        args->methodSignature = env->GetStringUTFChars(convertString(env, modelObj, "methodSignature"), 0);
        args->methodId = convertInt(env, modelObj, "methodId");
        threader->dispatch(*args); 
    }

    JNIEXPORT jint JNICALL Java_pthread_ThreadDispatcher_finish
    (JNIEnv *env, jclass _class){
        return threader->finish(env);
    }

    jstring convertString(JNIEnv* env, jobject modelObj, const char* fieldName) {
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
