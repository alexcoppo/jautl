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
package net.sf.jautl.md;

import org.junit.Assert;
import org.junit.Test;

public class UintHashUtilitiesTest {
	@Test
	public void testWang() {
		int result = UintHashUtilities.wang(0xcafebeef);
		Assert.assertTrue(result == 0xd1d44228);
	}

	@Test
	public void testJenkins() {
		int result = UintHashUtilities.jenkins(0xcafebeef);
		Assert.assertTrue(result == 0x80de37fe);
	}

	@Test
	public void testWard() {
		int result = UintHashUtilities.ward(0xcafebeef);
		Assert.assertTrue(result == 0x56edb9d5);
	}

	@Test
	public void testMix() {
        int result = UintHashUtilities.mix(0xcafebeef, 0xfeedbeef, 0xcafebabe);
		Assert.assertTrue(result == 0x991d46cb);
	}

	@Test
	public void testMurmur2() {
        int result = UintHashUtilities.murmur2(0xcafebeef, 0xfeedbeef);
		Assert.assertTrue(result == 0x7bc1962e);
	}

	@Test
	public void testMurmur3fmix() {
        int result = UintHashUtilities.murmur3fmix(0xcafebeef);
		Assert.assertTrue(result == 0x562ba297);
	}
}