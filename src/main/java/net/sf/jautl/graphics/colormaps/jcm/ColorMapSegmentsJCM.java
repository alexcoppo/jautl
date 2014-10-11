package net.sf.jautl.graphics.colormaps.jcm;

import java.io.InputStream;

import net.sf.jautl.graphics.colormaps.ColorMapSegment;
import net.sf.jautl.graphics.colormaps.ColorMapSegments;

public class ColorMapSegmentsJCM extends ColorMapSegments {
	public ColorMapSegmentsJCM(double gamma, String resourceName) {
		JCMReaderResources ggrr = new JCMReaderResources(resourceName);
		load(ggrr);
		linearToScreen(gamma);
	}

	public ColorMapSegmentsJCM(double gamma, InputStream is) {
		JCMReaderResources ggrr = new JCMReaderResources(is);
		load(ggrr);
		linearToScreen(gamma);
	}
	
	private void load(JCMReader jcmr) {
		jcmr.open();
		for (;;) {
			ColorMapSegment segment = jcmr.createNext();
			if (segment != null)
				add(segment);
			else
				break;
		}
		jcmr.close();
	}
}
