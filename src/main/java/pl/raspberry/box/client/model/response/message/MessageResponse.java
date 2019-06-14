package pl.raspberry.box.client.model.response.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import pl.raspberry.box.client.model.response.Response;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MessageResponse extends Response {

    public static final String NAME = "message";

    private String message;

}
