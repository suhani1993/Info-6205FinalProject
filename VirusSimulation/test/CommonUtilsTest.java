

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


import main.util.CommonUtils;

public class CommonUtilsTest {
	
	@Test
	public void testCalculateInfectedPercent() {
		
		assertEquals(CommonUtils.calculateInfectedPercent(0.5), 30);
		assertEquals(CommonUtils.calculateInfectedPercent(0.8), 60);
		assertEquals(CommonUtils.calculateInfectedPercent(2.0), 70);
	}
	
	@Test
	public void testEuclidianDistance() {
		
		int x1 = 3;
	    int y1 = 4;
	    int x2 = 7;
	    int y2 = 1;
		
		double distance = CommonUtils.euclidianDistance(x1, x2, y1, y2);

	    assertEquals(5, distance, 0.01);
		assertEquals(8.48, CommonUtils.euclidianDistance(8,2,10,4), 0.01);
	}
	
	@Test
	public void testRandomAge() {
		
		assertEquals(CommonUtils.randomAge(50), 50);
		assertEquals(CommonUtils.randomAge(15), 18);
	}
	
	@Test
	public void testGetDefaultKFactor() {
		assertEquals(CommonUtils.getDefaultKFactor("0.8"), "0.8");
		assertEquals(CommonUtils.getDefaultKFactor(null), "0.5");
	}
	
	@Test
	public void testGetDefaultRFactor() {
		assertEquals(CommonUtils.getDefaultRFactor("5"), "5");
		assertEquals(CommonUtils.getDefaultRFactor(null), "2");
	}
	
	@Test
	public void testGetDefaultSocialDistance() {
		assertEquals(CommonUtils.getDefaultSocialDistance("3"), "3");
		assertEquals(CommonUtils.getDefaultSocialDistance(null), "2");
	}
	
	@Test
	public void testGetDefaultMaskUsage() {
		assertEquals(CommonUtils.getDefaultMaskUsage("0.5"), "0.5");
		assertEquals(CommonUtils.getDefaultMaskUsage(null), "0.8");
	}
	
	@Test
	public void testGetDefaultEffectivenessOfMask() {
		assertEquals(CommonUtils.getDefaultMaskUsage("0.9"), "0.9");
		assertEquals(CommonUtils.getDefaultMaskUsage(null), "0.8");
	}

	@Test
	public void testRandomBoolean() {
		assertTrue(CommonUtils.getRandomBoolean(0));
		assertFalse(CommonUtils.getRandomBoolean(1));
	}
	
	@Test
	public void testRandomWalkPercent() {
		assertEquals(CommonUtils.randomWalkPercent(0), 0);
		assertEquals(CommonUtils.randomWalkPercent(8), 8);
	}
}
