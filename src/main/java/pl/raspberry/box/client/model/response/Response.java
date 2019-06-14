package pl.raspberry.box.client.model.response;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.raspberry.box.client.model.request.Request;
import pl.raspberry.box.client.model.response.button.ButtonResponse;
import pl.raspberry.box.client.model.response.message.MessageResponse;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = MessageResponse.class, name = MessageResponse.NAME),
        @JsonSubTypes.Type(value = ButtonResponse.class, name = ButtonResponse.NAME),

})
public class Response {

    private Request request;

}
