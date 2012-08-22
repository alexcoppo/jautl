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
package net.sf.jautl.graphics.colormaps.terrain;

import net.sf.jautl.graphics.colormaps.ColorMapMultiRamp;
import net.sf.jautl.graphics.colors.ColorRGBAF;

/**
 * This class implements a color map inspired to MicroDEM default terrain scale.
 */
public class MicroDEM extends ColorMapMultiRamp {
	/**
	 * The constructor.
	 * @param gamma the value of the gamma correction to apply to the color values
	 */
    public MicroDEM(double gamma) {
    	super(gamma);

    	loadPoints(
            null,
            new ColorRGBAF[] {
                new ColorRGBAF(0.760784f, 0.760784f, 0.949020f),
                new ColorRGBAF(0.690196f, 0.819608f, 0.960784f),
                new ColorRGBAF(0.643137f, 0.890196f, 0.941176f),
                new ColorRGBAF(0.607843f, 0.949020f, 0.913725f),

                new ColorRGBAF(0.596078f, 1.000000f, 0.874510f),
                new ColorRGBAF(0.607843f, 1.000000f, 0.874510f),
                new ColorRGBAF(0.631373f, 1.000000f, 0.788235f),
                new ColorRGBAF(0.670588f, 1.000000f, 0.752941f),

                new ColorRGBAF(0.729412f, 1.000000f, 0.713725f),
                new ColorRGBAF(0.800000f, 0.980392f, 0.694118f),
                new ColorRGBAF(0.866667f, 0.921569f, 0.682353f),
                new ColorRGBAF(0.933333f, 0.850980f, 0.682353f),

                new ColorRGBAF(0.988235f, 0.796078f, 0.694118f),
                new ColorRGBAF(1.000000f, 0.721569f, 0.721569f),
                new ColorRGBAF(1.000000f, 0.666667f, 0.756863f),
                new ColorRGBAF(1.000000f, 0.645098f, 0.776471f),

                new ColorRGBAF(0.945098f, 0.662745f, 0.819608f)
            }
        );
    }
}
