package pl.pr0gramista.todo;

import pl.pr0gramista.Completion;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.ZonedDateTime;

@Entity
public class TaskCompletion extends Completion {

    @Id
    private Long id;

    private Task task;

    protected TaskCompletion() {
        super(0, 0, null);
    }

    public TaskCompletion(int experience, int coins, ZonedDateTime dateTime, Task task) {
        super(experience, coins, dateTime);
        this.task = task;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}
