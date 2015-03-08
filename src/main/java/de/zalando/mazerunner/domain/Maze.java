package de.zalando.mazerunner.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import java.util.List;

@ApiModel("Maze")
public class Maze {
    @ApiModelProperty(value = "Unique identifier", required = true)
    private String code;

    @ApiModelProperty(required = true)
    private int width;

    @ApiModelProperty(required = true)
    private int height;

    @ApiModelProperty(value = "", required = true)
    @JsonIgnore
    private String fields;

    public Maze() {

    }

    @JsonIgnore
    public Coordinate getStart() {
        int idx = fields.indexOf('@');

        int x = idx % width;
        int y = idx / height;

        return new Coordinate(x, y);
    }

    @Override
    public String toString() {
        List<String> lines = Lists.newArrayList();

        for (int i = 0; i < height; i++) {
            lines.add(fields.substring(i * width, (i + 1) * width));
        }

        return Joiner.on("\n").join(lines);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getFields() {
        return fields;
    }

    public void setFields(String fields) {
        this.fields = fields;
    }
}
