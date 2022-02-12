/**
 * @file Chronometer.h
 * @author pavl_g.
 * @brief Calcualtes the elapsed time for a specific algorithm using cpu clocks.
 * @version 0.1
 * @date 2022-02-12
 * 
 * @copyright Copyright (c) Scrappers.
 * 
 */
#ifndef CHRONOGRAPH 

#define CHRONOGRAPH 

#include<ctime>

#define Clock clock_t

#define CPU_FREQUENCY CLOCKS_PER_SEC 

namespace Meters { 
	/**
	 * @brief Calcualtes the elapsed time using the cpu clocks and returns it in milliseconds.
	 * 
	 */
	struct Chronometer {
 		private:
			double* time;
			double* elapsedTime;
		public:
			Chronometer() noexcept;
			~Chronometer();
			/**
			 * @brief Get the current total cpu clock ticks since application start.
			 * 
			 * @return const Clock* a memory location pointing at the current cpu ticks.
			 */
			const Clock* getCPUClock();
			/**
			 * @brief Converts the cpu clocks to doubles.
			 * 
			 * @param cpuClock the cpuclock memory address.
			 * @return const double* a memory address to the clocks in milliseconds.
			 */
			const double* toDouble(const Clock* cpuClock);
			/**
			 * @brief Gets the Elapsed Time which is the difference between time1 and time0.
			 * 
			 * @param time0 the first time.
			 * @param time1 the second time.
			 * @return const double* a memory address to the elapsed time between time0 and time1.
			 */
			const double* getElapsedTime(double* time0, double* time1);
	};
}

#endif
