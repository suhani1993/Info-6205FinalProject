import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.Test;

import main.map.LocationPoint;
import main.person.Person;
import main.person.PersonDirectory;


public class PersonDirectoryTest {

	@Test
	public void testCompareObject() {
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
	
	@Test
	public void checkMaskEffectiveness() {
		Person person = new Person();
		person.setWearMask(true);
		
		Person nextPerson = new Person();
		nextPerson.setWearMask(false);
		
//		Reg
	}

}
