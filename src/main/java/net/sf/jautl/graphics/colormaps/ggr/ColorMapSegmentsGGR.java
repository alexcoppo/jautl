package net.sf.jautl.graphics.colormaps.ggr;

import java.io.InputStream;

import net.sf.jautl.graphics.colormaps.ColorMapSegment;
import net.sf.jautl.graphics.colormaps.ColorMapSegments;

public class ColorMapSegmentsGGR extends ColorMapSegments {
	public ColorMapSegmentsGGR(double gamma, String[] lines) {
		GGRReaderStringArray ggrr = new GGRReaderStringArray(lines);
		load(ggrr);
		linearToScreen(gamma);
	}

	public ColorMapSegmentsGGR(double gamma, String resourceName) {
		GGRReaderResources ggrr = new GGRReaderResources(resourceName);
		load(ggrr);
		linearToScreen(gamma);
	}

	public ColorMapSegmentsGGR(double gamma, InputStream is) {
		GGRReaderResources ggrr = new GGRReaderResources(is);
		load(ggrr);
		linearToScreen(gamma);
	}
	
	private void load(GGRReader ggrr) {
		ggrr.open();
		for (;;) {
			ColorMapSegment segment = ggrr.createNext();
			if (segment != null)
				add(segment);
			else
				break;
		}
		ggrr.close();
	}
}
