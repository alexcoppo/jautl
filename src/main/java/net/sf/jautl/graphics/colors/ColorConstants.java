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

/**
 * This class contains several color constants.
 */
public class ColorConstants {
	/**
	 * Solid black.
	 */
	public final ColorRGBAF BLACK = new ColorRGBAF(0, 0, 0, 1);

	/**
	 * Solid white.
	 */
    public static ColorRGBAF WHITE = new ColorRGBAF(1.000f, 1.000f, 1.000f);

	/**
	 * Transparent.
	 */
    public static ColorRGBAF TRANSPARENT = new ColorRGBAF(0.000f, 0.000f, 0.000f, 0.000f);

	/**
	 * Solid red.
	 */
    public static ColorRGBAF RED = new ColorRGBAF(1.000f, 0.000f, 0.000f);
	/**
	 * Solid green.
	 */
    public static ColorRGBAF GREEN = new ColorRGBAF(0.000f, 1.000f, 0.000f);
	/**
	 * Solid blue.
	 */
    public static ColorRGBAF BLUE = new ColorRGBAF(0.000f, 0.000f, 1.000f);

	/**
	 * Solid yellow.
	 */
    public static ColorRGBAF YELLOW = new ColorRGBAF(1.000f, 1.000f, 0.000f);
	/**
	 * Solid cyan.
	 */
    public static ColorRGBAF CYAN = new ColorRGBAF(0.000f, 1.000f, 1.000f);
	/**
	 * Solid magenta.
	 */
    public static ColorRGBAF MAGENTA = new ColorRGBAF(1.000f, 0.000f, 1.000f);
}
