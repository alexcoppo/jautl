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
package net.sf.jautl.rng.algorithms;

import net.sf.jautl.rng.interfaces.IDoublesSource;
import net.sf.jautl.rng.variates.VariateIUniform;

/**
 * Bag-of-methods to randomly shuffle vectors.
 */
public class Shuffler {
    /**
     * Shuffle a vector of integers.
     * @param ids the IDoublesSource to use as entropy source
     * @param v the vector to shuffle.
     */
    public static void shuffle(IDoublesSource ids, int[] v) {
        shuffle(ids, v, 0, v.length);
    }

    /**
     * Shuffle a vector of integers.
     * @param ids the IDoublesSource to use as entropy source
     * @param v the vector to shuffle
     * @param base the start index of the segment to shuffle
     * @param count the number of entries to shuffle
     */
    public static void shuffle(IDoublesSource ids, int[] v, int base, int count) {
        VariateIUniform vin = new VariateIUniform(ids);

        int max = base + count - 1;
        vin.setMaximum(max);
        
        while (base < max - 1) {
            vin.setMinimum(base + 1);
            int idx = (int)vin.draw();

            int tmp = v[base];
            v[base] = v[idx];
            v[idx] = tmp;

            base++;
        }
    }
}