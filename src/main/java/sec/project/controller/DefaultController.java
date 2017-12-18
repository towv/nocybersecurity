package sec.project.controller;

import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import sec.project.domain.Account;
import sec.project.repository.AccountRepository;

@Controller
public class DefaultController {
    
    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/login")
    public String login() {
        if (accountRepository.findAll().isEmpty()) {
            Account moderatorAccount = new Account();
            moderatorAccount.setModerator(true);
            moderatorAccount.setUsername("moderator");
            moderatorAccount.setPassword("mod");
            moderatorAccount.setTasks(new ArrayList<>());
            accountRepository.save(moderatorAccount);
        }
        return "login";
    }
}
