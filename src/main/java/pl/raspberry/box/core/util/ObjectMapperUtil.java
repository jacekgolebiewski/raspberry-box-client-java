package pl.raspberry.box.core.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

public class ObjectMapperUtil {

    @Getter
    private final static ObjectMapper objectMapper = new ObjectMapper();

}
