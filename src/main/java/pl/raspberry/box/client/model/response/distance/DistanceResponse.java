package pl.raspberry.box.client.model.response.distance;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import pl.raspberry.box.client.model.response.Response;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DistanceResponse extends Response {

    public static final String NAME = "distance";

    private Double distance;

}
