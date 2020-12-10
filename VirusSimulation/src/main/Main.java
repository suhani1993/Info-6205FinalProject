package main;

import java.io.IOException;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

import main.map.DistanceMap;
import main.map.LocationPoint;
import test.CommonUtilsTest;
import test.DistanceMapTest;
import test.LocationPointTest;
import test.PersonTest;
import test.RegionWiseSpreadTest;

public class Main {
	
	public static void main(String[] args) throws IOException {
		LocationPoint locationPoint = new LocationPoint();
		DistanceMap distanceMap = new DistanceMap();
		distanceMap.selectLocation(locationPoint);
		
		Result result = JUnitCore.runClasses(CommonUtilsTest.class, 
											 DistanceMapTest.class, 
											 LocationPointTest.class, 
											 PersonTest.class,
											 RegionWiseSpreadTest.class);
	    System.out.println("Finished. Result: Failures: " +
	  	      result.getFailureCount() + ". Ignored: " +
	  	      result.getIgnoreCount() + ". Tests run: " +
	  	      result.getRunCount() + ". Time: " +
	  	      result.getRunTime() + "ms.");
	}
	
	
}
