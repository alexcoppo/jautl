/*
    Copyright (c) 2000-2014 Alessandro Coppo
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
package net.sf.jautl.numeric.statistics;

import java.lang.reflect.Array;

import net.sf.jautl.numeric.DoubleRange;

public class Histogram1D {
    DoubleRange range;
    private int[] histogram;
    private int binsCount;
    private int underflows;
    private int overflows;
    private int total;
    private int maxHits;

    private int[] cumulative;
    
    public Histogram1D(double min, double max, int binsCount) {
    	this.range = new DoubleRange(min, max);
        this.histogram = new int[binsCount];
        this.binsCount = binsCount;
        
        clear();
    }

    public int getBinCount() {
        return binsCount;
    }

    public int getTotal() {
        return total;
    }

    public int getOverflows() {
        return overflows;
    }

    public int getUnderflows() {
        return underflows;
    }

    public int getBinHits(int index) {
        return histogram[index];
    }

    public int getMaxBinHits() {
        return maxHits;
    }

    public void clear() {
    	Array.set(histogram, 0, 0);
        
    	underflows = 0;
        overflows = 0;
        total = 0;
        maxHits = 0;
    }

    public void add(double value) {
        if (range.isInside(value)) {
        	int slot = bucketize(value);
        	
            histogram[slot]++;
            maxHits = Math.max(maxHits, histogram[slot]);
        } else {
            if (value < range.getMinimum())
                underflows++;
            else
                overflows++;
        }

        total++;
    }

    public double getMinimum() {
        return range.getMinimum();
    }

    public double getMaximum() {
        return range.getMaximum();
    }

    public int getHitsAt(double x) {
        if (range.isInside(x)) {
        	int slot = bucketize(x);
            return histogram[slot];
        } else
            return 0;
    }
    
    public void computeCumulative() {
		int runningTotal = 0;

		for (int bucket = 0; bucket < binsCount; bucket++) {
			runningTotal += histogram[bucket];
			cumulative[bucket] = runningTotal;
		}
    }
    
    public int getCumulativeAt(int index) {
    	return cumulative[index];
    }
    
    public double getCumulativeFractionAt(int index) {
    	return (double)cumulative[index] / total;
    }
    
    private int bucketize(double value) {
    	return (int)(.999999 * value * binsCount);
    }
}
