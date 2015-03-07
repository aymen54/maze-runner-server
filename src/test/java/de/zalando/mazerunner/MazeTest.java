package de.zalando.mazerunner;

import com.google.common.base.Joiner;
import de.zalando.mazerunner.domain.Maze;
import org.junit.Test;

import java.util.Arrays;
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
}
