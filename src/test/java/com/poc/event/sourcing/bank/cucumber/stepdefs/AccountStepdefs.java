package com.poc.event.sourcing.bank.cucumber.stepdefs;

import com.poc.event.sourcing.bank.TestConfig;
import com.poc.event.sourcing.bank.api.dto.AccountDto;
import com.poc.event.sourcing.bank.cucumber.World;
import cucumber.api.java8.En;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.Assert;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

import static org.junit.Assert.assertEquals;

public class AccountStepdefs extends TestConfig implements En {

    @LocalServerPort
    private Integer port;
    @Autowired
    private World world;
    private RestTemplate restTemplate;


    public AccountStepdefs() {

        Before(() -> {
            restTemplate = new RestTemplate(new HttpComponentsClientHttpRequestFactory());
        });

        Given("^the name (.*)$", (String accountCreator) -> {
            world.map.put("accountCreator", accountCreator);
        });

        Given("^a name not informed$", () -> { });

        When("^create account$", () -> {
            world.status = 200;
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<AccountDto> entity = new HttpEntity<>(buildDto(), headers);

            try {
                world.map.put("createdAccountId",
                        restTemplate.exchange(String.format("http://localhost:%s/api/v1/account", port),
                                HttpMethod.POST,
                                entity,
                                String.class)
                                .getBody()
                );
            } catch (HttpServerErrorException | HttpClientErrorException e) {
                world.status = e.getRawStatusCode();
                world.errorMessage = e.getMessage();
            }
        });

        Then("^should return status (\\d+)$", (Integer expectedStatus) -> {
            assertEquals(expectedStatus, world.status);
        });

        Then("^should return the account id created$", () -> {
            Assert.hasLength((String) world.map.get("createdAccountId"), "Missing account id");
        });

        Then("^should return a message of error$", () -> {
            Assert.hasLength(world.errorMessage, "Error occured");
        });
    }

    private AccountDto buildDto() {
        AccountDto accountDto = new AccountDto();
        accountDto.setName((String) world.map.get("accountCreator"));
        return accountDto;
    }
}
