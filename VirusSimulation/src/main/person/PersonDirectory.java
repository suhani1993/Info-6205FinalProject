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
	public static Map<Integer, List<Person>> agewiseInfectedPeople = new TreeMap<>();
	
	public PersonDirectory() {
		personList = new ArrayList<>();
	}

	public List<Person> getPersonList() {
		return personList;
	}

	public void setPersonList(List<Person> personList) {
		this.personList = personList;
	}
	
	//Create new person and add it to list 
	public void createPerson(int personId, int age, LocationPoint locationPoint, boolean isWearMask) {
		Person person = new Person();
		person.setPersonId(personId);
		person.setAge(age);
		person.setPoint(locationPoint);
//		person.setFollowSocialDistancing(true);
		person.setInfected(false);	
		person.setWearMask(isWearMask);
		personList.add(person);
	}
	
	/*
	 * R-Factor -> No of persons an infected person can infect
	 * totalWalk -> At each day, between 5% and 10% people of total population can walk
	 *              so it is the number which represents no of people who walk per day
	 * nextDate -> Day between 0 and 30 including from current date
	 * person -> The very person who is infected
	 * 
	 * 
	 * 1. check distance using Euclidian distance between two persons
	 * 2. If distance <= SOCIAL_DISTANCING (factor in config file) then check Maskeffectiveness
	 * 3. If both people have worn mask then only check mask effectiveness
	 * 4. Infect people if not already infected
	 */
	private void randomWalk(PersonDirectory personDirectory, int totalWalk, Date nextDate, RegionWiseSpread regionWiseSpread, Person person) {
		Random random = new Random();
		int R_Factor = (int) Math.round(regionWiseSpread.getR());
		/*
		 * Per day between 5% and 10% people walk
		 */
		for(int i=0;i<totalWalk;i++) {
			int randomPerson = random.nextInt(regionWiseSpread.getPopulation());
			if(i != 0 || person == null) {
				person = personDirectory.getPersonList().get(randomPerson);
			}
			
			//Perform random walk of a person
			person.gotoAnotherPlace(person.getPoint(), random.nextInt(100), random.nextInt(100));
			
			/*
			 * A person can infect maximum no of people = R_Factor
			 * If a person has already infected noOfPeople = R_Factor
			 * then stop further calculation of a person 
			 */
			if(person.isInfected() && (person.getInfectedPeople() != null &&
					person.getInfectedPeople().size() >= R_Factor)) {
				continue;
			}
			
			/*
			 * Check if an infected person can infect other people based on criteria
			 * 1. Check if one of the person is infected
			 * 2. If yes then find distance between two, if distance <= social distance then check 3rd point
			 * 3. check mask effectiveness
			 * 4. If mask is not effective then infect a person
			 */
			for(Person nextPerson : personDirectory.getPersonList()) {
				if(person.getPersonId() != nextPerson.getPersonId() && 
						(person.isInfected() && !nextPerson.isInfected()) || (!person.isInfected() && nextPerson.isInfected())){
					
					double euDistance = CommonUtils.euclidianDistance(person.getPoint().getX() , nextPerson.getPoint().getX(), 
							person.getPoint().getY(), nextPerson.getPoint().getY());
//					double xSquare = Math.pow((person.getPoint().getX() - nextPerson.getPoint().getX()), 2);
//					double ySquare = Math.pow((person.getPoint().getY() - nextPerson.getPoint().getY()), 2);
					if(euDistance <= regionWiseSpread.getSocialDistance()) {
					
						/*
						 * A person has no of infected people count
						 * If his count is equal to R_Factor then stop process of that person
						 */
						if(person.isInfected() && (person.getInfectedPeople() != null &&
								person.getInfectedPeople().size() >= R_Factor)) {
							break;
						}
						/*
						 * A person has no of infected people count
						 * If his count is equal to R_Factor then stop process of that person
						 */
						if(nextPerson.isInfected() && (nextPerson.getInfectedPeople() != null &&
								nextPerson.getInfectedPeople().size() >= R_Factor)) {
							continue;
						}
						//check mask effectiveness
						checkMaskEffectiveness(person, nextPerson, regionWiseSpread, random);
						
						if(perDayInfectedPeople.get(nextDate) == null) {
							perDayInfectedPeople.put(nextDate, new ArrayList<>());
						}
						
						/*
						 * 1. If one person has worn mask and other has not worn mask then infect him
						 * 2. If a person has worn mask and other person has also worn mask but anyone 
						 *    or both of their masks are not effective then infect them
						 * 3. If both persons has worn mask and both masks are effective then do
						 *    not infect a person
						 */
//						if(person.isMaskEffective() || nextPerson.isMaskEffective()) {
//							continue;
//						}else
						if(!person.isMaskEffective() || !nextPerson.isMaskEffective()) {
							if(person.getInfectedPeople() == null) {
								person.setInfectedPeople(new ArrayList<>());
							}
							
							if(nextPerson.getInfectedPeople() == null) {
								nextPerson.setInfectedPeople(new ArrayList<>());
							}
							
							if(!person.isInfected() && !person.isMaskEffective()) {
								person.setInfected(true);
//								if(agewiseInfectedPeople.get(person.getAge()) == null) {
//									agewiseInfectedPeople.put(person.getAge(), new ArrayList<>());
//								}
//								agewiseInfectedPeople.get(person.getAge()).add(person);
								nextPerson.getInfectedPeople().add(person);
								perDayInfectedPeople.get(nextDate).add(person);
							}
							if(!nextPerson.isInfected() && !nextPerson.isMaskEffective()) {
								nextPerson.setInfected(true);
								
//								if(agewiseInfectedPeople.get(nextPerson.getAge()) == null) {
//									agewiseInfectedPeople.put(nextPerson.getAge(), new ArrayList<>());
//								}
//								agewiseInfectedPeople.get(nextPerson.getAge()).add(nextPerson);
								
								person.getInfectedPeople().add(nextPerson);
								perDayInfectedPeople.get(nextDate).add(nextPerson);
							}
						}
					}
				}
			}
		}
	}

	/*
	 * agewiseInfectedPeople -> agewise infected people list
	 * 
	 * From 0 to 30 days, everyday perform 10% random walk of total population
	 * For Ex. If total population is 1000 then everyday perform random walk 
	 * of 100 people
	 * 
	 * Make the very first person from list of persons -> infected
	 */
	public void generatePatient(PersonDirectory personDirectory, RegionWiseSpread regionWiseSpread) {
		Calendar calendar = Calendar.getInstance();
		Random random = new Random();
		int randomWalkPercent = CommonUtils.randomWalkPercent(random.nextInt(5));
		int totalWalk = (regionWiseSpread.getPopulation() * randomWalkPercent)/100;
		//For 30 day the system performs disease spread calculation
		for(int i=0;i<regionWiseSpread.getNoofDays();i++) {
			calendar.setTime(new Date());
			calendar.add(Calendar.DAY_OF_YEAR, i);
			Date nextDate = calendar.getTime();
			
			if(i==0) {
				Person person = personDirectory.getPersonList().get(0);
				person.setFollowSocialDistancing(false);
				person.setInfected(true);
				if(perDayInfectedPeople.get(nextDate) == null) {
					perDayInfectedPeople.put(nextDate, new ArrayList<>());
				}
				perDayInfectedPeople.get(nextDate).add(person);
//				if(agewiseInfectedPeople.get(person.getAge()) == null) {
//					List<Person> persons = new ArrayList<>();
//					persons.add(person);
//					agewiseInfectedPeople.put(person.getAge(), persons);
//				}
				randomWalk(personDirectory, totalWalk, nextDate, regionWiseSpread, person);
			}else {
				randomWalk(personDirectory, totalWalk, nextDate, regionWiseSpread, null);
			}
		}
	}

	/*
	 * When a person is in range of social distance with other person then check if anyone has worn mask
	 * If yes then calculate mask effectiveness of the person who has worn mask
	 * Ex. Mask effectiveness = 0.8 mean 80% mask is effective and can control spread of disease 80%
	 *     So a person will not be infected has 80% chances
	 *     Get random no from 0 to 100 -> If no between 0 and 80 mean a person will not infect
	 *     If a no between 81 and 100 then a person will infect
	 */
	private void checkMaskEffectiveness(Person person, Person nextPerson, RegionWiseSpread regionWiseSpread, Random random) {
		if(person.isWearMask() || nextPerson.isWearMask()) {
			int maskEffectiveness = (int) Math.round(regionWiseSpread.getEffectivenessOfMask() * 100);
			int effectiveness = random.nextInt(100);
			if(effectiveness <= maskEffectiveness) {
				if(person.isWearMask()) {
					person.setMaskEffective(true);
				}
				if(nextPerson.isWearMask()) {
					nextPerson.setMaskEffective(true);
				}
			}else {
				if(person.isWearMask()) {
					person.setMaskEffective(false);
				}
				if(nextPerson.isWearMask()) {
					nextPerson.setMaskEffective(false);
				}
			}
		}
	}
}

