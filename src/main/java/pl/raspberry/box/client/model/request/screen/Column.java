package pl.raspberry.box.client.model.request.screen;


import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.List;

@NoArgsConstructor
public class Column extends MatrixLine {

    public Column(Integer index, List<Integer> cells) {
        super(index, cells);
    }

    public static Column createFullColumn(Integer index) {
        return new Column(index, Arrays.asList(1,1,1,1,1,1,1,1));
    }

    @Override
    public Column setIndex(Integer index) {
        return (Column) super.setIndex(index);
    }

    @Override
    public Column setCells(Integer... cells) {
        return (Column) super.setCells(cells);
    }

    @Override
    public Column setCells(List<Integer> cells) {
        return (Column) super.setCells(cells);
    }

}
