package pl.pr0gramista.todo;

import pl.pr0gramista.Completion;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.time.ZonedDateTime;

@Entity
public class TodoCompletion extends Completion {
    @Id
    private Long id;

    @OneToOne
    private Todo todo;

    protected TodoCompletion() {
        super(0, 0, null);
    }

    public TodoCompletion(int experience,
                          int coins,
                          ZonedDateTime dateTime,
                          Todo todo) {
        super(experience, coins, dateTime);
        this.todo = todo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Todo getTodo() {
        return todo;
    }

    public void setTodo(Todo todo) {
        this.todo = todo;
    }
}
