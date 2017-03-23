package pl.pr0gramista.enums;

import java.io.Serializable;

public enum CoinAmount implements Serializable {
    SMALL(0.5F), MEDIUM(1F), HIGH(1.5F);

    public final float multiplier;

    CoinAmount(float multiplier) {
        this.multiplier = multiplier;
    }
}