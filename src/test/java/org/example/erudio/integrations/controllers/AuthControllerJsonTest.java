package org.example.erudio.integrations.controllers;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertNotNull;

import org.example.erudio.configs.TestConfigs;
import org.example.erudio.integrations.AbstractIntegrationTest;
import org.example.erudio.integrations.moks.AccountCredentialsDto;
import org.example.erudio.integrations.moks.TokenDto;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestMethodOrder(OrderAnnotation.class)
public class AuthControllerJsonTest extends AbstractIntegrationTest {

	private static TokenDto TokenDto;
	
	@Test
	@Order(1)
	public void testSignin() throws JsonMappingException, JsonProcessingException {
		
		AccountCredentialsDto user = 
				new AccountCredentialsDto("leandro", "admin123");
		
		TokenDto = given()
				.basePath("/auth/signin")
					.port(TestConfigs.SERVER_PORT)
					.contentType(TestConfigs.CONTENT_TYPE_JSON)
				.body(user)
					.when()
				.post()
					.then()
						.statusCode(200)
							.extract()
							.body()
									.jsonPath().getObject("body", TokenDto.class);

		assertNotNull(TokenDto.getAccessToken());
		assertNotNull(TokenDto.getRefreshToken());
	}
	
	@Test
	@Order(2)
	public void testRefresh() throws JsonMappingException, JsonProcessingException {
		
		var newTokenDto = given()
				.basePath("/auth/refresh")
				.port(TestConfigs.SERVER_PORT)
				.contentType(TestConfigs.CONTENT_TYPE_JSON)
					.pathParam("username", TokenDto.getUsername())
					.header(TestConfigs.HEADER_PARAM_AUTHORIZATION, "Bearer " + TokenDto.getRefreshToken())
				.when()
					.put("{username}")
				.then()
					.statusCode(200)
				.extract()
					.body()
					.jsonPath().getObject("body", TokenDto.class);
		
		assertNotNull(newTokenDto.getAccessToken());
		assertNotNull(newTokenDto.getRefreshToken());
	}
}
