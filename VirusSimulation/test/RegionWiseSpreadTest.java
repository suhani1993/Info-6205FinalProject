

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.FileInputStream;
import java.util.Properties;
import java.util.Random;

import org.junit.Test;

import main.map.LocationPoint;
import main.spread.RegionWiseSpread;

public class RegionWiseSpreadTest {

	@Test
	public void testGetAndSetLocation() {
		RegionWiseSpread spread = new RegionWiseSpread();
		spread.setLocation("Boston");
		assertEquals(spread.getLocation(), "Boston");
	}
	
	@Test
	public void testGetAndSetPopulation() {
		RegionWiseSpread spread = new RegionWiseSpread();
		spread.setPopulation(74000);
		assertEquals(spread.getPopulation(), 74000);
	}
	
	@Test
	public void testGetAndSetSocialDistance() {
		RegionWiseSpread spread = new RegionWiseSpread();
		spread.setSocialDistance(2.0);
		assertEquals(spread.getSocialDistance(), 2.0);
	}
	
	@Test
	public void testGetAndSetUsageOfMask() {
		RegionWiseSpread spread = new RegionWiseSpread();
		spread.setUsageOfMask(0.8);
		assertEquals(spread.getUsageOfMask(), 0.8);
	}
	
	@Test
	public void testGetAndSetEffectivenessOfMask() {
		RegionWiseSpread spread = new RegionWiseSpread();
		spread.setEffectivenessOfMask(0.6);
		assertEquals(spread.getEffectivenessOfMask(), 0.6);
	}
	
	@Test
	public void testGetAndSetNoofDays() {
		RegionWiseSpread spread = new RegionWiseSpread();
		spread.setNoofDays(30);
		assertEquals(spread.getNoofDays(), 30);
	}
	
	@Test
	public void testGetAndSetR() {
		RegionWiseSpread spread = new RegionWiseSpread();
		spread.setR(2.0);
		assertEquals(spread.getR(), 2.0);
	}
	
	@Test
	public void testGetAndSetK() {
		RegionWiseSpread spread = new RegionWiseSpread();
		spread.setK(0.2);
		assertEquals(spread.getK(), 0.2);
	}
	
	@Test
	public void testLoadPropertiesFile() {
		try{
			FileInputStream in = new FileInputStream("config.properties");
			Properties p = new Properties();
			p.load(in);
			RegionWiseSpread spread = new RegionWiseSpread();
			Properties properties = spread.loadPropertiesFile();
			assertEquals(properties.getProperty("SOCIAL_DISTANCING"), p.getProperty("SOCIAL_DISTANCING"));
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testRegionWiseSpread() {
		RegionWiseSpread spread = new RegionWiseSpread();
		LocationPoint locationPoint = new LocationPoint();
		locationPoint.setName("Framingham");
		locationPoint.setX(70);
		locationPoint.setY(55);
		spread.setDataInLocationWiseSpread(spread, locationPoint);
		assertNotNull(spread);
	}
	
	@Test
	public void testNewLocationPoint() {
		RegionWiseSpread spread = new RegionWiseSpread();
		LocationPoint locationPoint = spread.createNewLocationPoint(new Random());
		assertNotNull(locationPoint);
	}

}
