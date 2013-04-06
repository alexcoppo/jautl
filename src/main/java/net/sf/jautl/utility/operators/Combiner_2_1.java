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
