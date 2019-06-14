package pl.raspberry.box.client.model.request;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import pl.raspberry.box.client.model.request.led.LedRequest;

import java.util.UUID;

@Data
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = MessageRequest.class, name = MessageRequest.NAME),
        @JsonSubTypes.Type(value = PropertyRequest.class, name = PropertyRequest.NAME),
        @JsonSubTypes.Type(value = LedRequest.class, name = LedRequest.NAME)
})
public class Request {

    private String uid = UUID.randomUUID().toString();

}
