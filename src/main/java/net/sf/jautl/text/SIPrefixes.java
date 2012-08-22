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
package net.sf.jautl.text;

import java.util.HashMap;

/**
 * This class encapsulates SI unit prefixes.
 */
public class SIPrefixes {
    /**
     * The exponent-&gt;symbol map.
     */
    public static final HashMap<Integer, String> SYMBOL;

    /**
     * The exponent-&gt;prefix map.
     */
    public static final HashMap<Integer, String> PREFIX;

    /**
     * Load the maps.
     */
    static {
        SYMBOL = new HashMap<Integer, String>();
        PREFIX = new HashMap<Integer, String>();

        register( 24, "Y", "yotta");
        register( 21, "Z", "zetta");
        register( 18, "E", "exa"  );
        register( 15, "P", "peta" );
        register( 12, "T", "tera" );
        register(  9, "G", "giga" );
        register(  6, "M", "mega" );
        register(  3, "k", "kilo" );
        register(  0, "" , "");
        register(- 3, "m", "milli");
        register(- 6, "u", "micro");
        register(- 9, "n", "nano" );
        register(-12, "p", "pico" );
        register(-15, "f", "femto");
        register(-18, "a", "atto" );
        register(-21, "z", "zepto");
        register(-24, "y", "yocto");
    }

    private static void register(int scale, String prefix, String name) {
        SYMBOL.put(scale, prefix);
        PREFIX.put(scale, name);
    }

    /**
     * Lookup a symbol given the exponent.
     * The empty string is returned if the exponent is smaller than -24 or
     * larger than 24.
     * @param exponent the exponent
     * @return 
     */
    public static String lookupSymbol(int exponent) {
        return (Math.abs(exponent) <= 24) ? SIPrefixes.SYMBOL.get(exponent) : "";
    }

    /**
     * Lookup a prefix given the exponent.
     * The empty string is returned if the exponent is smaller than -24 or
     * larger than 24.
     * @param exponent the exponent
     * @return 
     */
    public static String lookupPrefix(int exponent) {
        return (Math.abs(exponent) <= 24) ? SIPrefixes.PREFIX.get(exponent) : "";
    }
}
