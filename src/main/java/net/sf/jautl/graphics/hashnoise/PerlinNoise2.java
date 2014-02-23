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

import net.sf.jautl.md.Murmur3_32;
import net.sf.jautl.md.UintHashUtilities;
import net.sf.jautl.numeric.Constants;
import net.sf.jautl.numeric.easefunctions.QuinticEaseFunction;

/**
 * 
 */
public class PerlinNoise2 extends HashNoise2 {
    private static final int TABLESIZE = 1024;
    private static double[] gx;
    private static double[] gy;
    private static QuinticEaseFunction ef;

    private int salt;

    static {
        gx = new double[TABLESIZE];
        gy = new double[TABLESIZE];

        for (int index = 0; index < TABLESIZE; index++) {
            double theta = Constants.PI2 * index / TABLESIZE;
            gx[index] = Math.sin(theta);
            gy[index] = Math.cos(theta);
        }

         ef = new QuinticEaseFunction();
         ef.setXmin(0); ef.setFXmin(1);
         ef.setXmax(1); ef.setFXmax(0);
    }

    /**
     * The constructor.
     */
    public PerlinNoise2() {
    	super(new Murmur3_32());
    }

    @Override
	public void setSeed(String seed) {
		super.setSeed(seed);

        initiate();
        salt = terminate();
    }

	@Override
	public double generate(double x, double y) {
        int i = (int)Math.floor(x);
        double dx = x - i;

        int j = (int)Math.floor(y);
        double dy = y - j;

        double z = 0;
        z += surflet(i    , j    , dx    , dy    );
        z += surflet(i    , j + 1, dx    , dy - 1);
        z += surflet(i + 1, j    , dx - 1, dy    );
        z += surflet(i + 1, j + 1, dx - 1, dy - 1);

        return z;
	}

    private double surflet(int i, int j, double dx, double dy) {
        double rho2 = dx * dx + dy * dy;
        if (rho2 >= 1) return 0;

        int index = UintHashUtilities.mix(i, j, salt);
        index = index & (TABLESIZE - 1);

        double scalprod = gx[index] * dx + gy[index] * dy;
        return scalprod * ef.calc(Math.sqrt(rho2));
    }
}
