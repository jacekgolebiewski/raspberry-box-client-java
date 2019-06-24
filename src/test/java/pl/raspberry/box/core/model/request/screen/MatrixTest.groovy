package pl.raspberry.box.core.model.request.screen

import spock.lang.Specification

class MatrixTest extends Specification {

    final EMPTY_ARRAY = [
            [0, 0, 0, 0, 0, 0, 0, 0],
            [0, 0, 0, 0, 0, 0, 0, 0],
            [0, 0, 0, 0, 0, 0, 0, 0],
            [0, 0, 0, 0, 0, 0, 0, 0],
            [0, 0, 0, 0, 0, 0, 0, 0],
            [0, 0, 0, 0, 0, 0, 0, 0],
            [0, 0, 0, 0, 0, 0, 0, 0],
            [0, 0, 0, 0, 0, 0, 0, 0]
    ]

    def 'should be created with 0\'s' () {
        expect:
            new Matrix().getArray() == EMPTY_ARRAY
    }

    def 'should fillRow correctly fill one column' () {
        expect:
            new Matrix().fillColumn(3).getArray() == [
                    [0, 0, 0, 1, 0, 0, 0, 0],
                    [0, 0, 0, 1, 0, 0, 0, 0],
                    [0, 0, 0, 1, 0, 0, 0, 0],
                    [0, 0, 0, 1, 0, 0, 0, 0],
                    [0, 0, 0, 1, 0, 0, 0, 0],
                    [0, 0, 0, 1, 0, 0, 0, 0],
                    [0, 0, 0, 1, 0, 0, 0, 0],
                    [0, 0, 0, 1, 0, 0, 0, 0]
            ]
    }

    def 'should correctly build matrix with rows'() {
        when:
            Matrix matrix = new Matrix()
                    .setRow(1, [1, 1, 1])
                    .setRow(0, [0, 1])
        then:
            matrix.getArray() == [
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
            Matrix matrix = new Matrix()
                    .setColumn(3, [1, 1, 1, 1, 0, 1, 1, 1])
                    .setRow(1, [1, 1, 0, 0, 0, 0, 0, 0])
        then:
            matrix.getArray() == [
                    [0, 0, 0, 1, 0, 0, 0, 0],
                    [1, 1, 0, 0, 0, 0, 0, 0],
                    [0, 0, 0, 1, 0, 0, 0, 0],
                    [0, 0, 0, 1, 0, 0, 0, 0],
                    [0, 0, 0, 0, 0, 0, 0, 0],
                    [0, 0, 0, 1, 0, 0, 0, 0],
                    [0, 0, 0, 1, 0, 0, 0, 0],
                    [0, 0, 0, 1, 0, 0, 0, 0]
            ]
    }

    def 'should correctly fill multiple columns'() {
        when:
            Matrix matrix = new Matrix().fillColumns([1, 3, 0])
        then:
            matrix.getArray() == [
                    [1, 1, 0, 1, 0, 0, 0, 0],
                    [1, 1, 0, 1, 0, 0, 0, 0],
                    [1, 1, 0, 1, 0, 0, 0, 0],
                    [1, 1, 0, 1, 0, 0, 0, 0],
                    [1, 1, 0, 1, 0, 0, 0, 0],
                    [1, 1, 0, 1, 0, 0, 0, 0],
                    [1, 1, 0, 1, 0, 0, 0, 0],
                    [1, 1, 0, 1, 0, 0, 0, 0],
            ]
    }

}
