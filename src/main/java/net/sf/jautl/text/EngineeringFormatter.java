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
package net.sf.jautl.text;
/**
 * This class implements a formatter for engineering notation.
 * 
 * The engineering notation forces exponents to be multiple of 3. E.g.,
 * 100, which would be 1.00E+02 in scientific notation, becomes 100E+0
 */
public class EngineeringFormatter extends NumericFormatter {
	private ShowSignMode showSignMode = ShowSignMode.OnlyIfNegative;
	private char sign;

    /**
     * The mode to use for showing the sign.
     */
    public enum ShowSignMode {
		Never,
		Always,
		OnlyIfNegative
	}

    /**
     * Set the ShowSignMode to use for formatting.
     * @param showSignMode the new value of the ShowSignMode 
     */
	public void setShowSign(ShowSignMode showSignMode) {
		this.showSignMode = showSignMode;
	}

	/**
     * Return the current value of the ShowSignMode used for formatting.
     * @return 
     */
	public ShowSignMode getShowSignMode() {
		return showSignMode;
	}

    /**
     * Perform the formatting process. To scale numbers, the factor 1000 is used.
     * @param value the value to format
     */
    public void format(double value) {
    	if (value >= 0)
    		sign = '+';
    	else {
    		sign = '-';
    		value = -value;
    	}
 
		exponent = 0;

		if (value == 0) {
    		mantissa = 0;
    	} else if (value >= 1000) {
    		while (value >= 1000) {
    			value /= 1000;
    			exponent += 3;
    		}
    	} else {
    		while (value < 1) {
    			value *= 1000;
    			exponent -= 3;
    		}
    	}
		
		mantissa = value;
    }

    /**
     * Return the sign of the formatted value.
     * @return 
     */
    public String getSign() {
    	return Character.toString(sign);
    }
    
    /**
     * Build a string representation of the formatted number.
     * @return the resulting text
     */
    @Override
    public String buildString() {
    	StringBuilder SB = new StringBuilder();
    	
    	if (
			(sign == '-' && showSignMode != ShowSignMode.Never) ||
			showSignMode == ShowSignMode.Always
    	   )
    		SB.append(getSign());
    	
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
