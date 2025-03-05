//package org.example.erudio.integrations.controllers;
//
//import static io.restassured.RestAssured.given;
//import static org.junit.jupiter.api.Assertions.*;
//
//import org.example.erudio.configs.TestConfigs;
//import org.example.erudio.integrations.AbstractIntegrationTest;
//import org.example.erudio.integrations.moks.AccountCredentialsDto;
//import org.example.erudio.integrations.moks.Person2Dto;
//import org.example.erudio.integrations.moks.Person2Dto;
//import org.example.erudio.integrations.moks.TokenDto;
//import org.example.erudio.integrations.wrappers.WrapperPersonVO;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
//import org.junit.jupiter.api.Order;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.TestMethodOrder;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.DeserializationFeature;
//import com.fasterxml.jackson.databind.JsonMappingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//
//import io.restassured.builder.RequestSpecBuilder;
//import io.restassured.filter.log.LogDetail;
//import io.restassured.filter.log.RequestLoggingFilter;
//import io.restassured.filter.log.ResponseLoggingFilter;
//import io.restassured.specification.RequestSpecification;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
//@TestMethodOrder(OrderAnnotation.class)
//public class PersonControllerJsonTest extends AbstractIntegrationTest {
//
//	private static RequestSpecification specification;
//	private static ObjectMapper objectMapper;
//
//	private static Person2Dto person;
//
//	@BeforeAll
//	public static void setup() {
//		objectMapper = new ObjectMapper();
//		objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
//
//		person = new Person2Dto();
//	}
//
//	@Test
//	@Order(0)
//	public void auth() {
//		AccountCredentialsDto user = new AccountCredentialsDto("leandro", "admin123");
//		var accessToken = given()
//				.basePath("/auth/signin")
//				.port(TestConfigs.SERVER_PORT)
//				.contentType(TestConfigs.CONTENT_TYPE_JSON)
//				.body(user)
//				.when()
//				.post()
//				.then()
//				.statusCode(200)
//				.extract()
//				.body()
//				.jsonPath()
//				.getObject("body", TokenDto.class).getAccessToken();
//
//		specification = new RequestSpecBuilder()
//				.addHeader(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + accessToken)
//				.setBasePath("/api/person/v1")
//				.setPort(TestConfigs.SERVER_PORT)
//				.addFilter(new RequestLoggingFilter(LogDetail.ALL))
//				.addFilter(new ResponseLoggingFilter(LogDetail.ALL))
//				.build();
//	}
//
//	@Test
//	@Order(1)
//	public void testCreate() throws JsonMappingException, JsonProcessingException {
//		mockPerson();
//
//		var content = given().spec(specification)
//				.contentType(TestConfigs.CONTENT_TYPE_JSON)
//				.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_VALID)
//				.body(person)
//				.when()
//				.post()
//				.then()
//				.statusCode(200)
//				.extract()
//				.body()
//				.asString();
//
//		Person2Dto persistedPerson = objectMapper.readValue(content, Person2Dto.class);
//		person = persistedPerson;
//
//		assertNotNull(persistedPerson);
//
//		assertNotNull(persistedPerson.getId());
//		assertNotNull(persistedPerson.getFirstName());
//		assertNotNull(persistedPerson.getLastName());
//		assertNotNull(persistedPerson.getAddress());
//		assertNotNull(persistedPerson.getGender());
//
//		assertTrue(persistedPerson.getId() > 0);
//
//		assertEquals("Richard", persistedPerson.getFirstName());
//		assertEquals("Stallman", persistedPerson.getLastName());
//		assertEquals("New York City, New York, US", persistedPerson.getAddress());
//		assertEquals("Male", persistedPerson.getGender());
//	}
//
//	@Test
//	@Order(2)
//	public void testCreateWithWrongOrigin() throws JsonMappingException, JsonProcessingException {
//		mockPerson();
//
//		var content = given().spec(specification)
//				.contentType(TestConfigs.CONTENT_TYPE_JSON)
//				.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_INVALID)
//				.body(person)
//				.when()
//				.post()
//				.then()
//				.statusCode(403)
//				.extract()
//				.body()
//				.asString();
//
//		assertNotNull(content);
//		assertEquals("Invalid CORS request", content);
//	}
//
//	@Test
//	@Order(3)
//	public void testFindById() throws JsonMappingException, JsonProcessingException {
//		mockPerson();
//
//		var content = given().spec(specification)
//				.contentType(TestConfigs.CONTENT_TYPE_JSON)
//				.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_VALID)
//				.pathParam("id", person.getId())
//				.when()
//				.get("{id}")
//				.then()
//				.statusCode(200)
//				.extract()
//				.body()
//				.asString();
//
//		Person2Dto persistedPerson = objectMapper.readValue(content, Person2Dto.class);
//		person = persistedPerson;
//
//		assertNotNull(persistedPerson);
//
//		assertNotNull(persistedPerson.getId());
//		assertNotNull(persistedPerson.getFirstName());
//		assertNotNull(persistedPerson.getLastName());
//		assertNotNull(persistedPerson.getAddress());
//		assertNotNull(persistedPerson.getGender());
//
//		assertTrue(persistedPerson.getId() > 0);
//
//		assertEquals("Richard", persistedPerson.getFirstName());
//		assertEquals("Stallman", persistedPerson.getLastName());
//		assertEquals("New York City, New York, US", persistedPerson.getAddress());
//		assertEquals("Male", persistedPerson.getGender());
//	}
//
//	@Test
//	@Order(4)
//	public void testFindByIdWithWrongOrigin() throws JsonMappingException, JsonProcessingException {
//		mockPerson();
//
//		var content = given().spec(specification)
//				.contentType(TestConfigs.CONTENT_TYPE_JSON)
//				.header(TestConfigs.HEADER_PARAM_ORIGIN, TestConfigs.ORIGIN_INVALID)
//				.pathParam("id", person.getId())
//				.when()
//				.get("{id}")
//				.then()
//				.statusCode(403)
//				.extract()
//				.body()
//				.asString();
//
//		assertNotNull(content);
//		assertEquals("Invalid CORS request", content);
//	}
//
//	private void mockPerson() {
//		person.setFirstName("Richard");
//		person.setLastName("Stallman");
//		person.setAddress("New York City, New York, US");
//		person.setGender("Male");
//	}
//
////	@Test
////	@Order(5)
////	public void testFindAll() throws JsonMappingException, JsonProcessingException {
////
////		var content = given().spec(specification)
////				.contentType(TestConfigs.CONTENT_TYPE_JSON)
////				.accept(TestConfigs.CONTENT_TYPE_JSON)
////				.queryParams("page", 3, "size", 10, "direction", "asc")
////				.when()
////				.get()
////				.then()
////				.statusCode(200)
////				.extract()
////				.body()
////				.asString();
////
////		WrapperPersonVO wrapper = objectMapper.readValue(content, WrapperPersonVO.class);
////		var people = wrapper.getEmbedded().getPersons();
////
////		Person2Dto foundPersonOne = people.get(0);
////
////		assertNotNull(foundPersonOne.getId());
////		assertNotNull(foundPersonOne.getFirstName());
////		assertNotNull(foundPersonOne.getLastName());
////		assertNotNull(foundPersonOne.getAddress());
////		assertNotNull(foundPersonOne.getGender());
////
////		assertEquals(677, foundPersonOne.getId());
////
////		assertEquals("Alic", foundPersonOne.getFirstName());
////		assertEquals("Terbrug", foundPersonOne.getLastName());
////		assertEquals("3 Eagle Crest Court", foundPersonOne.getAddress());
////		assertEquals("Male", foundPersonOne.getGender());
////
////		Person2Dto foundPersonSix = people.get(5);
////
////		assertNotNull(foundPersonSix.getId());
////		assertNotNull(foundPersonSix.getFirstName());
////		assertNotNull(foundPersonSix.getLastName());
////		assertNotNull(foundPersonSix.getAddress());
////		assertNotNull(foundPersonSix.getGender());
////
////		assertEquals(911, foundPersonSix.getId());
////
////		assertEquals("Allegra", foundPersonSix.getFirstName());
////		assertEquals("Dome", foundPersonSix.getLastName());
////		assertEquals("57 Roxbury Pass", foundPersonSix.getAddress());
////		assertEquals("Female", foundPersonSix.getGender());
////	}
//
//}
