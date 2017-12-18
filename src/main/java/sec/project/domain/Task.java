
package sec.project.domain;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.AbstractPersistable;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task extends AbstractPersistable<Long> {

    private String text;
    private String date = new Date().toString();
    @ManyToOne
    private Account account;
}
