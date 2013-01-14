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
