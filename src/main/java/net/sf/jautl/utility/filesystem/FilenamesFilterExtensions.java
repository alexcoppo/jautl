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

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

/**
 * This calss implements the FilenameFilter interface for a collection
 * of filename extensions.
 */
public class FilenamesFilterExtensions implements FilenameFilter {
	/**
	 * Add an extension to the list.
	 * @param extension the extension to add
	 */
	public void registerExtension(String extension) {
		extensions.add(extension);
	}
	
	/**
	 * Accept a file if and only if its extension is one of the registered
	 * ones. Text comparison is case insensitive.
	 * @param dir the directory where the file is
	 * @param name the name of the file
	 * @return the result of the test 
	 */
	public boolean accept(File dir, String name) {
		for (String ext : extensions)
			if (FileNameUtilities.checkExtensionIgnoreCase(name, ext))
				return true;

		return false;
	}

	private ArrayList<String> extensions = new ArrayList<String>();
}
