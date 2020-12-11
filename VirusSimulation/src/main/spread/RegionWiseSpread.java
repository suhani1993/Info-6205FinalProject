package main.spread;

import java.awt.Color;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JPanel;

import main.Graph;
import main.map.LocationPoint;
import main.person.Person;
import main.person.PersonDirectory;
import main.util.CommonUtils;

public class RegionWiseSpread extends Spread {
	
	static Logger logger = Logger.getLogger(RegionWiseSpread.class.getName());

	private String location;
	private int population;
	private Double socialDistance;
	private double usageOfMask;
	private double effectivenessOfMask;
	private int noofDays;
	
	public int getNoofDays() {
		return noofDays;
	}

	public void setNoofDays(int noofDays) {
		this.noofDays = noofDays;
	}

	public double getUsageOfMask() {
		return usageOfMask;
	}

	public void setUsageOfMask(double usageOfMask) {
		this.usageOfMask = usageOfMask;
	}

	public double getEffectivenessOfMask() {
		return effectivenessOfMask;
	}

	public void setEffectivenessOfMask(double effectivenessOfMask) {
		this.effectivenessOfMask = effectivenessOfMask;
	}

	public Double getSocialDistance() {
		return socialDistance;
	}

	public void setSocialDistance(Double socialDistance) {
		this.socialDistance = socialDistance;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public int getPopulation() {
		return population;
	}

	public void setPopulation(int population) {
		this.population = population;
	}

	/*
	 * RegionWiseSpread -> Local object to save data of config file
	 * LocationPoint    -> Selected location's latitude and longitude from google map
	 * Set each property and it's value from config file to regionWiseSpread local object
	 */
	public RegionWiseSpread setDataInLocationWiseSpread(RegionWiseSpread regionWiseSpread,
			LocationPoint locationPoint) {
			try {
				
				Properties p = loadPropertiesFile();
	
				int population = Integer.parseInt(p.getProperty("POPULATION_DENSITY"));
				Double k = Double.parseDouble(CommonUtils.getDefaultKFactor(p.getProperty("K_FACTOR")));
				Double r = Double.parseDouble(CommonUtils.getDefaultRFactor(p.getProperty("R_FACTOR")));
				Double s = Double.parseDouble(CommonUtils.getDefaultSocialDistance(p.getProperty("SOCIAL_DISTANCING")));
				Double usageofMask = Double.parseDouble(CommonUtils.getDefaultMaskUsage(p.getProperty("USAGE_OF_MASK")));
				Double effectivenessofMask = Double.parseDouble(CommonUtils.getDefaultEffectivenessOfMask(p.getProperty("EFFECTIVENESS_OF_MASK")));
				Integer noofDays = Integer.parseInt(CommonUtils.getDefaultNoofDays(p.getProperty("NO_OF_DAYS_FROM_TODAY")));
				regionWiseSpread.setLocation(locationPoint.getName());
				regionWiseSpread.setPopulation(population);
				regionWiseSpread.setK(k);
				regionWiseSpread.setR(r);
				regionWiseSpread.setSocialDistance(s);
				regionWiseSpread.setUsageOfMask(usageofMask);
				regionWiseSpread.setEffectivenessOfMask(effectivenessofMask);
				regionWiseSpread.setNoofDays(noofDays);
				
				logger.info("Configurations added....");
				return regionWiseSpread;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;

	}

	public Properties loadPropertiesFile() {
		FileInputStream in;
		Properties p = null;
		try {
			in = new FileInputStream("config.properties");
			p = new Properties();
			p.load(in);
			
		}catch (Exception e) {
			e.printStackTrace();
		}
		return p;
	}

	/*
	 * Generate no of persons based on POPULATION_DENSITY from config file 
	 */
	public void generateRandomData(RegionWiseSpread regionWiseSpread) {
			int population = regionWiseSpread.getPopulation();
			Random random = new Random();
			//List of persons
			PersonDirectory personDirectory = new PersonDirectory();
			personDirectory.setPersonList(new ArrayList<>());
			
			/*
			 * Calculate usage of mask factor
			 * Ex. usageofMask = 0.6 mean 60% people have worn mask
			 *     If Population_Density = 1000 means 600 people have worn mask
			 *    
			 * Wear mask to persons randomly
			 */
			Double usageofMask = regionWiseSpread.getUsageOfMask();
			int totalMasks = (int)(population * usageofMask);
			
			/*
			 * If usageOfMask = 1 mean 100% people have worn mask 
			 * so no need to wear masks to people randomly
			 * 
			 * maskcount = no of people who wear mask
			 */
			int maskcount = 0;
			boolean randomMask = true;
			if(totalMasks == population) {
				randomMask = false;
				maskcount = population;
			}
			
			/*
			 * Get random position of person by taking random x and y coordinates from 0 to 100
			 * randomMask = true -> assign random masks if usageofmask is not equal to 100%
			 * randomMask = false -> usageofmask is 100% mean no need to assign masks randomly to person
			 *                       because each person have to wear mask in case of 100% mask usage
			 * While creating person, based on random number a person has worn mask or has not worn mask
			 */
			for(int i=0;i<population;i++) {
				
				LocationPoint locationPoint = createNewLocationPoint(random);
				if(randomMask) {
					/*
					 * Select random number either 0 or 1
					 * 0 mean true
					 * 1 mean false
					 * based on this criteria assign mask to person
					 */
					boolean isWearMask = CommonUtils.getRandomBoolean(random.nextInt(2));
					if(maskcount <= totalMasks && isWearMask) {
						maskcount++;
					}else {
						isWearMask = false;
					}					
					personDirectory.createPerson(i, CommonUtils.randomAge(random.nextInt(80)), locationPoint, isWearMask);
				}else {
					personDirectory.createPerson(i, CommonUtils.randomAge(random.nextInt(80)), locationPoint, true);
				}
			}
			
			/*
			 * Based on usageOfMask, assign mask to person randomly
			 * If totalNoOfAssigned masks < totalMasks that need to assigned 
			 * then assign mask to person who has not worn mask yet until 
			 * nofAssignedMasks < totalMasks that needs to be assigned
			 */
			List<Person> persons = personDirectory.getPersonList();
			int i=0;
			while(maskcount < totalMasks) {
				Person person = persons.get(i);
				if(!person.isWearMask()) {
					person.setWearMask(true);
					maskcount++;
				}
				i++;
			}
			logger.info("Persons created...");
			personDirectory.generatePatient(personDirectory, regionWiseSpread);
			
//			createLineGraphAgeWiseInfected();
			
			//Create date wise infected people graph
			createLineGraphDateWiseInfected();
	   }

	public LocationPoint createNewLocationPoint(Random random) {
		LocationPoint locationPoint = new LocationPoint();
		boolean isPositiveX = CommonUtils.getRandomBoolean(random.nextInt(2));
		int x = random.nextInt(200);
		if(!isPositiveX) {
			x = x*-1;
		}
		
		int y = random.nextInt(200);
		if(!isPositiveX) {
			y = y*-1;
		}
		
		locationPoint.setX(x);
		locationPoint.setY(y);
		return locationPoint;
	}

	/*
	 * Datewise infected people graph GUI
	 */
	private void createLineGraphDateWiseInfected() {
		JFrame jFrame = new JFrame("Graph");
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setSize(900, 900);
		jFrame.setLocationRelativeTo(null);
		jFrame.setVisible(true);
		JPanel jPanel = new JPanel();
		jPanel.setBackground(new Color(236, 113, 107));
		Graph graph = new Graph(jFrame, jPanel);
		
		//calculate line graph from infected people
		graph.timeSeriesChart();
	}
}

