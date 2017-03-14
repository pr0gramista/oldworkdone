package pl.pr0gramista.todo;

import pl.pr0gramista.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@Entity
public class Todo {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String title;

    @OneToMany
    private List<Task> taskList;

    @OneToOne
    private User owner;

    public Todo() {
    }

    public Todo(TodoBuilder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.owner = builder.owner;
        this.taskList = builder.taskList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Task> getTaskList() {
        return taskList;
    }

    public void setTaskList(List<Task> taskList) {
        this.taskList = taskList;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Todo todo = (Todo) o;

        if (id != null ? !id.equals(todo.id) : todo.id != null) return false;
        if (title != null ? !title.equals(todo.title) : todo.title != null) return false;
        if (taskList != null ? !taskList.equals(todo.taskList) : todo.taskList != null) return false;
        return owner != null ? owner.equals(todo.owner) : todo.owner == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (taskList != null ? taskList.hashCode() : 0);
        result = 31 * result + (owner != null ? owner.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Todo{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", taskList=" + taskList +
                ", owner=" + owner +
                '}';
    }

    public static class TodoBuilder {
        private long id;
        private String title;
        private List<Task> taskList = new LinkedList<>();
        private User owner;

        public TodoBuilder(Todo todoToCopy) {
            id = todoToCopy.getId();
            title = todoToCopy.getTitle();
            taskList = todoToCopy.getTaskList();
            owner = todoToCopy.getOwner();
        }

        public TodoBuilder(String title) {
            this.title = title;
        }

        public TodoBuilder owner(User user) {
            this.owner = owner;
            return this;
        }

        public TodoBuilder id(long id) {
            this.id = id;
            return this;
        }

        public TodoBuilder withTask(Task task) {
            taskList.add(task);
            return this;
        }

        public TodoBuilder withTasks(Collection<? extends Task> tasks) {
            taskList.addAll(tasks);
            return this;
        }

        public Todo build() {
            return new Todo(this);
        }
    }
}
