package id.mygilansyah.productmanagement.util.messages;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class CustomResponseGenerator<T> {

    public CustomResponse<T> successResponse(T dto, String detail){
        CustomResponse<T> customResponse = new CustomResponse<>();
        customResponse.setHttpStatusCode(HttpStatus.OK.value());
        customResponse.setMessage(HttpStatus.OK.getReasonPhrase());
        customResponse.setDetail(detail != null ? detail : HttpStatus.OK.toString());
        customResponse.setData(dto);
        return customResponse;
    }

    public CustomResponse<T> errorResponse(String detail){
        CustomResponse<T> customResponse = new CustomResponse<>();
        customResponse.setHttpStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
        customResponse.setMessage("Failed");
        customResponse.setDetail(detail);
        return customResponse;
    }

//    public CustomPagingResponse<T> pagingResponse(Page<T> page){
//        CustomPagingResponse<T> customPagingResponse = new CustomPagingResponse<>();
//        customPagingResponse.setPageNo();
//        customPagingResponse.setPageSize();
//        customPagingResponse.setTotalElements();
//        customPagingResponse.setTotalPages();
//        customPagingResponse.setContent();
//
//    }
}
