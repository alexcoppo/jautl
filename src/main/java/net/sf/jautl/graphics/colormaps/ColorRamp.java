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
package net.sf.jautl.graphics.colormaps;

import net.sf.jautl.graphics.colors.ColorRGBAF;
import net.sf.jautl.graphics.colors.GammaCorrection;
import net.sf.jautl.numeric.easefunctions.EaseFunction;

/**
 * A generic ramp of colors.
 */
public abstract class ColorRamp extends ColorMapRGBAF {
	/** The ease functor to map ramp positon values. */
    protected EaseFunction ef;
    private double gamma;

    /**
     * The constructor.
     * @param gamma the value of the Gamma correction to apply to color values
     * @param ef the EaseFunctor to use to map the position of the color within
     * the ramp into the logical ramp value. If null, the mapping is 1-to-1.
     */
    protected ColorRamp(double gamma, EaseFunction ef) {
        this.gamma = gamma;
        this.ef = ef;
    }
    
	@Override
	protected void lookupImpl(double x, ColorRGBAF color) {
        if (ef != null)
            lookupImplAux(ef.calc(x), color);
        else
        	lookupImplAux(x, color);

        GammaCorrection.linearToScreen(color, gamma);
	}

	/**
	 * The actual lookup method.
	 * @param x the index of the color
	 * @param color the corresponding color
	 */
    protected abstract void lookupImplAux(double x, ColorRGBAF color);
}
