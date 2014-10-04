package net.sf.jautl.graphics.colormaps.ggr;


public class GGRReaderStringArray extends GGRReader {
	private String lines[];
	private int nextLine;
	
	public GGRReaderStringArray(String[] lines) {
		this.lines = lines;
	}
	
	@Override
	public boolean open() {
		nextLine = 0;
		return true;
	}
	
	@Override
	protected String[] readLine() {
		if (nextLine < lines.length) {
			String[] items = lines[nextLine].split(" ");
			//TODO trim away null items due to consecutive blanks
			nextLine++;
			return items;
		}
		else
			return null;
	}
	
	@Override
	public void close() {
	}
}
