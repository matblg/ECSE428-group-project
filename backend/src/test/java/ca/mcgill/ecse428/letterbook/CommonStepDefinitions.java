package ca.mcgill.ecse428.letterbook;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import ca.mcgill.ecse428.letterbook.model.User;
import ca.mcgill.ecse428.letterbook.repository.UserRepository;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;

public class CommonStepDefinitions {

    @Autowired
    PasswordEncoder passwordEncoder;
    
    @Autowired
	private UserRepository userRepository;

    @Given("the accounts exist in the system")
    public void the_accounts_exist_in_the_system(DataTable dataTable) {
        // Convert DataTable to list of maps
        List<Map<String, String>> accounts = dataTable.asMaps(String.class, String.class);
        
        for (Map<String, String> account : accounts) {
            String username = account.get("username");
            String email = account.get("email");
            String bio = account.get("bio");
            String password = account.get("password");
            
            // Create user in the system
            User user = new User(username, email, bio, passwordEncoder.encode(password));
            userRepository.save(user);
        }
    }
}
