package id.mygilansyah.productmanagement.util.messages;

import lombok.*;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CustomPagingResponse<T> {

    private int pageNo;
    private int pageSize;
    private Long totalElements;
    private int totalPages;
    private List<T> content;

}
