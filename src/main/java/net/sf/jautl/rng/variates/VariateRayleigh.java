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
 * This class creates Rayleigh distributed variates.
 */
public class VariateRayleigh extends VariatesBaseDoublesSource {
    private double scale;

    /**
     * The constructor.
	 * @param ids an implementor of IDoublesSource interface
     */
    public VariateRayleigh(IDoublesSource ids) {
    	super(ids);
    }

    /**
     * Return the value of the scale parameter.
     * @return
     */
    public double getScale() {
        return scale;
    }

    /**
     * Set the scale parameter.
     * @param scale the new value of the scale parameter
     */
    public void setScale(double scale) {
        this.scale = scale;
    }

    /**
     * Draw a variate.
     * @return 
     */
	public double draw() {
		//a subset of normal generation using polar method
		double s;
		double x;
		double y;

		do {
			x = 2 * GeneratorsDouble.generate(ids, true, true) - 1;
			y = 2 * GeneratorsDouble.generate(ids, true, true) - 1;
			s = x * x + y * y;
		} while ((s > 1) || (s == 0));

		return scale * Math.sqrt(-2 * Math.log(s) / s);
	}
}