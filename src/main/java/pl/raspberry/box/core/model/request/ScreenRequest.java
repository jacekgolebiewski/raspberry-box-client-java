package pl.raspberry.box.core.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScreenRequest extends Request {
    public static final String NAME = "screen";

    private List<List<Integer>> matrix;

}
