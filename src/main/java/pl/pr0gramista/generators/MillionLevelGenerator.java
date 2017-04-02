package pl.pr0gramista.generators;

/**
 * Generator where 100 level requires 1 000 000 experience.
 */
public class MillionLevelGenerator implements LevelGenerator {
    @Override
    public int generate(int level) {
        level -= 1;
        return level * level * 100 + level * 200 + 100;
    }

    @Override
    public int getLevel(int experience) {
        return (int) Math.floor((-200 + Math.sqrt(200 * 200 - 4 * 100 * (100 - experience))) / 200 + 1);
    }
}
