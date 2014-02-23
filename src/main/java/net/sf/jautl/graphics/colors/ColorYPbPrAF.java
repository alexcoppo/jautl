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
package net.sf.jautl.graphics.colors;

import net.sf.jautl.numeric.GenericFunctions;
import net.sf.jautl.numeric.LinearInterpolator;

/**
 * This class represents a color in the YPbPr space with associated opacity,
 * all values given as single precision floats.
 */
public class ColorYPbPrAF {
	/** The maximum value for the color components. */
	public static final float MAX_VALUE = 1.0f;
	private float y;
	private float pb;
	private float pr;
	private float a;

	/**
	 * Default constructor. Create an opaque black.
	 */
	public ColorYPbPrAF() {
		this(0, 0, 0, MAX_VALUE);
	}
	
	/**
	 * The constructor for solid colors.
	 * @param y the Y component
	 * @param pb the Pb component
	 * @param pr the Pr component
	 */
	public ColorYPbPrAF(float y, float pb, float pr) {
		this(y, pb, pr, MAX_VALUE);
	}
	
	/**
	 * The constructor.
	 * @param y the Y component
	 * @param pb the Pb component
	 * @param pr the Pr component
	 * @param a the Opacity component
	 */
	public ColorYPbPrAF(float y, float pb, float pr, float a) {
		setY(y);
		setPb(pb);
		setPr(pr);
		setA(a);
	}
	
	/**
	 * The copy constructor.
	 * @param rhs
	 */
	public ColorYPbPrAF(ColorYPbPrAF rhs) {
		assign(rhs);
	}
	
	/**
	 * The assignment operator.
	 * @param rhs
	 */
	public void assign(ColorYPbPrAF rhs) {
		setY(rhs.getY());
		setPb(rhs.getPb());
		setPr(rhs.getPr());
		setA(rhs.getA());
	}
	
	/**
	 * The Y component of the color.
	 * @return the value in the range [0, MAX_VALUE]
	 */
	public final float getY() {
		return y;
	}

	/**
	 * Set the Y component of the color. 
	 * @param y the value in the range [0, MAX_VALUE]
	 */
	public final void setY(float y) {
		this.y = (float)GenericFunctions.clamp(y, 0, MAX_VALUE);
	}
	
	/**
	 * The Pb component of the color.
	 * @return the value in the range [-MAX_VALUE / 2, MAX_VALUE / 2]
	 */
	public final float getPb() {
		return pb;
	}
	
	/**
	 * Set the Pb component of the color. 
	 * @param pb the value in the range [-MAX_VALUE / 2, MAX_VALUE / 2]
	 */
	public final void setPb(float pb) {
		this.pb = (float)GenericFunctions.clamp(pb, -MAX_VALUE / 2, MAX_VALUE / 2);
	}
	
	/**
	 * The Pr component of the color.
	 * @return the value in the range [-MAX_VALUE / 2, MAX_VALUE / 2]
	 */
	public final float getPr() {
		return pr;
	}
	
	/**
	 * Set the Pr component of the color. 
	 * @param pr the value in the range [-MAX_VALUE / 2, MAX_VALUE / 2]
	 */
	public final void setPr(float pr) {
		this.pr = (float)GenericFunctions.clamp(pr, -MAX_VALUE / 2, MAX_VALUE / 2);
	}
	
	/**
	 * Return the opacity value.
	 */
	public final float getA() {
		return this.a;
	}
	
	/**
	 * Set the opacity value. The value is clipped in the [0, MAX_VALUE] range. 
	 * @param a the opacity value
	 */
	public final void setA(float a) {
		this.a = (float)GenericFunctions.clamp(a, 0, MAX_VALUE);
	}

	/**
	 * Linearily interpolate a color, component-wise.
	 * @param left the color when the x value is 0
	 * @param x the fraction
	 * @param right the color when the x value is 1
	 */
	public void interpolate(ColorYPbPrAF left, double x, ColorYPbPrAF right) {
		setY ((float)LinearInterpolator.interpolate(left.getY (), x, right.getY ()));
		setPb((float)LinearInterpolator.interpolate(left.getPb(), x, right.getPb()));
		setPr((float)LinearInterpolator.interpolate(left.getPr(), x, right.getPr()));
		setA ((float)LinearInterpolator.interpolate(left.getA (), x, right.getA ()));
	}
}
