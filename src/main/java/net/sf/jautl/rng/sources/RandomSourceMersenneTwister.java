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

// Copyright 2007-2008 Rory Plaire (codekaizen@gmail.com)

// Adapted from:

/* C# Version Copyright (C) 2001-2004 Akihilo Kramot (Takel).  */
/* C# porting from a C-program for MT19937, originaly coded by */
/* Takuji Nishimura and Makoto Matsumoto, considering the suggestions by */
/* Topher Cooper and Marc Rieffel in July-Aug. 1997.           */
/* This library is free software under the Artistic license:   */
/*                                                             */
/* You can find the original C-program at                      */
/*     http://www.math.keio.ac.jp/~matumoto/mt.html            */
/*                                                             */

// and:

/////////////////////////////////////////////////////////////////////////////
// C# Version Copyright (c) 2003 CenterSpace Software, LLC                 //
//                                                                         //
// This code is free software under the Artistic license.                  //
//                                                                         //
// CenterSpace Software                                                    //
// 2098 NW Myrtlewood Way                                                  //
// Corvallis, Oregon, 97330                                                //
// USA                                                                     //
// http://www.centerspace.net                                              //
/////////////////////////////////////////////////////////////////////////////

// and, of course:
/* 
   A C-program for MT19937, with initialization improved 2002/2/10.
   Coded by Takuji Nishimura and Makoto Matsumoto.
   This is a faster version by taking Shawn Cokus's optimization,
   Matthe Bellew's simplification, Isaku Wada's real version.

   Before using, initialize the state by using init_genrand(seed) 
   or init_by_array(init_key, key_length).

   Copyright (C) 1997 - 2002, Makoto Matsumoto and Takuji Nishimura,
   All rights reserved.                          

   Redistribution and use in source and binary forms, with or without
   modification, are permitted provided that the following conditions
   are met:

     1. Redistributions of source code must retain the above copyright
        notice, this list of conditions and the following disclaimer.

     2. Redistributions in binary form must reproduce the above copyright
        notice, this list of conditions and the following disclaimer in the
        documentation and/or other materials provided with the distribution.

     3. The names of its contributors may not be used to endorse or promote 
        products derived from this software without specific prior written 
        permission.

   THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
   "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
   LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
   A PARTICULAR PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
   CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
   EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
   PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
   PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
   LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
   NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
   SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.


   Any feedback is very welcome.
   http://www.math.sci.hiroshima-u.ac.jp/~m-mat/MT/emt.html
   email: m-mat @ math.sci.hiroshima-u.ac.jp (remove space)
*/

package net.sf.jautl.rng.sources;

import net.sf.jautl.rng.interfaces.GeneratorsInteger;
import net.sf.jautl.rng.interfaces.AdapterBlockedToBytesSource;
import net.sf.jautl.rng.interfaces.IIntegersSource;
import net.sf.jautl.rng.interfaces.IRandomizable;

/**
 * Generates pseudo-random numbers using the Mersenne Twister algorithm.
 * See <a href="http://www.math.sci.hiroshima-u.ac.jp/~m-mat/MT/emt.html">
 * http://www.math.sci.hiroshima-u.ac.jp/~m-mat/MT/emt.html</a> for details
 * on the algorithm. 
 */
public class RandomSourceMersenneTwister implements IRandomizable, IIntegersSource {
    private static final int   N     = 624;
    private static final int   M     = 397;
    private static final int[] MAG01 = { 0x0, 0x9908b0df };
    private int[] mt;
    private int   mti;

    /**
     * The constructor.
     */
    public RandomSourceMersenneTwister() {
        mt = new int[N];
    }

    public void factoryDefault() {
        setSeed(19650218);
    }

    public void randomize(byte[] seed) {
		RandomSourceHMAC2104 rs = new RandomSourceHMAC2104();
        AdapterBlockedToBytesSource abtb = new AdapterBlockedToBytesSource(rs);
		rs.randomize(seed);

		setSeed(GeneratorsInteger.generate(abtb));
    }

    /**
     * Seed the engine using an integer.
     * @param seed the seed
     */
    public void setSeed(int seed) {
        mt[0]= seed;

        for (mti = 1; mti < N; mti++) {
            mt[mti] = (int)(1812433253L * (mt[mti-1] ^ (mt[mti-1] >>> 30)) + mti);
            /* See Knuth TAOCP Vol2. 3rd Ed. P.106 for multiplier. */
            /* In the previous versions, MSBs of the seed affect   */
            /* only MSBs of the array mt[].                        */
            /* 2002/01/09 modified by Makoto Matsumoto             */
        }
    }

    /**
     * Seed the engine using a vector of integers.
     * @param seed the seed
     */
    public void setSeed(int[] seed) {
        setSeed(19650218);

        int i = 1;
        int j = 0;
        int k = (N > seed.length ? N : seed.length);

        for (; k != 0; k--) {
            mt[i] = (int)((mt[i] ^ ((mt[i-1] ^ (mt[i-1] >>> 30)) * 1664525L)) + seed[j] + j); /* non linear */
            i++;
            j++;
            if (i >= N) {
                mt[0] = mt[N-1];
                i = 1;
            }
            if (j >= seed.length)
                j=0;
        }
        
        for (k = N - 1; k != 0; k--) {
            mt[i] = (int)((mt[i] ^ ((mt[i-1] ^ (mt[i-1] >>> 30)) * 1566083941L)) - i); /* non linear */
            i++;
            if (i >= N) {
                mt[0] = mt[N-1];
                i = 1;
            }
        }

        mt[0] = 0x80000000; /* MSB is 1; assuring non-zero initial array */
    }

    public int nextInt() {
        int y;

        if (mti >= N) {
            int mtNext = mt[0];

            for (int k = 0; k < N - M; ++k) {
                int mtCurr = mtNext;
                mtNext = mt[k + 1];
                y = (mtCurr & 0x80000000) | (mtNext & 0x7fffffff);
                mt[k] = mt[k + M] ^ (y >>> 1) ^ MAG01[y & 0x1];
            }

            for (int k = N - M; k < N - 1; ++k) {
                int mtCurr = mtNext;
                mtNext = mt[k + 1];
                y = (mtCurr & 0x80000000) | (mtNext & 0x7fffffff);
                mt[k] = mt[k + (M - N)] ^ (y >>> 1) ^ MAG01[y & 0x1];
            }

            y = (mtNext & 0x80000000) | (mt[0] & 0x7fffffff);

            mt[N - 1] = mt[M - 1] ^ (y >>> 1) ^ MAG01[y & 0x1];

            mti = 0;
        }

        y = mt[mti++];

        y ^=  y >>> 11;
        y ^= (y <<   7) & 0x9d2c5680;
        y ^= (y <<  15) & 0xefc60000;
        y ^=  y >>> 18;

        return y;
    }
}
