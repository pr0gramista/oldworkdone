package pl.pr0gramista.generators;

import pl.pr0gramista.enums.CoinAmount;

public class FixedBaseCoinGenerator implements CoinGenerator {

    private int base = 1000;

    public FixedBaseCoinGenerator(int base) {
        this.base = base;
    }

    @Override
    public int generate(CoinAmount amount) {
        return Math.round(amount.multiplier * base);
    }
}
