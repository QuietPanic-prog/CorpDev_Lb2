package ru.kafpin.lb2;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import ru.kafpin.lb2.model.Serviceman;
import ru.kafpin.lb2.repository.ServicemanRepository;

@SpringBootTest
class Lb2ApplicationTests {

	@Autowired
	private ServicemanRepository servicemanRepository;

	@Test
	void contextLoads() {
		assertThat(servicemanRepository).isNotNull();
	}

	@Test
	void performsCrudOperations() {
		Serviceman serviceman = new Serviceman();
		serviceman.setLastName("Тестов");
		serviceman.setFirstName("Тест");
		serviceman.setPatronymic("Тестович");
		serviceman.setNationality("русский");
		serviceman.setBirthDate(LocalDate.of(2000, 1, 1));
		serviceman.setPosition("Тестовая должность");
		serviceman.setRank("рядовой");

		Serviceman saved = servicemanRepository.save(serviceman);
		assertThat(servicemanRepository.findById(saved.getId())).isPresent();

		saved.setRank("ефрейтор");
		servicemanRepository.save(saved);
		assertThat(servicemanRepository.findById(saved.getId()))
				.get()
				.extracting(Serviceman::getRank)
				.isEqualTo("ефрейтор");

		servicemanRepository.deleteById(saved.getId());
		assertThat(servicemanRepository.existsById(saved.getId())).isFalse();
	}

}
