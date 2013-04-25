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
package net.sf.jautl.json;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * This class provides a fluent style wrapper on JSONArray.
 */
public class JSONArrayBuilder {
    private JSONArray arr;
    private int firstFree;

    /**
     * The constructor.
     */
    public JSONArrayBuilder() {
        arr = new JSONArray();
        firstFree = 0;
    }

    /**
     * The constructor.
     *
     * @param arr the array to use to initialize this instance
     */
    public JSONArrayBuilder(JSONArray arr) {
        this();
        append(arr);
    }
    
    /**
     * Append an entry to the array.
     * 
     * The indexes a are used sequentially starting from 0.
     * @param obj the object to be added
     * @return the builder instance
     */
    public JSONArrayBuilder append(Object obj) {
        try {
            arr.put(firstFree++, obj);
        } catch (JSONException ex) {
           ;//TODO fix me
        }
        
        return this;
    }

    /**
     * Append all instances of a vector to this builder.
     * 
     * @param vec the vector of the data to be appended
     * @return the builder instance
     */
    public JSONArrayBuilder append(Object[] vec) {
        for (int index = 0; index < vec.length; index++)
            append(vec[index]);
        
        return this;
    }
    
    /**
     * Return the underlying array.
     * 
     * @return the underlying array
     */
    public JSONArray getArray() {
        return arr;
    }
    
    /**
     * Convert the underlying array to its string representation.
     * 
     * @return the string representation of the underlying array
     */
    public String asString() {
        return arr.toString();
    }
}
