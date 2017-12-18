package sec.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sec.project.domain.Account;
import sec.project.domain.Task;
import sec.project.repository.AccountRepository;
import sec.project.repository.TaskRepository;

@Controller
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("/tasks/{id}")
    public String tasks(Model model, @PathVariable Long id) {
        Account account = accountRepository.getOne(id);
        model.addAttribute("tasks", taskRepository.findByAccount(account));
        return "tasks";
    }

    @GetMapping("/tasks/{id}/addTask")
    public String getAddTask(@PathVariable Long id) {
        return "newTask";
    }

    @PostMapping("/tasks/{id}/addTask")
    public String add(@PathVariable Long id, @RequestParam String text) {
        Task task = new Task();
        task.setText(text);
        task.setAccount(accountRepository.getOne(id));
        taskRepository.save(task);
        return "done";
    }
}
