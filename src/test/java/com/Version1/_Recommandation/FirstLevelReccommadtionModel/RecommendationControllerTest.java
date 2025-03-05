package com.Version1._Recommandation.FirstLevelReccommadtionModel;

import com.Version1._Recommandation.FirstLevelReccommadtionModel.Controller.RecommendationController;
import com.Version1._Recommandation.FirstLevelReccommadtionModel.Entity.Product;
import com.Version1._Recommandation.FirstLevelReccommadtionModel.Service.RecommendationService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RecommendationControllerTest {

    @Mock
    private RecommendationService recommendationService; // Mocked service

    @InjectMocks
    private RecommendationController recommendationController; // Controller to test

    @Test
    void testGetRecommendations(){

        Long userId = 1L;

        List<Product> mockProducts = Arrays.asList(
                new Product(1L, "Laptop", "Electronics", "tech,computer"),
                new Product(2L, "Phone", "Electronics", "mobile,smartphone")
        );

        when(recommendationService.getRecommendedProducts(userId)).thenReturn(mockProducts);

        ResponseEntity<List<Product>> response =recommendationController.getRecommendations(userId);

        assertNotNull(response);

        assertEquals(200,response.getStatusCodeValue());

        assertEquals(2,response.getBody().size());

        assertEquals("Laptop",response.getBody().get(0).getName());

    }

}
