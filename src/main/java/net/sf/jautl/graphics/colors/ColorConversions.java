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
 * This class contains static methods to convert colors of different models.
 */
public class ColorConversions {
	private static final float A_BIT_MORE_THAN_6 = 6.0001f;
    private static final float ALMOST_6 = 5.9999f;
    private static final float ALMOST_ZERO = .001f;

    /**
     * Convert an HSVA color into an RGBA one.
     * @param dst the HSVA source
     * @param src the RGBA destination
     */
	public static void convert(ColorRGBAF dst, ColorHSVAF src) {
        dst.setA(src.getA());

        if (src.getS() < ALMOST_ZERO) {
            dst.setR(src.getV());
            dst.setG(src.getV());
            dst.setB(src.getV());
            return;
        }

        float h = src.getH() * ALMOST_6;
        int side = (int)h;
        float fraction = h - side;

        float m = src.getV() * (1 - src.getS());
        float n = src.getV() * (1 - src.getS() * fraction);
        float k = src.getV() * (1 - src.getS() * (1 - fraction));

        switch (side) {
            case 0:
            	dst.setR(src.getV());
            	dst.setG(k);
            	dst.setB(m);
            	break;
            case 1:
            	dst.setR(n);
            	dst.setG(src.getV());
            	dst.setB(m);
            	break;
            case 2:
            	dst.setR(m);
            	dst.setG(src.getV()); dst.setB(k);
            	break;
            case 3:
            	dst.setR(m);
            	dst.setG(n);
            	dst.setB(src.getV());
            	break;
            case 4:
            	dst.setR(k);
            	dst.setG(m);
            	dst.setB(src.getV());
            	break;
            case 5:
            	dst.setR(src.getV());
            	dst.setG(m);
            	dst.setB(n);
            	break;
        }
	}

	/**
	 * Convert an RGBA color into an HSVA one.
	 * @param dst the RGBA source
	 * @param src the HSVA destination
	 */
	public static void convert(ColorHSVAF dst, ColorRGBAF src) {
        dst.setA(src.getA());

        float max = Math.max(src.getR(), Math.max(src.getG(), src.getB()));
        float min = Math.min(src.getR(), Math.min(src.getG(), src.getB()));

        float delta = max - min;

        if (max < ALMOST_ZERO) {
            dst.setH(0);
            dst.setS(0);
            dst.setV(0);
            return;
        }

        if (delta < ALMOST_ZERO) {
            dst.setH(0);
            dst.setS(0);
            dst.setV((max + min) / 2);
            return;
        }

        dst.setS(delta / max);
        dst.setV(max);

        float cr = (max - src.getR()) / delta;
        float cg = (max - src.getG()) / delta;
        float cb = (max - src.getB()) / delta;

        float h1 = 0;
        if (src.getR() == max)
            h1 = cb - cg;
        if (src.getG() == max)
            h1 = 2 + cr - cb;
        if (src.getB() == max)
            h1 = 4 + cg - cr;

        if (h1 <  0) h1 += 6;
        if (h1 >= 6) h1 -= 6;

        dst.setH(h1 / A_BIT_MORE_THAN_6);
	}

	/**
	 * Convert an HLSA color into an RGBA one.
	 * @param dst the HLSA source
	 * @param src the RGBA destination
	 */
	public static void convert(ColorRGBAF dst, ColorHLSAF src) {
        dst.setA(src.getA());

        float m2;

        if (src.getL() <= 0.5)
            m2 = src.getL() * (1 + src.getS());
        else
            m2 = src.getL() + src.getS() - (src.getL() * src.getS());
        float m1 = 2 * src.getL() - m2;

        if (src.getS() == 0) {
            dst.setR(src.getL());
            dst.setG(src.getL());
            dst.setB(src.getL());
        } else {
            dst.setR(HLSVAL(m1, m2, src.getH() + 1 / 3.0f));
            dst.setG(HLSVAL(m1, m2, src.getH()));
            dst.setB(HLSVAL(m1, m2, src.getH() - 1 / 3.0f));
        }
	}

	/**
	 * Convert an RGBA color into an HLSA one.
	 * @param dst RGBA source
	 * @param src HLSA destination
	 */
	public static void convert(ColorHLSAF dst, ColorRGBAF src) {
        dst.setA(src.getA());

        float imax = Math.max(src.getR(), Math.max(src.getG(), src.getB()));
        float imin = Math.min(src.getR(), Math.min(src.getG(), src.getB()));

        dst.setL((imax + imin) / 2);

        if (imax == imin) {
            dst.setS(0);
            dst.setH(0);
        } else {
            if (dst.getL() <= 0.5)
                dst.setS((imax - imin) / (imax + imin));
            else
                dst.setS((imax - imin) / (2.0f - imax - imin));

            float cr = (imax - src.getR()) / (imax - imin);
            float cg = (imax - src.getG()) / (imax - imin);
            float cb = (imax - src.getB()) / (imax - imin);

            float h1;

            if (src.getR() == imax)
                h1 = cb - cg;
            else if (src.getG() == imax)
                h1 = 2 + cr - cb;
            else
                h1 = 4 + cg - cr;

            h1 /= A_BIT_MORE_THAN_6;
            if (h1 < 0)
                h1 += 1;
            if (h1 >= 1)
                h1 -= 1;

            dst.setH(h1);
        }
	}

	/**
	 * Convert an YPbPrA color into an RGBA one using ITU-R BT.601 parameters.
	 * @param dst the YPbPrA source
	 * @param src the RGBA destination
	 */
	public static void convert601(ColorRGBAF dst, ColorYPbPrAF src) {
		convert(dst, src, 0.114f, 0.299f);
	}

	/**
	 * Convert an YPbPrA color into an RGBA one using ITU-R BT.709 parameters.
	 * @param dst the YPbPrA source
	 * @param src the RGBA destination
	 */
	public static void convert709(ColorRGBAF dst, ColorYPbPrAF src) {
		convert(dst, src, 0.0722f, 0.2126f);
	}

	/**
	 * Convert an RGBA color into an YPbPrA one using ITU-R BT.601 parameters.
	 * @param dst the RGBA source
	 * @param src the YPbPrA destination
	 */
	public static void convert601(ColorYPbPrAF dst, ColorRGBAF src) {
		convert(dst, src, 0.114f, 0.299f);
	}

	/**
	 * Convert an RGBA color into an YPbPrA one using ITU-R BT.709 parameters. 
	 * @param dst the RGBA source
	 * @param src the YPbPrA destination
	 */
	public static void convert709(ColorYPbPrAF dst, ColorRGBAF src) {
		convert(dst, src, 0.0722f, 0.2126f);
	}

	private static void convert(ColorYPbPrAF dst, ColorRGBAF src, float Kb, float Kr) {
        float Y  = Kr * src.getR() + (1 - Kr - Kb) * src.getG() + Kb * src.getB();
        float Pr = 0.5f * (src.getR() - Y) / (1 - Kr);
        float Pb = 0.5f * (src.getB() - Y) / (1 - Kb);
		
        dst.setA(src.getA());
        dst.setY(Y);
        dst.setPb(Pb);
        dst.setPr(Pr);
	}

	private static void convert(ColorRGBAF dst, ColorYPbPrAF src, float Kb, float Kr) {
        float R = 2 * (1 - Kr) * src.getPr() + src.getY();
        float B = 2 * (1 - Kb) * src.getPb() + src.getY();
        float G = (src.getY() - Kr * R - Kb * B) / (1 - Kr - Kb);

        dst.setA(src.getA());
        dst.setR(R);
        dst.setG(G);
        dst.setB(B);
	}

    private static float HLSVAL(float n1, float n2, float hue) {
        if (hue >= 1)
            hue -= 1;
        else if (hue < 0)
            hue += 1;
        hue *= ALMOST_6;

        switch ((int)hue) {
            case 0:
                return n1 + ((n2 - n1) * hue);
            case 1:
            case 2:
                return n2;
            case 3:
                return n1 + ((n2 - n1) * (4 - hue));
            case 4:
            case 5:
                return n1;
            default:
                return n1;
        }
    }
}
