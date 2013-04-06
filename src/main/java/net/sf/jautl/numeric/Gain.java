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
 * This class implements the Gain computer graphics function.
 * The describing paper is Fast Alternatives to Perlin's Bias and Gain Functions,
 * Christophe Schlick , Graphic Gems vol 4.
 */
public class Gain implements UnaryFunction<Double, Double> {
    private Bias b;

    /**
     * The constructor.
     */
    public Gain() {
        this.b = new Bias();
    }

    /**
     * The constructor.
     * @param a the value of the A parameter
     */
    public Gain(double a) {
        b = new Bias(a);
    }

    /**
     * Set the new value of the A parameter
     * @param a the new value
     */
    public void setA(double a) {
        b.setA(a);
    }

    /**
     * Return the value of the A parameter
     * @return
     */
    public double getA() {
        return b.getA();
    }

    /**
     * Compute the function
     * @param x the value
     * @return 
     */
    public double call(double x) {
        if (x < .5)
            return b.call(2 * x) / 2;
        else
            return 1 - b.call(2 * (1 - x)) / 2;
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
