package pl.raspberry.box.client.model.request.screen.builder;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import pl.raspberry.box.client.model.request.screen.Column;
import pl.raspberry.box.client.model.request.screen.MatrixLine;
import pl.raspberry.box.client.model.request.screen.Row;
import pl.raspberry.box.client.util.MatrixUtil;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@NoArgsConstructor
public class MatrixBuilder {

    private Set<Row> rows = new HashSet<>();
    private Set<Column> columns = new HashSet<>();

    public MatrixBuilder addRow(Row row) {
        rows.add(row);
        return this;
    }

    public MatrixBuilder addColumn(Column column) {
        columns.add(column);
        return this;
    }

    public List<List<Integer>> build() {
        if (!CollectionUtils.isEmpty(rows) && !CollectionUtils.isEmpty(columns)) {
            log.warn("Warning: rows and columns used, notice that 1 will overwrite 0's");
        }

        List<List<Integer>> matrix = MatrixUtil.initMatrix(MatrixLine.SIZE);
        if (!CollectionUtils.isEmpty(rows)) {
            rows.forEach(row -> {
                MatrixLine.forEachIndex(colIndex -> {
                    if (row.get(colIndex) == 1) {
                        matrix.get(row.getIndex()).set(colIndex, 1);
                    }
                });
            });
        }
        if (!CollectionUtils.isEmpty(columns)) {
            columns.forEach(column -> {
                MatrixLine.forEachIndex(rowIndex -> {
                    if (column.get(rowIndex) == 1) {
                        matrix.get(rowIndex).set(column.getIndex(), 1);
                    }
                });
            });
        }
        return matrix;
    }

}
