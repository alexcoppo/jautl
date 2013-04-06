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
package net.sf.jautl.collections;

/**
 * Yet another Pair class.
 * @param <F> the type of the first argument
 * @param <S> the type of the second argument
 */
public class Pair<F,S> {
	private final F first;
	private final S second;

	/**
	 * The constructor.
	 * @param first the value of the first argument
	 * @param second the value of the second argument
	 */
	public Pair(F first, S second) {
		this.first = first;
		this.second = second;
	}

	/**
	 * Return the first argument.
	 */
	public F getFirst() {
		return first;
	}
  
	/**
	 * Return the second argument. 
	 */
	public S getSecond() {
		return second;
	}

	/**
	 * Construct a pair out of two items.
	 * @param first the first item of the pair
	 * @param second the second item of the pair
	 * @return the instance packing the two items
	 */
	public static <F, S> Pair<F, S> of(F first, S second) {
		return new Pair<F, S>(first, second);
	}
	
	/**
	 * The implementation of hashCode of this class.
	 * @return the result of the computation 
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		
		int result = 1;
		
		result = prime * result + ((first == null) ? 0 : first.hashCode());
		result = prime * result + ((second == null) ? 0 : second.hashCode());
		
		return result;
	}

	/**
	 * The implementation of equals of this class.
	 * @param obj the object to compare to
	 * @return the result of the computation 
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		
		if (!(obj instanceof Pair<?, ?>)) return false;
		
		Pair<?, ?> other = (Pair<?, ?>)obj;
		
		if (first == null) {
			if (other.first != null) return false;
		} else if (!first.equals(other.first))
			return false;
		if (second == null) {
			if (other.second != null) return false;
		} else if (!second.equals(other.second))
			return false;
		
		return true;
	}
}
