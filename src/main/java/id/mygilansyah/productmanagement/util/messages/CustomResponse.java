package id.mygilansyah.productmanagement.util.messages;

import lombok.*;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CustomResponse<T> {

    private T data;
    private String message;
    private String detail;
    private Integer httpStatusCode;

}
