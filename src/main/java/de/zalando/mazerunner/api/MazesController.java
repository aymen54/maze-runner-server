package de.zalando.mazerunner.api;

import com.wordnik.swagger.annotations.*;
import de.zalando.mazerunner.domain.*;
import de.zalando.mazerunner.service.MazeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Api(value = "Mazes API")
@RestController
@RequestMapping("/mazes")
public class MazesController {
    private static final Logger LOG = LoggerFactory.getLogger(MazesController.class);

    @Autowired
    private MazeService mazeService;

    public MazesController() { }

    @ApiOperation(value = "Lists mazes with their dimensions")
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    Mazes mazes() {
        return mazeService.getAll();
    }

    @ApiOperation(value = "Returns the coordinate of the start position",
                  notes = "Coordinates start at the upper left corner with indices starting at 0. " +
                          "The bottom-right corner has the coordinates (width - 1, height - 1).")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Maze not found")
    })
    @RequestMapping(value = "{code}/position/start", method = RequestMethod.GET)
    @ResponseBody
    Coordinate startPosition(@ApiParam(value = "maze code", required = true)
                             @PathVariable(value = "code") final String code) {
        final Optional<Maze> maze = mazeService.get(code);

        if (!maze.isPresent()) {
            LOG.warn("Maze [{}] not found!", code);

            throw new ResourceNotFoundException();
        }

        return maze.get().getStart();
    }

    @ApiOperation(value = "Validates whether the given move is valid or not",
                  notes = "The only valid originator coordinate is a way or starting position. " +
                          "In case the originator coordinate is invalid the maze service returns \"418 I'm a teapot\".\n" +
                          "The directions can be: NORTH, WEST, SOUTH, EAST.")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Valid move"),
            @ApiResponse(code = 404, message = "Maze not found"),
            @ApiResponse(code = 418, message = "Invalid move")
    })
    @RequestMapping(value = "{code}/position", method = RequestMethod.POST)
    @ResponseBody
    MoveResult validatePosition(@ApiParam(value = "maze code", required = true)
                                @PathVariable(value = "code") final String code,
                                @RequestBody final Move move) {
        final Optional<Maze> maze = mazeService.get(code);

        if (!maze.isPresent()) {
            LOG.warn("Maze [{}] not found!", code);

            throw new ResourceNotFoundException();
        }

        LOG.info("Received move: [{}] for maze [{}]", move, code);

        final Maze m = maze.get();
        if (!m.validateMove(move)) {
            LOG.info("Invalid move! [{}] for maze [{}]", move, code);

            throw new InvalidMoveException("Invalid move!");
        }

        return m.move(move);
    }
}
