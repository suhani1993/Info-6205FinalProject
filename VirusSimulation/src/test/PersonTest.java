package test;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import main.map.LocationPoint;
import main.person.Person;
import main.person.PersonDirectory;

class PersonTest {

	@Test
	void testGetAndSetAge() {
		Person person = new Person();
		person.setAge(18);
		assertEquals(person.getAge(), 18);
	}
	
	@Test
	void testGetAndSetInfected() {
		Person person = new Person();
		person.setInfected(true);
		assertEquals(person.isInfected(), true);
	}
	
	@Test
	void testIsWearMask() {
		Person person = new Person();
		person.setWearMask(true);
		assertEquals(person.isWearMask(), true);
	}

	@Test
	void testSetAndGetPersonId() {
		Person person = new Person();
		person.setPersonId(1);
		assertEquals(person.getPersonId(), 1);
	}
	
	@Test
	void testCompareObject() {
		int personId = 5;
		LocationPoint locationPoint = new LocationPoint();
		locationPoint.setX(10);
		locationPoint.setY(20);
		int age=35;
		boolean isWearMask = true;
		
		
		PersonDirectory directory = new PersonDirectory();
		directory.createPerson(personId, age, locationPoint, isWearMask);
		List<Person> personList = directory.getPersonList();
		Person person = personList.get(0);
		assertEquals(person.getPersonId(), personId);
		assertNotNull(person.getPoint());
		assertEquals(person.getPoint().getX(), 10);
		assertEquals(person.getPoint().getY(), 20);
		assertEquals(person.getAge(), age);
		assertEquals(person.isWearMask(), true);
	}
}
