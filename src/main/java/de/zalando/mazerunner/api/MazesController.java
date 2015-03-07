package de.zalando.mazerunner.api;

import de.zalando.mazerunner.domain.Mazes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MazesController {
    public static final Logger LOG = LoggerFactory.getLogger(MazesController.class);

    @Autowired
    private Mazes mazes;

    @RequestMapping("/mazes")
    @ResponseBody
    Mazes mazes() {
        LOG.info("");

        return mazes;
    }
}
