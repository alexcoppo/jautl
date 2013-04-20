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
package net.sf.jautl.scripting;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import javax.script.ScriptEngine;
import javax.script.ScriptException;

/**
 * This class provides support for adding an #include-like facility to scripting
 * engines like the C one, inclusive of multiple path searches.
 */
public class JavascriptIncludeHandler {
	private ScriptEngine se;
	private List<String> incPaths;
	
	public JavascriptIncludeHandler() {
		incPaths = new ArrayList<String>();
	}
	
	public void addIncludePath(String path) {
		incPaths.add(path);
	}
	
	public void setScriptEngine(ScriptEngine se) {
		this.se = se;
	}

    //TODO add resource scanning
    
	private boolean canExecuteScript(String filename) {
		File f = new File(filename);
		return f.canRead();
	}

	private String findScript(String filename) {
		for (String path : incPaths)
			if (canExecuteScript(path + filename))
				return path + filename;
		
		return null;
	}

	public boolean include(String filename) {
		try {
			Reader r = new FileReader(findScript(filename));
			se.eval(r);
			return true;
		} catch (ScriptException sex) {
            //TODO better error reporting
			sex.printStackTrace();
			return false;
		} catch (IOException ioex) {
            //TODO better error reporting
			ioex.printStackTrace();
			return false;
		}
	}
}
