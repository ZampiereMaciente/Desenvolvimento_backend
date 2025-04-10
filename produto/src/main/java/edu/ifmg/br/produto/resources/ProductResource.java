package edu.ifmg.br.produto.resources;

import edu.ifmg.br.produto.dtos.ProductDTO;
import edu.ifmg.br.produto.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/products")
public class ProductResource {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<Page><ProductDTO> findAll(Pageable pageable) {

        Page<ProductDTO> products = productService.findAll(pageable);
        return ResponseEntity.ok().body(products);

    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id) {

        ProductDTO product = productService.findAll(id);
        return ResponseEntity.ok().body(product);

    }

    @PostMapping
    public ResponseEntity<ProductDTO> insert(@RequestBody ProductDTO dto) {

        dto = productService.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(dto.getId()).toUri();

        return ResponseEntity.created(uri).body(dto);

    }

}
