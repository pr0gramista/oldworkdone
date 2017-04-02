package pl.pr0gramista.generators;

import pl.pr0gramista.enums.ExperienceAmount;

import java.util.Random;

public class InRangeBaseExperienceGenerator implements ExperienceGenerator {
    private float from;
    private float delta;

    private Random random = new Random();

    public InRangeBaseExperienceGenerator(float from, float to) {
        this.from = from;

        if (from >= to)
            throw new IllegalArgumentException("From cannot be bigger than to");

        if (from < 0 || to < 0)
            throw new IllegalArgumentException("Values must be positive");

        this.delta = to - from;
    }

    @Override
    public int generate(ExperienceAmount amount) {
        return Math.round(from * amount.multiplier + ((random.nextFloat() * delta) * amount.multiplier));
    }
}
