package pl.pr0gramista.habits;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.pr0gramista.user.User;
import pl.pr0gramista.user.UserService;

import java.util.Optional;

@RestController
@RequestMapping("/habit")
public class HabitController {

    private HabitRepository habitRepository;

    private UserService userService;

    public HabitController(@Autowired HabitRepository habitRepository, @Autowired UserService userService) {
        this.habitRepository = habitRepository;
        this.userService = userService;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Iterable<Habit> getAllHabits(User user) {
        return habitRepository.findAllByOwner(user);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Long createNewHabit(@RequestBody Habit habit, User user) {
        habit.setOwner(user);
        return habitRepository.save(habit).getId();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteHabit(@PathVariable long id, User user) {
        habitRepository.removeByIdAndOwner(id, user);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void editHabit(@PathVariable long id, @RequestBody Habit habit, User user) {
        Optional<Habit> habitOptional = habitRepository.findOneByIdAndOwner(id, user);
        if (habitOptional.isPresent()) {
            habit.setId(id);
            habit.setOwner(user);
            habitRepository.save(habit);
        }
    }
}
