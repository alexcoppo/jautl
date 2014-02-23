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
package net.sf.jautl.scripting;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import javax.script.ScriptEngine;
import javax.script.ScriptException;

/**
 *
 */
public class ScriptingEngine {
	private ScriptEngineFactory sef;
    private ScriptEngineLifecycleHandler selh;
    private ScriptExceptionHandler seh = new BasicScriptExceptionHandler();
    private IOExceptionHandler ioh = new BasicIOExceptionHandler();
    
    public ScriptingEngine(ScriptEngineFactory sef, ScriptEngineLifecycleHandler selh) {
        this.sef = sef;
        this.selh = (selh != null) ? selh : new BasicScriptEngineLifecycleHandler();
    }
    
    public void setScriptExceptionHandler(ScriptExceptionHandler seh) {
        this.seh = seh;
    }

    public void setIOExceptionHandler(IOExceptionHandler ioh) {
        this.ioh = ioh;
    }
    
	public boolean run(String scriptFilename) {
		ScriptEngine eng = sef.createEngine();

		try {
            if (!selh.setUp(eng)) return false;
            
			Reader r = new FileReader(scriptFilename);
			
			eng.eval(r);
            
            selh.cleanUp(eng);
            
			return true;
		} catch (ScriptException se) {
            seh.handleScriptException(se);
			return false;
		} catch (IOException ioe) {
			ioh.handleIOException(ioe);
			return false;
		}
    }
}
