package de.zalando.mazerunner.service;

import de.zalando.mazerunner.domain.Mazes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MazeService {

    @Autowired
    private Mazes mazes;

    public MazeService() {
    }

    public void setMazes(Mazes mazes) {
        this.mazes = mazes;
    }

    public Mazes findAll() {
        return mazes;
    }
}
