package pl.pr0gramista;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.pr0gramista.generators.*;

@Configuration
public class GeneratorConfig {

    @Bean
    public CoinGenerator coinGenerator() {
        return new InRangeBaseCoinGenerator(50, 150);
    }

    @Bean
    public ExperienceGenerator expierenceGenerator() {
        return new InRangeBaseExperienceGenerator(50, 150);
    }

    @Bean
    public LevelGenerator levelGenerator() {
        return new MillionLevelGenerator();
    }
}
