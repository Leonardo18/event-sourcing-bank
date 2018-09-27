package com.poc.event.sourcing.bank.cucumber.stepdefs;

import com.poc.event.sourcing.bank.EventSourcingBankApplication;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootContextLoader;
import org.springframework.test.context.ContextConfiguration;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features", glue = "com.poc.event.sourcing.bank.cucumber")
@ContextConfiguration(loader = SpringBootContextLoader.class, classes = EventSourcingBankApplication.class)
public class CucumberTestsRunner { }
