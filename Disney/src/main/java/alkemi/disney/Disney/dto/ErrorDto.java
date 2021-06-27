package alkemi.disney.Disney.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDto {

    private Integer i;
    private Exception ex;
    private String requestURI;

}
