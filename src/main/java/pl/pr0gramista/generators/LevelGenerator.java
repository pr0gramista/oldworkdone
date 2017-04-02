package pl.pr0gramista.generators;

public interface LevelGenerator {
    /**
     * Generates amount of experience needed to
     * acquire given level
     *
     * @param level in range from 1 to 100 (or infinity)
     * @return experience needed for given level
     */
    int generate(int level);

    /**
     * Gives what level should user have with given amount of experience
     *
     * @param experience experience
     * @return the highest level which given amount of experience fulfill, starts from 1
     */
    int getLevel(int experience);
}
