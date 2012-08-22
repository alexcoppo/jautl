/*
    Copyright (c) 2000-2012 Alessandro Coppo
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
package net.sf.jautl.utility.commandline;

/**
 * Instances of this class describe parameters, that is tags which require
 * a value.
 */
public class Parameter extends Option {
    private String value = "";
    private String defValue;

    /**
     * Constructs a parameter assigning it a tag, possibily a default
     * value and a help message.
     * @param tag the flag tag.
     * @param defValue the default value of this parameter.
     * @param help the help message string associated with this flag.
     */
    public Parameter(String tag, String defValue, String help) {
        super(tag, help);
        this.defValue = defValue;
    }

    /**
     * Sets the value of this parameter. This method is to be used only
     * by the CommandLineParser.
     * @param value the value of this parameter.
     */
    public final void setValue(String value) {
        this.value = value;
    }

    /**
     * Return whether this parameter has a default value.
     * @return whether this parameter has a default value
     */
    public final boolean hasDefault() {
        return !defValue.equals("");
    }

    /**
     * Return the default value of this parameter.
     * @return the default value of this parameter
     */
    public final String getDefault() {
        return defValue;
    }

    /**
     * Return the String value of this option. If the value
     * is missing but the option has a default, the default is
     * returned. If the option is missing and there is no default
     * value, SyntaxException is thrown.
     * @throws SyntaxException the exception thrown in case the option needs a
     * parameter and neither the default nor a value has been specified
     * @return the String value of this option
     */
    public final String getString() throws SyntaxException {
        if (!value.equals(""))
            return value;
        else if (hasDefault())
            return defValue;
        else
            throw new SyntaxException("Missing value for tag:" + getTag());
    }

    /**
     * Return the conversion (if possible) to byte of the String value.
     * @throws SyntaxException the exception thrown in case the option parameter
     * does not correctly parse as a byte
     * @return the value of the parameter
     */
    public final byte getByte() throws SyntaxException {
        return Byte.parseByte(getString());
    }

    /**
     * Return the conversion (if possible) to short of the String value.
     * @throws SyntaxException the exception thrown in case the option parameter
     * does not correctly parse as a short
     * @return the value of the parameter
     */
    public final short getShort() throws SyntaxException {
        return Short.parseShort(getString());
    }

    /**
     * Return the conversion (if possible) to integer of the String value.
     * @throws SyntaxException the exception thrown in case the option parameter
     * does not correctly parse as an integer
     * @return the value of the parameter
     */
    public final int getInt() throws SyntaxException {
        return Integer.parseInt(getString());
    }

    /**
     * Return the conversion (if possible) to long of the String value.
     * @throws SyntaxException the exception thrown in case the option parameter
     * does not correctly parse as a long
     * @return the value of the parameter
     */
    public final long getLong() throws SyntaxException {
        return Long.parseLong(getString());
    }

    /**
     * Return the conversion (if possible) to float of the String value.
     * @throws SyntaxException the exception thrown in case the option parameter
     * does not correctly parse as a float
     * @return the value of the parameter
     */
    public final float getFloat() throws SyntaxException {
        return Float.parseFloat(getString());
    }

    /**
     * Return the conversion (if possible) to double of the String value.
     * @throws SyntaxException the exception thrown in case the option parameter
     * does not correctly parse as a double
     * @return the value of the parameter
     */
    public final double getDouble() throws SyntaxException {
        return Double.parseDouble(getString());
    }
}