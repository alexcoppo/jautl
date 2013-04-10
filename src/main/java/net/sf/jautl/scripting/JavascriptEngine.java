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

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

/**
 * 
 */
public class JavascriptEngine {
	private ScriptEngineManager manager;
	private IncludeHandler ih;
    private String includeHandlerVarName;
    
	public JavascriptEngine() {
		manager = new ScriptEngineManager();
		ih = new IncludeHandler();
        includeHandlerVarName = "INCLUDE_HANDLER";
	}
	
	public IncludeHandler getIncludeHandler() {
		return ih;
	}

    public String getIncludeHandlerVarName() {
        return includeHandlerVarName;
    }

    public void setIncludeHandlerVarName(String includeHandlerVarName) {
        this.includeHandlerVarName = includeHandlerVarName;
    }

	public boolean run(String scriptFilename) {
		ScriptEngine jse = manager.getEngineByName("JavaScript");
		
		try {
			ih.setScriptEngine(jse);
			jse.put(includeHandlerVarName, ih);

			Reader r = new FileReader(scriptFilename);
			
			jse.eval(r);
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
