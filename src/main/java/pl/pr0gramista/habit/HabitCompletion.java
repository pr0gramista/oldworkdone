package pl.pr0gramista.habit;

import pl.pr0gramista.Completion;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.time.ZonedDateTime;

@Entity
public class HabitCompletion extends Completion {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private Habit habit;

    protected HabitCompletion() {
        super(0, 0, null);
    }

    public HabitCompletion(int experience,
                           int coins,
                           ZonedDateTime dateTime,
                           Habit habit) {
        super(experience, coins, dateTime);
        this.id = id;
        this.habit = habit;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Habit getHabit() {
        return habit;
    }

    public void setHabit(Habit habit) {
        this.habit = habit;
    }
}
