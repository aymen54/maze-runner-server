package de.zalando.mazerunner.api;

import com.wordnik.swagger.annotations.ApiOperation;
import de.zalando.mazerunner.domain.Mazes;
import de.zalando.mazerunner.service.MazeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
}
