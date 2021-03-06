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
package net.sf.jautl.graphics.colormaps.greys;

import net.sf.jautl.graphics.colormaps.ColorMapRGBAF;
import net.sf.jautl.graphics.colors.ColorRGBAF;
import net.sf.jautl.graphics.colors.GammaCorrection;

/**
 * This class implements a step-wise black to white colormap with two color
 * bands for low and high values.
 */
public class GreyStepsTagged extends ColorMapRGBAF {
    private double gamma;
    private int steps;
    private ColorRGBAF lo;
    private ColorRGBAF hi;
    
    /**
     * The constructor. The low values are encoded in Red, the high ones in Blue.
     * @param gamma the value of the Gamma correction to apply to color values
     * @param steps the number of the steps of the ramp
     */
    public GreyStepsTagged(double gamma, int steps) {
    	this(gamma, steps, new ColorRGBAF(1f, 0f, 0f), new ColorRGBAF(0f, 0f, 1f));
    }

    /**
     * The constructor.
     * @param gamma the value of the Gamma correction to apply to color values
     * @param steps the number of the steps of the ramp
     * @param lo the color to encode the low values
     * @param hi the color to encode the high values
     */
    public GreyStepsTagged(double gamma, int steps, ColorRGBAF lo, ColorRGBAF hi) {
        this.gamma = gamma;
        this.steps = steps;
        this.lo = new ColorRGBAF(lo);
        this.hi = new ColorRGBAF(hi);
    }

    @Override
	protected void lookupImpl(double x, ColorRGBAF color) {
    	int step = (int)(.999 * steps * x);
    	
    	if (step == 0)
    		color.assign(lo);
    	else if (step == steps - 1)
    		color.assign(hi);
    	else {
	        x = step / (double)(steps - 1);
	        
	        color.setR((float)x);
	        color.setG((float)x);
	        color.setB((float)x);
	        color.setA(1);
	        
	        GammaCorrection.linearToScreen(color, gamma);
    	}
	}
}
