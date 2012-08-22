package net.sf.jautl.utility.operators;

/**
 * This class describes the protocol of the generic operator to combine 2 objects
 * into a third one.
 *
 * @param <DstType> the type of the destination
 * @param <SrcAType> the type of the first source
 * @param <SrcBType> the type of the second source
 */
public abstract class Combiner_2_1<DstType, SrcAType, SrcBType> {
	/** The first source. */
	protected SrcAType srcA;
	/** The second source. */
	protected SrcBType srcB;
	/** The destination source. */
	protected DstType dst;
	
    /**
     * Return the first source.
     */
	public SrcAType getSourceA() {
		return srcA;
	}

    /**
     * Set the first source.
     * @param srcA the new value for the source
     */
	public void setSourceA(SrcAType srcA) {
		this.srcA = srcA;
	}

    /**
     * Return the second source.
     */
	public SrcBType getSourceB() {
		return srcB;
	}

    /**
     * Set the second source.
     * @param srcB the new value for the source
     */
	public void setSourceB(SrcBType srcB) {
		this.srcB = srcB;
	}

    /**
     * Return the destination.
     */
	public DstType getDestination() {
		return dst;
	}

    /**
     * Set the destination.
     * @param dst the new value for the destination
     */
	public void setDestination(DstType dst) {
		this.dst = dst;
	}
	
    /**
     * Perform the combination process.
     */
	public void combine() {
		setupDestination();
		setupOperator();
		combineImpl();
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
     * The actual implementation of the combine.
     */
	protected abstract void combineImpl();

    /**
     * Placeholder for the cleanup of the operator.
     */
    protected void teardownOperator() {
    }
}
