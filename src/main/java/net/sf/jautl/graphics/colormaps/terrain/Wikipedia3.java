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
package net.sf.jautl.graphics.colormaps.terrain;

import net.sf.jautl.graphics.colormaps.ColorMapMultiRamp;
import net.sf.jautl.graphics.colors.ColorRGBAF;

/**
 * This class implements a color map inspired to some Wikipedia maps.
 */
public class Wikipedia3 extends ColorMapMultiRamp {
	/**
	 * The constructor.
	 * @param gamma the value of the gamma correction to apply to the color values
	 */
	public Wikipedia3(double gamma) {
		super(gamma);

		loadPoints(
                null,
                new ColorRGBAF[] {
                    new ColorRGBAF(  0 / 255.0f,  97 / 255.0f,  71 / 255.0f),
                    new ColorRGBAF( 16 / 255.0f, 122 / 255.0f,  47 / 255.0f),
                    new ColorRGBAF(232 / 255.0f, 215 / 255.0f, 125 / 255.0f),
                    new ColorRGBAF(161 / 255.0f,  67 / 255.0f,   0 / 255.0f),
                    new ColorRGBAF(130 / 255.0f,  30 / 255.0f,  30 / 255.0f),
                    new ColorRGBAF(110 / 255.0f, 110 / 255.0f, 110 / 255.0f),
                    new ColorRGBAF(255 / 255.0f, 255 / 255.0f, 255 / 255.0f),
                    new ColorRGBAF(255 / 255.0f, 255 / 255.0f, 255 / 255.0f)
                }
            );
	}

}
