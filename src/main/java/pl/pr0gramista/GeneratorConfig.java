package pl.pr0gramista;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.pr0gramista.generators.CoinGenerator;
import pl.pr0gramista.generators.ExperienceGenerator;
import pl.pr0gramista.generators.FixedBaseCoinGenerator;
import pl.pr0gramista.generators.FixedBaseExperienceGenerator;

@Configuration
public class GeneratorConfig {

    @Bean
    public CoinGenerator coinGenerator() {
        return new FixedBaseCoinGenerator(1000);
    }

    @Bean
    public ExperienceGenerator expierenceGenerator() {
        return new FixedBaseExperienceGenerator(1000);
    }
}
