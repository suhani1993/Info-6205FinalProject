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
		if(randomWalkPercent == 0) {
			randomWalkPercent = randomWalkPercent + 1;
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

}
