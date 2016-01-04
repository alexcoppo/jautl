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
package net.sf.jautl.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A few JSON utility calls to map exceptions into nulls.
 */
public class JSONUtils {
	/**
	 * Check whether an attribute exists within JSON object
	 * @param obj the object to check
	 * @param name the name of the attribute
	 * @return
	 */
	public static boolean existsAttribute(JSONObject obj, String name) {
		return obj.has(name);
	}

	public static boolean isJSONObject(JSONObject obj, String name) {
		return isClass(obj, name, JSONObject.class);
	}

	public static boolean isJSONObject(JSONArray array, int index) {
		return isClass(array, index, JSONObject.class);
	}

	public static boolean isJSONArray(JSONObject obj, String name) {
		return isClass(obj, name, JSONArray.class);
	}

	public static boolean isJSONArray(JSONArray array, int index) {
		return isClass(array, index, JSONArray.class);
	}
	
	public static boolean isClass(JSONObject obj, String name, Class<?> clazz) {
		if (!existsAttribute(obj, name))
			return false;
		
		try {
			return obj.get(name).getClass().equals(clazz);
		} catch (JSONException e) {
			return false;
		}
	}
	
	/**
	 * Checks whether an entry in a JSON array is of a specific class.
	 * @param array the array containing the entry
	 * @param index the index of the entry
	 * @param clazz the class to check
	 */
	public static boolean isClass(JSONArray array, int index, Class<?> clazz) {
		try {
			return array.get(index).getClass().equals(clazz);
		} catch (JSONException e) {
			return false;
		}
	}
	
	/**
	 * Get a value. If the value is missing or there are exceptions, defValue
	 * is returned.
	 * @param obj the object to access
	 * @param name the name of the attribute
	 * @param defValue the default value
	 */
	public static boolean getBoolean(JSONObject obj, String name, boolean defValue) {
		if (!existsAttribute(obj, name))
			return defValue;

		try {
			boolean value = obj.getBoolean(name);
			return value;
		} catch (JSONException e) {
			return defValue;
		}
	}

	/**
	 * Get a value. If the value there are exceptions, defValue is returned.
	 * @param array the array to access
	 * @param index the index of the element
	 * @param defValue the default value
	 */
	public static boolean getBoolean(JSONArray array, int index, boolean defValue) {
		try {
			boolean value = array.getBoolean(index);
			return value;
		} catch (JSONException e) {
			return defValue;
		}
	}

	/**
	 * Get a value. If the value is missing or there are exceptions, defValue
	 * is returned.
	 * @param obj the object to access
	 * @param name the name of the attribute
	 * @param defValue the default value
	 */
	public static String getString(JSONObject obj, String name, String defValue) {
		if (!existsAttribute(obj, name))
			return defValue;

		try {
			String value = obj.getString(name);
			return value;
		} catch (JSONException e) {
			return defValue;
		}
	}
	
	/**
	 * Get a value. If the value there are exceptions, defValue is returned.
	 * @param array the array to access
	 * @param index the index of the element
	 * @param defValue the default value
	 */
	public static String getString(JSONArray array, int index, String defValue) {
		try {
			String value = array.getString(index);
			return value;
		} catch (JSONException e) {
			return defValue;
		}
	}
	
	/**
	 * Get a value. If the value is missing or there are exceptions, defValue
	 * is returned.
	 * @param obj the object to access
	 * @param name the name of the attribute
	 * @param defValue the default value
	 */
	public static int getInteger(JSONObject obj, String name, int defValue) {
		if (!existsAttribute(obj, name))
			return defValue;

		try {
			int value = obj.getInt(name);
			return value;
		} catch (JSONException e) {
			return defValue;
		}
	}
	
	/**
	 * Get a value. If the value there are exceptions, defValue is returned.
	 * @param array the array to access
	 * @param index the index of the element
	 * @param defValue the default value
	 */
	public static int getInteger(JSONArray array, int index, int defValue) {
		try {
			int value = array.getInt(index);
			return value;
		} catch (JSONException e) {
			return defValue;
		}
	}
	
	/**
	 * Get a value. If the value is missing or there are exceptions, defValue
	 * is returned.
	 * @param obj the object to access
	 * @param name the name of the attribute
	 * @param defValue the default value
	 * @return
	 */
	public static long getLong(JSONObject obj, String name, long defValue) {
		if (!existsAttribute(obj, name))
			return defValue;

		try {
			long value = obj.getLong(name);
			return value;
		} catch (JSONException e) {
			return defValue;
		}
	}
	
	/**
	 * Get a value. If the value there are exceptions, defValue is returned.
	 * @param array the array to access
	 * @param index the index of the element
	 * @param defValue the default value
	 * @return
	 */
	public static long getLong(JSONArray array, int index, long defValue) {
		try {
			long value = array.getLong(index);
			return value;
		} catch (JSONException e) {
			return defValue;
		}
	}
	
	/**
	 * Get a value. If the value is missing or there are exceptions, defValue
	 * is returned.
	 * @param obj the object to access
	 * @param name the name of the attribute
	 * @param defValue the default value
	 * @return
	 */
	public static double getFloat(JSONObject obj, String name, float defValue) {
		if (!existsAttribute(obj, name))
			return defValue;

		try {
			double value = obj.getDouble(name);
			return (float)value;
		} catch (JSONException e) {
			return defValue;
		}
	}
	
	/**
	 * Get a value. If the value there are exceptions, defValue is returned.
	 * @param array the array to access
	 * @param index the index of the element
	 * @param defValue the default value
	 */
	public static float getFloat(JSONArray array, int index, float defValue) {
		try {
			double value = array.getDouble(index);
			return (float)value;
		} catch (JSONException e) {
			return defValue;
		}
	}
	
	/**
	 * Get a value. If the value is missing or there are exceptions, defValue
	 * is returned.
	 * @param obj the object to access
	 * @param name the name of the attribute
	 * @param defValue the default value
	 */
	public static double getDouble(JSONObject obj, String name, double defValue) {
		if (!existsAttribute(obj, name))
			return defValue;

		try {
			double value = obj.getDouble(name);
			return value;
		} catch (JSONException e) {
			return defValue;
		}
	}
	
	/**
	 * Get a value. If the value there are exceptions, defValue is returned.
	 * @param array the array to access
	 * @param index the index of the element
	 * @param defValue the default value
	 */
	public static double getDouble(JSONArray array, int index, double defValue) {
		try {
			double value = array.getDouble(index);
			return value;
		} catch (JSONException e) {
			return defValue;
		}
	}
	
	/**
	 * Get an object within another object. If is does not exists or there are
	 * exceptions, default value is returned.
	 * @param obj the object to access
	 * @param name the name of the attribute
	 * @param defValue the default value
	 */
    public static JSONObject getObject(JSONObject obj, String name, JSONObject defValue) {
		if (!existsAttribute(obj, name))
			return defValue;

		try {
			JSONObject subObj = obj.getJSONObject(name);
			return subObj;
		} catch (JSONException e) {
			return defValue;
		}
    }
    
	/**
	 * Get an array within another object. If is does not exists or there are
	 * exceptions, default value is returned.
	 * @param obj the object to access
	 * @param name the name of the attribute
	 * @param defValue the default value
	 */
    public static JSONArray getArray(JSONObject obj, String name, JSONArray defValue) {
		try {
			JSONArray array = obj.getJSONArray(name);
			return array;
		} catch (JSONException e) {
			return defValue;
		}
    }
    
    /**
	 * Get an object in an array. If there are exceptions, default value is returned.
	 * @param array the array to access
	 * @param index the index of the element
	 * @param defValue the default value
     */
    public static JSONObject getObject(JSONArray arr, int index, JSONObject defValue) {
		try {
			JSONObject obj = arr.getJSONObject(index);
			return obj;
		} catch (JSONException e) {
			return defValue;
		}
    }
}
