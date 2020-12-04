package test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import main.util.CommonUtils;

class CommonUtilsTest {
	
	@Test
	void testCalculateInfectedPercent() {
		
		assertEquals(CommonUtils.calculateInfectedPercent(0.5), 30);
		assertEquals(CommonUtils.calculateInfectedPercent(0.8), 60);
		assertEquals(CommonUtils.calculateInfectedPercent(2.0), 70);
	}
	
	@Test
	void testRandomAge() {
		
		assertEquals(CommonUtils.randomAge(50), 50);
		assertEquals(CommonUtils.randomAge(15), 18);
	}
	
	@Test
	void testGetDefaultKFactor() {
		assertEquals(CommonUtils.getDefaultKFactor("0.8"), "0.8");
		assertEquals(CommonUtils.getDefaultKFactor(null), "0.5");
	}
	
	@Test
	void testGetDefaultRFactor() {
		assertEquals(CommonUtils.getDefaultRFactor("5"), "5");
		assertEquals(CommonUtils.getDefaultRFactor(null), "2");
	}
	
	@Test
	void testGetDefaultSocialDistance() {
		assertEquals(CommonUtils.getDefaultSocialDistance("3"), "3");
		assertEquals(CommonUtils.getDefaultSocialDistance(null), "2");
	}
	
	@Test
	void testGetDefaultMaskUsage() {
		assertEquals(CommonUtils.getDefaultMaskUsage("0.5"), "0.5");
		assertEquals(CommonUtils.getDefaultMaskUsage(null), "0.8");
	}
	
	@Test
	void testGetDefaultEffectivenessOfMask() {
		assertEquals(CommonUtils.getDefaultMaskUsage("0.9"), "0.9");
		assertEquals(CommonUtils.getDefaultMaskUsage(null), "0.8");
	}

	@Test
	void testRandomBoolean() {
		assertTrue(CommonUtils.getRandomBoolean(0));
		assertFalse(CommonUtils.getRandomBoolean(1));
	}
	
	@Test
	void testRandomWalkPercent() {
		assertEquals(CommonUtils.randomWalkPercent(0), 1);
		assertEquals(CommonUtils.randomWalkPercent(5), 5);
	}
}
