package pl.raspberry.box.core.model.request;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

import java.util.UUID;

@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = MessageRequest.class, name = MessageRequest.NAME),
        @JsonSubTypes.Type(value = PropertyRequest.class, name = PropertyRequest.NAME),
        @JsonSubTypes.Type(value = ScreenRequest.class, name = ScreenRequest.NAME)
})
public class Request {

    private String uid = UUID.randomUUID().toString();

}
