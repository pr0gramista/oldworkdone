package pl.pr0gramista.generators;

import pl.pr0gramista.enums.ExperienceAmount;

public interface ExperienceGenerator {
    int generate(ExperienceAmount amount);
}
