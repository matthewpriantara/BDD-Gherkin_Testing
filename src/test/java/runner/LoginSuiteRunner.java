package runner;

import io.cucumber.junit.platform.engine.Constants;
import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

@Suite
@IncludeEngines("cucumber")
@SelectClasspathResource("features/login.feature")
@ConfigurationParameter(key = Constants.GLUE_PROPERTY_NAME, value = "stepDef")
@ConfigurationParameter(key = Constants.FILTER_TAGS_PROPERTY_NAME, value = "@StandardUser")
public class LoginSuiteRunner {
}
