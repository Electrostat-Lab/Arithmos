#include <stdio.h>
#include <main_TestCase.h>
#include <Test.h>

JNIEXPORT void JNICALL Java_main_TestCase_testNatives(JNIEnv *, jclass) {
    Test::testMe();
}
