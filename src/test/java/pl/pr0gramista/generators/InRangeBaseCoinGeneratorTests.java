package pl.pr0gramista.generators;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import pl.pr0gramista.enums.CoinAmount;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThanOrEqualTo;
import static org.hamcrest.core.CombinableMatcher.both;
import static org.hamcrest.core.Is.is;

@RunWith(JUnit4.class)
public class InRangeBaseCoinGeneratorTests {

    private CoinGenerator coinGenerator = new InRangeBaseCoinGenerator(100, 200);

    @Test
    public void amountsAreGood() throws Exception {
        for (int i = 0; i < 100; i++) {
            assertThat(coinGenerator.generate(CoinAmount.HIGH), is(both(greaterThanOrEqualTo(150)).and(lessThanOrEqualTo(300))));
            assertThat(coinGenerator.generate(CoinAmount.MEDIUM), is(both(greaterThanOrEqualTo(100)).and(lessThanOrEqualTo(200))));
            assertThat(coinGenerator.generate(CoinAmount.SMALL), is(both(greaterThanOrEqualTo(50)).and(lessThanOrEqualTo(100))));
        }
    }
}