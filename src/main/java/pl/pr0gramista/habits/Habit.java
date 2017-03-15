package pl.pr0gramista.habits;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import pl.pr0gramista.user.User;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
@JsonIgnoreProperties({"owner"})
public class Habit {

    @Id
    @GeneratedValue
    private Long id;

    private float expReward;
    private float coinReward;

    private String title;
    private String description;

    @OneToOne
    private User owner;

    public Habit() {
    }

    public Habit(HabitBuilder builder) {
        this.id = builder.id;
        this.title = builder.title;
        this.expReward = builder.expReward;
        this.coinReward = builder.coinReward;
        this.owner = builder.owner;
        this.description = builder.description;
    }

    public Habit(float expReward, float coinReward, String title, String description) {
        this.expReward = expReward;
        this.coinReward = coinReward;
        this.title = title;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public float getExpReward() {
        return expReward;
    }

    public void setExpReward(float expReward) {
        this.expReward = expReward;
    }

    public float getCoinReward() {
        return coinReward;
    }

    public void setCoinReward(float coinReward) {
        this.coinReward = coinReward;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

        Habit habit = (Habit) o;

        if (Float.compare(habit.expReward, expReward) != 0) return false;
        if (Float.compare(habit.coinReward, coinReward) != 0) return false;
        if (id != null ? !id.equals(habit.id) : habit.id != null) return false;
        if (title != null ? !title.equals(habit.title) : habit.title != null) return false;
        return description != null ? description.equals(habit.description) : habit.description == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (expReward != +0.0f ? Float.floatToIntBits(expReward) : 0);
        result = 31 * result + (coinReward != +0.0f ? Float.floatToIntBits(coinReward) : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Habit{" +
                "id=" + id +
                ", expReward=" + expReward +
                ", coinReward=" + coinReward +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    public static class HabitBuilder {
        private Long id;
        private String title;
        private String description;
        private User owner;
        private float expReward;
        private float coinReward;

        public HabitBuilder(Habit habitToCopy) {
            id = habitToCopy.getId();
            title = habitToCopy.getTitle();
            owner = habitToCopy.getOwner();
            expReward = habitToCopy.getExpReward();
            coinReward = habitToCopy.getCoinReward();
        }

        public HabitBuilder(String title) {
            this.title = title;
        }

        public Habit.HabitBuilder owner(User user) {
            this.owner = owner;
            return this;
        }

        public Habit.HabitBuilder id(long id) {
            this.id = id;
            return this;
        }

        public Habit.HabitBuilder expReward(float expReward) {
            this.expReward = expReward;
            return this;
        }

        public Habit.HabitBuilder description(String description) {
            this.description = description;
            return this;
        }

        public Habit.HabitBuilder coinReward(float coinReward) {
            this.coinReward = coinReward;
            return this;
        }

        public Habit build() {
            return new Habit(this);
        }
    }
}
