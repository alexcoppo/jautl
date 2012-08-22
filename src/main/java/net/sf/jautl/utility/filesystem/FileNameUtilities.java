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
package net.sf.jautl.utility.filesystem;

/**
 * This class contains utility functions for processing file names.
 */
public class FileNameUtilities {
	/**
	 * Return the extension of the filename or empty string if none.
	 * @param filename the filename to process
	 * @return the extension of the filename
	 */
    public static String getExtension(String filename) {
        int pos = filename.lastIndexOf(".");

        return (pos != -1) ? filename.substring(pos) : "";
    }

    /**
     * Remove the extension from a filename, returning the resulting string.
     * @param filename the filename to process
	 * @return the filename without the extension
     */
    public static String removeExtension(String filename) {
        int pos = filename.lastIndexOf(".");

        return (pos != -1) ? filename.substring(0, pos) : filename;
    }

    /**
     * Check whether the filename terminates with a given extension.
     * The comparison is case sensitive.
     * @param filename the filename to check
     * @param extension the extension to check
	 * @return the result of the check
     */
    public static boolean checkExtension(String filename, String extension) {
        return filename.endsWith(extension);
    }

    /**
     * Check whether the filename terminates with a given extension.
     * The comparison is case insensitive.
     * @param filename the filename to check
     * @param extension the extension to check
	 * @return the result of the check
     */
    public static boolean checkExtensionNoCase(String filename, String extension) {
        return filename.toLowerCase().endsWith(extension.toLowerCase());
    }
    
    /**
     * Check the extension of the file, case insensitive.
     * @param filename the name to check
     * @param extension the required extension
	 * @return the result of the check
     */
    public static boolean checkExtensionIgnoreCase(String filename, String extension) {
    	filename = filename.toLowerCase();
    	extension = extension.toLowerCase();

        return checkExtension(filename, extension);
    }
    
    /**
     * Force, if necessary, the extension of a filename.
     * @param filename the filename to check 
     * @param extension the extension to force
     * @result 
     */
    public static String forceExtension(String filename, String extension) {
    	if (checkExtensionIgnoreCase(filename, extension))
    		return filename;
    	else
    		return removeExtension(filename) + "." + extension;
    }
}
