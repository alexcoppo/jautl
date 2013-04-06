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
package net.sf.jautl.graphics.colormaps.terrain;

import net.sf.jautl.graphics.colormaps.ColorMapMultiRamp;
import net.sf.jautl.graphics.colors.ColorRGBAF;

/**
 * This class implements a color map inspired to some Wikipedia maps.
 */
public class Wikipedia1 extends ColorMapMultiRamp {
	/**
	 * The constructor.
	 * @param gamma the value of the gamma correction to apply to the color values
	 */
    public Wikipedia1(double gamma) {
    	super(gamma);

        loadPoints(
            null,
            new ColorRGBAF[] {
                new ColorRGBAF(172 / 255.0f, 208 / 255.0f, 165 / 255.0f),
                new ColorRGBAF(148 / 255.0f, 191 / 255.0f, 139 / 255.0f),
                new ColorRGBAF(168 / 255.0f, 198 / 255.0f, 143 / 255.0f),
                new ColorRGBAF(189 / 255.0f, 204 / 255.0f, 150 / 255.0f),
                new ColorRGBAF(209 / 255.0f, 215 / 255.0f, 171 / 255.0f),
                new ColorRGBAF(225 / 255.0f, 228 / 255.0f, 181 / 255.0f),
                new ColorRGBAF(239 / 255.0f, 235 / 255.0f, 192 / 255.0f),
                new ColorRGBAF(232 / 255.0f, 225 / 255.0f, 182 / 255.0f),
                new ColorRGBAF(222 / 255.0f, 214 / 255.0f, 163 / 255.0f),
                new ColorRGBAF(211 / 255.0f, 202 / 255.0f, 157 / 255.0f),
                new ColorRGBAF(202 / 255.0f, 185 / 255.0f, 107 / 255.0f),
                new ColorRGBAF(195 / 255.0f, 167 / 255.0f, 130 / 255.0f),
                new ColorRGBAF(192 / 255.0f, 154 / 255.0f,  83 / 255.0f)
            }
        );
    }
}
