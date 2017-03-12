package pl.pr0gramista.habits;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.validation.Valid;

@RestController
@RequestMapping("/habit")
public class HabitController {

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private HabitRepository habitRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Iterable<Habit> getAllHabits() {
        return habitRepository.findAll();
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public void createNewHabit(@Valid Habit habit) {
        habitRepository.save(habit);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteHabit(@PathVariable long id) {
        habitRepository.delete(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void editHabit(@PathVariable long id, @Valid @RequestBody Habit habit) {
        habit.setId(id);
        System.out.println(habit.toString());
        habitRepository.save(habit);
    }
}
