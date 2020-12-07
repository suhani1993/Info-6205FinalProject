package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import main.map.LocationPoint;

class LocationPointTest {

	@Test
	void testGetAndSetX() {
		LocationPoint locationPoint = new LocationPoint();
		locationPoint.setX(10);
		assertEquals(locationPoint.getX(), 10);
	}
	
	@Test
	void testGetAndSetY() {
		LocationPoint locationPoint = new LocationPoint();
		locationPoint.setY((int)72.5);
		assertEquals(locationPoint.getY(), 72);
	}
	
	@Test
	void testGetAndSetName() {
		LocationPoint locationPoint = new LocationPoint();
		locationPoint.setName("Boston");
		assertEquals(locationPoint.getName(), "Boston");
	}

}
