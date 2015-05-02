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

    @Test
    public void canComputeAllPossibleStartingPositions() {
        Maze maze = new Maze();
        maze.setWidth(8);
        maze.setHeight(3);

        String s = "########" +
                   "#......#" +
                   "########";

        for (int i = 0; i < maze.getWidth(); i++) {
            for (int j = 0; j < maze.getHeight(); j++) {
                char chars[] = s.toCharArray();
                chars[i + j * maze.getWidth()] = '@';

                maze.setFields(new String(chars));
                assertThat(maze.getStart(), is(new Coordinate(i, j)));
            }
        }
    }

    @Test
    public void shouldAllowMovingFromOneWayToAnotherWay() {
        Maze maze = new Maze();
        maze.setWidth(2);
        maze.setHeight(1);
        maze.setFields("..");

        Move move = new Move();
        move.setFrom(new Coordinate(0, 0));
        move.setDirection(Direction.EAST);

        assertThat(maze.validateMove(move), is(true));
    }

    @Test
    public void shouldAllowMovingFromStartToExit() {
        Maze maze = new Maze();
        maze.setWidth(2);
        maze.setHeight(1);
        maze.setFields("@x");

        Move move = new Move();
        move.setFrom(new Coordinate(0, 0));
        move.setDirection(Direction.EAST);

        assertThat(maze.validateMove(move), is(true));
    }

    @Test
    public void shouldDisallowMovingToWalls() {
        Maze maze = new Maze();
        maze.setWidth(1);
        maze.setHeight(2);
        maze.setFields("#.");

        Move move = new Move();
        move.setFrom(new Coordinate(0, 1));
        move.setDirection(Direction.NORTH);

        assertThat(maze.validateMove(move), is(false));
    }

    @Test
    public void shouldDisallowMovingFromWallToAnywhere() {
        Maze maze = new Maze();
        maze.setWidth(1);
        maze.setHeight(2);
        maze.setFields("#.");

        Move move = new Move();
        move.setFrom(new Coordinate(0, 0));
        move.setDirection(Direction.SOUTH);

        assertThat(maze.validateMove(move), is(false));
    }

    @Test
    public void shouldDisallowMovingFromExitToAnywhere() {
        Maze maze = new Maze();
        maze.setWidth(1);
        maze.setHeight(2);
        maze.setFields("x.");

        Move move = new Move();
        move.setFrom(new Coordinate(0, 0));
        move.setDirection(Direction.SOUTH);

        assertThat(maze.validateMove(move), is(false));
    }

    @Test
    public void shouldDisallowMovesFromANonExistingVerticalPosition() {
        Maze maze = new Maze();
        maze.setWidth(1);
        maze.setHeight(1);
        maze.setFields(".");

        Move move = new Move();
        move.setFrom(new Coordinate(0, 1));
        move.setDirection(Direction.SOUTH);

        assertThat(maze.validateMove(move), is(false));
    }

    @Test
    public void shouldDisallowMovesFromANonExistingHorizontalPosition() {
        Maze maze = new Maze();
        maze.setWidth(1);
        maze.setHeight(1);
        maze.setFields(".");

        Move move = new Move();
        move.setFrom(new Coordinate(1, 0));
        move.setDirection(Direction.SOUTH);

        assertThat(maze.validateMove(move), is(false));
    }

    @Test
    public void shouldDisallowMovesToANonExistingPosition() {
        Maze maze = new Maze();
        maze.setWidth(1);
        maze.setHeight(1);
        maze.setFields(".");

        Move move = new Move();
        move.setFrom(new Coordinate(0, 0));
        move.setDirection(Direction.SOUTH);

        assertThat(maze.validateMove(move), is(false));
    }

    @Test
    public void shouldDisallowMovesFromAnInvalidPosition() {
        Maze maze = new Maze();
        maze.setWidth(1);
        maze.setHeight(1);
        maze.setFields(".");

        Move move = new Move();
        move.setFrom(new Coordinate(-1, -1));
        move.setDirection(Direction.SOUTH);

        assertThat(maze.validateMove(move), is(false));
    }

    @Test
    public void shouldDisallowMovesToAnInvalidPosition() {
        Maze maze = new Maze();
        maze.setWidth(1);
        maze.setHeight(1);
        maze.setFields(".");

        Move move = new Move();
        move.setFrom(new Coordinate(0, 0));
        move.setDirection(Direction.NORTH);

        assertThat(maze.validateMove(move), is(false));
    }
}
