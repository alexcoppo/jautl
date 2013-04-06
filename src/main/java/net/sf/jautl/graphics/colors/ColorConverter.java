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
package net.sf.jautl.graphics.colors;

/**
 * This class implements a wrapper that allows to convert between any of the
 * supported color models.
 */
public class ColorConverter {
	private ColorRGBAF rgba = new ColorRGBAF();
	private ColorHSVAF hsva = new ColorHSVAF();
	private ColorHLSAF hlsa = new ColorHLSAF();
	private ColorYPbPrAF ypbpra = new ColorYPbPrAF();

	/**
	 * Set the color using the RGB format.
	 * @param rgba the color to set
	 */
	public final void set(ColorRGBAF rgba) {
		this.rgba.assign(rgba);

		ColorConversions.convert(hlsa, rgba);
		ColorConversions.convert(hsva, rgba);
		ColorConversions.convert709(ypbpra, rgba);
	}

	/**
	 * Set the color using the HSV format.
	 * @param hsva the color to set
	 */
	public final void set(ColorHSVAF hsva) {
		this.hsva.assign(hsva);

		ColorConversions.convert(rgba, hsva);
		ColorConversions.convert(hlsa, rgba);
		ColorConversions.convert709(ypbpra, rgba);
	}

	/**
	 * Set the color using the HLS format.
	 * @param hlsa the color to set
	 */
	public final void set(ColorHLSAF hlsa) {
		this.hlsa.assign(hlsa);

		ColorConversions.convert(rgba, hlsa);
		ColorConversions.convert(hsva, rgba);
		ColorConversions.convert709(ypbpra, rgba);
	}

	/**
	 * Set the color using the YPbPr format.
	 * @param ypbpra the color to set
	 */
	public final void set(ColorYPbPrAF ypbpra) {
		this.ypbpra.assign(ypbpra);

		ColorConversions.convert709(rgba, ypbpra);
		ColorConversions.convert(hlsa, rgba);
		ColorConversions.convert(hsva, rgba);
	}

	/**
	 * Get the current value of the RGB model.
	 * @return the current value
	 */
	public final ColorRGBAF getRGBA() {
		return rgba;
	}

	/**
	 * Get the current value of the HSV model.
	 * @return the current value
	 */
	public final ColorHSVAF getHSVA() {
		return hsva;
	}

	/**
	 * Get the current value of the HLS model.
	 * @return the current value
	 */
	public final ColorHLSAF getHLSA() {
		return hlsa;
	}

	/**
	 * Get the current value of the YPbPr model.
	 * @return the current value
	 */
	public final ColorYPbPrAF getYPbPr() {
		return ypbpra;
	}

	/**
	 * Return the first component of the color, first being R for RGB,
	 * Hue for HLS and HSV, Y for YPbPr.
	 * @param colorModel the name of the color model 
	 * @return the value of the component
	 */
	public final float getX(String colorModel) {
		if (colorModel.equals("RGBA"))
			return rgba.getR();
		else if (colorModel.equals("HLSA"))
			return hlsa.getH();
		else if (colorModel.equals("HSVA"))
			return hsva.getH();
		else if (colorModel.equals("YPbPrA"))
			return ypbpra.getY();
		else
			return -1;
	}

	/**
	 * Return the second component of the color, first being G for RGB,
	 * Luminosity for HLS, Saturation for HSV, Pb for YPbPr.
	 * @param colorModel the name of the color model 
	 * @return the value of the component
	 */
	public final float getY(String colorModel) {
		if (colorModel.equals("RGBA"))
			return rgba.getG();
		else if (colorModel.equals("HLSA"))
			return hlsa.getL();
		else if (colorModel.equals("HSVA"))
			return hsva.getS();
		else if (colorModel.equals("YPbPrA"))
			return ypbpra.getPb();
		else
			return -1;
	}

	/**
	 * Return the third component of the color, first being G for RGB,
	 * Saturation for HLS, Value for HSV, Pr for YPbPr.
	 * @param colorModel the name of the color model 
	 * @return the value of the component
	 */
	public final float getZ(String colorModel) {
		if (colorModel.equals("RGBA"))
			return rgba.getB();
		else if (colorModel.equals("HLSA"))
			return hlsa.getS();
		else if (colorModel.equals("HSVA"))
			return hsva.getV();
		else if (colorModel.equals("YPbPrA"))
			return ypbpra.getPr();
		else
			return -1;
	}

	/**
	 * Return the opacity of the color.
	 * @param colorModel the name of the color model 
	 * @return the value of the component
	 */
	public final float getA(String colorModel) {
		if (colorModel.equals("RGBA"))
			return rgba.getA();
		else if (colorModel.equals("HLSA"))
			return hlsa.getA();
		else if (colorModel.equals("HSVA"))
			return hsva.getA();
		else if (colorModel.equals("YPbPrA"))
			return ypbpra.getA();
		else
			return -1;
	}
	
	/**
	 * Utility method to get a component using a numeric index.
	 * @param index the index in [0, 3]
	 * @param colorModel the name of the color model
	 * @return the value of the component
	 */
	public final float get(int index, String colorModel) {
		switch (index) {
		case 0:	return getX(colorModel);
		case 1:	return getY(colorModel);
		case 2:	return getZ(colorModel);
		case 3:	return getA(colorModel);
		default: return -1;
		}
	}
}
