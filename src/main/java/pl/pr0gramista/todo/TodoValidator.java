package pl.pr0gramista.todo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class TodoValidator implements Validator {

    private TaskValidator taskValidator;

    public TodoValidator(@Autowired TaskValidator taskValidator) {
        this.taskValidator = taskValidator;
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(Todo.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", "title.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "expReward", "expReward.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "coinReward", "coinReward.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "color", "color.empty");

        Todo todo = (Todo) target;
        if (todo.getOwner() != null)
            errors.rejectValue("owner", "owner.assignedbyuser");

        if (todo.getTitle().length() > 255)
            errors.rejectValue("title", "title.toolong");

        if (todo.getTags().size() > 30)
            errors.rejectValue("tags", "tags.toobig");

        todo.getTags().forEach(s -> {
            if (s.length() > 255)
                errors.rejectValue("tags", "tags.toolong");
        });

        if (todo.getTasks().size() > 100)
            errors.rejectValue("tasks", "tasks.toobig");

        int i = 0;
        for (Task task : todo.getTasks()) {
            errors.pushNestedPath("tasks[" + i + "]");
            ValidationUtils.invokeValidator(taskValidator, task, errors);
            errors.popNestedPath();
            i++;
        }
    }
}
