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
package net.sf.jautl.collections.arrays;

import org.testng.Assert;
import org.testng.annotations.*;

public class TestCombSorter11 {
	private int[] numbers;

	@BeforeMethod
	public void allocate() {
		numbers = new int[20];
	}

	@AfterMethod
	public void free() {
		numbers = null;
	}
	
	@Test
	public void testInOrder() {
		for (int i = 0; i < numbers.length; i++)
			numbers[i] = i;
			
		SA sa = new SA(numbers);
		CombSorter11.sort(numbers.length, sa);
		
		checkAllPresentNoDuplicates();
		checkOrder();
	}
	
	@Test
	public void testInOrderInline() {
		for (int i = 0; i < numbers.length; i++)
			numbers[i] = i;
			
		CombSorter11.sort(numbers.length, new CombSorter11APIAdapter() {
			public boolean areOrdered(int i, int j) {
				return numbers[i] <= numbers[j];
			}

			public void exchange(int i, int j) {
				int tmp = numbers[i];
				numbers[i] = numbers[j];
				numbers[j] = tmp;
			}
		});
		
		checkAllPresentNoDuplicates();
		checkOrder();
	}
	
	@Test
	public void testReverseOrder() {
		for (int i = 0; i < numbers.length; i++)
			numbers[i] = numbers.length - 1 - i;
			
		SA sa = new SA(numbers);
		CombSorter11.sort(numbers.length, sa);
		
		checkAllPresentNoDuplicates();
		checkOrder();
	}

	@Test
	public void testMixedOrder() {
		int index = 0;
		for (int i = 0, j = numbers.length - 1; i < j; i++, j--) {
			numbers[i] = index++;
			numbers[j] = index++;
		}
			
		SA sa = new SA(numbers);
		CombSorter11.sort(numbers.length, sa);
		
		checkAllPresentNoDuplicates();
		checkOrder();
	}
	
	private void checkAllPresentNoDuplicates() {
		for (int soughtFor = 0; soughtFor < numbers.length; soughtFor++) {
			int count = 0;
			for (int index = 0; index < numbers.length; index++)
				if (numbers[index] == soughtFor)
					count++;
			
			Assert.assertTrue(count == 1);
		}
	}
	
	private void checkOrder() {
		for (int i = 0; i < numbers.length - 1; i++)
			Assert.assertTrue(numbers[i] <=  numbers[i + 1]);
	}

	private class SA implements IndexedCollectionSortAPI {
		private int[] numbers;
		
		public SA(int[] numbers) {
			this.numbers = numbers;
		}
		
		public boolean areOrdered(int i, int j) {
			return numbers[i] <= numbers[j];
		}

		public void exchange(int i, int j) {
			int tmp = numbers[i];
			numbers[i] = numbers[j];
			numbers[j] = tmp;
		}
	}
}
