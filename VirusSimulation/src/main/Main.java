package main;

import java.io.IOException;

import main.map.DistanceMap;
import main.map.LocationPoint;

public class Main {
	
	public static void main(String[] args) throws IOException {
		LocationPoint locationPoint = new LocationPoint();
		DistanceMap distanceMap = new DistanceMap();
		distanceMap.selectLocation(locationPoint);
		
	}
	
	
}
