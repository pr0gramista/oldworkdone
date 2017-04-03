package pl.pr0gramista.todo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import pl.pr0gramista.enums.CoinAmount;
import pl.pr0gramista.enums.Color;
import pl.pr0gramista.enums.ExperienceAmount;
import pl.pr0gramista.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

@Entity
@JsonIgnoreProperties({"owner"})
public class Todo {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String title;

    @OneToMany(orphanRemoval = true)
    private List<Task> tasks;

    @OneToOne
    private User owner;

    @ElementCollection(targetClass = String.class)
    private List<String> tags;

    private ExperienceAmount expReward;

    private CoinAmount coinReward;

    @NotNull
    private Color color;

    public Todo() {
    }

    public Todo(TodoBuilder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.owner = builder.owner;
        this.tasks = builder.taskList;
        this.tags = builder.tags;
        this.color = builder.color;
        this.coinReward = builder.coinReward;
        this.expReward = builder.expReward;
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

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public ExperienceAmount getExpReward() {
        return expReward;
    }

    public void setExpReward(ExperienceAmount expReward) {
        this.expReward = expReward;
    }

    public CoinAmount getCoinReward() {
        return coinReward;
    }

    public void setCoinReward(CoinAmount coinReward) {
        this.coinReward = coinReward;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Todo todo = (Todo) o;

        if (id != null ? !id.equals(todo.id) : todo.id != null) return false;
        if (title != null ? !title.equals(todo.title) : todo.title != null) return false;
        if (tasks != null ? !tasks.equals(todo.tasks) : todo.tasks != null) return false;
        if (owner != null ? !owner.equals(todo.owner) : todo.owner != null) return false;
        if (tags != null ? !tags.equals(todo.tags) : todo.tags != null) return false;
        if (expReward != todo.expReward) return false;
        if (coinReward != todo.coinReward) return false;
        return color == todo.color;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (tasks != null ? tasks.hashCode() : 0);
        result = 31 * result + (owner != null ? owner.hashCode() : 0);
        result = 31 * result + (tags != null ? tags.hashCode() : 0);
        result = 31 * result + (expReward != null ? expReward.hashCode() : 0);
        result = 31 * result + (coinReward != null ? coinReward.hashCode() : 0);
        result = 31 * result + (color != null ? color.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Todo{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", tasks=" + tasks +
                ", owner=" + owner +
                ", tags=" + tags +
                ", expReward=" + expReward +
                ", coinReward=" + coinReward +
                ", color=" + color +
                '}';
    }

    public static class TodoBuilder {
        private Long id;
        private String title;
        private List<Task> taskList = new LinkedList<>();
        private User owner;
        private List<String> tags;
        private Color color;
        private CoinAmount coinReward;
        private ExperienceAmount expReward;

        public TodoBuilder(Todo todoToCopy) {
            id = todoToCopy.getId();
            title = todoToCopy.getTitle();
            taskList = todoToCopy.getTasks();
            owner = todoToCopy.getOwner();
        }

        public TodoBuilder(String title) {
            this.title = title;
        }

        public TodoBuilder owner(User owner) {
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

        public TodoBuilder tags(List<String> tags) {
            this.tags = tags;
            return this;
        }

        public TodoBuilder expReward(ExperienceAmount expReward) {
            this.expReward = expReward;
            return this;
        }

        public TodoBuilder coinReward(CoinAmount coinReward) {
            this.coinReward = coinReward;
            return this;
        }

        public TodoBuilder color(Color color) {
            this.color = color;
            return this;
        }

        public TodoBuilder tags(String... tags) {
            if (this.tags == null)
                this.tags = new LinkedList<>();

            for (String tag : tags)
                this.tags.add(tag);
            return this;
        }

        public Todo build() {
            return new Todo(this);
        }
    }
}
