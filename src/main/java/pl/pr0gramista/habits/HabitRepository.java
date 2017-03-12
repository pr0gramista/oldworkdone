package pl.pr0gramista.habits;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.pr0gramista.user.User;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface HabitRepository extends CrudRepository<Habit, Long> {
    List<Habit> findAllByOwner(User owner);

    Optional<Habit> findOneByIdAndOwner(long id, User owner);

    @Transactional
    void removeByIdAndOwner(long id, User owner);
}
