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
package net.sf.jautl.numeric.easefunctions;

/**
 * This class is the base of all ease functions.
 */
public abstract class EaseFunction {
    private double xmin;
    private double xmax;
    private double FXmin;
    private double FXmax;

    /**
     * Set the minimum abscissa.
     * @param xmin the minimum abscissa
     */
	public void setXmin(double xmin) {
		this.xmin = xmin;
	}

	/**
     * Return the minimum abscissa.
     */
    public double getXmin() {
		return xmin;
	}

    /**
     * Set the maximum abscissa.
     * @param xmax the maximum abscissa
     */
	public void setXmax(double xmax) {
		this.xmax = xmax;
	}

    /**
     * Return the maximum abscissa.
     */
	public double getXmax() {
		return xmax;
	}

    /**
     * Set the value of the function at xmin.
     * @param fXmin the value of the function at xmin
     */
	public void setFXmin(double fXmin) {
		FXmin = fXmin;
	}

	/**
     * Return the value of the function at xmin.
     */
	public double getFXmin() {
		return FXmin;
	}

    /**
     * Set the value of the function at xmax.
     * @param fXmax the value of the function at xmax
     */
	public void setFXmax(double fXmax) {
		FXmax = fXmax;
	}

    /**
     * Return the value of the function at xmax.
     */
	public double getFXmax() {
		return FXmax;
	}

    /**
     * Compute a functor at a given abscissa.
     * @param x the abscissa where compute the function
     * @return the value of the functor
     */
    public double calc(double x) {
    	double frac;
    	
        if (x <= xmin)
        	frac = 0;
        else if (x >= xmax)
        	frac = 1;
        else
        	frac = (x - xmin) / (xmax - xmin);
    	
    	frac = calcFrac(frac);

    	return frac * (FXmax - FXmin) + FXmin;
    }

    /**
     * Compute the fractional value of the ease function.
     * @param frac the fraction where to compute the value
     * @return the fractional value of the ease function
     */
    protected abstract double calcFrac(double frac);
}
