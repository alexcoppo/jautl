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

public class RIPEMD160Test {
	private DigestEngine md;

    @Before
	public void setUp() {
		md = new RIPEMD160();
		md.initiate();
	}
	
    @Test
	public void test1() {
		md.add("");
		md.terminate();
		String result = md.getAsHex();
		assertTrue(result.equalsIgnoreCase("9C1185A5C5E9FC54612808977EE8F548B2258D31"));
	}

    @Test
	public void test2() {
		md.add("a");
		md.terminate();
		String result = md.getAsHex();
		assertTrue(result.equalsIgnoreCase("0BDC9D2D256B3EE9DAAE347BE6F4DC835A467FFE"));
	}

    @Test
	public void test3() {
		md.add("abc");
		md.terminate();
		String result = md.getAsHex();
		assertTrue(result.equalsIgnoreCase("8EB208F7E05D987A9B044A8E98C6B087F15A0BFC"));
	}

    @Test
	public void test4() {
		md.add("message digest");
		md.terminate();
		String result = md.getAsHex();
		assertTrue(result.equalsIgnoreCase("5D0689EF49D2FAE572B881B123A85FFA21595F36"));
	}

    @Test
	public void test5() {
		md.add("abcdefghijklmnopqrstuvwxyz");
		md.terminate();
		String result = md.getAsHex();
		assertTrue(result.equalsIgnoreCase("F71C27109C692C1B56BBDCEB5B9D2865B3708DBC"));
	}

    @Test
	public void test6() {
		md.add("abcdbcdecdefdefgefghfghighijhijkijkljklmklmnlmnomnopnopq");
		md.terminate();
		String result = md.getAsHex();
		assertTrue(result.equalsIgnoreCase("12A053384A9C0C88E405A06C27DCF49ADA62EB2B"));
	}

    @Test
	public void test7() {
		md.add("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789");
		md.terminate();
		String result = md.getAsHex();
		assertTrue(result.equalsIgnoreCase("B0E20B6E3116640286ED3A87A5713079B21F5189"));
	}

    @Test
	public void test8() {
		md.add("12345678901234567890123456789012345678901234567890123456789012345678901234567890");
		md.terminate();
		String result = md.getAsHex();
		assertTrue(result.equalsIgnoreCase("9B752E45573D4B39F4DBD3323CAB82BF63326BFB"));
	}

    @Test
	public void test1M() {
        for (int index = 0; index < 1000000; index++)
            md.add("a");
		md.terminate();
		String result = md.getAsHex();
		assertTrue(result.equalsIgnoreCase("52783243c1697bdbe16d37f97f68f08325dc1528"));
	}
}
