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
package net.sf.jautl.rng.variates;

import net.sf.jautl.rng.interfaces.GeneratorsDouble;
import net.sf.jautl.rng.interfaces.IDoublesSource;

/**
 * This class generate uniformly distributed points in the unit sphere.
 */
public class VariateUnitSphere extends VariatesBaseDoublesSource {
    private double x;
    private double y;
    private double z;
    private double r2;

    /**
     * The constructor.
	 * @param ids an implementor of IDoublesSource interface
     */
    public VariateUnitSphere(IDoublesSource ids) {
    	super(ids);
    }

    /**
     * Compute a variate.
     * @param excludeZero if true, no zero radius points are generated
     * @param excludeOne if true, no unit radius points are generated
     */
	public void compute(boolean excludeZero, boolean excludeOne) {
        boolean ok;
        
        do {
            x = 2 * GeneratorsDouble.generate(ids, true, true) - 1;
            y = 2 * GeneratorsDouble.generate(ids, true, true) - 1;
            z = 2 * GeneratorsDouble.generate(ids, true, true) - 1;
            r2 = x * x + y * y + z * z;
            ok = true;
            if (r2 > 1) ok = false;
            if (r2 == 0 && excludeZero) ok = false;
            if (r2 == 1 && excludeOne) ok = false;
        } while (!ok);
	}

    /**
     * Return the x component.
     * @return 
     */
    public double getX() {
        return x;
    }

    /**
     * Return the y component.
     * @return 
     */
    public double getY() {
        return y;
    }

    /**
     * Return the y component.
     * @return 
     */
    public double getZ() {
        return z;
    }

    /**
     * Return the square of the radius.
     * @return 
     */
    public double getR2() {
        return r2;
    }

    /**
     * Return the radius.
     * @return 
     */
    public double getR() {
        return Math.sqrt(r2);
    }
}