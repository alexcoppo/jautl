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
 * This interface describes the protocol of marshalling data to/fro strings.
 * 
 * @author acoppo
 */
public interface StringMarshaller {
    /**
     * 
     * @param value the value to be marshalled
     * @return the result of the conversion to string
     */
    String marshall(String value);

    /**
     * 
     * @param value the string to be unmarshalled
     * @param defValue the value to be returned in case of null string or
     * format conversion error
     * @return 
     */
    String asString(String value, String defValue);

    /**
     * 
     * @param value the value to be marshalled
     * @return the result of the conversion to string
     */
    String marshall(boolean value);

    /**
     * 
     * @param value the string to be unmarshalled
     * @param defValue the value to be returned in case of null string or
     * format conversion error
     * @return 
     */
    boolean asBoolean(String value, boolean defValue);

    /**
     * 
     * @param value the value to be marshalled
     * @return the result of the conversion to string
     */
    String marshall(byte value);

    /**
     * 
     * @param value the string to be unmarshalled
     * @param defValue the value to be returned in case of null string or
     * format conversion error
     * @return 
     */
    byte asByte(String value, byte defValue);

    /**
     * 
     * @param value the value to be marshalled
     * @return the result of the conversion to string
     */
    String marshall(char value);

    /**
     * 
     * @param value the string to be unmarshalled
     * @param defValue the value to be returned in case of null string or
     * format conversion error
     * @return 
     */
    char asChar(String value, char defValue);
    
    /**
     * 
     * @param value the value to be marshalled
     * @return the result of the conversion to string
     */
    String marshall(short value);

    /**
     * 
     * @param value the string to be unmarshalled
     * @param defValue the value to be returned in case of null string or
     * format conversion error
     * @return 
     */
    short asShort(String value, short defValue);
    
    /**
     * 
     * @param value the value to be marshalled
     * @return the result of the conversion to string
     */
    String marshall(int value);

    /**
     * 
     * @param value the string to be unmarshalled
     * @param defValue the value to be returned in case of null string or
     * format conversion error
     * @return 
     */
    int asInteger(String value, int defValue);

    /**
     * 
     * @param value the value to be marshalled
     * @return the result of the conversion to string
     */
    String marshall(long value);

    /**
     * 
     * @param value the string to be unmarshalled
     * @param defValue the value to be returned in case of null string or
     * format conversion error
     * @return 
     */
    long asLong(String value, long defValue);

    /**
     * 
     * @param value the value to be marshalled
     * @return the result of the conversion to string
     */
    String marshall(float value);

    /**
     * 
     * @param value the string to be unmarshalled
     * @param defValue the value to be returned in case of null string or
     * format conversion error
     * @return 
     */
    float asFloat(String value, float defValue);

    /**
     * 
     * @param value the value to be marshalled
     * @return the result of the conversion to string
     */
    String marshall(double value);

    /**
     * 
     * @param value the string to be unmarshalled
     * @param defValue the value to be returned in case of null string or
     * format conversion error
     * @return 
     */
    double asDouble(String value, double defValue);
}
