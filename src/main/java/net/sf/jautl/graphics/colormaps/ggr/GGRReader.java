package net.sf.jautl.graphics.colormaps.ggr;

import net.sf.jautl.graphics.colormaps.ColorMapControlPoint;
import net.sf.jautl.graphics.colormaps.ColorMapSegment;
import net.sf.jautl.graphics.colormaps.ColorMapSegmentsFactory;
import net.sf.jautl.graphics.colors.ColorRGBAF;

public abstract class GGRReader implements ColorMapSegmentsFactory {
	@Override
	public ColorMapSegment createNext() {
		String[] items = readLine();

		if (items == null)
			return null;

		try {
			float left = Float.parseFloat(items[0]);
			//float center = Float.parseFloat(items[1]);
			float right = Float.parseFloat(items[2]);
			
			float left_r = Float.parseFloat(items[3]);
			float left_g = Float.parseFloat(items[4]);
			float left_b = Float.parseFloat(items[5]);
			float left_a = Float.parseFloat(items[6]);
			ColorRGBAF leftColor = new ColorRGBAF(left_r, left_g, left_b, left_a);
			ColorMapControlPoint cmcpLeft = new ColorMapControlPoint(left, leftColor);
	
			float right_r = Float.parseFloat(items[7]);
			float right_g = Float.parseFloat(items[8]);
			float right_b = Float.parseFloat(items[9]);
			float right_a = Float.parseFloat(items[10]);
			ColorRGBAF rightColor = new ColorRGBAF(right_r, right_g, right_b, right_a);
			ColorMapControlPoint cmcpRight = new ColorMapControlPoint(right, rightColor);
			
			//int blending_function = Integer.parseInt(items[11]);
			//int coloring_type = Integer.parseInt(items[12]);
			
			ColorMapSegment CMS = new ColorMapSegment(cmcpLeft, cmcpRight);
			return CMS;
		} catch (NumberFormatException nfe) {
			return null;
		}
	}
	
	protected abstract String[] readLine();
}
