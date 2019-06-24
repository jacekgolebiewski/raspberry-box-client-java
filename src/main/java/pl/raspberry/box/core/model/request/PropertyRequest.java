package pl.raspberry.box.core.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PropertyRequest extends Request {

    public static final String NAME = "property";

    private String key;
    private String value;

}
