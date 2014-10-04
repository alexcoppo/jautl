package net.sf.jautl.graphics.colormaps;

public interface ColorMapSegmentsFactory {
	public boolean open();
	
	public ColorMapSegment createNext();
	
	public void close();
}
