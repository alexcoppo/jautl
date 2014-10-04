package net.sf.jautl.graphics.colormaps.ggr;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class GGRReaderResources extends GGRReader {
	private String resourceName;
	private InputStream is;
	private InputStreamReader isr;
	private BufferedReader br;
	
	public GGRReaderResources(String resourceName) {
		this.resourceName = resourceName;
		this.is = null;
	}
	
	public GGRReaderResources(InputStream is) {
		this.resourceName = null;
		this.is = is;
	}
	
	@Override
	public boolean open() {
		if (is == null)
			is = this.getClass().getClassLoader().getResourceAsStream(resourceName);
		
		if (is != null) {
			isr =new InputStreamReader(is);
			br = new BufferedReader(isr);
			
			//skip header
			readLineAux();
			readLineAux();
			readLineAux();
			
			return true;
		} else
			return false;
	}
	
	@Override
	protected String[] readLine() {
		String line = readLineAux();

		if (line == null)
			return null;
		else
			//TODO trim away null items due to consecutive blanks
			return line.split(" ");
	}
	
	@Override
	public void close() {
		if (isr != null)
			try {
				isr.close();
				is = null;
			} catch (IOException e) {
			} finally {
				isr = null;
			}

		if (br != null)
			try {
				br.close();
			} catch (IOException e) {
			} finally {
				br = null;
			}

		if (is != null && resourceName != null) {
			try {
				is.close();
			} catch (IOException e) {
			} finally { 
				is = null;
			}
		}
	}
	
	private String readLineAux() {
		try {
			return br.readLine();
		} catch (IOException e) {
			return null;
		}
	}
}
