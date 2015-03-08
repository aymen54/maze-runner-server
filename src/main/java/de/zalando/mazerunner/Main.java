package de.zalando.mazerunner;

import de.zalando.mazerunner.domain.Maze;
import de.zalando.mazerunner.domain.Mazes;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableAutoConfiguration
@EnableAsync
@EnableScheduling
@ComponentScan
public class Main {
    @Bean
    public Mazes mazes() {
        Mazes mazes = new Mazes();

        Maze maze = new Maze();
        maze.setCode("maze-1");
        maze.setWidth(3);
        maze.setHeight(3);
        maze.setFields("#@##.#..x");

        mazes.getMazes().add(maze);

        return mazes;
    }

    public static void main(final String[] args) throws Exception {
        new SpringApplicationBuilder().showBanner(false).sources(Main.class).run(args);
    }
}
