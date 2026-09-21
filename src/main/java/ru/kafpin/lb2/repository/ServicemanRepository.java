package ru.kafpin.lb2.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ru.kafpin.lb2.model.Serviceman;

public interface ServicemanRepository extends JpaRepository<Serviceman, Long> {
}
