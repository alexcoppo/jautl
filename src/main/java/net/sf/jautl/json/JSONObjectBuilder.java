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

import java.util.Iterator;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * This class provides a fluent style wrapper on JSONObject.
 */
public class JSONObjectBuilder {
    private JSONObject obj;
    
    /**
     * The constructor.
     */
    public JSONObjectBuilder() {
        reset();
    }

    /**
     * The constructor.
     * 
     * @param obj the object whose properties copy
     */
    public JSONObjectBuilder(JSONObject obj) {
        this();
        merge(obj);
    }

    /**
     * Reset the builder to the initial state.
     * @return the builder instance
     */
    public JSONObjectBuilder reset() {
        obj = new JSONObject();

        return this;
    }
    
    /**
     * Set an entry of the assiciative array to a value.
     * 
     * @param key the key
     * @param value the value to set
     * @return the builder instance
     */
    public JSONObjectBuilder put(String key, Object value) {
        try {
            obj.put(key, value);
        } catch (JSONException ex) {
           ;//TODO fix me
        }
        
        return this;
    }

    /**
     * Merge all entries of an object into this builder. Items already existing
     * are overwritten.
     * 
     * @param obj the object whose items to copy
     * @return the builder instance
     */
    public JSONObjectBuilder merge(JSONObject obj) {
        try {
            Iterator itr = obj.keys();
            for (itr.hasNext();;) {
                String key = (String)itr.next();
                put(key, obj.get(key));
            }
        } catch (JSONException ex) {
           ;//TODO fix me
        }
        
        return this;
    }
    
    /**
     * Return the underlying object.
     * 
     * @return the underlying object
     */
    public JSONObject getObject() {
        return obj;
    }
    
    /**
     * Convert the underlying object to its string representation.
     * 
     * @return the string representation of the underlying object
     */
    public String asString() {
        return obj.toString();
    }
}
