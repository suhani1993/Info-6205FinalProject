

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.Test;

import main.map.LocationPoint;
import main.person.Person;
import main.person.PersonDirectory;

public class PersonTest {

	@Test
	public void testGetAndSetAge() {
		Person person = new Person();
		person.setAge(18);
		assertEquals(person.getAge(), 18);
	}
	
	@Test
	public void testGetAndSetInfected() {
		Person person = new Person();
		person.setInfected(true);
		assertEquals(person.isInfected(), true);
	}
	
	@Test
	public void testIsWearMask() {
		Person person = new Person();
		person.setWearMask(true);
		assertEquals(person.isWearMask(), true);
	}

	@Test
	public void testSetAndGetPersonId() {
		Person person = new Person();
		person.setPersonId(1);
		assertEquals(person.getPersonId(), 1);
	}
	
}
