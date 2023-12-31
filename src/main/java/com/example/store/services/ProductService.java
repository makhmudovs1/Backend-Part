package com.example.store.services;

import com.example.store.models.Image;
import com.example.store.models.Product;
import com.example.store.models.User;
import com.example.store.repositories.ProductRepository;
import com.example.store.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    public List<Product> listProducts(String title) {
        List<Product> products = productRepository.findAll();
        if (title != null) return productRepository.findByTitle(title);
        return productRepository.findAll();
    }

    public void saveProduct(Principal principal, Product product, MultipartFile file1, MultipartFile file2, MultipartFile file3) throws IOException {
        User user = getUserByPrincipal(principal);
        product.setUser(user);

        if (file1.getSize() != 0) {
            Image image1 = toImageEntity(file1);
            image1.setPreviewImage(true);
            product.addImageToProduct(image1);
        }
        if (file2.getSize() != 0) {
            Image image2 = toImageEntity(file2);
            product.addImageToProduct(image2);
        }
        if (file3.getSize() != 0) {
            Image image3 = toImageEntity(file3);
            product.addImageToProduct(image3);
        }

        log.info("Saving new Product. Title: {}; Author email: {}", product.getTitle(), user.getEmail());

        try {
            Product savedProduct = productRepository.save(product);
            savedProduct.setPreviewImageId(savedProduct.getImages().get(0).getId());
            productRepository.save(savedProduct);
        } catch (Exception e) {
            log.error("Error saving product: {}", e.getMessage());
            throw new RuntimeException("Error saving product", e);
        }
    }


    public User getUserByPrincipal(Principal principal) {
        if (principal == null) return new User();
        return userRepository.findByEmail(principal.getName());
    }

    private Image toImageEntity(MultipartFile file) throws IOException {
        Image image = new Image();
        image.setName(file.getName());
        image.setOriginalFileName(file.getOriginalFilename());
        image.setContentType(file.getContentType());
        image.setSize(file.getSize());
        image.setBytes(file.getBytes());
        return image;
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }
}
