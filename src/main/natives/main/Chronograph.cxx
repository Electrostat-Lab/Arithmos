#include<utils_Chronograph.h>
#include<Chronometer.h>

extern "C" {
	Meters::Chronometer chronograph;
	JNIEXPORT jdouble JNICALL Java_utils_Chronograph_getCPUClock(JNIEnv* env, jobject object) {
		double cpuClockTime = *(chronograph.Meters::Chronometer::toDouble(chronograph.getCPUClock()));
		return cpuClockTime;
	}

	JNIEXPORT jdouble JNICALL Java_utils_Chronograph_getElapsedTime(JNIEnv* env, jobject object, jdouble time0, jdouble time1) {
		double elapsedTime = *(chronograph.Meters::Chronometer::getElapsedTime(&time0, &time1));
		return elapsedTime;
	}

}
