package runners;



import org.junit.runner.RunWith;

import cucumber.api.CucumberOptions;
import cucumber.api.SnippetType;
import cucumber.api.junit.Cucumber;


@RunWith(Cucumber.class)
@CucumberOptions(
		features = {"src/main/resources/features"}, 
		glue = {"steps", "core"},
		tags = {"@01"},
        plugin = {"json:target/cucumber.json"},
		snippets = SnippetType.UNDERSCORE		
		)

public class RunnerTest {

}
