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
package net.sf.jautl.graphics.colors;

import java.awt.Color;
import java.awt.color.ColorSpace;

import net.sf.jautl.numeric.LinearInterpolator;

/**
 * This class represents a color in the RGB space with associated opacity,
 * all values given as single precision floats.
 */
public class ColorRGBAF {
	/** The maximum value for the color components. */
	public static final float MAX_VALUE = 1.0f;
	private float r;
	private float g;
	private float b;
	private float a;
	private static final ColorSpace CS_LINEAR_RGB = ColorSpace.getInstance(ColorSpace.CS_LINEAR_RGB);

	/**
	 * Default constructor. Creates an opaque black.
	 */
	public ColorRGBAF() {
		this(0, 0, 0, MAX_VALUE);
	}

	/**
	 * The constructor for solid colors.
	 * @param r the value of the Red component
	 * @param g the value of the Green component
	 * @param b the value of the Blue component
	 */
	public ColorRGBAF(float r, float g, float b) {
		this(r, g, b, MAX_VALUE);
	}

	/**
	 * The constructor.
	 * @param r the value of the Red component
	 * @param g the value of the Green component
	 * @param b the value of the Blue component
	 * @param a the value of the Opacity component
	 */
	public ColorRGBAF(float r, float g, float b, float a) {
		setR(r);
		setG(g);
		setB(b);
		setA(a);
	}

	/**
	 * The copy constructor.
	 * @param rhs
	 */
	public ColorRGBAF(ColorRGBAF rhs) {
		assign(rhs);
	}

	/**
	 * The constructor from an HTML compatible color definition. 
	 * @param hexSpec
	 */
	public ColorRGBAF(String hexSpec) {
        if (hexSpec.startsWith("#"))
            hexSpec = hexSpec.substring(1);

        r = Integer.parseInt(hexSpec.substring(0, 2), 16) / 255.0f;
        g = Integer.parseInt(hexSpec.substring(2, 4), 16) / 255.0f;
        b = Integer.parseInt(hexSpec.substring(4, 6), 16) / 255.0f;
        setA(MAX_VALUE);
	}

	/**
	 * The assignment operator.
	 * @param rhs
	 */
	public void assign(ColorRGBAF rhs) {
		setR(rhs.getR());
		setG(rhs.getG());
		setB(rhs.getB());
		setA(rhs.getA());
	}
	
	/**
	 * Get the Red component of the color.
	 * @return the value in the interval [0, MAX_VALUE]
	 */
	public final float getR() {
		return r;
	}

	/**
	 * Set the red component of the color. The value is clipped in the [0, MAX_VALUE] range.
	 * @param r the value of the red component
	 */
	public final void setR(float r) {
		this.r = clipValues(r);
	}
	
	/**
	 * Get the Green component of the color.
	 * @return the value in the interval [0, MAX_VALUE]
	 */
	public final float getG() {
		return g;
	}
	
	/**
	 * Set the green component of the color. The value is clipped in the [0, MAX_VALUE] range.
	 * @param g the value of the green component
	 */
	public final void setG(float g) {
		this.g = clipValues(g);
	}
	
	/**
	 * Get the Blue component of the color.
	 * @return the value in the interval [0, MAX_VALUE]
	 */
	public final float getB() {
		return b;
	}
	
	/**
	 * Set blue component of the color. The value is clipped in the [0, MAX_VALUE] range.
	 * @param b the value of the blue component
	 */
	public final void setB(float b) {
		this.b = b;
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
	 * Perform the gamma correction on the color.
	 * @param gamma the value to use for gamma
	 */
	public void gammaCorrect(double gamma) {
        if (gamma == 1) return;

        double invGamma = 1 / gamma;

        r = (float)Math.pow(r, invGamma);
        g = (float)Math.pow(g, invGamma);
        b = (float)Math.pow(b, invGamma);
    }
	
	/**
	 * Return an AWT Color instance representing the color.
	 */
	public Color toAWTColor() {
		float[] f = new float[3];
		f[0] = r;
		f[1] = g;
		f[2] = b;

		return new Color(CS_LINEAR_RGB, f, getA());
	}
	
	/**
	 * Return an integer description compatible with BufferedImage-s.
	 */
	public int toIntRGBA() {
		final double ALMOST_256 = 255.999;
		
		int IR = (int)(ALMOST_256 * r);
		int IG = (int)(ALMOST_256 * g);
		int IB = (int)(ALMOST_256 * b);
		int IA = (int)(ALMOST_256 * a);
		
		int result = IA << 24 | IR << 16 | IG << 8 | IB;
		return result;
	}
	
	/**
	 * Linearily interpolate a color, component-wise.
	 * @param left the color when the x value is 0
	 * @param x the fraction
	 * @param right the color when the x value is 1
	 */
	public void interpolate(ColorRGBAF left, double x, ColorRGBAF right) {
		setR((float)LinearInterpolator.interpolate(left.getR(), x, right.getR()));
		setG((float)LinearInterpolator.interpolate(left.getG(), x, right.getG()));
		setB((float)LinearInterpolator.interpolate(left.getB(), x, right.getB()));
		setA((float)LinearInterpolator.interpolate(left.getA(), x, right.getA()));
	}
	
	private float clipValues(float f) {
		if (f < 0) return 0;
		if (f > 1) return 1;
		return f;
	}
}
