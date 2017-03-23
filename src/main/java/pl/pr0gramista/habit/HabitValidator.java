package pl.pr0gramista.habit;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class HabitValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.equals(Habit.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        System.out.println("Validating habit");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "text", "text.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "expReward", "expReward.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "coinReward", "coinReward.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "color", "color.empty");

        Habit habit = (Habit) target;
        if (habit.getOwner() != null)
            errors.reject("owner", "owner.assignedbyuser");

        if (habit.getText().length() > 255)
            errors.reject("text", "text.toolong");

        if (habit.getTags().size() > 30)
            errors.reject("tags", "tags.toobig");

        habit.getTags().forEach(s -> {
            if (s.length() > 255)
                errors.rejectValue("tags", "tags.toolong");
        });
    }
}
