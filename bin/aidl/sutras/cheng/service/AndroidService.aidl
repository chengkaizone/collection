package sutras.cheng.service;

import java.util.List;
import sutras.cheng.service.Person;

interface AndroidService{
	String getName();
	double getSalary();
	List<Person> getPersons();
	String print(in Person person);
}