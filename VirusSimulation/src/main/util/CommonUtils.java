package main.util;

public class CommonUtils {
	
	public static int calculateInfectedPercent(Double K_Factor) {
		if(K_Factor <= 0.5) {
			return 30; //30% pass virus to others
		}else if(K_Factor > 0.5 && K_Factor <= 1.0) {
			return 60; //60% pass virus to others
		}else {
			return 70; //70% pass virus to others
		}
	}
	
	public static boolean getRandomBoolean(int i) {
		if(i==0) {
			return true;
		}else {
			return false;
		}
	}
	
	public static int randomAge(int inputAge) {
		if(inputAge < 18) {
			return 18;
		}
		return inputAge;
	}
	
	public static int randomWalkPercent(int randomWalkPercent) {
		if(randomWalkPercent <5) {
			randomWalkPercent = randomWalkPercent + 5;
		}
		return randomWalkPercent;
	}

	public static String getDefaultKFactor(String property) {
		if(property == null) {
			return String.valueOf(0.5);
		}
		return property;
	}

	public static String getDefaultRFactor(String property) {
		if(property == null) {
			return String.valueOf(2);
		}
		return property;
	}

	public static String getDefaultSocialDistance(String property) {
		if(property == null) {
			return String.valueOf(2);
		}
		return property;
	}

	public static String getDefaultMaskUsage(String property) {
		if(property == null) {
			return String.valueOf(0.8);
		}
		return property;
	}

	public static String getDefaultEffectivenessOfMask(String property) {
		if(property == null) {
			return String.valueOf(0.8);
		}
		return property;
	}
	
	public static String getDefaultNoofDays(String property) {
		if(property == null) {
			return String.valueOf(30);
		}
		return property;
	}

	public static double euclidianDistance(int x1, int x2, int y1, int y2) {
		double xDistnace = Math.pow((x2-x1), 2);
		double yDistance = Math.pow((y2-y1), 2);
		return Math.sqrt(xDistnace + yDistance);
		
	}


}
