package de.zalando.mazerunner;

import de.zalando.mazerunner.domain.Maze;
import de.zalando.mazerunner.domain.Mazes;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@ComponentScan
public class Main {
    @Bean
    public Mazes mazes() {
        Mazes mazes = new Mazes();

        Maze first = new Maze();
        first.setCode("maze-1");
        first.setWidth(3);
        first.setHeight(3);
        first.setFields("#@#" +
                        "#.#" +
                        "..x");

        mazes.getMazes().add(first);

        Maze second = new Maze();
        second.setCode("maze-2");
        second.setWidth(8);
        second.setHeight(5);
        second.setFields("########" +
                         "@.#....x" +
                         "#.##.###" +
                         "#....###" +
                         "########");

        mazes.getMazes().add(second);

        return mazes;
    }

    public static void main(final String[] args) throws Exception {
        new SpringApplicationBuilder().showBanner(true).sources(Main.class).run(args);
    }
}
