package org.example.erudio.repositories;

import org.example.erudio.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface PersonReopsitory extends JpaRepository<Person, Long> {
}
