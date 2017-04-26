package pl.pr0gramista.todo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.pr0gramista.Completion;
import pl.pr0gramista.user.User;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/todo")
public class TodoController {
    private TodoRepository todoRepository;
    private TodoValidator validator;

    private TodoService todoService;
    private TaskService taskService;

    public TodoController(@Autowired TodoRepository todoRepository,
                          @Autowired TodoValidator validator,
                          @Autowired TodoService todoService,
                          @Autowired TaskService taskService) {
        this.todoRepository = todoRepository;
        this.validator = validator;
        this.todoService = todoService;
        this.taskService = taskService;
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
    public List<Completion> editTodo(@PathVariable long id, @RequestBody Todo todo, User user, BindingResult result) throws BindException {
        validator.validate(todo, result);

        List<Completion> completions = new LinkedList<>();

        if (result.hasErrors())
            throw new BindException(result);

        Optional<Todo> existingTodoOptional = todoRepository.findOneByIdAndOwner(id, user);
        if (existingTodoOptional.isPresent()) {
            todo.setId(id);
            todo.setOwner(user);

            todo.getTasks().forEach(task -> {
                if (task.isDone()) {
                    taskService.completeTask(task, todo).ifPresent(completions::add);
                }
            });

            //If list is completely done then make a completion
            if (todo.getTasks().stream().allMatch(Task::isDone)) {
                todoService.complete(todo).ifPresent(completions::add);
            }

            todoRepository.save(todo);
            return completions;
        }
        return null;
    }
}
