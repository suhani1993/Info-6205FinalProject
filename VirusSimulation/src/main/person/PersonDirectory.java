package main.person;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.TreeMap;
import java.util.logging.Logger;

import main.map.LocationPoint;
import main.spread.RegionWiseSpread;
import main.util.CommonUtils;

public class PersonDirectory {
	
	static Logger logger = Logger.getLogger(PersonDirectory.class.getName());
	
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
	 * nextDate -> Day between 0 and 30 including current date
	 * previousDate -> Previous date 
	 * person -> The very person who is infected
	 * 
	 * 
	 * 1. check distance using Euclidian distance between two persons
	 * 2. If distance <= SOCIAL_DISTANCING (factor in config file) then check Maskeffectiveness
	 * 3. If both people have worn mask then only check mask effectiveness
	 * 4. Infect people if not already infected
	 */
	private void randomWalk(PersonDirectory personDirectory, Date nextDate, Date previousDate, RegionWiseSpread regionWiseSpread, Queue<Person> personQueue) {
		Random random = new Random();
		int R_Factor = (int) Math.round(regionWiseSpread.getR());
		while(personQueue.peek() != null && personQueue.peek().getInfectionDate().equals(previousDate)) {
			Person person = personQueue.remove();
			
			person.gotoAnotherPlace(person.getPoint(), random.nextInt(200), random.nextInt(200));
			
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
						
						/*
						 * 1. If one person has worn mask and other has not worn mask then infect him
						 * 2. If a person has worn mask and other person has also worn mask but anyone 
						 *    or both of their masks are not effective then infect them
						 * 3. If both persons has worn mask and both masks are effective then do
						 *    not infect a person
						 */
						if(person.isMaskEffective() || nextPerson.isMaskEffective()) {
							continue;
						}else if(!person.isMaskEffective() || !nextPerson.isMaskEffective()) {
							if(person.getInfectedPeople() == null) {
								person.setInfectedPeople(new ArrayList<>());
							}
							
							if(nextPerson.getInfectedPeople() == null) {
								nextPerson.setInfectedPeople(new ArrayList<>());
							}
							
							if(!person.isInfected() && !person.isMaskEffective()) {
								person.setInfected(true);
								person.setInfectionDate(nextDate);
								nextPerson.getInfectedPeople().add(person);
								addDataInInfectedPeopleMap(nextDate, person);
								personQueue.add(person);
							}
							if(!nextPerson.isInfected() && !nextPerson.isMaskEffective()) {
								nextPerson.setInfected(true);
								nextPerson.setInfectionDate(nextDate);
								person.getInfectedPeople().add(nextPerson);
								addDataInInfectedPeopleMap(nextDate, nextPerson);
								personQueue.add(nextPerson);
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
		Queue<Person> personQueue = new LinkedList<Person>();
		int initialInfected = (int)Math.round((regionWiseSpread.getPopulation() * 0.02)/100);
		if(initialInfected == 0) {
			initialInfected = 10;
		}
		for(int i=0;i<regionWiseSpread.getNoofDays();i++) {
			
			Date nextDate = getNextDate(i, calendar);
			if(i==0) {
				logger.info("Virus Spread Start...");
				for(int k=0;k<initialInfected;k++) {
					Person person = personDirectory.getPersonList().get(k);
					person.setInfected(true);
					person.setInfectionDate(nextDate);
					addDataInInfectedPeopleMap(nextDate, person);
					personQueue.add(person);
				}
			}else {
				if(i==(regionWiseSpread.getNoofDays()/2)) {
					logger.info("50% calculation complete...");
				}
				if(!personQueue.isEmpty()) {
					calendar.setTime(nextDate);
					calendar.add(Calendar.DAY_OF_YEAR, -1);
					Date previousDate = calendar.getTime();
					randomWalk(personDirectory, nextDate, previousDate, regionWiseSpread, personQueue);
				}else {
					break;
				}
			}
		}
		logger.info("Virus Spread Complete...");
	}

	public Date getNextDate(int i, Calendar calendar) {
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_YEAR, i);
		calendar = setDateAndTimeZero(calendar);
		return calendar.getTime();
		
	}

	public void addDataInInfectedPeopleMap(Date nextDate, Person person) {
		if(perDayInfectedPeople.get(nextDate) == null) {
			perDayInfectedPeople.put(nextDate, new ArrayList<>());
		}
		perDayInfectedPeople.get(nextDate).add(person);
	}

	public Calendar setDateAndTimeZero(Calendar calendar) {
		calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR, 0);
        return calendar;
	}

	/*
	 * When a person is in range of social distance with other person then check if anyone has worn mask
	 * If yes then calculate mask effectiveness of the person who has worn mask
	 * Ex. Mask effectiveness = 0.8 mean 80% mask is effective and can control spread of disease 80%
	 *     So a person will not be infected has 80% chances
	 *     Get random no from 0 to 100 -> If no between 0 and 80 mean a person will not infect
	 *     If a no between 81 and 100 then a person will infect
	 */
	public void checkMaskEffectiveness(Person person, Person nextPerson, RegionWiseSpread regionWiseSpread, Random random) {
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

