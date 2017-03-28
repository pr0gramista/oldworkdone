package pl.pr0gramista.generators;

import pl.pr0gramista.enums.CoinAmount;

public interface CoinGenerator {
    int generate(CoinAmount amount);
}
