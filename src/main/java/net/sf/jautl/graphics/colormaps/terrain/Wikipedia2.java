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
public class Wikipedia2 extends ColorMapMultiRamp {
	/**
	 * The constructor.
	 * @param gamma the value of the gamma correction to apply to the color values
	 */
	public Wikipedia2(double gamma) {
		super(gamma);
        
		loadPoints(
                null,
                new ColorRGBAF[] {
                    new ColorRGBAF("#71ABD8"),
                    new ColorRGBAF("#79B2DE"),
                    new ColorRGBAF("#84B9E3"),
                    new ColorRGBAF("#96C9F0"),
                    new ColorRGBAF("#A1D2F7"),
                    new ColorRGBAF("#ACDBFB"),
                    new ColorRGBAF("#B9E3FF"),
                    new ColorRGBAF("#C6ECFF"),
                    new ColorRGBAF("#D8F2FE"),
                    new ColorRGBAF("#A7DFD2"),
                    new ColorRGBAF("#ACD0A5"),
                    new ColorRGBAF("#94BF8B"),
                    new ColorRGBAF("#A8C68F"),
                    new ColorRGBAF("#BDCC96"),
                    new ColorRGBAF("#D1D7AB"),
                    new ColorRGBAF("#E1E4B5"),
                    new ColorRGBAF("#EFEBC0"),
                    new ColorRGBAF("#E8E1B6"),
                    new ColorRGBAF("#DED6A3"),
                    new ColorRGBAF("#D3CA9D"),
                    new ColorRGBAF("#CAB982"),
                    new ColorRGBAF("#C3A76B"),
                    new ColorRGBAF("#B9985A"),
                    new ColorRGBAF("#AA8753"),
                    new ColorRGBAF("#AC9A7C"),
                    new ColorRGBAF("#BAAE9A"),
                    new ColorRGBAF("#CAC3B8"),
                    new ColorRGBAF("#E0DED8"),
                    new ColorRGBAF("#F5F4F2")
                }
            );
	}
}
