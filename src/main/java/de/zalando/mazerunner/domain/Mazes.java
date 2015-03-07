package de.zalando.mazerunner.domain;

import com.google.common.collect.Lists;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import de.zalando.mazerunner.domain.Maze;

import java.util.List;

@ApiModel("Available mazes")
public class Mazes {
    @ApiModelProperty(value = "List of available mazes", required = true)
    private List<Maze> mazes = Lists.newArrayList();

    public Mazes() {
    }

    public List<Maze> getMazes() {
        return mazes;
    }

    public void setMazes(List<Maze> mazes) {
        this.mazes = mazes;
    }
}
