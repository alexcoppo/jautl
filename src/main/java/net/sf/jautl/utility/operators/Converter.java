package net.sf.jautl.utility.operators;

/**
 * This class describes the generic conversion operator between two objects.
 * @param <DstType> the type of the destination
 * @param <SrcType> the type of the source
 */
public abstract class Converter<DstType, SrcType> {
    /** The source instance. */
	protected SrcType src;
    /** The destination instance. */
	protected DstType dst;
	
    /**
     * Return the source.
     */
	public SrcType getSource() {
		return src;
	}

    /**
     * Set the source.
     * @param src the new value for the source.
     */
	public void setSource(SrcType src) {
		this.src = src;
	}

    /**
     * Return the destination.
     */
	public DstType getDestination() {
		return dst;
	}

    /**
     * Set the destination.
     * @param dst the new value of the destination.
     */
	public void setDestination(DstType dst) {
		this.dst = dst;
	}
	
    /**
     * Perform the conversion process.
     */
	public void convert() {
		setupDestination();
		setupOperator();
		convertImpl();
		teardownOperator();
	}
	
    /**
     * Placeholder for the initialization of the destination.
     */
	protected void setupDestination() {
	}
	
    /**
     * Placeholder for the initialization of the operator itself.
     */
    protected void setupOperator() {
    }

    /**
     * The actual conversion implementation.
     */
	protected abstract void convertImpl();

    /**
     * Placeholder for the cleanup of the operator.
     */
    protected void teardownOperator() {
    }
}
