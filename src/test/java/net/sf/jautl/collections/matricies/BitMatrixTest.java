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
package net.sf.jautl.collections.matricies;

import org.testng.Assert;
import org.testng.annotations.*;

public class BitMatrixTest {
	@Test
	public void testEmpty() {
		BitMatrix bm = new BitMatrix();
		
		Assert.assertTrue(bm.isEmpty());
		Assert.assertTrue(bm.getWidth() == 0);
		Assert.assertTrue(bm.getHeight() == 0);
		Assert.assertTrue(bm.getSize() == 0);
	}

	@Test
	public void testNotEmpty() {
		BitMatrix bm = new BitMatrix(4, 3);
		
		Assert.assertFalse(bm.isEmpty());
		Assert.assertTrue(bm.getWidth() == 4);
		Assert.assertTrue(bm.getHeight() == 3);
		Assert.assertTrue(bm.getSize() == 12);
	}

	@Test
	public void testSetGet() {
		BitMatrix bm = new BitMatrix(4, 3);
		
		bm.setRC(1, 2, true);
		Assert.assertTrue(bm.getRC(1, 2));
		bm.setXY(2, 1, true);
		Assert.assertTrue(bm.getXY(2, 1));
	}

	@Test
	public void testXYRC() {
		BitMatrix bm = new BitMatrix(4, 4);
		
		bm.setRC(1, 2, true);
		Assert.assertTrue(bm.getXY(2, 2));
	}

	@Test
	public void testRealloc() {
		BitMatrix bm = new BitMatrix(4, 4);
		
		Assert.assertFalse(bm.resize(16, 1));
		Assert.assertTrue(bm.getWidth() == 16);
		Assert.assertTrue(bm.getHeight() == 1);
		Assert.assertTrue(bm.resize(8, 3));
		Assert.assertTrue(bm.getWidth() == 8);
		Assert.assertTrue(bm.getHeight() == 3);
	}
	
	@Test
	public void testIsInside() {
		BitMatrix bm = new BitMatrix(2, 4);
		
		Assert.assertTrue(bm.isInsideRC(1, 1));
		Assert.assertFalse(bm.isInsideRC(-1, 1));
		Assert.assertFalse(bm.isInsideRC(1, -1));
		Assert.assertFalse(bm.isInsideRC(2, 2));
		Assert.assertFalse(bm.isInsideRC(1, 4));

		Assert.assertTrue(bm.isInsideXY(1, 1));
		Assert.assertFalse(bm.isInsideXY(-1, 1));
		Assert.assertFalse(bm.isInsideXY(1, -1));
		Assert.assertFalse(bm.isInsideXY(2, 2));
		Assert.assertFalse(bm.isInsideXY(1, 4));
	}
	
	@Test
	public void testClip() {
		BitMatrix bm = new BitMatrix(2, 4);
		
		Assert.assertTrue(bm.clipX(-1) == 0);
		Assert.assertTrue(bm.clipX(1) == 1);
		Assert.assertTrue(bm.clipX(3) == 1);

		Assert.assertTrue(bm.clipC(-1) == 0);
		Assert.assertTrue(bm.clipC(1) == 1);
		Assert.assertTrue(bm.clipC(3) == 1);
		
		Assert.assertTrue(bm.clipY(-1) == 0);
		Assert.assertTrue(bm.clipY(4) == 3);
		Assert.assertTrue(bm.clipY(1) == 1);
		
		Assert.assertTrue(bm.clipR(-1) == 0);
		Assert.assertTrue(bm.clipR(4) == 3);
		Assert.assertTrue(bm.clipR(1) == 1);
	}
}
