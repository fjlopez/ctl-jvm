package es.unizar.iaaa.ctl.compliance.inspire.discovery31;

import org.junit.runner.RunWith;

import cucumber.api.junit.Cucumber;

@RunWith(value = Cucumber.class)
@Cucumber.Options(glue = { 
		"es.unizar.iaaa.ctl.tests.inspire.TestSteps",
		"es.unizar.iaaa.ctl.hooks.inspire.discovery31.IGNHooksEs" }, features = {
		"src/test/features/discovery31/discovery-31-ir-13_es.feature" })
public class DiscoveryIR13TestEs {

}
