package edu.ifmg.br.produto.repository;

import edu.ifmg.br.produto.entities.Product;
import edu.ifmg.br.produto.util.Factory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

@DataJpaTest
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    private Long existingID;

    @BeforeEach
    void setUp() throws Exception{
        existingID = 1L;
    }


    @Test
    @DisplayName(value = "Verificando se o objeto" + "nao existe no BD depois de deletado")
    public void deleteShouldDeleteObjectWhenIdExists() {

        productRepository.deleteById(existingID);
        Optional<Product> obj = productRepository.findById(existingID);

        Assertions.assertFalse(obj.isPresent());

    }

    @Test
    @DisplayName(value = "Verificando autoincremento" + "da chave primaria")
    public void insertShouldPersistWithAutoIncrementIdWhenIdZero(){

        Product product = Factory.createProduct();
        product.setId(0);

        Product p = productRepository.save(product);
        Optional<Product> obj = productRepository.findById(p.getId());
        Assertions.assertTrue(obj.isPresent());
        Assertions.assertNotEquals(0, obj.get().getId());
        Assertions.assertEquals(26, obj.get().getId());

    }

}
