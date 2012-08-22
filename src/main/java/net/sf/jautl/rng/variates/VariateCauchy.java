/*
    Copyright (c) 2000-2012 Alessandro Coppo
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
package net.sf.jautl.rng.variates;

import net.sf.jautl.rng.interfaces.GeneratorsDouble;
import net.sf.jautl.rng.interfaces.IDoublesSource;

/**
 * This class creates Cauchy distributed variates.
 */
public class VariateCauchy extends VariatesBaseDoublesSource {
    private double median;
    private double quartile;

    /**
     * The constructor.
	 * @param ids an implementor of IDoublesSource interface
     */
    public VariateCauchy(IDoublesSource ids) {
    	super(ids);
    }

    /**
     * Return the value of the median parameter.
     * @return
     */
    public double getMedian() {
        return median;
    }

    /**
     * Set the median parameter.
     * @param median the new value of the median parameter
     */
    public void setMedian(double median) {
        this.median = median;
    }

    /**
     * Return the value of the quartile parameter.
     * @return
     */
    public double getQuartile() {
        return quartile;
    }

    /**
     * Set the quartile parameter.
     * @param quartile the new value of the quartile parameter
     */
    public void setQuartile(double quartile) {
        this.quartile = quartile;
    }

    /**
     * Draw a variate.
     * @return 
     */
	public double draw() {
	    double x;
		double y;
    
    	//Compute, using the rejection method, two numbers in [-1,1],[0,1]
	    //so that their distance from the origin is <= 1 and their argument
    	//is uniformly distributed in [0,pi]. X must be non-zero to prevent
		//divisions by zero
    	do {
        	x = 2 * GeneratorsDouble.generate(ids, true, true) - 1;
	        y = GeneratorsDouble.generate(ids, true, true);
		} while (x * x + y * y > 1 && x != 0);

	    //The ratio Y/X is the tangent of the above mentioned argument.
    	return median + quartile * y / x;
	}
}