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
package net.sf.jautl.rng.variates;

import net.sf.jautl.rng.interfaces.GeneratorsDouble;
import net.sf.jautl.rng.interfaces.IDoublesSource;

/** 
 * This class creates GumbelII distributed variates.
 */
public class VariateGumbelII extends VariatesBaseDoublesSource {
    private double base;
    private double scale;

    /**
     * The constructor.
	 * @param ids an implementor of IDoublesSource interface
     */
    public VariateGumbelII(IDoublesSource ids) {
    	super(ids);
    }

    /**
     * Return the value of the base parameter.
     * @return
     */
    public double getBase() {
        return base;
    }

    /**
     * Set the base parameter.
     * @param base the new value of the base parameter
     */
    public void setBase(double base) {
        this.base = base;
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
        return Math.pow(-scale / Math.log(GeneratorsDouble.generate(ids, false, false)), 1 / scale);
    }
}