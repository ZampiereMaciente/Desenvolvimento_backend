package edu.ifmg.br.produto.resources;

import edu.ifmg.br.produto.dtos.ProductDTO;
import edu.ifmg.br.produto.services.ProductService;
import edu.ifmg.br.produto.util.Factory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(ProductResource.class)
class ProductResourceTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    private ProductDTO productDTO;
    private PageImpl<ProductDTO> page;
    private Long existingId;
    private Long nonExistingId;

    @BeforeEach
    void setUp() {
        existingId = 1L;
        nonExistingId = 2000L;
        productDTO = Factory.createProductDTO();
        productDTO.setId(1);
        page = new PageImpl<>(List.of(productDTO));
    }

    @Test
    void findAllShouldReturnAllPage() throws Exception {

        // cria o metodo mocado
        when(productService.findAll(any()))
                .thenReturn(page);

        // teste a requisicao
        ResultActions result = mockMvc.perform(get("/product")
                .accept("application/json"));

        // analisa o resultado
        result.andExpect(status().isOk());
    }

    @Test
    void findByIdShouldReturnProductWhenIdExists() throws Exception {

        // cria o metodo mocado
        when(productService.findById(existingId))
                .thenReturn(productDTO);

        // teste a requisicao
        ResultActions result = mockMvc.perform(get("/product/{id}", existingId)
                .accept("application/json"));

        // analisa o resultado
        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").value(productDTO.getId()));
        result.andExpect(jsonPath("$.name").value(productDTO.getName()));
        result.andExpect(jsonPath("$.description").value(productDTO.getDescription()));
    }
}