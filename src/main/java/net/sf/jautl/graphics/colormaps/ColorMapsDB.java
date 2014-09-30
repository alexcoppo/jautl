/*
    Copyright (c) 2000-2014 Alessandro Coppo
    All rights reserved.

    Redistribution and use in source and binary forms, with or without
    modification, are permitted provided that the following conditions
    are met:
    1. Redistributions of source code must retain the above copyright
       notice, this list of conditions and the following disclaimer.
    2. Redistributions in binary form must reproduce the above copyright
       notice, this list of conditions and the following disclaimer in the
       documentation and/or other materials provided with the distribution.
    3. The name of the author may not be used to endorse or promote products
       derived from this software without specific prior written permission.

    THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
    IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
    OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
    IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
    INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
    NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
    DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
    THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
    (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
    THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package net.sf.jautl.graphics.colormaps;

import java.util.LinkedHashMap;
import java.util.Map;

import net.sf.jautl.graphics.colormaps.greys.GreyContinuos;
import net.sf.jautl.graphics.colormaps.greys.GreyContinuosTagged;
import net.sf.jautl.graphics.colormaps.greys.GreySteps;
import net.sf.jautl.graphics.colormaps.greys.GreyStepsTagged;
import net.sf.jautl.graphics.colormaps.terrain.DEMPoster;
import net.sf.jautl.graphics.colormaps.terrain.DEMPrint;
import net.sf.jautl.graphics.colormaps.terrain.DEMScreen;
import net.sf.jautl.graphics.colormaps.terrain.Landserf;
import net.sf.jautl.graphics.colormaps.terrain.MOLA;
import net.sf.jautl.graphics.colormaps.terrain.MicroDEM;
import net.sf.jautl.graphics.colormaps.terrain.ThreeDEM;
import net.sf.jautl.graphics.colormaps.terrain.Wikipedia1;
import net.sf.jautl.graphics.colormaps.terrain.Wikipedia2;
import net.sf.jautl.graphics.colormaps.terrain.Wikipedia3;
import net.sf.jautl.graphics.colormaps.warmbody.WarmBodyEx;

public class ColorMapsDB {
	private Map<String, IColorMapRGBAF> lutsDB = new LinkedHashMap<String, IColorMapRGBAF>(16, .75f, false);

	public ColorMapsDB() {
		lutsDB.put("Grey", new GreyContinuos(2.2));
		lutsDB.put("Grey Tagged", new GreyContinuosTagged(2.2, .05));
		lutsDB.put("Grey Steps 16", new GreySteps(2.2, 16));
		lutsDB.put("Grey Steps Tagged 16", new GreyStepsTagged(2.2, 16));

		lutsDB.put("DEM Poster", new DEMPoster(2.2));
		lutsDB.put("DEM Print", new DEMPrint(2.2));
		lutsDB.put("DEM Screen", new DEMScreen(2.2));
		lutsDB.put("Landserf", new Landserf(2.2));
		lutsDB.put("MicroDEM", new MicroDEM(2.2));
		lutsDB.put("MOLA", new MOLA(2.2));
		lutsDB.put("3DEM", new ThreeDEM(2.2));
		lutsDB.put("Wikpedia 1", new Wikipedia1(2.2));
		lutsDB.put("Wikpedia 2", new Wikipedia2(2.2));
		lutsDB.put("Wikpedia 3", new Wikipedia3(2.2));
		
		lutsDB.put("WarmBodyEx", new WarmBodyEx(2.2));
	}

	public Object[] getNames() {
		return lutsDB.keySet().toArray();
	}
	
	public String getName(int index) {
		Object names[] = getNames(); 
		return (String)names[0];
	}
	
	public IColorMapRGBAF lookup(String name) {
		return lutsDB.get(name);
	}
}
