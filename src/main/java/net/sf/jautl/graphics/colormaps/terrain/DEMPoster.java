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
 * This class implements a topographic color map inspired to the
 * DEMPoster map from cpt-city (http://soliton.vm.bytemark.co.uk/pub/cpt-city/). 
 */
public class DEMPoster extends ColorMapMultiRamp {
	/**
	 * The constructor.
	 * @param gamma the value of the gamma correction to apply to the color values
	 */
	public DEMPoster(double gamma) {
		super(gamma);

        loadPoints(
                new double[] {
                    0.00000,
                    0.01020,
                    0.10200,
                    0.24490,
                    0.34690,
                    0.57140,
                    0.81630,
                    1.00000
                }, 
                new ColorRGBAF[] {
                    new ColorRGBAF(0.0000f, 0.3804f, 0.2784f),
                    new ColorRGBAF(0.0627f, 0.4784f, 0.1843f),
                    new ColorRGBAF(0.9098f, 0.8431f, 0.4902f),
                    new ColorRGBAF(0.6314f, 0.2627f, 0.0000f),
                    new ColorRGBAF(0.6196f, 0.0000f, 0.0000f),
                    new ColorRGBAF(0.4314f, 0.4314f, 0.4314f),
                    new ColorRGBAF(1.0000f, 1.0000f, 1.0000f),
                    new ColorRGBAF(1.0000f, 1.0000f, 1.0000f)
                }
            );
	}
}
