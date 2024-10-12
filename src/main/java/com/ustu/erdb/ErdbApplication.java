package com.ustu.erdb;

import com.ustu.erdb.base.services.impl.EnumerationService;
import com.ustu.erdb.base.services.impl.EnumerationValueService;
import com.ustu.erdb.base.store.models.Enumeration;
import com.ustu.erdb.base.store.models.EnumerationValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class ErdbApplication {

	public static void main(String[] args) {
		SpringApplication.run(ErdbApplication.class, args);
	}

	@Autowired
	private EnumerationService enumerationService;

	@Autowired
	private EnumerationValueService enumerationValueService;

	@EventListener(ApplicationReadyEvent.class)
	public void s() {

		Enumeration enumeration = enumerationService.create(Enumeration.builder()
				.label("студент")
				.code("student")
				.build());

		EnumerationValue enumerationValue = enumerationValueService.create(EnumerationValue.builder()
				.label("ИСТ-18")
				.code("ИСТ-18")
				.enumeration(enumeration)
				.build());

		Enumeration enumeration2 = enumerationService.create(Enumeration.builder()
				.label("преподаватель")
				.code("teacher")
				.build());

		EnumerationValue enumerationValue2 = enumerationValueService.create(EnumerationValue.builder()
				.label("доцент")
				.code("доцент")
				.enumeration(enumeration2)
				.build());

		System.out.println(enumerationValueService.getByEnumeration(enumeration));
		System.out.println(enumerationValueService.getByEnumeration(enumeration2));
	}
}
