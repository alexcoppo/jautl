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
package net.sf.jautl.text;

/**
 * This class implements a formatter for numbers related to computers.
 */
public class ProgrammerFormatter extends NumericFormatter {
    /**
     * Perform the formatting process. To scale numbers, the factor 1024 is used.
     * @param value the value to format
     */
    public void format(long value) {
		exponent = 0;

		if (value == 0) {
    		mantissa = 0;
    	} else if (value >= 1024) {
    		while (value >= 1024) {
    			value /= 1024;
    			exponent += 3;
    		}
    	}
		
		mantissa = value;
    }

    /**
     * Build a string representation of the formatted number.
     * @return the resulting text
     */
    @Override
    public String buildString() {
    	StringBuilder SB = new StringBuilder();
    	
    	SB.append(getMantissa(mantissaDigits));
    	
		SB.append(" ");
		switch (unitMode) {
		case Symbol:
    		SB.append(SIPrefixes.lookupSymbol(exponent));
			break;
		case Prefix:
    		SB.append(SIPrefixes.lookupPrefix(exponent));
			break;
		}

    	return SB.toString();
    }
}
