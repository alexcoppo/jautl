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
package net.sf.jautl.collections.arrays;

/**
 * The Combsort 11 sorting algorithm.
 */
public class CombSorter11 {
	/**
	 * The sort method.
	 * @param count the number of entries in the vector to sort
	 * @param comparator the comparator to use for performing the comparisons
	 * @param exchanger the exchanger to use to swap elements
	 */
    public static <T> void sort(int count, IndexedCollectionItemItemComparator comparator, IndexedCollectionItemExchanger exchanger) {
    	ComparisonResult cr = new ComparisonResult();
 
        for (int gap = count; ; ) {
            gap = (int)(gap / 1.247330950103979);
            if (gap == 9 || gap == 10) gap = 11;
            if (gap < 1) gap = 1;

            boolean swapped = false;

            for (int i = 0; i < count - gap; i++) {
                int j = i + gap;

                comparator.compare(i, j, cr);
                if (cr.isGreaterThan()) {
                	exchanger.exchange(i, j);
                    swapped = true;
                }
            }

            if (gap == 1 && !swapped)
                break;
        }
    }
}
