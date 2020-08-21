package ru.examples.controller;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import ru.examples.exception.NotFoundException;
import ru.examples.model.City;
import ru.examples.repository.CityRepository;

@RestController
@RequestMapping("/api/city")
public class CityController {

  private final CityRepository repository;

  @Autowired
  public CityController(CityRepository repository) {
    this.repository = repository;
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  public City create(@RequestBody City city) {
    return repository.save(city);
  }

  @PutMapping("/{name}")
  public City update(@PathVariable String name, @RequestBody City city ) {
    if (!city.getName().equals(name)) {
      throw new IllegalArgumentException();
    }
    getByName(name);
    return repository.save(city);
  }

  @DeleteMapping("/{name}")
  public void delete(@PathVariable String name) {
    City city = getByName(name);
    repository.delete(city);
  }

  @GetMapping("/{name}")
  public City getByName(@PathVariable String name) {
    return Optional.ofNullable(repository.findByName(name))
        .orElseThrow(NotFoundException::new);
  }
}
