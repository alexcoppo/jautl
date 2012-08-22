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
package net.sf.jautl.md;

import org.junit.*;
import static org.junit.Assert.*;

public class Murmur2ATest {
	private DigestEngine md;

    @Before
	public void setUp() {
		md = new Murmur2A();
		md.initiate();
	}
	
    @Test
	public void test1() {
		md.add("");
		md.terminate();
		String result = md.getAsHex();
		assertTrue(result.equalsIgnoreCase("00000000"));
	}

    @Test
	public void test2() {
		md.add("a");
		md.terminate();
		String result = md.getAsHex();
		assertTrue(result.equalsIgnoreCase("8b880308"));
	}

    @Test
	public void test3() {
		md.add("abc");
		md.terminate();
		String result = md.getAsHex();
		assertTrue(result.equalsIgnoreCase("679f5811"));
	}

    @Test
	public void test4() {
		md.add("message digest");
		md.terminate();
		String result = md.getAsHex();
		assertTrue(result.equalsIgnoreCase("c3691f31"));
	}

    @Test
	public void test5() {
		md.add("abcdefghijklmnopqrstuvwxyz");
		md.terminate();
		String result = md.getAsHex();
		assertTrue(result.equalsIgnoreCase("e35fb08d"));
	}

    @Test
	public void test6() {
		md.add("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789");
		md.terminate();
		String result = md.getAsHex();
		assertTrue(result.equalsIgnoreCase("e17df8ab"));
	}

    @Test
	public void test7() {
		md.add("12345678901234567890123456789012345678901234567890123456789012345678901234567890");
		md.terminate();
		String result = md.getAsHex();
		assertTrue(result.equalsIgnoreCase("c7a9ca8d"));
	}
}
