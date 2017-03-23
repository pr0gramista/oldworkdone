package pl.pr0gramista.habit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import pl.pr0gramista.enums.CoinAmount;
import pl.pr0gramista.enums.Color;
import pl.pr0gramista.enums.ExperienceAmount;
import pl.pr0gramista.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.LinkedList;
import java.util.List;

@Entity
@JsonIgnoreProperties({"owner"})
public class Habit {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @Size(min = 1, max = 255)
    private String text;

    private ExperienceAmount expReward;

    private CoinAmount coinReward;

    @NotNull
    private Color color;

    @OneToOne
    private User owner;

    @ElementCollection(targetClass = String.class)
    private List<String> tags;

    public Habit() {
    }

    public Habit(HabitBuilder builder) {
        id = builder.id;
        text = builder.text;
        expReward = builder.expReward;
        coinReward = builder.coinReward;
        color = builder.color;
        owner = builder.owner;
        tags = builder.tags;
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public static class HabitBuilder {
        private Long id;
        private String text;
        private User owner;
        private ExperienceAmount expReward;
        private CoinAmount coinReward;
        private List<String> tags;
        private Color color;

        public HabitBuilder(Habit habitToCopy) {
            id = habitToCopy.getId();
            text = habitToCopy.getText();
            owner = habitToCopy.getOwner();
            expReward = habitToCopy.getExpReward();
            coinReward = habitToCopy.getCoinReward();
            tags = habitToCopy.getTags();
            color = habitToCopy.getColor();
        }

        public HabitBuilder(String text) {
            this.text = text;
        }

        public Habit.HabitBuilder owner(User newOwner) {
            this.owner = newOwner;
            return this;
        }

        public Habit.HabitBuilder id(long id) {
            this.id = id;
            return this;
        }

        public Habit.HabitBuilder expReward(ExperienceAmount expReward) {
            this.expReward = expReward;
            return this;
        }

        public Habit.HabitBuilder coinReward(CoinAmount coinReward) {
            this.coinReward = coinReward;
            return this;
        }

        public Habit.HabitBuilder color(Color color) {
            this.color = color;
            return this;
        }

        public Habit.HabitBuilder tags(List<String> tags) {
            this.tags = tags;
            return this;
        }

        public Habit.HabitBuilder tags(String... tags) {
            if (this.tags == null)
                this.tags = new LinkedList<>();

            for (String tag : tags)
                this.tags.add(tag);
            return this;
        }

        public Habit build() {
            return new Habit(this);
        }
    }
}
