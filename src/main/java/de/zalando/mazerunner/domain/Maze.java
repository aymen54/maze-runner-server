package de.zalando.mazerunner.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Set;

@ApiModel("Maze")
public class Maze {
    private static final char WALL = '#';
    private static final char EXIT = 'x';
    private static final char WAY = '.';

    private static final Set<Character> VALID_TARGETS = Sets.newHashSet(WAY, EXIT);
    private static final Set<Character> INVALID_SOURCES = Sets.newHashSet(WALL, EXIT);

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

    public Boolean validateMove(final Move move) {
        final Coordinate from = move.getFrom();

        final Character fromField = getField(from);
        if (INVALID_SOURCES.contains(fromField)) {
            return false;
        }

        final Coordinate to = from.move(move.getDirection());
        final Character field = getField(to);

        return VALID_TARGETS.contains(field);
    }

    private Character getField(final Coordinate coordinate) {
        final int index = coordinate.getX() + coordinate.getY() * width;

        if (isOutOfBounds(coordinate.getX(), width) || isOutOfBounds(coordinate.getY(), height)) {
            return WALL;
        }

        return fields.charAt(index);
    }

    private boolean isOutOfBounds(final int pos, final int max) {
        return pos < 0 || pos >= max;
    }

    public MoveResult move(final Move move) {
        Preconditions.checkState(this.validateMove(move), "move has to be valid");

        final Coordinate position = move.getFrom().move(move.getDirection());
        final Character field = getField(position);

        return new MoveResult(position, field);
    }
}
