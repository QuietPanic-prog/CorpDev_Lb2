package ru.kafpin.lb2.config;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import ru.kafpin.lb2.model.Serviceman;
import ru.kafpin.lb2.repository.ServicemanRepository;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner seedDatabase(ServicemanRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                repository.save(createServiceman("Бунеев", "Юрий", "Сергеевич", "русский",
                        LocalDate.of(1997, 5, 14), "Командир взвода", "старший лейтенант"));
                repository.save(createServiceman("Прокопов", "Дмитрий", "Александрович", "русский",
                        LocalDate.of(1992, 11, 2), "Командир отделения", "старший сержант"));
                repository.save(createServiceman("Якушев", "Олег", "Владимирович", "русский",
                        LocalDate.of(1993, 2, 21), "Начальник станции", "младший сержант"));
            }
        };
    }

    private Serviceman createServiceman(String lastName, String firstName, String patronymic,
            String nationality, LocalDate birthDate, String position, String rank) {
        Serviceman serviceman = new Serviceman();
        serviceman.setLastName(lastName);
        serviceman.setFirstName(firstName);
        serviceman.setPatronymic(patronymic);
        serviceman.setNationality(nationality);
        serviceman.setBirthDate(birthDate);
        serviceman.setPosition(position);
        serviceman.setRank(rank);
        return serviceman;
    }
}
