package main.person;

import java.util.Date;
import java.util.List;
import java.util.Random;

import main.map.LocationPoint;
import main.util.CommonUtils;

public class Person {

	private int personId;
//	private List<Symptom> symptoms;
//	private boolean isAsymptotic;
	private LocationPoint point;
	private int age;
	private boolean isInfected;
	private int noofPeopleInContact;
	private boolean isWearMask;
	private boolean isFollowSocialDistancing;
	private Date infectionDate;
	private boolean isMaskEffective;
	private List<Person> infectedPeople;

	public Date getInfectionDate() {
		return infectionDate;
	}

	public void setInfectionDate(Date infectionDate) {
		this.infectionDate = infectionDate;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public LocationPoint getPoint() {
		return point;
	}

	public void setPoint(LocationPoint point) {
		this.point = point;
	}

	public boolean isInfected() {
		return isInfected;
	}

	public void setInfected(boolean isInfected) {
		this.isInfected = isInfected;
	}

	public int getNoofPeopleInContact() {
		return noofPeopleInContact;
	}

	public void setNoofPeopleInContact(int noofPeopleInContact) {
		this.noofPeopleInContact = noofPeopleInContact;
	}

	public boolean isWearMask() {
		return isWearMask;
	}

	public void setWearMask(boolean isWearMask) {
		this.isWearMask = isWearMask;
	}

	public boolean isFollowSocialDistancing() {
		return isFollowSocialDistancing;
	}

	public void setFollowSocialDistancing(boolean isFollowSocialDistancing) {
		this.isFollowSocialDistancing = isFollowSocialDistancing;
	}

	public int getPersonId() {
		return personId;
	}

	public void setPersonId(int personId) {
		this.personId = personId;
	}

//	public List<Symptom> getSymptoms() {
//		return symptoms;
//	}
//
//	public void setSymptoms(List<Symptom> symptoms) {
//		this.symptoms = symptoms;
//	}

	public List<Person> getInfectedPeople() {
		return infectedPeople;
	}

	public void setInfectedPeople(List<Person> infectedPeople) {
		this.infectedPeople = infectedPeople;
	}

	/*
	 * Walk a person randomly in x and y direction between 0 and 100 steps
	 * Add that random number in person's previous walk mean previous x and y location
	 */
	public static void gotoAnotherPlace(LocationPoint locationPoint, int nextX, int nextY) {
		Random random = new Random();

		boolean isPositiveX = CommonUtils.getRandomBoolean(random.nextInt(2));
		if(!isPositiveX) {
			nextX = nextX * -1;
		}
		
		boolean isPositiveY = CommonUtils.getRandomBoolean(random.nextInt(2));
		if(!isPositiveY) {
			nextY = nextY * -1;
		}
		nextX = locationPoint.getX() + nextX;
		if(nextX > 200) {
			nextX = random.nextInt(200);
		}else if(nextX < -200) {
			nextX = random.nextInt(200) * -1;
		}
		nextY = locationPoint.getY() + nextY;
		if(nextY > 200) {
			nextY = random.nextInt(200);
		}else if(nextY < -200) {
			nextY = random.nextInt(200) * -1;
		}

		locationPoint.setX(nextX);
		locationPoint.setY(nextY);
	}

	public boolean isMaskEffective() {
		return isMaskEffective;
	}

	public void setMaskEffective(boolean isMaskEffective) {
		this.isMaskEffective = isMaskEffective;
	}

}

