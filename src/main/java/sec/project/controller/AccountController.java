package sec.project.controller;

import java.util.ArrayList;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sec.project.domain.Account;
import sec.project.repository.AccountRepository;

@Controller
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @PostMapping("/login")
    public String login(HttpSession session, @RequestParam String username, @RequestParam String password) {
        Account account = accountRepository.findByUsername(username);

        if (account != null) {
            if (account.getPassword().equals(password)) {
                session.setAttribute("user", account);
                return "redirect:/tasks/" + account.getId();
            }
        }

        return "index";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String password) {
        Account existingAccount = accountRepository.findByUsername(username);
        Account newAccount = new Account();
        if (existingAccount == null) {
            newAccount.setUsername(username);
            newAccount.setPassword(password);
            newAccount.setTasks(new ArrayList<>());
            accountRepository.save(newAccount);
        } else {
            return "redirect:/register";
        }

        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.setAttribute("user", null);
        return "index";
    }

    @GetMapping("/{id}/modifyAccount")
    public String editAccount(Model model, @PathVariable Long id) {
        model.addAttribute("user", accountRepository.getOne(id));
        return "modifyAccount";
    }

    @PostMapping("/{id}/modifyAccount")
    public String editAccount(HttpSession session, @PathVariable Long id, @RequestParam String username, @RequestParam String password) {
        Account account = accountRepository.getOne(id);
        account.setUsername(username);
        account.setPassword(password);
        accountRepository.save(account);
        session.setAttribute("user", null);
        return "redirect:/login";
    }
}
