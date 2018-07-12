package tech.ippon.chatbotdemo.cucumber.stepdefs;

import tech.ippon.chatbotdemo.InsuranceMicroserviceApp;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.boot.test.context.SpringBootTest;

@WebAppConfiguration
@SpringBootTest
@ContextConfiguration(classes = InsuranceMicroserviceApp.class)
public abstract class StepDefs {

    protected ResultActions actions;

}
