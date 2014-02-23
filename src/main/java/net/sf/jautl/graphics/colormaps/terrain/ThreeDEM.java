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
 * This class implements a color map inspired to 3DEM default terrain scale.
 */
public class ThreeDEM extends ColorMapMultiRamp {
	/**
	 * The constructor.
	 * @param gamma the value of the gamma correction to apply to the color values
	 */
	public ThreeDEM(double gamma) {
		super(gamma);

        loadPoints(
                null,
                new ColorRGBAF[] {
                    new ColorRGBAF(139 / 255.0f, 146 / 255.0f, 112 / 255.0f),
                    new ColorRGBAF(158 / 255.0f, 159 / 255.0f, 117 / 255.0f),
                    new ColorRGBAF(177 / 255.0f, 173 / 255.0f, 123 / 255.0f),
                    new ColorRGBAF(196 / 255.0f, 186 / 255.0f, 129 / 255.0f),
                    new ColorRGBAF(215 / 255.0f, 200 / 255.0f, 135 / 255.0f),
                    new ColorRGBAF(208 / 255.0f, 190 / 255.0f, 128 / 255.0f),
                    new ColorRGBAF(202 / 255.0f, 180 / 255.0f, 121 / 255.0f),
                    new ColorRGBAF(195 / 255.0f, 170 / 255.0f, 114 / 255.0f),
                    new ColorRGBAF(189 / 255.0f, 160 / 255.0f, 107 / 255.0f),
                    new ColorRGBAF(183 / 255.0f, 150 / 255.0f, 101 / 255.0f),
                    new ColorRGBAF(179 / 255.0f, 154 / 255.0f, 113 / 255.0f),
                    new ColorRGBAF(175 / 255.0f, 158 / 255.0f, 126 / 255.0f),
                    new ColorRGBAF(171 / 255.0f, 162 / 255.0f, 138 / 255.0f),
                    new ColorRGBAF(167 / 255.0f, 167 / 255.0f, 151 / 255.0f)
                }
            );
	}
}
