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
 * This class wraps the result of the comparison between items and provides some
 * utility methods to write more self-documenting code.
 */
public class ComparisonResult {
	private final int LT = 0;
	private final int EQ = 1;
	private final int GT = 2;

	private int result;

	/**
	 * Mark the result as equal.
	 */
	public void markEqual() {
		result = EQ;
	}

	/**
	 * Mark the result as less-than.
	 */
	public void markLessThan() {
		result = LT;
	}

	/**
	 * Mark the result as greater-than.
	 */
	public void markGreaterThan() {
		result = GT;
	}

	/**
	 * Check whether the result was equal.
	 */
	public boolean isEqual() {
		return result == EQ;
	}

	/**
	 * Check whether the result was not equal.
	 */
	public boolean isNotEqual() {
		return result != EQ;
	}

	/**
	 * Check whether the result was less-than.
	 */
	public boolean isLessThen() {
		return result == LT;
	}

	/**
	 * Check whether the result was either equal or less-than.
	 */
	public boolean isLessEqual() {
		return result != GT;
	}

	/**
	 * Check whether the result was greater-than.
	 */
	public boolean isGreaterThan() {
		return result == GT;
	}

	/**
	 * Check whether the result was either equal or greater-than.
	 */
	public boolean isGreaterEqual() {
		return result != LT;
	}
}
