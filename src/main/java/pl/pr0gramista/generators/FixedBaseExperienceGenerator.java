package pl.pr0gramista.generators;

import pl.pr0gramista.enums.ExperienceAmount;

public class FixedBaseExperienceGenerator implements ExperienceGenerator {

    private int base;

    public FixedBaseExperienceGenerator(int base) {
        this.base = base;
    }

    @Override
    public int generate(ExperienceAmount amount) {
        return Math.round(amount.multiplier * base);
    }
}
