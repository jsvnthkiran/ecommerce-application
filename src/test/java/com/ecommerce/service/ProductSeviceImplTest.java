package com.ecommerce.service;

import com.ecommerce.dao.ProductDAO;
import com.ecommerce.model.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProductServiceImplTest {

    private ProductServiceImpl productService;
    private ProductDAOStub productDAOStub;

    @BeforeEach
    void setUp() {
        productDAOStub = new ProductDAOStub();
        productService = new ProductServiceImpl(productDAOStub);
    }

    @Test
    void testAddProduct() {
        Product product = new Product(1, "Test Product", 100.0);
        productService.addProduct(product);

        assertEquals(1, productDAOStub.products.size());
        assertEquals(product, productDAOStub.products.get(0));
    }

    @Test
    void testGetProduct() {
        Product product = new Product(1, "Test Product",  100.0);
        productDAOStub.products.add(product);

        Product retrievedProduct = productService.getProduct(1);

        assertEquals(product, retrievedProduct);
    }

    @Test
    void testGetAllProducts() {
        Product product1 = new Product(1, "Product 1", 50.0);
        Product product2 = new Product(2, "Product 2",  75.0);
        productDAOStub.products.add(product1);
        productDAOStub.products.add(product2);

        List<Product> retrievedProducts = productService.getAllProducts();

        assertEquals(2, retrievedProducts.size());
        assertTrue(retrievedProducts.contains(product1));
        assertTrue(retrievedProducts.contains(product2));
    }

    @Test
    void testUpdateProduct() {
        Product product = new Product(1, "Old Product", 50.0);
        productDAOStub.products.add(product);

        Product updatedProduct = new Product(1, "Updated Product",  150.0);
        productService.updateProduct(updatedProduct);

        Product retrievedProduct = productDAOStub.products.get(0);
        assertEquals(updatedProduct, retrievedProduct);
    }

    @Test
    void testDeleteProduct() {
        Product product = new Product(1, "Test Product",  100.0);
        productDAOStub.products.add(product);

        productService.deleteProduct(1);

        assertTrue(productDAOStub.products.isEmpty());
    }

    // Stub class for ProductDAO
    static class ProductDAOStub implements ProductDAO {

        List<Product> products = new ArrayList<>();

        @Override
        public void createProduct(Product product) {
            products.add(product);
        }

        @Override
        public Product getProductById(int productId) {
            for (Product product : products) {
                if (product.getId() == productId) {
                    return product;
                }
            }
            return null; // Or throw an exception if you prefer
        }

        @Override
        public List<Product> getAllProducts() {
            return new ArrayList<>(products);
        }

        @Override
        public void updateProduct(Product product) {
            for (int i = 0; i < products.size(); i++) {
                if (products.get(i).getId() == product.getId()) {
                    products.set(i, product);
                    break;
                }
            }
        }

        @Override
        public void deleteProduct(int productId) {
            products.removeIf(product -> product.getId() == productId);
        }
    }
}
