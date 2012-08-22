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
package net.sf.jautl.swing;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.event.ComponentAdapter;
import java.awt.image.BufferedImage;

/**
 * This class implements a Canvas component with an associated BufferedImage
 * used to paint it.
 */
@SuppressWarnings("serial")
public class DoubleBufferfedCanvas extends Canvas {
	private BufferedImage drawingSurface;

	/**
	 * The constructor.
	 */
	public DoubleBufferfedCanvas() {
		addComponentListener(new ResizeHandler());
	}

	/**
	 * Access the backing store for drawing on this control.
	 * @return the associated BufferedImage canvas
	 */
	public BufferedImage getDrawingSurface() {
		return drawingSurface;
	}

	@Override
	/**
	 * Paint the canvas by drawing the backing store on it.
	 * @param g the Graphics instace
	 */
	public void paint(Graphics g) {
		g.drawImage(drawingSurface, 0, 0, null);
	}
	
	private class ResizeHandler extends ComponentAdapter {
		@Override
		public void componentResized(java.awt.event.ComponentEvent evt) {
			DoubleBufferfedCanvas ownCanvas = (DoubleBufferfedCanvas)evt.getSource();
			
			ownCanvas.drawingSurface = new BufferedImage(
					ownCanvas.getWidth(),
					ownCanvas.getHeight(),
					BufferedImage.TYPE_3BYTE_BGR
					);
		}
	}
}
