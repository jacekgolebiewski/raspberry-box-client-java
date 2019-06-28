package pl.raspberry.box.core.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.raspberry.box.core.util.MatrixUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.IntConsumer;
import java.util.stream.IntStream;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Matrix {

    public static Integer SIZE = 8;

    @Getter
    private List<List<Integer>> array;

    public Matrix() {
        array = MatrixUtil.initMatrix(SIZE);
    }

    public static void forEachIndex(IntConsumer action) {
        IntStream.range(0, SIZE).forEach(action);
    }

    public static List<Integer> getFullList() {
        return Collections.nCopies(SIZE, 1);
    }

    public static List<Integer> getEmptyList() {
        return Collections.nCopies(SIZE, 0);
    }

    public static Matrix fromArray(int[][] array) {
        Matrix result = new Matrix();
        forEachIndex(row -> {
            forEachIndex(column -> {
                result.fillCell(row, column, array[row][column]);
            });
        });
        return result;
    }

    public Integer getCellValue(int row, int column) {
        return array.get(row).get(column);
    }

    public Matrix fillCell(int row, int column, int value) {
        if (row < 0 || row >= SIZE) {
            throw new IllegalArgumentException("Row index out of bounds: " + row);
        }
        if (column < 0 || column >= SIZE) {
            throw new IllegalArgumentException("Column index out of bounds: " + column);
        }
        if (value != 0 && value != 1) {
            throw new IllegalArgumentException("Value must be 0 or 1, found: " + value);
        }
        array.get(row).set(column, value);
        return this;
    }

    public Matrix setRow(int index, List<Integer> row) {
        array.set(index, adjustList(row));
        return this;
    }

    public Matrix setFirstRow(List<Integer> row) {
        return this.setRow(0, row);
    }

    public Matrix setLastRow(List<Integer> row) {
        return this.setRow(SIZE - 1, row);
    }

    public Matrix fillRow(int index) {
        setRow(index, getFullList());
        return this;
    }

    public Matrix emptyRow(int index) {
        setRow(index, getEmptyList());
        return this;
    }

    public Matrix fillRows(List<Integer> indexes) {
        indexes.forEach(this::fillRow);
        return this;
    }

    public Matrix setColumn(int index, List<Integer> column) {
        final List<Integer> adjustedColumn = adjustList(column);
        forEachIndex(idx -> array.get(idx).set(index, adjustedColumn.get(idx)));
        return this;
    }

    public Matrix setFirstColumn(List<Integer> column) {
        return this.setColumn(0, column);
    }

    public Matrix setLastColumn(List<Integer> column) {
        return this.setColumn(SIZE - 1, column);
    }

    public Matrix fillColumn(int index) {
        setColumn(index, getFullList());
        return this;
    }

    public Matrix emptyColumn(int index) {
        setColumn(index, getEmptyList());
        return this;
    }

    public Matrix fillAll() {
        forEachIndex(this::fillRow);
        return this;
    }

    public Matrix fillColumns(List<Integer> indexes) {
        indexes.forEach(this::fillColumn);
        return this;
    }

    public Matrix flipVertically() {
        array = MatrixUtil.flipVertically(array);
        return this;
    }

    public Matrix flipHorizontally() {
        array = MatrixUtil.flipHorizontally(array);
        return this;
    }

    public Matrix rotate(int count) {
        array = MatrixUtil.rotate(array, count);
        return this;
    }

    public Matrix rotateCounterClockwise(int count) {
        array = MatrixUtil.rotateCounterClockwise(array, count);
        return this;
    }

    public Matrix shiftLeft() {
        array = MatrixUtil.shiftLeft(array);
        return this;
    }

    public Matrix shiftRight() {
        array = MatrixUtil.shiftRight(array);
        return this;
    }

    public Matrix shiftUp() {
        array = MatrixUtil.shiftUp(array);
        return this;
    }

    public Matrix shiftDown() {
        array = MatrixUtil.shiftDown(array);
        return this;
    }

    private List<Integer> adjustList(List<Integer> list) {
        Objects.requireNonNull(list);
        List<Integer> result = new ArrayList<>(list);
        while (result.size() < SIZE) {
            result.add(0);
        }
        if (result.size() > SIZE) {
            throw new IllegalArgumentException("Parameter must have size = " + SIZE);
        }
        Optional<Integer> illegalArgument = result.stream()
                .filter(a -> a != 0 && a != 1)
                .findFirst();
        if (illegalArgument.isPresent()) {
            throw new IllegalArgumentException("List must consist of values (0,1), found: " + illegalArgument.get());
        }
        return result;
    }

    public Matrix join(Matrix matrix) {
        forEachIndex(row -> {
            forEachIndex(column -> {
                if (matrix.getCellValue(row, column) == 1) {
                    this.fillCell(row, column, 1);
                }
            });
        });
        return this;
    }

    @Override
    public Matrix clone() {
        return new Matrix(MatrixUtil.clone(array));
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder().append("[");
        forEachIndex(idx -> builder.append(array.get(idx)).append("\n"));
        return builder.append("]").toString();
    }

}
