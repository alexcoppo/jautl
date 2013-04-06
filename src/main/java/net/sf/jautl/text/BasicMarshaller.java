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
 * This class implements basic marshalling on strings.
 */
public class BasicMarshaller implements StringMarshaller {
    final int NaN_BITS_INT = Float.floatToIntBits(Float.NaN);
    final long NaN_BITS_LONG = Double.doubleToLongBits(Double.NaN);

    @Override
    public String marshall(String value) {
        return value;
    }

    @Override
    public String asString(String value, String defValue) {
        if (value == null || value.equals("")) return defValue;
        
        return value;
    }

    @Override
    public String marshall(boolean value) {
        return Boolean.toString(value);
    }

    @Override
    public boolean asBoolean(String value, boolean defValue) {
        if (value == null || value.equals("")) return defValue;
        
        return Boolean.parseBoolean(value);
    }

    @Override
    public String marshall(byte value) {
        return Byte.toString(value);
    }

    @Override
    public byte asByte(String value, byte defValue) {
        if (value == null || value.equals("")) return defValue;
        
        try {
            return Byte.parseByte(value);
        } catch (NumberFormatException nfe) {
            return defValue;
        }
    }

    @Override
    public String marshall(char value) {
        return Character.toString(value);
    }

    @Override
    public char asChar(String value, char defValue) {
        if (value == null || value.equals("")) return defValue;
        
        return value.charAt(0);
    }

    @Override
    public String marshall(short value) {
        return Short.toString(value);
    }

    @Override
    public short asShort(String value, short defValue) {
        if (value == null || value.equals("")) return defValue;
        
        try {
            return Short.parseShort(value);
        } catch (NumberFormatException nfe) {
            return defValue;
        }
    }

    @Override
    public String marshall(int value) {
        return Integer.toString(value);
    }

    @Override
    public int asInteger(String value, int defValue) {
        if (value == null || value.equals("")) return defValue;
        
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException nfe) {
            return defValue;
        }
    }

    @Override
    public String marshall(long value) {
        return Long.toString(value);
    }

    @Override
    public long asLong(String value, long defValue) {
        if (value == null || value.equals("")) return defValue;
        
        try {
            return Long.parseLong(value);
        } catch (NumberFormatException nfe) {
            return defValue;
        }
    }

    @Override
    public String marshall(float value) {
        return marshall(Float.floatToIntBits(value));
    }

    @Override
    public float asFloat(String value, float defValue) {
        if (value == null || value.equals("")) return defValue;
        
        int bits = asInteger(value, NaN_BITS_INT);
        
        return (bits == NaN_BITS_INT) ? defValue : Float.intBitsToFloat(bits);
    }

    @Override
    public String marshall(double value) {
        return marshall(Double.doubleToLongBits(value));
    }

    @Override
    public double asDouble(String value, double defValue) {
        if (value == null || value.equals("")) return defValue;
        
        long bits = asLong(value, NaN_BITS_LONG);
        
        return (bits == NaN_BITS_LONG) ? defValue : Double.longBitsToDouble(bits);
    }
}
