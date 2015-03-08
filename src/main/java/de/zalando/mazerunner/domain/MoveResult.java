package de.zalando.mazerunner.domain;

import com.google.common.base.MoreObjects;
import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

@ApiModel("Result of a move within the maze")
public class MoveResult {
    @ApiModelProperty(required = true)
    private Coordinate position;

    @ApiModelProperty(required = true)
    private Character field;

    public MoveResult() { }

    public MoveResult(final Coordinate position, final Character field) {
        this.position = position;
        this.field = field;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("position", position).add("field", field).toString();
    }

    public Coordinate getPosition() {
        return position;
    }

    public void setPosition(final Coordinate position) {
        this.position = position;
    }

    public Character getField() {
        return field;
    }

    public void setField(final Character field) {
        this.field = field;
    }
}
