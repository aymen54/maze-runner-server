package de.zalando.mazerunner.api;

import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiParam;
import de.zalando.mazerunner.domain.Coordinate;
import de.zalando.mazerunner.domain.Maze;
import de.zalando.mazerunner.domain.Mazes;
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
    public static final Logger LOG = LoggerFactory.getLogger(MazesController.class);

    @Autowired
    private MazeService mazeService;

    public MazesController() { }

    public MazesController(MazeService mazeService) {
        this.mazeService = mazeService;
    }

    @ApiOperation(value = "Returns available mazes")
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    Mazes mazes() {
        LOG.info("");

        return mazeService.findAll();
    }

    @ApiOperation(value = "Returns the coordinate of the start position")
    @RequestMapping(value = "{code}/position/start", method = RequestMethod.GET)
    @ResponseBody
    Coordinate startPosition(@ApiParam(value = "maze code", required = true) @PathVariable(value = "code") String code) {
        Optional<Maze> maze = mazeService.get(code);

        if (maze.isPresent()) {
            return mazeService.get(code).get().getStart();
        } else {
            throw new ResourceNotFoundException();
        }
    }
}
