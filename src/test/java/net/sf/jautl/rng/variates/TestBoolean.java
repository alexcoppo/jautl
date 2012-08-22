package net.sf.jautl.rng.variates;

import static org.mockito.Mockito.*;
import net.sf.jautl.rng.interfaces.IDoublesSource;

import org.junit.*;
import static org.junit.Assert.*;

public class TestBoolean {
	private IDoublesSource ids;
	
	@Before
	public void setUp() {
		ids = mock(IDoublesSource.class);
	}
	
	@Test
	public void test() {
		when(ids.nextDouble()).
            thenReturn(0.0).
            thenReturn(0.299).
            thenReturn(0.301).
            thenReturn(1.0);
		
		VariateBoolean vb = new VariateBoolean(ids);
		vb.setProbability(0.3);
		assert(vb.draw() == true);
		assert(vb.draw() == true);
		assert(vb.draw() == false);
		assert(vb.draw() == false);
	}
	
	@After
	public void tearDown() {
		ids = null;
	}
}
