package ca.mcgill.ecse428.letterbook;

import org.springframework.stereotype.Component;
import io.cucumber.spring.ScenarioScope;

@Component
@ScenarioScope
public class TestContext {
    public boolean loggedIn;
    public String loggedInEmail;
    public String loggedInUsername;

    public String loggedInPasswordPlain;
    public String jwtToken;
    public String lastMessage;
}
