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
 * This class represents a color in the HSV space with associated opacity,
 * all values given as single precision floats.
 */
public class ColorHSVAF {
	/** The maximum value for the color components. */
	public static final float MAX_VALUE = 1.0f;
	private float h;
	private float s;
	private float v;
	private float a;

	/**
	 * Default constructor. Create an opaque black.
	 */
	public ColorHSVAF() {
		this(0, 0, 0, MAX_VALUE);
	}
	
	/**
	 * The constructor for solid colors.
	 * @param h the Hue component
	 * @param s the Saturation component
	 * @param v the Value component
	 */
	public ColorHSVAF(float h, float s, float v) {
		this(h, s, v, MAX_VALUE);
	}
	
	/**
	 * The constructor.
	 * @param h the Hue component
	 * @param s the Saturation component
	 * @param v the Value component
	 * @param a the Opacity component
	 */
	public ColorHSVAF(float h, float s, float v, float a) {
		setH(h);
		setS(s);
		setV(v);
		setA(a);
	}
	
	/**
	 * The copy constructor.
	 * @param rhs
	 */
	public ColorHSVAF(ColorHSVAF rhs) {
		assign(rhs);
	}
	
	/**
	 * The assignement operator.
	 * @param rhs
	 */
	public void assign(ColorHSVAF rhs) {
		setH(rhs.getH());
		setS(rhs.getS());
		setV(rhs.getV());
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
	 * Get the Value component of the color.
	 * @return the value in the interval [0, 1]
	 */
	public final float getV() {
		return v;
	}
	
	/**
	 * Set the Value component of the color. 
	 * @param v the value in the range [0, 1]
	 */
	public final void setV(float v) {
		this.v = clipValues(v);
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
	public void interpolate(ColorHSVAF left, double x, ColorHSVAF right) {
		setH((float)LinearInterpolator.interpolate(left.getH(), x, right.getH()));
		setS((float)LinearInterpolator.interpolate(left.getS(), x, right.getS()));
		setV((float)LinearInterpolator.interpolate(left.getV(), x, right.getV()));
		setA((float)LinearInterpolator.interpolate(left.getA(), x, right.getA()));
	}
	
	private float clipValues(float f) {
		if (f < 0) return 0;
		if (f > 1) return 1;
		return f;
	}
}
