package pl.pr0gramista.todo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.pr0gramista.user.User;

import java.util.Optional;

@RestController
@RequestMapping("/todo")
public class TodoController {
    private TodoRepository todoRepository;
    private TodoValidator validator;

    public TodoController(@Autowired TodoRepository todoRepository,
                          @Autowired TodoValidator validator) {
        this.todoRepository = todoRepository;
        this.validator = validator;
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public Iterable<Todo> getAllTodos(User user) {
        return todoRepository.findAllByOwner(user);
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Long createNewTodo(@RequestBody Todo todo, User user, BindingResult result) throws BindException {
        validator.validate(todo, result);

        if (result.hasErrors())
            throw new BindException(result);

        todo.setOwner(user);
        return todoRepository.save(todo).getId();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteTodo(@PathVariable long id, User user) {
        todoRepository.removeByIdAndOwner(id, user);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public void editTodo(@PathVariable long id, @RequestBody Todo todo, User user, BindingResult result) throws BindException {
        validator.validate(todo, result);

        if (result.hasErrors())
            throw new BindException(result);

        Optional<Todo> todoOptional = todoRepository.findOneByIdAndOwner(id, user);
        if (todoOptional.isPresent()) {
            todo.setId(id);
            todo.setOwner(user);
            todoRepository.save(todo);
        }
    }
}
