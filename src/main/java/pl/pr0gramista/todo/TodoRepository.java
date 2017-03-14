package pl.pr0gramista.todo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.pr0gramista.user.User;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface TodoRepository extends CrudRepository<Todo, Long> {
    List<Todo> findAllByOwner(User owner);

    Optional<Todo> findOneByIdAndOwner(long id, User owner);

    @Transactional
    void removeByIdAndOwner(long id, User owner);
}
