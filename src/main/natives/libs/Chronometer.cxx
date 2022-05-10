#include<Chronometer.h>

Meters::Chronometer::Chronometer() noexcept {
	this->time = new double();
	this->elapsedTime = new double();
}

const Clock* Meters::Chronometer::getCPUClock() {
		Clock* cpuClock = new Clock();
		*cpuClock = clock(); 
	return cpuClock;
}			

const double* Meters::Chronometer::toDouble(const Clock* cpuClock) {
		// the cpuClock is the number of cpu ticks or cycles and by convention : CPU_F = cycles/seconds
		// so, seconds = cycles / CPU_F.
		// and to convert it to millisecond then multiply the result by 1000. 
		*time = (double) (*cpuClock / CPU_FREQUENCY) * 1000;
		 delete cpuClock;
   return time;
}

const double* Meters::Chronometer::getElapsedTime(double* time0, double* time1) {
		*elapsedTime = *time0 - *time1;
    return elapsedTime;
}

Meters::Chronometer::~Chronometer() {
	delete (this->time);
	delete (this->elapsedTime);
}
