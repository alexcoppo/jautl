package net.sf.jautl.graphics.colormaps;

import net.sf.jautl.graphics.colors.ColorRGBAF;
import net.sf.jautl.graphics.colors.GammaCorrection;

public class ColorMapSegment {
	private ColorMapControlPoint start;
	private ColorMapControlPoint stop;
	
	public ColorMapSegment(ColorMapControlPoint start, ColorMapControlPoint stop) {
		if (start.getX() > stop.getX()) {
			ColorMapControlPoint tmp = start;
			start = stop;
			stop = tmp;
		}

		this.start = start;
		this.stop = stop;
	}

	public ColorMapControlPoint getStart() {
		return start;
	}
	
	public ColorMapControlPoint getStop() {
		return stop;
	}
	
	public void linearToScreen(double gamma) {
		GammaCorrection.linearToScreen(start.getColorRGBAF(), gamma);
		GammaCorrection.linearToScreen(stop. getColorRGBAF(), gamma);
	}

	public boolean isInside(double x) {
		return (x >= start.getX()) && (x <= stop.getX());
	}
	
	public double getFraction(double x) {
		double min = start.getX();
		double max = stop.getX();
		
		if (min == max)
			return 0.5;
		else
			return (x - min) / (max - min);
	}

	public void lookup(double x, ColorRGBAF color) {
		double frac = getFraction(x);
		color.interpolate(start.getColorRGBAF(), frac, stop.getColorRGBAF());
	}
	
	public static int compare(ColorMapSegment arg0, ColorMapSegment arg1) {
		if (arg0.start.getX() < arg1.stop.getX())
			return 1;
		if (arg0.start.getX() > arg1.stop.getX())
			return -1;
		return 0;
	}
}
