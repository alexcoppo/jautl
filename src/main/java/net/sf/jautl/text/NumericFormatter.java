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
}
