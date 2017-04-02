package pl.pr0gramista.generators;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(JUnit4.class)
public class MillionLevelGeneratorTests {
    LevelGenerator levelGenerator = new MillionLevelGenerator();

    @Test
    public void experienceAmountsAreGood() throws Exception {
        assertThat(levelGenerator.generate(100), is(1000000));
        assertThat(levelGenerator.generate(1), is(100));
        assertThat(levelGenerator.generate(27), is(72900));
        assertThat(levelGenerator.generate(84), is(705600));
        assertThat(levelGenerator.generate(58), is(336400));
    }

    @Test
    public void levelsAreGood() throws Exception {
        assertThat(levelGenerator.getLevel(110), is(1));
        assertThat(levelGenerator.getLevel(800000), is(89));
        assertThat(levelGenerator.getLevel(476100), is(69));
        assertThat(levelGenerator.getLevel(220950), is(47));
        assertThat(levelGenerator.getLevel(3600), is(6));
        assertThat(levelGenerator.getLevel(67599), is(25));
    }
}
