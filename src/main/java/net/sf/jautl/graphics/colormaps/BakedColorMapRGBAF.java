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
package net.sf.jautl.graphics.colormaps;

import net.sf.jautl.graphics.colors.ColorRGBAF;

/**
 * This class implements a colormap which is the baking into a vector
 * of values of a given colormap.
 */
public class BakedColorMapRGBAF extends ColorMapRGBAF {
	private float[] Rs;
	private float[] Gs;
	private float[] Bs;
	private float[] As;
	
	/**
	 * The constructor. Allocates memory and, for each entry, looks up
	 * and stores the corresponding color.
	 * @param size the size of the storage
	 * @param cmap the colormap to bake
	 */
	public BakedColorMapRGBAF(int size, IColorMapRGBAF cmap) {
		Rs = new float[size];
		Gs = new float[size];
		Bs = new float[size];
		As = new float[size];
	
		ColorRGBAF color = new ColorRGBAF();
		
		for (int index = 0; index < size; index++) {
			cmap.lookup((double)index / (size -1), color);

			Rs[index] = color.getR();
			Gs[index] = color.getG();
			Bs[index] = color.getB();
			As[index] = color.getA();
		}
	}

	@Override
	protected void lookupImpl(double x, ColorRGBAF color) {
		int index = (int)(.9999 * Rs.length * x);
		
		color.setR(Rs[index]);
		color.setG(Gs[index]);
		color.setB(Bs[index]);
		color.setA(As[index]);
	}
}
