package de.zalando.mazerunner.domain;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class MazeTest {
    @Test
    public void shouldPrintMazeAsRectangle() {
        Maze maze = new Maze();
        maze.setWidth(3);
        maze.setHeight(3);
        maze.setFields("#.##.##..");

        assertThat(maze.toString(), is("#.#\n#.#\n#.."));
    }

    @Test
    public void canComputeStartingPosition() {
        Maze maze = new Maze();
        maze.setWidth(3);
        maze.setHeight(3);
        maze.setFields("#.##.@#..");

        assertThat(maze.getStart(), is(new Coordinate(2, 1)));
    }
}
