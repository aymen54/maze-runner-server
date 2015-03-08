package de.zalando.mazerunner.service;

import de.zalando.mazerunner.domain.Maze;
import de.zalando.mazerunner.domain.Mazes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class MazeService {

    @Autowired
    private Mazes mazes;

    public MazeService() { }

    public void setMazes(final Mazes mazes) {
        this.mazes = mazes;
    }

    public Mazes getAll() {
        return mazes;
    }

    public Optional<Maze> get(final String code) {
        return mazes.getMazes().stream().filter(maze -> maze.getCode().equals(code)).findFirst();
    }
}
