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
package net.sf.jautl.numeric;

import net.sf.jautl.utility.functional.UnaryFunction;

/**
 * This class implements the Bias computer graphics function.
 * The describing paper is Fast Alternatives to Perlin's Bias
 * and Gain Functions, Christophe Schlick , Graphic Gems vol 4.
 */
public class Bias implements UnaryFunction<Double, Double> {
    private double K;

    /**
     * The constructor.
	 * Sets the A parameter to .5.
	 */
	public Bias() {
        setA(.5);
	}

	/**
	 * The constructor.
	 * @param A the value of the A parameter
	 */
	public Bias(double A) {
        setA(A);
	}

    /**
     * Set the new value of the A parameter
     * @param a the new value
     */
    public final void setA(double a) {
    	K = 1 / a - 2;
    }
    
    /**
     * Return the value of the A parameter
     * @return
     */
    public final double getA() {
    	return 1 / (K + 2);
    }

    /**
     * Compute the function
     * @param x the value
     * @return 
     */
	public final double call(double x) {
    	return x / (K * (1 - x) + 1);
    }

    /**
     * Wrapper to adapt to the UnaryFunction<> interface.
     * @param _arg0 the argument of the function
     * @return 
     */
	public final Double fn(Double _arg0) {
		return new Double(call(_arg0));
	}
}
