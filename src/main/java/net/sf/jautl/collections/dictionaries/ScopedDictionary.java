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
package net.sf.jautl.collections.dictionaries;

import java.util.HashMap;

/**
 * This class implements a scoped chain of String -> Value maps. 
 */
public class ScopedDictionary {
    private LinkedDictionaryScope local;
    private LinkedDictionaryScope global;

    /**
     * The constructor.
     */
    public ScopedDictionary() {
    	clear();
    }

    /**
     * Reset the chain to a single empty map.
     */
    public void clear() {
    	local = new LinkedDictionaryScope();
        global = local;
    }

    /**
     * Check wheter there is only one scope.
     * @return the result of the test
     */
    public boolean isSingle() {
    	return local == global;
    }
    
    /**
     * Create a new scope.
     */
    public void createScope() {
    	local = new LinkedDictionaryScope(local);
    }

    /**
     * Delete most recently created scope.
     * If only one scope, reinit the chain.
     */
    public void deleteScope() {
    	if (isSingle())
    		clear();
    	else
    		local = local.getParent();
    }

    /**
     * Check whether a variable exists in local scope.
     * @param name the name of the variable
     * @return the result of the test
     */
    public boolean existsLocal(String name) {
        return local.containsKey(name);
    }

    /**
     * Check whether a variable exists in global scope.
     * @param name the name of the variable
     * @return the result of the test
     */
    public boolean existsGlobal(String name) {
        return global.containsKey(name);
    }

    /**
     * Check whether a variable exists somewhere in the scope list.
     * @param name the name of the variable
     * @return the result of the test
     */
    public boolean exists(String name) {
        for (LinkedDictionaryScope current = local; current != null; current = current.getParent())
            if (current.containsKey(name))
                return true;

        return false;
    }

    /**
     * Create a new local variable; it should not already exists in local scope.
     * @param name the name of the variable
     * @throws RuntimeException
     */
    public void declareLocal(String name) throws RuntimeException {
        if (!existsLocal(name))
        	local.put(name, null);
        else
            throw new RuntimeException(name + " already exists");
    }

    /**
     * Create a new global variable; it should not already exists in global scope.
     * @param name the name of the variable
     * @throws RuntimeException
     */
    public void declareGlobal(String name) throws RuntimeException {
        if (!existsGlobal(name))
            global.put(name, null);
        else
            throw new RuntimeException(name + " already exists");
    }

    /**
     * Set an entry to a value in the local scope.
     * If the entry does not exist, an exception is thrown.
     * @param name the name of the entry
     * @param value the new value of the entry
     * @throws RuntimeException
     */
    public void setLocal(String name, Object value) throws RuntimeException {
        if (local.containsKey(name))
        	local.put(name, value);
        else
        	throw new RuntimeException(name + " does not exists");
    }

    /**
     * Set an entry to a value in the global scope.
     * If the entry does not exist, an exception is thrown.
     * @param name the name of the entry
     * @param value the new value of the entry
     * @throws RuntimeException
     */
    public void setGlobal(String name, Object value) throws RuntimeException {
        if (global.containsKey(name))
            global.put(name, value);
        else
        	throw new RuntimeException(name + " does not exists");
    }

    /**
     * Set a variable to a new value.
     * Scopes are explored from local to global, if the name is not found anywhere,
     * an exception is thrown.
     * @param name the name of the variable
     * @param value the new value of the variable
     * @throws RuntimeException
     */
    public void set(String name, Object value) throws RuntimeException {
        for (LinkedDictionaryScope current = local; current != null; current = current.getParent())
            if (current.containsKey(name)) {
            	current.put(name, value);
                return;
            }

        throw new RuntimeException(name + " does not exists");
    }

    /**
     * Return an item from the local scope given its name; if not found, return null.
     * @param name the name of the item to search
     * @return the value of the item or null if not found
     */
    public Object getLocal(String name) {
        if (local.containsKey(name))
            return local.get(name);
        else
        	return null;
    }

    /**
     * Return an item from the global scope given its name; if not found, return null.
     * @param name the name of the item to search
     * @return the value of the item or null if not found
     */
    public Object getGlobal(String name) {
        if (global.containsKey(name))
            return global.get(name);
        else
        	return null;
    }

    /**
     * Return an item given its name; if not found, return null.
     * If the item is not present in the current scope, lookup on parent scope (up to the root).
     * @param name the name of the item to search
     * @return the value of the object if found or null
     */
    public Object get(String name) {
        for (LinkedDictionaryScope current = local; current != null; current = current.getParent())
            if (current.containsKey(name))
                return current.get(name);

        return null;
    }

    /**
     * Remove a variable in local scope.
     * @param name the name of the variable to remove
     */
    public void removeLocal(String name) {
    	local.remove(name);
    }

    /**
     * Remove all variables in current scope.
     */
    public void removeLocalAll() {
    	local.clear();
    }

    /**
     * Remove all variables in global scope.
     */
    public void removeGlobal(String name) {
        global.remove(name);
    }

    /**
     * Remove all variables in global scope.
     */
    public void removeGlobalAll() {
        global.clear();
    }

    @SuppressWarnings("serial")
	private class LinkedDictionaryScope extends HashMap<String, Object> {
    	private LinkedDictionaryScope parent;
    	
    	public LinkedDictionaryScope() {
    		parent = null;
    	}
    	
    	public LinkedDictionaryScope(LinkedDictionaryScope parent) {
    		this.parent = parent;
    	}
    	
    	public LinkedDictionaryScope getParent() {
    		return parent;
    	}
    }
}
