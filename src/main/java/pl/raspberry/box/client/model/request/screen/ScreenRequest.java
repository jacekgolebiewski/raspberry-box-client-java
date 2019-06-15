package pl.raspberry.box.client.model.request.screen;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.raspberry.box.client.model.request.Request;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScreenRequest extends Request {
    public static final String NAME = "screen";

    private List<List<Integer>> matrix;

}
