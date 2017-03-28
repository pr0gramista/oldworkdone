package pl.pr0gramista;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.ZonedDateTime;

public abstract class Completion {
    private int experience;
    private int coins;

    //https://stackoverflow.com/questions/31627992/spring-data-jpa-zoneddatetime-format-for-json-serialization
    //Needed to add this annotation, because it was generating something like this: 1462931135.03600
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private ZonedDateTime dateTime;

    public Completion(int experience, int coins, ZonedDateTime dateTime) {
        this.experience = experience;
        this.coins = coins;
        this.dateTime = dateTime;
    }

    public int getExperience() {
        return experience;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }

    public int getCoins() {
        return coins;
    }

    public void setCoins(int coins) {
        this.coins = coins;
    }

    public ZonedDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(ZonedDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
