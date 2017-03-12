package pl.pr0gramista.habits;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.pr0gramista.user.User;
import pl.pr0gramista.user.UserService;

import javax.persistence.EntityManager;
import java.util.Optional;

@RestController
@RequestMapping("/habit")
public class HabitController {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private HabitRepository habitRepository;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Iterable<Habit> getAllHabits(User user) {
        return habitRepository.findAllByOwner(user);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public void createNewHabit(@RequestBody Habit habit, User user) {
        habit.setOwner(user);
        habitRepository.save(habit);
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
