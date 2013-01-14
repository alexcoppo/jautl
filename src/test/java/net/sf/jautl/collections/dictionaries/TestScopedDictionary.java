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
package net.sf.jautl.collections.dictionaries;

import org.testng.Assert;
import org.testng.annotations.*;

public class TestScopedDictionary {
	@Test
	public void testIsSingle() {
		ScopedDictionary sd = new ScopedDictionary();

		Assert.assertTrue(sd.isSingle());

		sd.createScope();
		Assert.assertFalse(sd.isSingle());
		
		sd.deleteScope();
		Assert.assertTrue(sd.isSingle());
	}

	@Test
	public void testDeleteScope() {
		ScopedDictionary sd = create(1);

		sd.declareLocal("local");
		sd.declareGlobal("global");
		
		Assert.assertTrue(sd.exists("local"));
		Assert.assertTrue(sd.exists("global"));
		
		sd.deleteScope();

		Assert.assertFalse(sd.exists("local"));
		Assert.assertTrue(sd.exists("global"));
	}

	@Test
	public void testExistsLocal() {
		ScopedDictionary sd = create(1);
		
		Assert.assertFalse(sd.existsLocal("foo"));
		
		sd.declareLocal("foo");
		Assert.assertTrue(sd.existsLocal("foo"));
		
		sd.removeLocal("foo");
		Assert.assertFalse(sd.existsLocal("foo"));
	}

	@Test
	public void testExistsGlobal() {
		ScopedDictionary sd = create(1);
		
		Assert.assertFalse(sd.existsGlobal("foo"));
		
		sd.declareGlobal("foo");
		Assert.assertTrue(sd.existsGlobal("foo"));
		
		sd.removeGlobal("foo");
		Assert.assertFalse(sd.existsGlobal("foo"));
	}

	@Test
	public void testExists() {
		ScopedDictionary sd = create(1);

		sd.declareGlobal("global");
		sd.declareLocal("local");
		
		Assert.assertTrue(sd.exists("local"));
		Assert.assertTrue(sd.exists("global"));
		Assert.assertFalse(sd.exists("foobar"));
	}

	@Test
	public void testSetLocal() {
		ScopedDictionary sd = create(1);

		sd.declareLocal("foo");
		Assert.assertTrue(sd.getLocal("foo") == null);
		
		Object o = new Object();
		
		sd.setLocal("foo", o);
		Assert.assertTrue(sd.getLocal("foo") == o);
	}

	@Test
	public void testSetGlobal() {
		ScopedDictionary sd = create(1);

		sd.declareGlobal("foo");
		Assert.assertTrue(sd.getGlobal("foo") == null);
		
		Object o = new Object();
		
		sd.setGlobal("foo", o);
		Assert.assertTrue(sd.getGlobal("foo") == o);
	}

	@Test
	public void testSet() {
		ScopedDictionary sd = create(1);

		sd.declareGlobal("global");
		sd.declareLocal("local");

		Object og = new Object();
		Object ol = new Object();
		
		sd.set("global", og);
		sd.set("local", ol);
		
		Assert.assertTrue(sd.get("global") == og);
		Assert.assertTrue(sd.get("local") == ol);
	}

	@Test
	public void testRemoveLocalAll() {
		ScopedDictionary sd = create(1);

		sd.declareGlobal("global");
		sd.declareLocal("local");
		
		sd.removeLocalAll();
		Assert.assertFalse(sd.exists("local"));
		Assert.assertTrue(sd.exists("global"));
	}

	@Test
	public void testRemoveGlobalAll() {
		ScopedDictionary sd = create(1);

		sd.declareGlobal("global");
		sd.declareLocal("local");
		
		sd.removeGlobalAll();
		Assert.assertTrue(sd.exists("local"));
		Assert.assertFalse(sd.exists("global"));
	}

	private ScopedDictionary create(int scopeCount) {
		ScopedDictionary sd = new ScopedDictionary();

		while (scopeCount-- > 0)
			sd.createScope();
		
		return sd;
	}
}
