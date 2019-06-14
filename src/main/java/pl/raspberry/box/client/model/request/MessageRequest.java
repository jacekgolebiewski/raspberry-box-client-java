package pl.raspberry.box.client.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class MessageRequest extends Request {
    public static final String NAME = "message";

    private String message;

}
