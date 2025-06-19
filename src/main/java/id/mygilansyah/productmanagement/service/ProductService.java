package id.mygilansyah.productmanagement.service;

import id.mygilansyah.productmanagement.dto.ProductDTO;
import id.mygilansyah.productmanagement.model.Product;
import id.mygilansyah.productmanagement.repository.ProductRepository;
import id.mygilansyah.productmanagement.util.exception.CustomException;
import id.mygilansyah.productmanagement.util.exception.ErrorCode;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
