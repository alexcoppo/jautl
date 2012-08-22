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

public class MD2Test {
	private DigestEngine md;

    @Before
	public void setUp() {
		md = new MD2();
		md.initiate();
	}
	
    @Test
	public void test1() {
		md.add("");
		md.terminate();
		String result = md.getAsHex();
		assertTrue(result.equalsIgnoreCase("8350e5a3e24c153df2275c9f80692773"));
	}

    @Test
	public void test2() {
		md.add("a");
		md.terminate();
		String result = md.getAsHex();
		assertTrue(result.equalsIgnoreCase("32ec01ec4a6dac72c0ab96fb34c0b5d1"));
	}

    @Test
	public void test3() {
		md.add("abc");
		md.terminate();
		String result = md.getAsHex();
		assertTrue(result.equalsIgnoreCase("da853b0d3f88d99b30283a69e6ded6bb"));
	}

    @Test
	public void test4() {
		md.add("message digest");
		md.terminate();
		String result = md.getAsHex();
		assertTrue(result.equalsIgnoreCase("ab4f496bfb2a530b219ff33031fe06b0"));
	}

	public void test5() {
		md.add("abcdefghijklmnopqrstuvwxyz");
		md.terminate();
		String result = md.getAsHex();
		assertTrue(result.equalsIgnoreCase("4e8ddff3650292ab5a4108c3aa47940b"));
	}

	@Test
	public void test6() {
		md.add("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789");
		md.terminate();
		String result = md.getAsHex();
		assertTrue(result.equalsIgnoreCase("da33def2a42df13975352846c30338cd"));
	}

	@Test
	public void test7() {
		md.add("12345678901234567890123456789012345678901234567890123456789012345678901234567890");
		md.terminate();
		String result = md.getAsHex();
		assertTrue(result.equalsIgnoreCase("d5976f79d83d3a0dc9806c3c66f3efd8"));
	}
}
