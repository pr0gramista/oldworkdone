package pl.pr0gramista.habit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.pr0gramista.enums.CoinAmount;
import pl.pr0gramista.enums.Color;
import pl.pr0gramista.enums.ExperienceAmount;
import pl.pr0gramista.user.User;

import java.util.Optional;

@RestController
@RequestMapping("/habit")
public class HabitController {

    private HabitRepository repository;

    private HabitValidator validator;

    public HabitController(@Autowired HabitRepository habitRepository,
                           @Autowired HabitValidator habitValidator) {
        this.repository = habitRepository;
        this.validator = habitValidator;
    }

    @RequestMapping("/test")
    public Habit test() {
        return new Habit.HabitBuilder("wow")
                .coinReward(CoinAmount.HIGH)
                .expReward(ExperienceAmount.SMALL)
                .color(Color.RED)
                .tags("wow", "youtube")
                .build();
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Iterable<Habit> getAllHabits(User user) {
        return repository.findAllByOwner(user);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Long createNewHabit(@RequestBody Habit habit, BindingResult result, User user) throws BindException {
        validator.validate(habit, result);

        if (result.hasErrors())
            throw new BindException(result);

        habit.setOwner(user);
        return repository.save(habit).getId();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteHabit(@PathVariable long id, User user) {
        repository.removeByIdAndOwner(id, user);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void editHabit(@PathVariable long id, @RequestBody Habit habit, BindingResult result, User user) throws BindException {
        validator.validate(habit, result);

        if (result.hasErrors())
            throw new BindException(result);

        Optional<Habit> habitOptional = repository.findOneByIdAndOwner(id, user);
        if (habitOptional.isPresent()) {
            habit.setId(id);
            habit.setOwner(user);
            repository.save(habit);
        }
    }
}
