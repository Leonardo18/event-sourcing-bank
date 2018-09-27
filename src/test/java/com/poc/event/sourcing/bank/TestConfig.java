package com.poc.event.sourcing.bank;

import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = {EventSourcingBankApplication.class})
public class TestConfig { }
