package net.sf.jautl.graphics.colormaps;

import net.sf.jautl.graphics.colors.ColorRGBAF;

public class ColorMapControlPoint {
	private double x;
	private ColorRGBAF color;

	public ColorMapControlPoint() {
	}

	public ColorMapControlPoint(double x, ColorRGBAF color) {
		this.x = x;
		this.color = color;
	}

	public ColorMapControlPoint(ColorMapControlPoint cmcp) {
		this.x = cmcp.x;
		this.color = cmcp.color;
	}

	public ColorMapControlPoint(double x, ColorMapControlPoint cmcp) {
		this.x = x;
		this.color = cmcp.color;
	}

	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}
	
	public ColorRGBAF getColorRGBAF() {
		return color;
	}
	
	public void setColorRGBAF(ColorRGBAF color) {
		this.color = color;
	}
}