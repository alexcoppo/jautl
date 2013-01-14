package net.sf.jautl.rng.variates;

import net.sf.jautl.rng.interfaces.IDoublesSource;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.testng.Assert;
import org.testng.annotations.*;

public class TestBoolean {
	private IDoublesSource ids;
	
	@BeforeTest
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
		Assert.assertTrue(vb.draw() == true);
		Assert.assertTrue(vb.draw() == true);
		Assert.assertTrue(vb.draw() == false);
		Assert.assertTrue(vb.draw() == false);
	}
	
	@AfterTest
	public void tearDown() {
		ids = null;
	}
}
