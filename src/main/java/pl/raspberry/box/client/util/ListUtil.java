package pl.raspberry.box.client.util;

import lombok.experimental.UtilityClass;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.IntStream;

@UtilityClass
public class ListUtil {

    public static <T> List<T> initList(List<T> existingList, Supplier<T> supplier, int size) {
        if (!CollectionUtils.isEmpty(existingList)) {
            throw new IllegalArgumentException("Existing list must be empty");
        }
        IntStream.range(0, size).forEach(index -> existingList.add(supplier.get()));
        return existingList;
    }

}
