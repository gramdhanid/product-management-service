package id.mygilansyah.productmanagement.service;

import id.mygilansyah.productmanagement.dto.ProductDTO;
import id.mygilansyah.productmanagement.model.Product;
import id.mygilansyah.productmanagement.repository.ProductRepository;
import id.mygilansyah.productmanagement.util.exception.CustomException;
import id.mygilansyah.productmanagement.util.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Transactional
    public ProductDTO create(ProductDTO productDTO) throws CustomException {
        try {
            log.info("Creating or updating product {}", productDTO);
            Product product = new Product();
            if (productDTO.getId() != null) {
                product = productRepository.findByIdAndDeleted(productDTO.getId(), false)
                        .orElseThrow(() -> new CustomException("Product Not Found", ErrorCode.GENERIC_FAILURE));
            }
            product.setName(productDTO.getName());
            product.setPrice(productDTO.getPrice());
            product.setDeleted(false);
            productRepository.save(product);
            return toDto(product);
        } catch (Exception e) {
            throw new CustomException(e.getMessage(), ErrorCode.GENERIC_FAILURE);
        }
    }

    private ProductDTO toDto(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setPrice(product.getPrice());
        return productDTO;
    }

    public ProductDTO getProductById(Long id) throws CustomException {
        log.info("Get product by id: {}", id);
        return toDto(productRepository.findById(id)
                .orElseThrow(() -> new CustomException("Product Not Found", ErrorCode.GENERIC_FAILURE)));
    }

    public Page<ProductDTO> pagingProduct(String sSearch, int startPage, int pageSize, String sortBy, String sortDir) throws CustomException {
        log.info("Paging product by: {}", sSearch);
        Page<Product> page = productRepository.getProduct(sSearch, PageRequest.of(startPage, pageSize, sortDirection(sortBy, sortDir)));
        List<ProductDTO> dtoList = toDtoList(page.getContent());
        return new PageImpl<>(dtoList, PageRequest.of(startPage, pageSize), page.getTotalElements());
    }

    private List<ProductDTO> toDtoList(List<Product> products) {
        List<ProductDTO> dtoList = new ArrayList<>();
        if (products != null) {
            for (Product product : products) {
                dtoList.add(toDto(product));
            }
        }
        return dtoList;
    }

    private Sort sortDirection(String sortBy, String sortDir) {
        if (!sortDir.isEmpty() && !sortDir.isEmpty()) {
            return sortDir.toUpperCase(Locale.ROOT).equals("ASC") ? Sort.by(Sort.Direction.ASC, sortBy) : Sort.by(Sort.Direction.DESC, sortBy);
        } else {
            return Sort.by(Sort.Direction.ASC, "id");
        }
    }

    public Boolean deleteProductById(Long id) throws CustomException {
        try {
            log.info("Delete role by id: {}", id);
            Product product = productRepository.findByIdAndDeleted(id, false)
                    .orElseThrow(() -> new CustomException("Product not found", ErrorCode.GENERIC_FAILURE));
            product.setDeleted(true);
            productRepository.save(product);
            return true;
        } catch (Exception e){
            throw new CustomException(e.getMessage(), ErrorCode.GENERIC_FAILURE);
        }
    }
}
