package pl.pr0gramista.todo;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class TaskValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(Task.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "content", "content.empty");

        Task task = (Task) target;

        if (task.getContent().length() > 255)
            errors.rejectValue("content", "content.toolong");
    }
}
