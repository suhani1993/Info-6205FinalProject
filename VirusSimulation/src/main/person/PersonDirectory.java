package main.person;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

import main.map.LocationPoint;
import main.spread.RegionWiseSpread;
import main.util.CommonUtils;

public class PersonDirectory {
	
	private List<Person> personList;
	public static Map<Date, List<Person>> perDayInfectedPeople = new TreeMap<>();
	
	public PersonDirectory() {
		personList = new ArrayList<>();
	}

	public List<Person> getPersonList() {
		return personList;
	}

	public void setPersonList(List<Person> personList) {
		this.personList = personList;
	}
	
	public void createPerson(int personId, int age, LocationPoint locationPoint, boolean isWearMask) {
		Person person = new Person();
		person.setPersonId(personId);
		person.setAge(age);
		person.setPoint(locationPoint);
		person.setFollowSocialDistancing(true);
		person.setInfected(false);	
		person.setWearMask(isWearMask);
		personList.add(person);
	}
	
	private void randomWalk(PersonDirectory personDirectory, int totalWalk, int population, Date nextDate, Double socialDistance) {
		Random random = new Random();
		for(int i=0;i<totalWalk;i++) {
			int randomPerson = random.nextInt(population);
			Person person = personDirectory.getPersonList().get(randomPerson);
			person.gotoAnotherPlace(person.getPoint(), random.nextInt(100), random.nextInt(100));
			
			for(Person nextPerson : personDirectory.getPersonList()) {
				if(person.getPersonId() != nextPerson.getPersonId() && 
						(person.isInfected() && !nextPerson.isInfected()) || (!person.isInfected() && nextPerson.isInfected())){
					
					double xSquare = Math.pow((person.getPoint().getX() - nextPerson.getPoint().getX()), 2);
					double ySquare = Math.pow((person.getPoint().getY() - nextPerson.getPoint().getY()), 2);
					if(Math.sqrt((xSquare + ySquare)) <= socialDistance) {
					
						if(perDayInfectedPeople.get(nextDate) == null) {
							perDayInfectedPeople.put(nextDate, new ArrayList<>());
						}
						if(!person.isInfected()) {
							person.setInfected(true);
							perDayInfectedPeople.get(nextDate).add(nextPerson);
						}else if(!nextPerson.isInfected()){
							nextPerson.setInfected(true);
							perDayInfectedPeople.get(nextDate).add(person);
						}
					}
				}
			}
		}
	}

	public void generatePatient(PersonDirectory personDirectory, RegionWiseSpread regionWiseSpread) {
		Calendar calendar = Calendar.getInstance();
		Random random = new Random();
		int randomWalkPercent = CommonUtils.randomWalkPercent(random.nextInt(6));
		int totalWalk = (regionWiseSpread.getPopulation() * randomWalkPercent)/100;
		for(int i=0;i<31;i++) {
			calendar.setTime(new Date());
			calendar.add(Calendar.DAY_OF_YEAR, i);
			Date nextDate = calendar.getTime();
			
			if(i==0) {
				Person person = personDirectory.getPersonList().get(0);
				person.setFollowSocialDistancing(false);
//				person.setWearMask(false);
				person.setInfected(true);
				
				person.gotoAnotherPlace(person.getPoint(), random.nextInt(100), random.nextInt(100));
				
				for(Person nextPerson : personDirectory.getPersonList()) {
					if(person.getPersonId() != nextPerson.getPersonId() && (person.isInfected() || nextPerson.isInfected())){
						
						double xSquare = Math.pow((person.getPoint().getX() - nextPerson.getPoint().getX()), 2);
						double ySquare = Math.pow((person.getPoint().getY() - nextPerson.getPoint().getY()), 2);
						if(Math.sqrt((xSquare + ySquare)) <= regionWiseSpread.getSocialDistance()) {
							if(perDayInfectedPeople.get(nextDate) == null) {
								perDayInfectedPeople.put(nextDate, new ArrayList<>());
							}
							if(!person.isInfected()) {
								person.setInfected(true);
								perDayInfectedPeople.get(nextDate).add(nextPerson);
							}else {
								nextPerson.setInfected(true);
								perDayInfectedPeople.get(nextDate).add(person);
							}
						}
					}
				}
			}
			
			if(i==0) {
				randomWalk(personDirectory, totalWalk-1, regionWiseSpread.getPopulation(), nextDate, regionWiseSpread.getSocialDistance());
			}else {
				randomWalk(personDirectory, totalWalk, regionWiseSpread.getPopulation(), nextDate, regionWiseSpread.getSocialDistance());
			}
		}
	}
}
