package main.spread;

import java.awt.Color;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

import main.Graph;
import main.map.LocationPoint;
import main.person.PersonDirectory;
import main.util.CommonUtils;

public class RegionWiseSpread extends Spread {

	private String location;
	private int population;
	private int death;
	private int cases;
	private int recover;
	private Double socialDistance;
	private double usageOfMask;
	private double effectivenessOfMask;

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

	public int getDeath() {
		return death;
	}

	public void setDeath(int death) {
		this.death = death;
	}

	public int getCases() {
		return cases;
	}

	public void setCases(int cases) {
		this.cases = cases;
	}

	public int getRecover() {
		return recover;
	}

	public void setRecover(int recover) {
		this.recover = recover;
	}

	public RegionWiseSpread setDataInLocationWiseSpread(RegionWiseSpread regionWiseSpread,
			LocationPoint locationPoint) {
			FileInputStream in;
			try {
				in = new FileInputStream("config.properties");
				Properties p = new Properties();
				p.load(in);
	
				int population = Integer.parseInt(p.getProperty("POPULATION_DENSITY"));
				Double k = Double.parseDouble(CommonUtils.getDefaultKFactor(p.getProperty("K_FACTOR")));
				Double r = Double.parseDouble(CommonUtils.getDefaultRFactor(p.getProperty("R_FACTOR")));
				Double s = Double.parseDouble(CommonUtils.getDefaultSocialDistance(p.getProperty("SOCIAL_DISTANCING")));
				Double usageofMask = Double.parseDouble(CommonUtils.getDefaultMaskUsage(p.getProperty("USAGE_OF_MASK")));
				Double effectivenessofMask = Double.parseDouble(CommonUtils.getDefaultEffectivenessOfMask(p.getProperty("EFFECTIVENESS_OF_MASK")));
				regionWiseSpread.setLocation(locationPoint.getName());
				regionWiseSpread.setPopulation(population);
				regionWiseSpread.setK(k);
				regionWiseSpread.setR(r);
				regionWiseSpread.setSocialDistance(s);
				regionWiseSpread.setUsageOfMask(usageofMask);
				regionWiseSpread.setEffectivenessOfMask(effectivenessofMask);			
				return regionWiseSpread;
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;

	}

	public void generateRandomData(RegionWiseSpread regionWiseSpread) {
			int population = regionWiseSpread.getPopulation();
			Random random = new Random();
			PersonDirectory personDirectory = new PersonDirectory();
			personDirectory.setPersonList(new ArrayList<>());
			
			
			Double usageofMask = regionWiseSpread.getUsageOfMask();
			int totalMasks = (int)(population * usageofMask);
			
			int maskcount = 0;
			for(int i=0;i<population;i++) {
				LocationPoint locationPoint = new LocationPoint();
				locationPoint.setX((random.nextInt(50)));
				locationPoint.setY((random.nextInt(50)));
				boolean isWearMask = CommonUtils.getRandomBoolean(random.nextInt(2));
				if(maskcount <= totalMasks && isWearMask) {
					maskcount++;
				}else {
					isWearMask = false;
				}
				personDirectory.createPerson(i, CommonUtils.randomAge(random.nextInt(80)), locationPoint, isWearMask);
			}
			
			personDirectory.generatePatient(personDirectory, regionWiseSpread);
			
			createGraph();
	   }

	private void createGraph() {
		JFrame jFrame = new JFrame("Graph");
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setSize(800, 800);
		jFrame.setVisible(true);
		JPanel jPanel = new JPanel();
		jPanel.setBackground(new Color(236, 113, 107));
		Graph graph = new Graph(jFrame, jPanel);
		graph.populateBarGraph();
	}

}
