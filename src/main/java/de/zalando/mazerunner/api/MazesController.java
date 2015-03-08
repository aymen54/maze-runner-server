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

    @ApiOperation(value = "Returns available mazes")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Maze not found")
    })
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    Mazes mazes() {
        LOG.info("");

        return mazeService.findAll();
    }

    @ApiOperation(value = "Returns the coordinate of the start position")
    @ApiResponses(value = {
            @ApiResponse(code = 404, message = "Maze not found")
    })
    @RequestMapping(value = "{code}/position/start", method = RequestMethod.GET)
    @ResponseBody
    Coordinate startPosition(@ApiParam(value = "maze code", required = true) @PathVariable(value = "code") final String code) {
        Optional<Maze> maze = mazeService.get(code);

        if (maze.isPresent()) {
            return maze.get().getStart();
        }

        throw new ResourceNotFoundException();
    }

    @ApiOperation(value = "Validates whether the given move is valid")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Valid move"),
            @ApiResponse(code = 404, message = "Maze not found"),
            @ApiResponse(code = 418, message = "Invalid move")
    })
    @RequestMapping(value = "{code}/position", method = RequestMethod.POST)
    @ResponseBody
    MoveResult validatePosition(@ApiParam(value = "maze code", required = true) @PathVariable(value = "code") final String code,
                          @RequestBody final Move move) {
        Optional<Maze> maze = mazeService.get(code);

        if (!maze.isPresent()) {
            LOG.warn("Maze [{}] not found!", code);

            throw new ResourceNotFoundException();
        }

        LOG.info("Received move: [{}] for maze [{}]", move, code);

        Maze m = maze.get();
        if (!m.validateMove(move)) {
            LOG.info("Invalid move! [{}]", move);

            throw new InvalidMoveException();
        }

        return m.move(move);
    }
}
