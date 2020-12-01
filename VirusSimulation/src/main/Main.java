package main;

import java.awt.BorderLayout;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Map.DistanceMap;
import Map.LocationPoint;

public class Main {
	
	public static void main(String[] args) throws IOException {
		LocationPoint locationPoint = new LocationPoint();
		DistanceMap distanceMap = new DistanceMap();
		distanceMap.selectLocation(locationPoint);
		

	}

	

}
