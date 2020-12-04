package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import javax.swing.JFrame;

import org.junit.Test;

import main.map.DistanceMap;

public class DistanceMapTest {

	@Test
	public void testSetConfig() {
		JFrame frame = new JFrame();
		assertEquals(DistanceMap.setNameOfComponent(frame, "Set Configurations"), frame);
	}
	
	@Test
	public void testConfigFile() {
		File file = new File("config.properties");
		assertTrue(file.exists());		
	}
	
	@Test
	public void testReadConfigFile() {
		assertNotNull(DistanceMap.readConfigFile("config.properties"));	
	}
	
	@Test
	public void testCheckJFrame() {
		JFrame jFrame = new JFrame();
		jFrame.setVisible(true);
		assertTrue(jFrame.isVisible());
	}

}
