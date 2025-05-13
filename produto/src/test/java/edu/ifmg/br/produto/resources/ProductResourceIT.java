package edu.ifmg.br.produto.resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.ifmg.br.produto.dtos.ProductDTO;
import edu.ifmg.br.produto.util.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class ProductResourceIT {

    // objeto que ira fazer as requisiceos
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Long existingId;
    private Long nonExistingId;

    @BeforeEach
    void setUp() {
        existingId = 1L;
        nonExistingId = 2000L;

    }

    @Test
    void findAllShouldReturnSortedPageWhenSortByName() throws Exception {

        ResultActions result =
        mockMvc.perform(
                get("/product?page=0&size=10&sort=name,asc")
                        .accept(MediaType.APPLICATION_JSON)
        );

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.content[0].name").value("Macbook Pro"));
        result.andExpect(jsonPath("$.content[1].name").value("PC Gamer"));
    }

    @Test
    public void updateShouldReturnDtoWhenIdExists() throws Exception {

        ProductDTO dto = Factory.createProductDTO();
        // converter o objeto em JSON
        String dtoJson = objectMapper.writeValueAsString(dto);

        String nameExpected = dto.getName();
        String descriptionExpected = dto.getDescription();

        ResultActions result =
                mockMvc.perform(
                        put("/product/{id}", existingId)
                                .content(dtoJson)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                );

        result.andExpect(status().isOk());
        result.andExpect(jsonPath("$.id").value(existingId));
        result.andExpect(jsonPath("$.name").value(nameExpected));
        result.andExpect(jsonPath("$.description").value(descriptionExpected));

    }

    @Test
    public void updateShouldReturnNotFoundWhenIdDoesNotExists() throws Exception {

        ProductDTO dto = Factory.createProductDTO();
        // converter o objeto em JSON
        String dtoJson = objectMapper.writeValueAsString(dto);

        ResultActions result =
                mockMvc.perform(
                        put("/product/{id}", nonExistingId)
                                .content(dtoJson)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                );

        result.andExpect(status().isNotFound());

    }

    @Test
    public void insertShouldReturnNewObjectWhenDataAreCorret() throws Exception{

        // Bruno pediu pra gente criar esse teste pra ver se a gente ta entendendo
        // checar se o status 201 create

        // checar se o Id e 26

        // checar se o nome inserido e esperado

        ProductDTO dto = Factory.createProductDTO();
        String dtoJson = objectMapper.writeValueAsString(dto);

        Long idExpected = 26L;

        String nameExpected = dto.getName();

        ResultActions result =
                mockMvc.perform(
                        post("/product")
                                .content(dtoJson)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)
                );

        result.andExpect(status().isCreated());
        result.andExpect(jsonPath("$.id").value(idExpected));
        result.andExpect(jsonPath("$.name").value(nameExpected));

    }

    @Test
    public void deleteShouldReturnOkNoContentWhenIdExist() throws Exception {

        ResultActions result =
                mockMvc.perform(
                        delete("/product/{id}", existingId)
                                .accept(MediaType.APPLICATION_JSON)
                );

        result.andExpect(status().isNoContent());

    }

    @Test
    public void deleteShouldReturnNoFoundWhenIdDoesNotExist() throws Exception {

        ResultActions result =
                mockMvc.perform(
                        delete("/product/{id}", nonExistingId)
                                .accept(MediaType.APPLICATION_JSON)
                );

        result.andExpect(status().isNotFound());

    }

    @Test
    public void findByIdShouldReturnProductWhenIdExists() throws Exception {

        ResultActions result =
                mockMvc.perform(
                        get("/product/{id}", existingId)
                                .accept(MediaType.APPLICATION_JSON)
                );

        result.andExpect(status().isOk());
        String resultJson =
                            result.andReturn().getResponse().getContentAsString();

        System.out.println(resultJson);
        // transformou texto em objeto
        ProductDTO dto = objectMapper.readValue(resultJson, ProductDTO.class);

        Assertions.assertEquals(existingId, dto.getId());
        Assertions.assertEquals("The Lord of the Rings", dto.getName());

    }

}
