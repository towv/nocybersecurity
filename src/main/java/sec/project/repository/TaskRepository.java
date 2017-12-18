package sec.project.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import sec.project.domain.Account;
import sec.project.domain.Task;


public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByAccount(Account account);
}
