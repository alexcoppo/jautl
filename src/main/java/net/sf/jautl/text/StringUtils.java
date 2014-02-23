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
 * This class contains several String and StringBuffer utility methods.
 */
public final class StringUtils {
    /**
     * Basic-like Left$ function.
     * @param s the string to be segmented
     * @param size the size of the left segment to be returned
     * @return the size-most left characters.
     */
    public static String left(String s, int size) {
        return s.substring(0, Math.min(size, s.length()));
    }

    /**
     * Basic-like Right$ function.
     * @param s the string to be segmented
     * @param size the size of the right segment to be returned
     * @return the size-most right characters.
     */
    public static String right(String s, int size) {
        return s.substring(Math.max(s.length() - size, 0));
    }

    /**
     * Returns the part of the input string <em>after</em> the Separator.
     * Should the separator not be found, the unchanged string is returned.
     * @param src the string to process
     * @param separator the separator sequencue
     * @return the result of the processing
     */
    public static String after(String src, String separator) {
        int pos = src.indexOf(separator);

        if (pos < 1)
            return src;
        else
            return src.substring(pos + separator.length());
    }

    /**
     * Return the part of the input string <em>before</em> the Separator.
     * Should the separator not be found, the unchanged string is returned.
     * @param src the string to process
     * @param separator the separator sequencue
     * @return the result of the processing
     */
    public static String before(String src, String separator) {
        int pos = src.indexOf(separator);

        if (pos < 1)
            return src;
        else
            return src.substring(0, pos);
    }

    /**
     * Create a StringBuffer made up by n characters.
     * @param chars the number of characters
     * @param ch the character to be replicated.
     * @return the new StringBuffer instance 
     */
    public static StringBuffer createStringBuffer(int chars, char ch) {
        StringBuffer sb = new StringBuffer(chars);

        for (int i = 0; i < chars; i++)
            sb.append(ch);

        return sb;
    }

    /**
     * Create a String made up by n characters.
     * @param chars the number of characters
     * @param ch the character to be replicated.
     * @return the new string
     */
    public static String createString(int chars, char ch) {
        return createStringBuffer(chars, ch).toString();
    }

    /**
     * Locate a character in a string buffer.
     * @param sb the StribgBuffer to be searched
     * @param c the sought character
     * @param start the search start position
     * @return the position or -1 if not found
     */
    public static int indexOf(StringBuffer sb, int c, int start) {
        int length = sb.length();
        
        while (start < length) {
            if (sb.charAt(start) == c)
                return start;
            else
                start++;
        }
        
        return -1;
    }

    /**
     * Check whether a string is into a StringBuffer.
     * @param sb the stringbuffer to be searched
     * @param search the target string
     * @param start the position from which start the match
     * @return the result of the match 
     */
    public static boolean match(StringBuffer sb, String search, int start) {
        for (int index = 0;;) {
            if (sb.charAt(start + index) != search.charAt(index))
                return false;
            index++;
            if (index == search.length())
                return true;
            if (index + start == sb.length())
                return false;
        }
    }

    /**
     * Locate a string into a StringBuffer.
     * @param sb the StringBuffer to be searched
     * @param search the target string
     * @param start the location from which start the search
     * @return the location or -1 if not found
     */
    public static int indexOf(StringBuffer sb, String search, int start) {
        int length = sb.length();
        int searchLength = search.length();
        
        while (start <= length - searchLength) {
            if (match(sb, search, start))
                return start;
            start++;
        }
        
        return -1;
    }

    /**
     * Replace all instances of a substrings in a given string.
     * @param source the StringBuffer to be processed
     * @param search the substring to be searched
     * @param replace the string to be replaced (it can be null, in this case
     * is it taken as "")
     * @return the result of the processing
     */
    public static boolean replaceAll(StringBuffer source, String search, String replace) {
        if (replace == null)
            replace = new String();

        boolean modified = false;
        
        for (;;) {
            int pos = indexOf(source, search, 0);
            if (pos == -1)
                break;
            source.replace(pos, pos + search.length(), replace);
            modified = true;
        }
        
        return modified;
    }

    /**
     * Check whether a character is a blank.
     * @param c the character to be checked
     * @return the result of the test
     */
    public static boolean isSpace(char c) {
        return c == ' ' || c == '\n' || c == '\r' || c == '\t';
    }
    
    /**
     * Remove all trailing blanks.
     * @param s the string to be processed
     * @return the result of the processing
     */
    public static String rtrim(String s) {
        int index = s.length() - 1;

        while (index >= 0 && isSpace(s.charAt(index)))
            index--;
        
        return s.substring(0, index + 1);
    }

    /**
     * Remove all leading blanks.
     * @param s the string to be processed
     * @return the result of the processing
     */
    public static String ltrim(String s) {
        int index = 0;

        while (index < s.length() && isSpace(s.charAt(index)))
            index++;

        return s.substring(index, s.length());
    }

    /**
     * Convert a vector of bytes into a regularily formatted string
     * of hexadecimal values.
     * @param b the vector to convert
     * @return the result of the processing
     */
    public static String bytesToHexString(byte[] b) {
        String space = " ";
        StringBuilder sb = new StringBuilder();
        
        for (int index = 0; index < b.length; index++) {
            int tmp = b[index];
            tmp &= 0xFF;
            sb.append("0x");
            if (tmp < 16)
                sb.append(space);
            sb.append(Integer.toHexString(tmp));
            if (index != b.length - 1)
                sb.append(space);
        }

        return sb.toString();
    }

    private StringUtils() {
    }
}