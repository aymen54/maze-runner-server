package de.zalando.mazerunner.domain;

import com.google.common.collect.Lists;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import java.util.Arrays;
import java.util.List;

@ApiModel("Available mazes")
public class Mazes {
    @ApiModelProperty(value = "List of available mazes", required = true)
    private List<Maze> mazes = Lists.newArrayList();

    public Mazes() {
    }

    public Mazes(final Maze ... mazes) {
        this.mazes.addAll(Arrays.asList(mazes));
    }

    public List<Maze> getMazes() {
        return mazes;
    }

    public void setMazes(final List<Maze> mazes) {
        this.mazes = mazes;
    }
}
