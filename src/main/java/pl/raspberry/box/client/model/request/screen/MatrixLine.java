package pl.raspberry.box.client.model.request.screen;


import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

@Data
@NoArgsConstructor
public abstract class MatrixLine {

    public static Integer SIZE = 8;

    @Setter(AccessLevel.NONE)
    private Integer index;

    @Setter(AccessLevel.NONE)
    private List<Integer> cells = new ArrayList<>(SIZE);

    public MatrixLine(Integer index, List<Integer> cells) {
        this.index = index;
        this.setCells(cells);
    }

    public MatrixLine setIndex(Integer index) {
        this.index = index;
        return this;
    }

    public MatrixLine setCells(Integer ... cells) {
        return this.setCells(Arrays.asList(cells));
    }

    public MatrixLine setCells(List<Integer> cells) {
        this.cells = new ArrayList<>();
        this.cells.addAll(cells);
        while(this.cells.size() < SIZE) {
            this.cells.add(0);
        }
        return this;
    }

    public void forEach(Consumer<Integer> action) {
        cells.forEach(action);
    }

    public Integer get(int index) {
        return cells.get(index);
    }

    public static void forEachIndex(IntConsumer action) {
        IntStream.range(0, MatrixLine.SIZE).forEach(action);
    }
}

