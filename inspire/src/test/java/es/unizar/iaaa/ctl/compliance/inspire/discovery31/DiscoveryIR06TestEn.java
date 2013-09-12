package es.unizar.iaaa.ctl.compliance.inspire.discovery31;

import org.junit.runner.RunWith;

import cucumber.api.junit.Cucumber;

@RunWith(value = Cucumber.class)
@Cucumber.Options(glue = { 
		"es.unizar.iaaa.ctl.tests.inspire.TestSteps",
		"es.unizar.iaaa.ctl.hooks.inspire.discovery31.IGNHooksEn" }, features = {
		"src/test/features/discovery31/discovery-31-ir-06_en.feature" })
public class DiscoveryIR06TestEn {

}
