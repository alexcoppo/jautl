/*
    Copyright (c) 2000-2013 Alessandro Coppo
    All rights reserved.

    Redistribution and use in source and binary forms, with or without
    modification, are permitted provided that the following conditions
    are met:
    1. Redistributions of source code must retain the above copyright
       notice, this list of conditions and the following disclaimer.
    2. Redistributions in binary form must reproduce the above copyright
       notice, this list of conditions and the following disclaimer in the
       documentation and/or other materials provided with the distribution.
    3. The name of the author may not be used to endorse or promote products
       derived from this software without specific prior written permission.

    THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
    IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
    OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
    IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
    INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
    NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
    DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
    THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
    (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
    THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package net.sf.jautl.utility.benchmark;

/**
 * Benchmarking engine. The code has been inspired by the benchmarking
 * machinery of SciMark2 benchmark suite.
 */
public class Benchmarker {
    /**
     * Estimate the number of loops to be performed to have the benchmark last
     * a minimum given time. Estimation takes about the double of the minRunTime
     * requested.
     * @param b the Benchmarkable instance to time
     * @param minRunTime the minimum benchmark run time in seconds.
     * @return the estimated number of loops to cover at least
     * the required minRunTime
     */
    public static long estimateLoopCount(Benchmarkable b, double minRunTime) {
    	long loops = 1;
        
        for (;;) {
            double elapsedTime = benchmark(b, loops);
            if (elapsedTime >= minRunTime)
                break;
            else
                loops *= 2;
        }

        return loops;
    }

    /**
     * Perform the benchmark.
     * @param b the Benchmarkable instance to time
     * @param loops the number of iterations for the benchmark.
     * @return the total benchmark elapsed time
     */
    public static double benchmark(Benchmarkable b, long loops) {
        Stopwatch stopwatch = new Stopwatch();
        stopwatch.reset();
        b.setup();
        
        stopwatch.start();
        for (long index = 0; index < loops; index++)
            b.execute();
        stopwatch.stop();
        
        b.cleanup();
        
        return stopwatch.getElapsedTime();
    }

    /**
     * Perform automatically a sequence of timings.
     * @param b the Benchmarkable instance to time
     * @param loops the number of iterations for the benchmark
     * @param timings the vector where put the results
     * @param base the first vector index to be populated with timings
     * @param count the number of timings to log
     */
    public static void benchmark(Benchmarkable b, long loops, double[] timings, int base, int count) {
        for (int index = 0; index < count; index++)
            timings[index + base] = benchmark(b, loops);
    }
}