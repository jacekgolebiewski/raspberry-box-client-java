package pl.raspberry.box.client.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * First index is row number
 */
public class MatrixUtil {

    public static List<List<Integer>> initMatrix(int size) {
        return Stream.generate(() -> new ArrayList<>(Collections.nCopies(size, 0)))
                .limit(size)
                .collect(Collectors.toList());
    }

    public static List<List<Integer>> flipVertically(List<List<Integer>> matrix) {
        int size = matrix.size();
        return transformMatrix(matrix, source -> new Pair<>(size - source.getFirst() - 1, source.getSecond()));
    }

    public static List<List<Integer>> flipHorizontally(List<List<Integer>> matrix) {
        int size = matrix.size();
        return transformMatrix(matrix, source -> new Pair<>(source.getFirst(), size - source.getSecond() - 1));
    }

    public static List<List<Integer>> shiftLeft(List<List<Integer>> matrix) {
        int size = matrix.size();
        return transformMatrix(matrix, source -> new Pair<>(source.getFirst(), (source.getSecond() -1 + size) % size));
    }

    public static List<List<Integer>> rotate(List<List<Integer>> matrix, int count) {
        if (count < 1 || count > 3) {
            throw new IllegalArgumentException("Flip count parameter must be in range 1-3, got: " + count);
        }
        for (int i = 0; i < count; i++) {
            matrix = rotateClockwise(matrix);
        }
        return matrix;
    }

    public static List<List<Integer>> rotateCounterClockwise(List<List<Integer>> matrix, int count) {
        if (count < 1 || count > 3) {
            throw new IllegalArgumentException("Flip count parameter must be in range 1-3, got: " + count);
        }
        return rotate(matrix,4-count);
    }

    private static List<List<Integer>> rotateClockwise(List<List<Integer>> matrix) {
        int size = matrix.size();
        return transformMatrix(matrix, source -> new Pair<>(source.getSecond(), size - source.getFirst() - 1));
    }

    public static List<List<Integer>> clone(List<List<Integer>> matrix) {
        return transformMatrix(matrix, Function.identity());
    }

    private static List<List<Integer>> transformMatrix(List<List<Integer>> matrix, Function<Pair<Integer>, Pair<Integer>> indexTransform) {
        int size = matrix.size();
        List<List<Integer>> result = initMatrix(size);
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Pair<Integer> indices = new Pair<>(i,j);
                Pair<Integer> newIndices = indexTransform.apply(indices);
                Integer currentValue = matrix.get(indices.getFirst()).get(indices.getSecond());
                result.get(newIndices.getFirst()).set(newIndices.getSecond(), currentValue);
            }
        }
        return result;
    }


}
