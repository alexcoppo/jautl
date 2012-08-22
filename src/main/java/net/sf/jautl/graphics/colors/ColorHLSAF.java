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
package net.sf.jautl.graphics.colors;

import net.sf.jautl.numeric.LinearInterpolator;

/**
 * This class represents a color in the HLS space with associated opacity,
 * all values given as single precision floats.
 */
public class ColorHLSAF {
	/** The maximum value for the color components. */
	public static final float MAX_VALUE = 1.0f;
	private float h;
	private float l;
	private float s;
	private float a;

	/**
	 * Default constructor. Create an opaque black.
	 */
	public ColorHLSAF() {
		this(0, 0, 0, MAX_VALUE);
	}

	/**
	 * The constructor for solid colors.
	 * @param h the Hue component
	 * @param l the Luminosity component
	 * @param s the Saturation component
	 */
	public ColorHLSAF(float h, float l, float s) {
		this(h, l, s, MAX_VALUE);
	}

	/**
	 * The constructor.
	 * @param h the Hue component
	 * @param l the Luminosity component
	 * @param s the Saturation component
	 * @param a the Opacity component
	 */
	public ColorHLSAF(float h, float l, float s, float a) {
		setH(h);
		setL(l);
		setS(s);
		setA(a);
	}
	
	/**
	 * The copy constructor.
	 * @param rhs
	 */
	public ColorHLSAF(ColorHLSAF rhs) {
		assign(rhs);
	}
	
	/**
	 * The assignement operator.
	 * @param rhs
	 */
	public void assign(ColorHLSAF rhs) {
		setH(rhs.getH());
		setL(rhs.getL());
		setS(rhs.getS());
		setA(rhs.getA());
	}

	/**
	 * Get the Hue component of the color.
	 * @return the value in the interval [0, MAX_VALUE)
	 */
	public final float getH() {
		return h;
	}

	/**
	 * Set the Hue component of the color. 
	 * @param h the value in the range [0, MAX_VALUE)
	 */
	public final void setH(float h) {
		this.h = clipValues(h);
	}
	
	/**
	 * Get the Luminosity component of the color.
	 * @return the value in the interval [0, MAX_VALUE]
	 */
	public final float getL() {
		return l;
	}
	
	/**
	 * Set the Luminosity component of the color. 
	 * @param l the value in the range [0, MAX_VALUE]
	 */
	public final void setL(float l) {
		this.l = clipValues(l);
	}
	
	/**
	 * Get the Saturation component of the color.
	 * @return the value in the interval [0, MAX_VALUE]
	 */
	public final float getS() {
		return s;
	}
	
	/**
	 * Set the Saturation component of the color. 
	 * @param s the value in the range [0, MAX_VALUE]
	 */
	public final void setS(float s) {
		this.s = clipValues(s);
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
		this.a = clipValues(a);
	}

	/**
	 * Linearily interpolate a color, component-wise.
	 * @param left the color when the x value is 0
	 * @param x the fraction
	 * @param right the color when the x value is 1
	 */
	public void interpolate(ColorHLSAF left, double x, ColorHLSAF right) {
		setH((float)LinearInterpolator.interpolate(left.getH(), x, right.getH()));
		setL((float)LinearInterpolator.interpolate(left.getL(), x, right.getL()));
		setS((float)LinearInterpolator.interpolate(left.getS(), x, right.getS()));
		setA((float)LinearInterpolator.interpolate(left.getA(), x, right.getA()));
	}
	
	private float clipValues(float f) {
		if (f < 0) return 0;
		if (f > 1) return 1;
		return f;
	}
}
