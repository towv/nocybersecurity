package sec.project.controller;

import java.util.ArrayList;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import sec.project.domain.Account;
import sec.project.domain.Task;
import sec.project.repository.AccountRepository;
import sec.project.repository.TaskRepository;

@Controller
public class DefaultController {
    
    @Autowired
    private AccountRepository accountRepository;
    
    @Autowired
    private TaskRepository taskRepository;
    
    @GetMapping("/")
    public String index() {
        return "index";
    }
    
    @GetMapping("/register")
    public String register() {
        if (accountRepository.findAll().isEmpty()) {
            Account moderatorAccount = new Account();
            moderatorAccount.setModerator(true);
            moderatorAccount.setUsername("moderator");
            moderatorAccount.setPassword("mod");
            moderatorAccount.setTasks(new ArrayList<>());
            accountRepository.save(moderatorAccount);
        }
        return "register";
    }
    
    @GetMapping("/login")
    public String login() {
        
        return "login";
    }
    
    @GetMapping("/checkDatabase")
    public String checkDatabase() {
        return "redirect:/h2-console";
    }
    
    @GetMapping("/clearDatabase")
    public String clearDatabase(HttpSession session) {
        for (Account account : accountRepository.findAll()) {
            account.setTasks(new ArrayList<>());
        }
        for (Task task : taskRepository.findAll()) {
            task.setAccount(null);
        }
        accountRepository.deleteAll();
        taskRepository.deleteAll();
        session.setAttribute("user", null);
        return "redirect:/";
    }
}
