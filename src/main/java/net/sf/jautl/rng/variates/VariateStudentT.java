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

import net.sf.jautl.rng.interfaces.IDoublesSource;

/**
 * This class creates Student-t distributed variates.
 */
public class VariateStudentT extends VariatesBaseDoublesSource {
    private double a;
    private VariateUnitCircle vuc;

    /**
     * The constructor.
	 * @param ids an implementor of IDoublesSource interface
     */
    public VariateStudentT(IDoublesSource ids) {
    	super(ids);
    	vuc = new VariateUnitCircle(ids);
    }

    /**
     * Return the value of the A parameter.
     * @return
     */
    public double getA() {
        return a;
    }

    /**
     * Set the A parameter.
     * @param a the new value of the A parameter
     */
    public void setA(double a) {
        this.a = a;
    }

    /**
     * Draw a variate.
     * @return 
     */
	public double draw() {
        vuc.compute(true, true);
 
        return vuc.getX() * Math.sqrt(a * (Math.exp(-2 / a * Math.log(vuc.getR2())) - 1) / vuc.getR2());
	}
}