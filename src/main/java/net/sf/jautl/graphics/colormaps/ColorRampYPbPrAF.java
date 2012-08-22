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
package net.sf.jautl.graphics.colormaps;

import net.sf.jautl.graphics.colormaps.ColorRamp;
import net.sf.jautl.graphics.colors.ColorConversions;
import net.sf.jautl.graphics.colors.ColorRGBAF;
import net.sf.jautl.graphics.colors.ColorYPbPrAF;
import net.sf.jautl.numeric.easefunctions.EaseFunction;

/**
 * This class describes a color ramp performed in the YPbPr color space.
 */
public class ColorRampYPbPrAF extends ColorRamp {
	private ColorYPbPrAF left;
	private ColorYPbPrAF right;
	private ColorYPbPrAF tmp;

    /**
	 * The constructor.
	 * @param gamma the value of the Gamma Correction to apply to colors
	 * @param ef the EaseFunctor to process lookup position
	 */
	protected ColorRampYPbPrAF(double gamma, EaseFunction ef) {
		super(gamma, ef);

        this.left = new ColorYPbPrAF();
        this.right = new ColorYPbPrAF();
        this.tmp = new ColorYPbPrAF();
	}
    
	public ColorRampYPbPrAF(ColorYPbPrAF left, ColorYPbPrAF right, double gamma, EaseFunction ef) {
        this(gamma, ef);
        
        this.left.assign(left);
        this.right.assign(right);
    }

	public ColorRampYPbPrAF(ColorRGBAF left, ColorRGBAF right, double gamma, EaseFunction ef) {
		this(gamma, ef);

        ColorConversions.convert601(this.left, left);
        ColorConversions.convert601(this.right, right);
    }
    
	@Override
	protected void lookupImplAux(double x, ColorRGBAF color) {
		if (ef != null) x = ef.calc(x);
		tmp.interpolate(left, x, right);
		ColorConversions.convert601(color, tmp);
	}
}
