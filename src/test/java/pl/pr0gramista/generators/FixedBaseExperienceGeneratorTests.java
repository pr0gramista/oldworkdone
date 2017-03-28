package pl.pr0gramista.generators;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import pl.pr0gramista.enums.ExperienceAmount;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class FixedBaseExperienceGeneratorTests {
    ExperienceGenerator experienceGenerator = new FixedBaseExperienceGenerator(1000);

    @Test
    public void amountsAreGood() throws Exception {
        assertEquals(1500, experienceGenerator.generate(ExperienceAmount.HIGH.HIGH));
        assertEquals(1000, experienceGenerator.generate(ExperienceAmount.MEDIUM));
        assertEquals(500, experienceGenerator.generate(ExperienceAmount.SMALL));
    }
}
