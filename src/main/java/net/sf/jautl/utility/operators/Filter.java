package net.sf.jautl.utility.operators;

/**
 * This class describes the general protocol of a filter, i.e. an operator which
 * processes an input giving an output.
 * @param <T> the class of the filtered object
 */
public abstract class Filter<T> {
    /** The source object. */
	protected T src;
    /** The destination object. */
	protected T dst;
	
    /**
     * Return the source.
     */
	public T getSource() {
		return src;
	}

    /**
     * Set the source.
     * @param src the new value for the source
     */
	public void setSource(T src) {
		this.src = src;
	}

    /**
     * Return the destination.
     */
	public T getDestination() {
		return dst;
	}

    /**
     * Set the destination.
     * @param dst the new value for the destination
     */
	public void setDestination(T dst) {
		this.dst = dst;
	}
	
    /**
     * Perform the filtering process.
     */
	public void filter() {
		setupDestination();
		setupOperator();
		filterImpl();
		teardownOperator();
	}
	
    /**
     * Placeholder for the initialization of the destination.
     */
	protected void setupDestination() {
	}

    /**
     * Placeholder for the setup of the operator itself.
     */
    protected void setupOperator() {
    }

    /**
     * The actual implementation of the filter.
     */
	protected abstract void filterImpl();

    /**
     * Placeholder for the cleanup of the operator.
     */
    protected void teardownOperator() {
    }
}
