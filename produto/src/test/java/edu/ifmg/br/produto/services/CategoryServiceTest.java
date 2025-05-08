package edu.ifmg.br.produto.services;

import edu.ifmg.br.produto.dtos.ProductDTO;
import edu.ifmg.br.produto.entities.Product;
import edu.ifmg.br.produto.repository.ProductRepository;
import edu.ifmg.br.produto.services.exceptions.ResourceNotFound;
import edu.ifmg.br.produto.util.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatcher;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;

//import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    @InjectMocks
    private ProductService productService;

    private long existingID;
    private long nonExistingID;
    private PageImpl<Product> page;

    @Mock
    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {

        existingID = 1L;
        nonExistingID = 2L;
        Product product = Factory.createProduct();
        product.setId(existingID);
        page = new PageImpl<>(List.of(product, product));
    }

    @Test
    @DisplayName("Verificando se o objeto foi deletado no BD")
    void deleteShouldDoNothingWhenIdExists() {

        when(productRepository.existsById(existingID)).thenReturn(true);
        doNothing().when(productRepository).deleteById(existingID);

        Assertions.assertDoesNotThrow(() -> {
            productService.delete(existingID);
        });

        verify(productRepository, times(1)).deleteById(existingID);

        productService.delete(existingID);

    }

    @Test
    @DisplayName("Verificando se lenvanta uma execao" + "se o objeto nao existe no BD")
    void deleteShouldThrowExceptionWhenIdNonExists() {

        when(productRepository.existsById(nonExistingID)).thenReturn(false);
        //doNothing().when(productRepository).deleteById(nonExistingID);

        Assertions.assertThrows(ResourceNotFound.class, () -> {
            productService.delete(nonExistingID);
        });

        verify(productRepository, times(0)).deleteById(nonExistingID);

    }

    @Test
    @DisplayName("Verificando se o findall retorna os dados paginados")
    void findAllShouldReturnOnePage() {

        when(productRepository.findAll((Pageable) ArgumentMatchers.any())).thenReturn(page);

        Pageable pagina = PageRequest.of(0, 10);
        Page<ProductDTO> result = productService.findAll(pagina);


        Assertions.assertNotNull(result);
        Assertions.assertEquals(1, result.getContent().get(0).getId());
        verify(productRepository, times(1)).findAll(pagina);

    }

    @Test
    @DisplayName("Verificando a busca de um produto" + "por id existente")
    void findByIdShouldReturnProductDTOWhenIdExists() {

        Product p = Factory.createProduct();
        p.setId(existingID);
        when(productRepository.findById(existingID))
                .thenReturn(Optional.of(p));

        ProductDTO dto = productService.findById(existingID);
        Assertions.assertNotNull(dto);
        Assertions.assertEquals(existingID, dto.getId());
        verify(productRepository, times(1)).findById(existingID);

    }

}
