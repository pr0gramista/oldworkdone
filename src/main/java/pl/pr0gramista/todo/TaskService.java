package pl.pr0gramista.todo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pr0gramista.generators.CoinGenerator;
import pl.pr0gramista.generators.ExperienceGenerator;
import pl.pr0gramista.user.UserService;

import java.time.ZonedDateTime;
import java.util.Optional;

@Service
public class TaskService {
    private UserService userService;

    private CoinGenerator coinGenerator;

    private ExperienceGenerator experienceGenerator;

    public TaskService(
            @Autowired UserService userService,
            @Autowired CoinGenerator coinGenerator,
            @Autowired ExperienceGenerator experienceGenerator) {
        this.coinGenerator = coinGenerator;
        this.experienceGenerator = experienceGenerator;
        this.userService = userService;
    }

    /**
     * Completes task.
     * <p>
     * Creates a new {@link TaskCompletion} instance and
     * adds experience and coins to owner.
     * <p>
     * If user is already rewarded for this task then returns null
     * <p>
     * <b>Warning:</b> this method sets <i>rewarded</i> flag to true
     *
     * @param task task to complete
     * @return new {@link TaskCompletion} or null
     */
    public Optional<TaskCompletion> completeTask(Task task, Todo parent) {
        if (task.isRewarded())
            return Optional.empty();

        TaskCompletion taskCompletion = new TaskCompletion(
                experienceGenerator.generate(parent.getExpReward()),
                coinGenerator.generate(parent.getCoinReward()),
                ZonedDateTime.now(),
                task
        );

        task.setRewarded(true);

        userService.addExperience(parent.getOwner(), taskCompletion.getExperience());
        userService.addCoins(parent.getOwner(), taskCompletion.getCoins());

        return Optional.of(taskCompletion);
    }
}
