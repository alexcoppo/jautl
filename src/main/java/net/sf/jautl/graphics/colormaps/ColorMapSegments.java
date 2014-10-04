package net.sf.jautl.graphics.colormaps;

import java.util.ArrayList;
import java.util.List;

import net.sf.jautl.collections.arrays.BinarySearcher;
import net.sf.jautl.collections.arrays.ComparisonResult;
import net.sf.jautl.collections.arrays.IndexedCollectionItemKeyComparator;
import net.sf.jautl.graphics.colors.ColorRGBAF;

public class ColorMapSegments implements IColorMapRGBAF {
	private List<ColorMapSegment> segments;
	
	public ColorMapSegments() {
		this.segments = new ArrayList<ColorMapSegment>();
	}

	public ColorMapSegments(ColorMapSegment segments[]) {
		this.segments = new ArrayList<ColorMapSegment>();
		for (int index = 0; index < segments.length; index++)
			this.segments.add(segments[index]);
	}
	
	public void add(ColorMapSegment segment) {
		segments.add(segment);
	}

	public void linearToScreen(double gamma) {
		for (ColorMapSegment cms : segments)
			cms.linearToScreen(gamma);
	}
	
	public ColorMapSegment lookup(double x) {
		int index = BinarySearcher.find(segments.size(), x, findComparator);
		return (index < 0) ? null : segments.get(index);
	}

	@Override
	public void lookup(double x, ColorRGBAF color) {
		ColorMapSegment segment = lookup(x);
		if (segment != null)
			segment.lookup(x, color);
	}
	
	private class FindComparator implements IndexedCollectionItemKeyComparator<Double> {
		@Override
		public void compare(Double key, int index, ComparisonResult result) {
			double d = key.doubleValue();
			ColorMapSegment CRS = segments.get(index);
			
			if (d < CRS.getStart().getX())
				result.markLessThan();
			else if  (d > segments.get(index).getStop().getX())
				result.markGreaterThan();
			else
				result.markEqual();
		}
	}
	
	private FindComparator findComparator = new FindComparator();
}
