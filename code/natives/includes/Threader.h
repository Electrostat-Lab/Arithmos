#ifndef __THREADER
#define __THREADER

#include <pthread.h>
#include <jni.h>
#include <unistd.h>
#include <cstdlib>

/* redefine some types */
#define ThreadDispatcher pthread_t 
#define Mutex pthread_mutex_t 
#define MutexAttr pthread_mutexattr_t
#define Class jclass 
#define Method jmethodID 
#define Object jobject
#define Integer jint
#define String const char* 

/* redefine some methods */
#define dispatchThread pthread_create
#define join pthread_join
#define mutexInit pthread_mutex_init
#define setMutexAttrType pthread_mutexattr_settype
#define lockMutex pthread_mutex_lock
#define checkIfLocked pthread_mutex_timedlock
#define unlockMutex pthread_mutex_unlock
#define destroyMutex pthread_mutex_destroy
#define exitCurrentThread pthread_exit

/* define available Platforms */
#define Linux_x64 0xFF
#define Android_x86_x64 0x00
/* define default platform */
#define Platform Linux_x64

static int isInitialized = -1;
/* 1000 micro-sec = 1 ms */
static constexpr int RECYCLE_TIME = 1000;

namespace POSIX {
    static int coolDown(int time) {
        return usleep(time);
    }
    static void forceCoolDown(int time) {
        int remainingTime = coolDown(time);
        while(remainingTime == time);
    } 
    struct Threader {
        public:
           struct DispatcherArgs {
                public:
                    Mutex* mutex;
                    MutexAttr* mutexAttr;
                    String classPath;
                    jobject params;
                    JavaVM* javaVM;
                    JavaVMAttachArgs* jvmArgs;
                    int threadType = (int) ASYNC;
                    char* INTERFACING_METHOD;
                    char* INTERFACING_METHOD_SIG;
                    char* INTERFACE_CONSTRUCTOR_SIG;
                    Object javaDispatcherInstance;
                    Threader* instance;
                    u_int32_t delay = 0;
            };
            constexpr static int ASYNC = 123;
            constexpr static int MUTEX = 456;
            void dispatch();
            jint finish(JNIEnv* env);
        public:
            Threader(DispatcherArgs*);
            ~Threader();
        private:
            DispatcherArgs* args;
            ThreadDispatcher* dispatcher;
              
            friend bool initSyncDispatcher(void*);
            friend void attachDisptacher(void*);
            friend void startMutex(void*);
            friend void* startDispatcher(void*);
            friend void* methodDispatcher(void*);
    };
}

#endif
