package pl.pr0gramista.todo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.pr0gramista.generators.CoinGenerator;
import pl.pr0gramista.generators.ExperienceGenerator;
import pl.pr0gramista.user.UserService;

import java.time.ZonedDateTime;
import java.util.Optional;

@Service
public class TodoService {

    private UserService userService;

    private CoinGenerator coinGenerator;

    private ExperienceGenerator experienceGenerator;

    public TodoService(
            @Autowired UserService userService,
            @Autowired CoinGenerator coinGenerator,
            @Autowired ExperienceGenerator experienceGenerator) {
        this.coinGenerator = coinGenerator;
        this.experienceGenerator = experienceGenerator;
        this.userService = userService;
    }

    /**
     * Completes list of tasks.
     * <p>
     * Creates a new {@link TodoCompletion} instance and
     * adds experience and coins to owner.
     * <p>
     * If user is already rewarded for this list then returns null
     * <p>
     * <b>Warning:</b> this method sets <i>rewarded</i> flag to true
     *
     * @param todo todo to complete
     * @return new {@link TodoCompletion}
     */
    public Optional<TodoCompletion> complete(Todo todo) {
        if (todo.isRewarded())
            return Optional.empty();

        TodoCompletion todoCompletion = new TodoCompletion(
                experienceGenerator.generate(todo.getExpReward()),
                coinGenerator.generate(todo.getCoinReward()),
                ZonedDateTime.now(),
                todo
        );

        todo.setRewarded(true);

        userService.addExperience(todo.getOwner(), todoCompletion.getExperience());
        userService.addCoins(todo.getOwner(), todoCompletion.getCoins());

        return Optional.of(todoCompletion);
    }
}
