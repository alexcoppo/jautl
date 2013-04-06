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
package net.sf.jautl.rng.variates;

import net.sf.jautl.rng.interfaces.IDoublesSource;

/**
 * This class creates normaly distributed variates.
 */
public class VariateNormal extends VariatesBaseDoublesSource {
    private VariateUnitCircle vuc;
	private boolean isAvailable = false;
	private double savedZ;
    private double average;
    private double standardDeviation;

    /**
     * The constructor.
	 * @param ids an implementor of IDoublesSource interface
     */
	public VariateNormal(IDoublesSource ids) {
		super(ids);
        vuc = new VariateUnitCircle(ids);
	}

    /**
     * Return the value of the average parameter.
     * @return
     */
    public double getAverage() {
        return average;
    }

    /**
     * Set the average parameter.
     * @param average the new value of the average parameter
     */
    public void setAverage(double average) {
        this.average = average;
    }

    /**
     * Return the value of the standardDeviation parameter.
     * @return
     */
    public double getStandardDeviation() {
        return standardDeviation;
    }

    /**
     * Set the standardDeviation parameter.
     * @param standardDeviation the new value of the standardDeviation parameter
     */
    public void setStandardDeviation(double standardDeviation) {
        this.standardDeviation = standardDeviation;
    }

    /**
     * Draw a variate.
     * @return 
     */
	public double draw() {
    	if (isAvailable) {
	    	//if Z saved, use it
        	isAvailable = false;
	        return average + savedZ * standardDeviation;
		} else {
		    //otherwise generate a pair, returning one and saving the other
            vuc.compute(true, false);
		
        	double s = vuc.getR2();
            s = Math.sqrt(-2 * Math.log(s) / s);
            
	        savedZ = vuc.getX() * s;
    	    isAvailable = true;

        	return average + (vuc.getY() * s) * standardDeviation;
		}
	}
}