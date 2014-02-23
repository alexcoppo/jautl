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
package net.sf.jautl.rng.algorithms;

import net.sf.jautl.rng.interfaces.IDoublesSource;

/**
 * This class provides a way of randomly index a vector of elements. The
 * randomization is performed at creation time and therefore subsequently the
 * class works in a completely deterministic way.
 */
public class RandomLookupIndexer {
	private int[] entries;
	private int mask;

    /**
     * Create an instance, with a given number of entries.
     * @param ids the IDoublesSource to use as entropy source
     * @param slots the number of entries (which must be a power of two)
     */
	public RandomLookupIndexer(IDoublesSource ids, int slots) {
		if (!isPowerOf2(slots))
			throw new IllegalArgumentException("Slots parameter must be a power of 2");

		entries = new int[slots];
		mask = slots - 1;

		for (int i = 0; i < slots; i++)
			entries[i] = i;
		Shuffler.shuffle(ids, entries);
	}

    /**
     * Return the number of slots.
     * @return 
     */
    public int getSlotCount() {
        return entries.length;
    }

    /**
     * Return a randomized entry.
     * @param i the "x-index" of the entry
     * @return 
     */
	public int lookup(int i) {
		return entries[i & mask];
	}

    /**
     * Return a randomized entry.
     * @param i the "x-index" of the entry
     * @param j the "y-index" of the entry
     * @return 
     */
	public int lookup(int i, int j) {
		return entries[(j + lookup(i)) & mask];
	}

    /**
     * Return a randomized entry.
     * @param i the "x-index" of the entry
     * @param j the "y-index" of the entry
     * @param k the "z-index" of the entry
     * @return 
     */
	public int lookup(int i, int j, int k) {
		return entries[(k + lookup(i, j)) & mask];
	}

	private static boolean isPowerOf2(int x) {
        //e.g. x = 100, x - 1 = 011 -> x & x - 1 == 0
        //e.g. x = 110, x - 1 = 101 -> x & x - 1 == 100 != 0
		return (x & (x - 1)) == 0;
	}
}