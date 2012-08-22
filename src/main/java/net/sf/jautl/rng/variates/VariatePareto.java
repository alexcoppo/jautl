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
 * This class creates Pareto-distributed variates.
 */
public class VariatePareto extends VariatesBaseDoublesSource {
    private double scale;
    private double shape;

    /**
     * The constructor.
	 * @param ids an implementor of IDoublesSource interface
     */
    public VariatePareto(IDoublesSource ids) {
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
     * Return the value of the shape parameter.
     * @return
     */
    public double getShape() {
        return shape;
    }

    /**
     * Set the shape parameter.
     * @param shape the new value of the shape parameter
     */
    public void setShape(double shape) {
        this.shape = shape;
    }

    /**
     * Draw a variate.
     * @return 
     */
	public double draw() {
		return scale * Math.pow(GeneratorsDouble.generate(ids, false, true), -1 / shape);
	}
}