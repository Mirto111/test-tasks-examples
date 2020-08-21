package ru.examples.controller;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.annotation.PostConstruct;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import ru.examples.Application;
import ru.examples.model.City;


@SpringBootTest(classes = Application.class, webEnvironment = WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
public class CityControllerTest {

  private static final CharacterEncodingFilter CHARACTER_ENCODING_FILTER = new CharacterEncodingFilter();

  static {
    CHARACTER_ENCODING_FILTER.setEncoding("UTF-8");
    CHARACTER_ENCODING_FILTER.setForceEncoding(true);
  }

  private City city;

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private WebApplicationContext webApplicationContext;

  @PostConstruct
  private void postConstruct() {
    mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
        .addFilter(CHARACTER_ENCODING_FILTER)
        .build();
  }

  @BeforeEach
  void setUp() throws Exception {
    city = createRandomCity();
  }

  @Test
  public void create() throws Exception {
    City city = new City();
    city.setName("Москва");
    city.setInfo("Посетите Красную площадь");

    MvcResult result = this.mockMvc.perform(post("/api/city")
        .contentType(MediaType.APPLICATION_JSON).content(mapToJson(city)))
        .andExpect(status().isCreated()).andReturn();

    City actual = fromResponse(result);
    city.setId(actual.getId());
    assertEquals(city, actual);
  }

  @Test
  public void update() throws Exception {
    city.setInfo("newInfo");
    MvcResult result = this.mockMvc.perform(put("/api/city/" + city.getName())
        .contentType(MediaType.APPLICATION_JSON).content(mapToJson(city)))
        .andExpect(status().isOk()).andReturn();

    City actual = fromResponse(result);
    assertEquals(city, actual);
  }

  @Test
  public void deleteCity() throws Exception {
    this.mockMvc.perform(delete("/api/city/" + city.getName()))
        .andExpect(status().isOk());
    this.mockMvc.perform(get("/api/city/" + city.getName()))
        .andExpect(status().isNotFound());
  }

  @Test
  public void getByName() throws Exception {
    this.mockMvc.perform(get("/api/city/" + city.getName()))
        .andExpect(status().isOk())
        .andExpect(content()
            .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
        .andExpect(content().json(mapToJson(city)));

  }

  private City createRandomCity() throws Exception {
    final City city = new City();
    city.setName(randomAlphabetic(10));
    city.setInfo(randomAlphabetic(15));
    MvcResult result = this.mockMvc.perform(post("/api/city")
        .contentType(MediaType.APPLICATION_JSON).content(mapToJson(city)))
        .andExpect(status().isCreated()).andReturn();
    city.setId(fromResponse(result).getId());
    return city;
  }

  private String mapToJson(Object obj) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.writeValueAsString(obj);
  }

  private <T> T mapFromJson(String json, Class<T> clazz) throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.readValue(json, clazz);
  }

  private City fromResponse(MvcResult result) throws Exception {
    String string = result.getResponse().getContentAsString();
    return mapFromJson(string, City.class);
  }

}
