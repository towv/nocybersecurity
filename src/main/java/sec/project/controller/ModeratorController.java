package sec.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import sec.project.repository.AccountRepository;

@Controller
public class ModeratorController {

    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("/moderator")
    public String moderator(Model model) {
        model.addAttribute("users", accountRepository.findAll());
        return "moderate";
    }

    @DeleteMapping("/moderator/{id}/delete")
    public String delete(@PathVariable Long id) {
        accountRepository.delete(accountRepository.getOne(id));
        return "redirect:/moderator";
    }
}
