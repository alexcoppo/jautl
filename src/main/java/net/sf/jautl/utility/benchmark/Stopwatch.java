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
 * This class samples time periods.
 */
public class Stopwatch {
    private boolean running;
    private long startTime;
    private long stopTime;
    private static final double TICKS_PER_SECOND = 1e9;
    
    /**
     * Construct a stopwatch.
     */
    public Stopwatch() {
        reset();
    }

    /**
     * Reset the stopwatch to the initial state.
     */
    public final void reset() {
        running = false;
    }

    /**
     * Start the stopwatch.
     */
    public final void start() {
        if (running)
            throw new IllegalStateException();

        running = true;

        startTime = System.nanoTime();
    }

    /**
     * Stop the stopwatch and acculumate statistics.
     */
    public final void stop() {
        stopTime = System.nanoTime();

        if (!running)
            throw new IllegalStateException();

        running = false;
    }

    /**
     * Return the total accumulated time.
     * @return the total time in seconds
     */
    public final double getElapsedTime() {
        if (running)
            throw new IllegalStateException();

        return (stopTime - startTime) / TICKS_PER_SECOND;
    }

    /**
     * Estimate the platform nanoTime() API granularity.
     * @param runTime the number of seconds the test should run
     * @return the estimated timer granularity in seconds
     */
    public static double granularityEstimate(double runTime) {
        long result = Long.MAX_VALUE;
        
        long runStart = System.nanoTime();
        
        while ((System.nanoTime() - runStart) / TICKS_PER_SECOND < runTime) {
            long t0 = System.nanoTime(), t1;
            
            do
                t1 = System.nanoTime();
            while (t1 == t0);
            
            result = Math.min(result, t1 - t0);
        }
        
        return result / TICKS_PER_SECOND;
    }
}