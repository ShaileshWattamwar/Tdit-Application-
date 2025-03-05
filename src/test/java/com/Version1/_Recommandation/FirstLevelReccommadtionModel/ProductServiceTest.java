package com.Version1._Recommandation.FirstLevelReccommadtionModel;

//import com.Version1._Recommandation.FirstLevelReccommadtionModel.Entity.Product;
import com.Version1._Recommandation.FirstLevelReccommadtionModel.Controller.ProductController;
import com.Version1._Recommandation.FirstLevelReccommadtionModel.Entity.Product;
import com.Version1._Recommandation.FirstLevelReccommadtionModel.Repository.ProductRepository;
import com.Version1._Recommandation.FirstLevelReccommadtionModel.Service.ServiceImpl.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.reactive.server.WebTestClient;


import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductServiceTest {

    @InjectMocks
    ProductController productController;

   @Mock
   ProductServiceImpl productServiceImpl;
   //Product product;
    // private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
    }

//    @Test
//    void getAllProducts_ShouldReturnAllProducts() {
//        Product product1 = new Product(1L, "Product1", "abc","nj");
//        Product product2 = new Product(2L, "Product2", "abc","nj");
//        List<Product> productList = Arrays.asList(product1, product2);
//
//        when(productRepository.findAll()).thenReturn(productList);
//
//        List<Product> result = productServiceImpl.getAllProducts();
//
//        assertNotNull(result);
//        assertEquals(2, result.size());
//        assertEquals(product1, result.get(0));
//        assertEquals(product2, result.get(1));
//
//    }

    @Test
     void testGetAllProducts(){
        // Arrange: Prepare test data

        Product p1=new Product(1L,"Laptop", "Electronics", "Gaming, Work");
        when(productServiceImpl.getAllProducts()).thenReturn(List.of(p1));

        // Act: Call the controller method
        List<Product> result=productController.getAllProducts();

        // Assert: Check if the response is correct
      assertNotNull(result);
      assertEquals(1,result.size());
      assertEquals("Laptop",result.get(0).getName());

        // Verify: Ensure that service method was called once
      verify(productServiceImpl,times(1)).getAllProducts();

    }

    @Test
    void testAddProduct(){
        Product product=new Product(2L, "Phone", "Electronics", "Smartphone");
        when(productServiceImpl.saveProduct(product)).thenReturn(product);

        // Act: Call the controller method
        ResponseEntity<Product> response =productController.addProduct(product);

        //Assert: Check if the response is correct

        assertNotNull(response);
        assertEquals(200 ,response.getStatusCodeValue());
        assertEquals("Phone",response.getBody().getName());

        // Verify: Ensure that service method was called once

        verify(productServiceImpl,times(1)).saveProduct(product);

    }

}
