package pl.raspberry.box.client.model.response.button;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pl.raspberry.box.client.model.response.Response;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ButtonResponse extends Response {

    public static final String NAME = "button";

    private Button button;
    private ButtonAction action;

}
