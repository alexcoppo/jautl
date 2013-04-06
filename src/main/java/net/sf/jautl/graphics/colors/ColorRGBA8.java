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

import net.sf.jautl.numeric.LinearInterpolator;

/**
 * This class represents a color in the RGB space with associated opacity,
 * all channel values in [0, 255].
 */
public class ColorRGBA8 {
	/** The maximum value for the color components. */
	public static final int MAX_VALUE = 255;
	private int r;
	private int g;
	private int b;
	private int a;

	/**
	 * Default constructor. Creates an opaque black.
	 */
	public ColorRGBA8() {
		this(0, 0, 0, MAX_VALUE);
	}

	/**
	 * The constructor for solid colors.
	 * @param r the value of the Red component
	 * @param g the value of the Green component
	 * @param b the value of the Blue component
	 */
	public ColorRGBA8(int r, int g, int b) {
		this(r, g, b, MAX_VALUE);
	}

	/**
	 * The constructor.
	 * @param r the value of the Red component
	 * @param g the value of the Green component
	 * @param b the value of the Blue component
	 * @param a the value of the Opacity component
	 */
	public ColorRGBA8(int r, int g, int b, int a) {
		setR(r);
		setG(g);
		setB(b);
		setA(a);
	}

	/**
	 * The copy constructor.
	 * @param rhs
	 */
	public ColorRGBA8(ColorRGBA8 rhs) {
		assign(rhs);
	}

	/**
	 * The constructor from an HTML compatible color definition. 
	 * @param hexSpec
	 */
	public ColorRGBA8(String hexSpec) {
        if (hexSpec.startsWith("#"))
            hexSpec = hexSpec.substring(1);

        r = Integer.parseInt(hexSpec.substring(0, 2), 16);
        g = Integer.parseInt(hexSpec.substring(2, 4), 16);
        b = Integer.parseInt(hexSpec.substring(4, 6), 16);
        setA(MAX_VALUE);
	}

	/**
	 * The assignment operator.
	 * @param rhs
	 */
	public void assign(ColorRGBA8 rhs) {
		setR(rhs.getR());
		setG(rhs.getG());
		setB(rhs.getB());
		setA(rhs.getA());
	}
	
	/**
	 * Get the Red component of the color.
	 * @return the value in the interval [0, MAX_VALUE]
	 */
	public final int getR() {
		return r;
	}

	/**
	 * Set the red component of the color.
	 * @param r the value of the red component
	 */
	public final void setR(int r) {
		this.r = clipValues(r);
	}
	
	/**
	 * Get the Green component of the color.
	 * @return the value in the interval [0, MAX_VALUE]
	 */
	public final int getG() {
		return g;
	}
	
	/**
	 * Set the green component of the color.
	 * @param g the value of the green component
	 */
	public final void setG(int g) {
		this.g = clipValues(g);
	}
	
	/**
	 * Get the Blue component of the color.
	 * @return the value in the interval [0, MAX_VALUE]
	 */
	public final int getB() {
		return b;
	}
	
	/**
	 * Set blue component of the color.
	 * @param b the value of the blue component
	 */
	public final void setB(int b) {
		this.b = clipValues(b);
	}

	/**
	 * Set the opacity value. 
	 * @param a the opacity value
	 */
	public final void setA(int a) {
		this.a = clipValues(a);
	}
	
	/**
	 * Return the opacity value.
	 */
	public final int getA() {
		return this.a;
	}

	/**
	 * Create an awt.Color instance from the current color.
	 * @return
	 */
	public Color toColor() {
		return new Color(getR(), getG(), getB(), getA());
	}
	
	/**
	 * Return an integer description compatible with BufferedImage-s.
	 */
	public int toIntRGBA() {
		int result = getA() << 24 | getR() << 16 | getG() << 8 | getB();
		return result;
	}
	
	/**
	 * Linearily interpolate a color, component-wise.
	 * @param left the color when the x value is 0
	 * @param x the fraction
	 * @param right the color when the x value is 1
	 */
	public void interpolate(ColorRGBA8 left, double x, ColorRGBA8 right) {
		setR((int)LinearInterpolator.interpolate(left.getR(), x, right.getR()));
		setG((int)LinearInterpolator.interpolate(left.getG(), x, right.getG()));
		setB((int)LinearInterpolator.interpolate(left.getB(), x, right.getB()));
		setA((int)LinearInterpolator.interpolate(left.getA(), x, right.getA()));
	}

	private int clipValues(int i) {
		if (i < 0) return 0;
		if (i > MAX_VALUE) return MAX_VALUE;
		return i;
	}
}
