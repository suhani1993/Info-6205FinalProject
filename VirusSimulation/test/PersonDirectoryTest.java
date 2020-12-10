import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.junit.Test;

import main.map.LocationPoint;
import main.person.Person;
import main.person.PersonDirectory;
import main.spread.RegionWiseSpread;


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
		
		RegionWiseSpread spread = new RegionWiseSpread();
		spread.setEffectivenessOfMask(1);
		
		PersonDirectory directory = new PersonDirectory();
		directory.checkMaskEffectiveness(person, nextPerson, spread, new Random());
		
		assertEquals(true, person.isMaskEffective());
		assertEquals(false, nextPerson.isMaskEffective());
	}

	@Test
	public void testSetDateAndTimeZero() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		PersonDirectory directory = new PersonDirectory();
		directory.setDateAndTimeZero(calendar);
		assertNotSame(calendar.getTime(), new Date());
	}
	
	@Test
	public void testGetNextDate() {
		Calendar calendar = Calendar.getInstance();
		PersonDirectory directory = new PersonDirectory();
		Date date = directory.getNextDate(2, calendar);
		
		
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_YEAR, 2);
		calendar.set(Calendar.MILLISECOND, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.HOUR, 0);
		Date nextDate = calendar.getTime();
		
		assertEquals(date, nextDate, "Both Dates are same.");
	}
	
	@Test
	public void testGetAndSetPersonDirectory() {
		PersonDirectory directory = new PersonDirectory();
		assertNotNull(directory.getPersonList());
		
		List<Person> personList = new ArrayList<>();
		personList.add(new Person());
		personList.add(null);
		directory.setPersonList(personList);
		
		assertEquals(2, directory.getPersonList().size());
		
	}
}
