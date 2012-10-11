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
package net.sf.jautl.graphics.colormaps;

import java.util.ArrayList;
import java.util.Collections;

import net.sf.jautl.graphics.colors.ColorRGBAF;

/**
 * This class implements a color map based upon a set of color ramps.
 */
public class ColorMapMultiRamp implements IColorMapRGBAF {
	private double gamma;
    private ArrayList<Double> minimums;
    private ArrayList<Double> ranges;
    private ArrayList<ColorRamp> ramps;

    /**
     * The constructor.
     * @param gamma the value of the Gamma Correction to apply to colors
     */
    public ColorMapMultiRamp(double gamma) {
        clear();
        this.gamma = gamma;
    }
    
    /**
     * Reset the collections.
     */
    public void clear() {
        minimums = new ArrayList<Double>();
        ranges = new ArrayList<Double>();
        ramps = new ArrayList<ColorRamp>();
    }

    /**
     * Load the color points.
     * @param xs the array of color maps locations
     * @param colors the array of the corresponding colors
     */
    public void loadPoints(double[] xs, ColorRGBAF[] colors) {
        if (xs == null) {
            xs = new double[colors.length];
            for (int index = 0; index < colors.length; index++)
                xs[index] = (double)index / (colors.length - 1);
        }

        for (int index = 0; index < xs.length - 1; index++)
            add(xs[index], xs[index + 1], new ColorRampRGBAF(colors[index], colors[index + 1], gamma, null));
    }
   
    /**
     * Add a ramp.
     * @param min the starting position of the ramp
     * @param max the ending position of the ramp
     * @param ramp the ramp
     */
    public void add(double min, double max, ColorRamp ramp) {
        minimums.add(min);
        ranges.add(max - min);
        ramps.add(ramp);
    }
    
	/**
	 * Lookup the color given a [0, 1] fractional index.
	 * @param x the index of the color, clipped to [0, 1] if required
	 * @param color the corresponding color
	 */
    public void lookup(double x, ColorRGBAF color) {
		int index = Collections.binarySearch(minimums, new Double(x), null);
        if (index < 0) index = -index - 2;

        double frac = (x - minimums.get(index)) / ranges.get(index);

        ramps.get(index).lookup(frac, color);
	}
}
