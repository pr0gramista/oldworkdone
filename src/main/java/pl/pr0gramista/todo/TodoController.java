package pl.pr0gramista.todo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pl.pr0gramista.user.User;

import java.util.Optional;

@RestController
@RequestMapping("/todo")
public class TodoController {
    private TodoRepository todoRepository;

    public TodoController(@Autowired TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Iterable<Todo> getAllTodos(User user) {
        return todoRepository.findAllByOwner(user);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public void createNewTodo(@RequestBody Todo todo, User user) {
        todo.setOwner(user);
        todoRepository.save(todo);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteTodo(@PathVariable long id, User user) {
        todoRepository.removeByIdAndOwner(id, user);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void editTodo(@PathVariable long id, @RequestBody Todo todo, User user) {
        Optional<Todo> todoOptional = todoRepository.findOneByIdAndOwner(id, user);
        if (todoOptional.isPresent()) {
            todo.setId(id);
            todo.setOwner(user);
            todoRepository.save(todo);
        }
    }
}
