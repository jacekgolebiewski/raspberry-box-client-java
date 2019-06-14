package pl.raspberry.box.client.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

public class ObjectMapperUtil {

    @Getter
    private final static ObjectMapper objectMapper = new ObjectMapper();

}
