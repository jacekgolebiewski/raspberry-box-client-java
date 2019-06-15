package pl.raspberry.box.client.model.request.screen;


import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@NoArgsConstructor
public class Row extends MatrixLine {

    public Row(Integer index, List<Integer> cells) {
        super(index, cells);
    }

    public static Row createFullRow(Integer index) {
        return new Row(index, Arrays.asList(1,1,1,1,1,1,1,1));
    }

    @Override
    public Row setIndex(Integer index) {
        return (Row) super.setIndex(index);
    }

    @Override
    public Row setCells(Integer... cells) {
        return (Row) super.setCells(cells);
    }

    @Override
    public Row setCells(List<Integer> cells) {
        return (Row) super.setCells(cells);
    }

}

