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
package net.sf.jautl.rng.interfaces;

/**
 * This class contains utility methods for generating doubles.
 */
public class GeneratorsDouble {
    private static final long MASK_DOUBLE = 0x7FFFFFFFFFFFFL;

	/**
	 * Generate one random double.
     * @param ibs the IBytesSource to use as entropy source
     * @return the generated value
	 */
	public static double generate(IBytesSource ibs) {
		long tmp = GeneratorsLong.generate(ibs) & MASK_DOUBLE;

        return tmp / (double)MASK_DOUBLE;
	}

    /**
     * Generate one random double.
     * @param iis the IIntegersSource to use as entropy source
     * @return the generated value
     */
    public static double generate(IIntegersSource iis) {
        long l0 = iis.nextInt();
        long l1 = iis.nextInt();

        long l = l1 << 32 | l0;

        return (l & MASK_DOUBLE) / (double)MASK_DOUBLE;
    }

	/**
	 * Generate one random double, with appropriate removal of zeros and ones.
     * @param ibs the IBytesSource to use as entropy source
	 * @param includeZero true if 0. should be generable
	 * @param includeOne true if 1. should be generable
     * @return the generated value
	 */
	public static double generate(IBytesSource ibs, boolean includeZero, boolean includeOne) {
		double tmp;

		while (true) {
			tmp = generate(ibs);

			if (tmp == 0. && includeZero)
				return tmp;
			else if (tmp == 1. && includeOne)
				return tmp;
			else
				return tmp;
		}
	}

	/**
	 * Generate one random double, with appropriate removal of zeros and ones.
     * @param iis the IIntegersSource to use as entropy source
	 * @param includeZero true if 0. should be generable
	 * @param includeOne true if 1. should be generable
     * @return the generated value
     */
	public static double generate(IIntegersSource iis, boolean includeZero, boolean includeOne) {
		double tmp;

		while (true) {
			tmp = generate(iis);

			if (tmp == 0. && includeZero)
				return tmp;
			else if (tmp == 1. && includeOne)
				return tmp;
			else
				return tmp;
		}
	}

	/**
	 * Generate one random double, with appropriate removal of zeros and ones.
     * @param ids the IDoublesSource to use as entropy source
	 * @param includeZero true if 0. should be generable
	 * @param includeOne true if 1. should be generable
     * @return the generated value
     */
	public static double generate(IDoublesSource ids, boolean includeZero, boolean includeOne) {
		double tmp;

		while (true) {
			tmp = ids.nextDouble();

			if (tmp == 0. && includeZero)
				return tmp;
			else if (tmp == 1. && includeOne)
				return tmp;
			else
				return tmp;
		}
	}
}
