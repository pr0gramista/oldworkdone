package pl.pr0gramista.generators;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import pl.pr0gramista.enums.CoinAmount;

import static org.junit.Assert.assertEquals;

@RunWith(JUnit4.class)
public class FixedBaseCoinGeneratorTests {

    CoinGenerator coinGenerator = new FixedBaseCoinGenerator(1000);

    @Test
    public void amountsAreGood() throws Exception {
        assertEquals(1500, coinGenerator.generate(CoinAmount.HIGH));
        assertEquals(1000, coinGenerator.generate(CoinAmount.MEDIUM));
        assertEquals(500, coinGenerator.generate(CoinAmount.SMALL));
    }
}
