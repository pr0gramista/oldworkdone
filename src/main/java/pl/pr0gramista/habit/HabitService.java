package pl.pr0gramista.habit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pr0gramista.generators.CoinGenerator;
import pl.pr0gramista.generators.ExperienceGenerator;
import pl.pr0gramista.user.UserService;

import java.time.ZonedDateTime;

@Service
public class HabitService {

    private UserService userService;

    private CoinGenerator coinGenerator;

    private ExperienceGenerator experienceGenerator;

    public HabitService(
            @Autowired UserService userService,
            @Autowired CoinGenerator coinGenerator,
            @Autowired ExperienceGenerator experienceGenerator) {
        this.coinGenerator = coinGenerator;
        this.experienceGenerator = experienceGenerator;
        this.userService = userService;
    }

    /**
     * Completes habit.
     * <p>
     * Creates a new {@link HabitCompletion} instance and
     * adds experience and coins to owner.
     *
     * @param habit habit to complete
     * @return new {@link HabitCompletion}
     */
    public HabitCompletion complete(Habit habit) {
        HabitCompletion habitCompletion = new HabitCompletion(
                experienceGenerator.generate(habit.getExpReward()),
                coinGenerator.generate(habit.getCoinReward()),
                ZonedDateTime.now(),
                habit
        );

        userService.addExperience(habit.getOwner(), habitCompletion.getExperience());
        userService.addCoins(habit.getOwner(), habitCompletion.getCoins());

        return habitCompletion;
    }
}
