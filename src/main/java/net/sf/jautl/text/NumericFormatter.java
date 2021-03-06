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
 * This class is the basis for all formatters which handle separately
 * mantissa and exponent. 
 */
public abstract class NumericFormatter {
	/** The mantissa of the number. */
    protected double mantissa;
	/** The exponent of the number. */
    protected int exponent;
    /** The mode of scale decoding. */
    protected UnitMode unitMode = UnitMode.Symbol;
    
    protected int mantissaDigits = 0;
    
    /**
     * The kind of scale indication.
     */
    public enum UnitMode {
    	Symbol,
    	Prefix
    }

    public final UnitMode getUnitMode() {
		return unitMode;
	}

	public final void setUnitMode(UnitMode unitMode) {
		this.unitMode = unitMode;
	}

	public final int getMantissaDigits() {
		return mantissaDigits;
	}

	public final void setMantissaDigits(int mantissaDigits) {
		this.mantissaDigits = mantissaDigits;
	}

	/**
     * Return the mantissa of the formatted number.
     * @return the mantissa
     */
    public double getMantissa() {
    	return mantissa;
    }
    
    /**
     * Return a string representation of the mantissa of the formatted number.
     * @param digits the number of digits in the result
     * @return the mantissa
     */
    public String getMantissa(int digits) {
    	String result = new Double(mantissa).toString() + "000000000000000000000000000000000";
    	return result.substring(0, digits + 1);
    }
    
    /**
     * Return the exponent as a number.
     * @return the exponent
     */
    public int getExponent() {
    	return exponent;
    }

    public abstract String buildString();
}
