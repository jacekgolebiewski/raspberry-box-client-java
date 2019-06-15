package pl.raspberry.box.client.model.request.screen.builder

import pl.raspberry.box.client.model.request.screen.Column
import pl.raspberry.box.client.model.request.screen.Row
import spock.lang.Specification

class MatrixBuilderTest extends Specification {

    def 'should correctly build empty matrix'() {
        when:
            List<List<Integer>> matrix = new MatrixBuilder()
                    .build()
        then:
            matrix == [
                    [0, 0, 0, 0, 0, 0, 0, 0],
                    [0, 0, 0, 0, 0, 0, 0, 0],
                    [0, 0, 0, 0, 0, 0, 0, 0],
                    [0, 0, 0, 0, 0, 0, 0, 0],
                    [0, 0, 0, 0, 0, 0, 0, 0],
                    [0, 0, 0, 0, 0, 0, 0, 0],
                    [0, 0, 0, 0, 0, 0, 0, 0],
                    [0, 0, 0, 0, 0, 0, 0, 0]
            ]
    }

    def 'should correctly build matrix with rows'() {
        when:
            List<List<Integer>> matrix = new MatrixBuilder()
                    .addRow(new Row().setIndex(1).setCells(1, 1, 1))
                    .addRow(new Row().setIndex(0).setCells(0, 1))
                    .build()
        then:
            matrix == [
                    [0, 1, 0, 0, 0, 0, 0, 0],
                    [1, 1, 1, 0, 0, 0, 0, 0],
                    [0, 0, 0, 0, 0, 0, 0, 0],
                    [0, 0, 0, 0, 0, 0, 0, 0],
                    [0, 0, 0, 0, 0, 0, 0, 0],
                    [0, 0, 0, 0, 0, 0, 0, 0],
                    [0, 0, 0, 0, 0, 0, 0, 0],
                    [0, 0, 0, 0, 0, 0, 0, 0]
            ]
    }

    def 'should correctly build matrix with rows and columns'() {
        when:
            List<List<Integer>> matrix = new MatrixBuilder()
                    .addColumn(new Column().setIndex(3).setCells(1, 1, 1, 1, 1, 1, 1, 1))
                    .addRow(new Row().setIndex(1).setCells(1, 1, 0, 0, 0, 0, 0))
                    .build()
        then:
            matrix == [
                    [0, 0, 0, 1, 0, 0, 0, 0],
                    [1, 1, 0, 1, 0, 0, 0, 0],
                    [0, 0, 0, 1, 0, 0, 0, 0],
                    [0, 0, 0, 1, 0, 0, 0, 0],
                    [0, 0, 0, 1, 0, 0, 0, 0],
                    [0, 0, 0, 1, 0, 0, 0, 0],
                    [0, 0, 0, 1, 0, 0, 0, 0],
                    [0, 0, 0, 1, 0, 0, 0, 0]
            ]
    }

}
