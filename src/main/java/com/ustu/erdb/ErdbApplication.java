package com.ustu.erdb;

import com.ustu.erdb.base.services.impl.EnumerationService;
import com.ustu.erdb.base.services.impl.EnumerationValueService;
import com.ustu.erdb.base.store.models.Enumeration;
import com.ustu.erdb.base.store.models.EnumerationValue;
import com.ustu.erdb.modules.persons.services.PersonService;
import com.ustu.erdb.modules.persons.services.UserService;
import com.ustu.erdb.modules.persons.store.models.Person;
import com.ustu.erdb.modules.persons.store.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class ErdbApplication {

	public static void main(String[] args) {
		SpringApplication.run(ErdbApplication.class, args);
	}

	@Autowired
	private EnumerationService enumerationService;

	@Autowired
	private EnumerationValueService enumerationValueService;

	@Autowired
	private UserService userService;

	@Autowired
	private PersonService personService;

	@EventListener(ApplicationReadyEvent.class)
	public void s() {

//		Enumeration enumeration = enumerationService.create(Enumeration.builder()
//				.label("Тип пользователя")
//				.code("persontype")
//				.build());
//		EnumerationValue enumerationValue = enumerationValueService.create(EnumerationValue.builder()
//				.label("Студент")
//				.code("student")
//				.enumeration(enumeration)
//				.build());
//		EnumerationValue enumerationValue1 = enumerationValueService.create(EnumerationValue.builder()
//				.label("Преподаватель")
//				.code("teacher")
//				.enumeration(enumeration)
//				.build());
//
//		Enumeration enumeration2 = enumerationService.create(Enumeration.builder()
//				.label("Группа")
//				.code("group")
//				.build());
//		EnumerationValue enumerationValue3 = enumerationValueService.create(EnumerationValue.builder()
//				.label("Тестовая группа")
//				.code("testGroup")
//				.enumeration(enumeration2)
//				.build());
//
//		Enumeration enumeration3 = enumerationService.create(Enumeration.builder()
//				.label("Должность")
//				.code("position")
//				.build());
//		EnumerationValue enumerationValue4 = enumerationValueService.create(EnumerationValue.builder()
//				.label("Доцент")
//				.code("доцент")
//				.enumeration(enumeration3)
//				.build());
//
//		User user = userService.create(User.builder().login("test").password("test").build());
//		personService.create(Person.builder().firstName("A").lastName("B").type(enumerationValue1).group(enumerationValue4).user(user).build());
	}
}
