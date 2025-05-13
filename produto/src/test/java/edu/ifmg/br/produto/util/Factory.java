package edu.ifmg.br.produto.util;

import edu.ifmg.br.produto.dtos.ProductDTO;
import edu.ifmg.br.produto.entities.Category;
import edu.ifmg.br.produto.entities.Product;

public class Factory {

    public static Product createProduct() {

        Product p = new Product();
        p.setName("Iphone XXX");
        p.setPrice(5000);
        p.setImageUrl("https://img.com/iphonexxx.jpg");
        p.getCategories().add(new Category(1L, "Livros"));

        return p;
    }

    public static ProductDTO createProductDTO() {

        Product p = createProduct();

        return new ProductDTO(p);
    }
}
