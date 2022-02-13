#ifndef __THREADER
#define __THREADER

#include<pthread.h>
#include<jni.h>
#include <cstdlib>

#define ThreadDispatcher pthread_t 
#define dispatchThread pthread_create

#define Class jclass 
#define Method jmethodID 
#define String const char* 

#define Linux_x64 0xFF
#define Android_x86_x64 0x00
/* define default platform */
#define Platform Android_x86_x64


namespace POSIX {
    struct Threader {
        private:
            ThreadDispatcher* dispatcher;
        public:
            
           struct DispatcherArgs {
                public:
                    String classRelativePath;
                    String methodName;
                    int methodId;
                    String methodSignature;
                    JNIEnv* javaEnv;
                    JavaVM* javaVM;
            };
            Threader();
            ~Threader();
            void dispatch(DispatcherArgs& args);
            jint finish(JNIEnv* env);
            friend void* initDispatcher(void*);
    };
}


#endif
