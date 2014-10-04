package net.sf.jautl.graphics.colormaps.jcm;

import org.json.JSONArray;
import org.json.JSONObject;

import net.sf.jautl.graphics.colormaps.ColorMapControlPoint;
import net.sf.jautl.graphics.colormaps.ColorMapSegment;
import net.sf.jautl.graphics.colormaps.ColorMapSegmentsFactory;
import net.sf.jautl.graphics.colors.ColorRGBAF;
import net.sf.jautl.json.JSONUtils;

public abstract class JCMReader implements ColorMapSegmentsFactory {
	private JSONObject colorMap;
	private JSONArray colorMapSegments; 
	private int nextSegment;
	
	protected JCMReader(JSONObject colorMap) {
		this.colorMap = colorMap;
	}
	
	public boolean open() {
		nextSegment = 0;

		if (colorMap == null)
			return false;
		if (JSONUtils.getInteger(colorMap, "version", 0) != 1)
			return false;
		
		colorMapSegments = JSONUtils.getArray(colorMap, "segments", null);
		return colorMapSegments != null;
	}
		
	@Override
	public ColorMapSegment createNext() {
		if (nextSegment >= colorMapSegments.length())
			return null;

		JSONObject segmentDescriptor = JSONUtils.getObject(colorMapSegments, nextSegment, null);
		
		String rampModel = JSONUtils.getString(segmentDescriptor, "ramp-color-model", "rgb");
		String rampType = JSONUtils.getString(segmentDescriptor, "ramp-type", "linear");
		
		double loPos = JSONUtils.getDouble(segmentDescriptor, "lo-pos", -1);
		JSONObject loColorDescriptor = JSONUtils.getObject(segmentDescriptor, "lo-color", null);
		double hiPos = JSONUtils.getDouble(segmentDescriptor, "hi-pos", -1);
		JSONObject hiColorDescriptor = JSONUtils.getObject(segmentDescriptor, "hi-color", null);
		
		if (loPos == -1 && loColorDescriptor == null) {
			if (nextSegment == 0) {
				//TODO its an error provide better diagnostics
				return null;
			}
			
			//assume lo control point equal to previous segment hi control point
			JSONObject prevSegmentDescriptor = JSONUtils.getObject(colorMapSegments, nextSegment - 1, null);
			loPos = JSONUtils.getDouble(prevSegmentDescriptor, "hi-pos", -1);
			loColorDescriptor = JSONUtils.getObject(prevSegmentDescriptor, "hi-color", null);
		}

		ColorRGBAF loColor = getColor(loColorDescriptor);
		ColorMapControlPoint loControlPoint = new ColorMapControlPoint(loPos, loColor);
		ColorRGBAF hiColor = getColor(hiColorDescriptor);
		ColorMapControlPoint hiControlPoint = new ColorMapControlPoint(hiPos, hiColor);
		
		ColorMapSegment cms = new ColorMapSegment(loControlPoint, hiControlPoint);
		nextSegment++;
		return cms;
	}
	
	@Override
	public void close() {
	}

	private ColorRGBAF getColor(JSONObject colorDescriptor) {
		ColorRGBAF result = new ColorRGBAF();
		
		String colorModel = JSONUtils.getString(colorDescriptor, "model", null);
		JSONArray colorTuple = JSONUtils.getArray(colorDescriptor, "values", null);
		
		if ("rgb".equals(colorModel)) {
			result.setR(JSONUtils.getFloat(colorTuple, 0, 0));
			result.setG(JSONUtils.getFloat(colorTuple, 1, 0));
			result.setB(JSONUtils.getFloat(colorTuple, 2, 0));
			result.setA(1);
		} else if ("rgba".equals(colorModel)) {
			result.setR(JSONUtils.getFloat(colorTuple, 0, 0));
			result.setG(JSONUtils.getFloat(colorTuple, 1, 0));
			result.setB(JSONUtils.getFloat(colorTuple, 2, 0));
			result.setA(JSONUtils.getFloat(colorTuple, 3, 0));
		} else if ("hsv".equals(colorModel)) {
			//TODO Add support
		} else if ("hsva".equals(colorModel)) {
			//TODO Add support
		} else if ("hls".equals(colorModel)) {
			//TODO Add support
		} else if ("hlva".equals(colorModel)) {
			//TODO Add support
		} else if ("ypbpr".equals(colorModel)) {
			//TODO Add support
		} else if ("ypbpra".equals(colorModel)) {
			//TODO Add support
		}

		return result;
	}
}
