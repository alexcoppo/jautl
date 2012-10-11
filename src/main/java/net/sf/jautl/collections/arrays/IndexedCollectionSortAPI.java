package net.sf.jautl.collections.arrays;

/**
 * This interface models the API required to sort a generic integer indexable
 * collection.
 */
public interface IndexedCollectionSortAPI {
	/**
	 * Return whether element i is less or equal to element j.
	 * @param i the first element to compare 
	 * @param j the second element to compare
	 * @return the result of the comparison
	 */
    public boolean areOrdered(int i, int j);

    /**
     * Exchange place between elements i and j.
     * @param i the first element to exchange
     * @param j the second element to exchange
     */
    public void exchange(int i, int j);
}
