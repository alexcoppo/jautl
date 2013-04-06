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
