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
package net.sf.jautl.graphics.hashnoise;

import net.sf.jautl.md.SipHash;

/**
 * 
 */
public class CellNoise2 extends HashNoise2 {
	private double sizeX;
	private double sizeY;

    /**
     * The constructor.
     */
	public CellNoise2() {
		super(new SipHash(2, 4));
	}
	
    /**
     * Return the number of cells per abscissa unit.
     * @return number of cells per abscissa unit
     */
	public final double getSizeX() {
		return sizeX;
	}
	
	public final void setSizeX(double sizeX) {
		this.sizeX = sizeX;
	}
	
	public final double getSizeY() {
		return sizeY;
	}
	
	public final void setSizeY(double sizeY) {
		this.sizeY = sizeY;
	}
	
	@Override
	public double generate(double x, double y) {
		final int _7F = 0x7FFFFFFF;
		final double _7FD = (double)_7F;

		initiate();
		
		de.add(Double.toHexString((int)(x / sizeX)));
		de.add(Double.toHexString((int)(y / sizeY)));

		int i = terminate();

		double z = (i & _7F) / _7FD;

		return z;
	}
}
