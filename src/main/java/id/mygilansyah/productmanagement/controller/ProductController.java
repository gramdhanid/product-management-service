package id.mygilansyah.productmanagement.controller;

import id.mygilansyah.productmanagement.dto.ProductDTO;
import id.mygilansyah.productmanagement.service.ProductService;
import id.mygilansyah.productmanagement.util.exception.CustomException;
import id.mygilansyah.productmanagement.util.messages.CustomResponse;
import id.mygilansyah.productmanagement.util.messages.CustomResponseGenerator;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;
    private final CustomResponseGenerator<Object> customResponseGenerator;

    public ProductController(ProductService productService, CustomResponseGenerator<Object> customResponseGenerator) {
        this.productService = productService;
        this.customResponseGenerator = customResponseGenerator;
    }

    @PostMapping("/add")
    CustomResponse<Object> createUpdateProduct(@RequestBody ProductDTO productDTO) {
        try {
            return customResponseGenerator.successResponse(productService.create(productDTO), HttpStatus.CREATED.getReasonPhrase());
        } catch (Exception e){
            return customResponseGenerator.errorResponse(e.getMessage());
        }
    }

    @GetMapping("")
    CustomResponse<Object> getProductById(@RequestParam Long id) {
        try {
            return customResponseGenerator.successResponse(productService.getProductById(id), HttpStatus.OK.getReasonPhrase());
        } catch (Exception e){
            return customResponseGenerator.errorResponse(e.getMessage());
        }
    }

    @GetMapping("/paging")
    CustomResponse<Object> getProductByPage(@RequestParam String sSearch,
                                            @RequestParam Integer startPage,
                                            @RequestParam Integer pageSize,
                                            @RequestParam String sortBy,
                                            @RequestParam String sortDir)
            throws CustomException {
        try {
            return customResponseGenerator.successResponse(productService.pagingProduct(sSearch, startPage, pageSize, sortBy, sortDir), HttpStatus.OK.getReasonPhrase());
        } catch (Exception e){
            return customResponseGenerator.errorResponse(e.getMessage());
        }
    }

    @DeleteMapping("/delete")
    CustomResponse<Object> deleteProductById(@RequestParam Long id) {
        try {
            return customResponseGenerator.successResponse(productService.deleteProductById(id), HttpStatus.OK.getReasonPhrase());
        } catch (Exception e){
            return customResponseGenerator.errorResponse(e.getMessage());
        }
    }
}
