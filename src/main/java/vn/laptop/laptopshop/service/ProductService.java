package vn.laptop.laptopshop.service;

import java.util.List;

import org.springframework.stereotype.Service;

import vn.laptop.laptopshop.domain.Product;
import vn.laptop.laptopshop.domain.User;
import vn.laptop.laptopshop.repository.ProductRepository;

@Service
public class ProductService {
    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product handleSaveProduct(Product product) {
        return this.productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return this.productRepository.findAll();
    }
}
