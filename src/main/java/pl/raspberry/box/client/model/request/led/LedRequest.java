package pl.raspberry.box.client.model.request.led;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.raspberry.box.client.model.request.Request;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LedRequest extends Request {
    public static final String NAME = "led";

    private LedColor color;
    private LedState state;

}
