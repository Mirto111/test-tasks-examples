package ru.examples.repository;

import org.springframework.data.repository.CrudRepository;
import ru.examples.model.City;

public interface CityRepository extends CrudRepository<City, Long> {

  City findByName(String name);

}
