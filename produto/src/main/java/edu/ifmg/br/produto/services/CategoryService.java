package edu.ifmg.br.produto.services;

import edu.ifmg.br.produto.dtos.CategoryDTO;
import edu.ifmg.br.produto.entities.Category;
import edu.ifmg.br.produto.repository.CategoryRepository;
import edu.ifmg.br.produto.services.exceptions.DataBaseException;
import edu.ifmg.br.produto.services.exceptions.ResourceNotFound;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public Page<CategoryDTO> findAll(Pageable pageable) {
        Page<Category> list = categoryRepository.findAll(pageable);
        return list.map( c -> new CategoryDTO(c));
    }

    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id) {
        Optional<Category> obj = categoryRepository.findById(id);

        Category category = obj.orElseThrow(() -> new ResourceNotFound("Category not found: "+id));
        return new CategoryDTO(category);
    }

    @Transactional
    public CategoryDTO insert(CategoryDTO dto) {

        Category entity = new Category();
        entity.setName(dto.getName());
        entity = categoryRepository.save(entity);
        return new CategoryDTO(entity);
    }

    @Transactional
    public CategoryDTO update(Long id, CategoryDTO dto) {

       try {

        Category entity = categoryRepository.getReferenceById(id);
        entity.setName(dto.getName());
        entity = categoryRepository.save(entity);
        return new CategoryDTO(entity);

      } catch (EntityNotFoundException e) {
           throw new ResourceNotFound("Category not found: "+id);
       }
    }

    @Transactional
    public void delete(Long id){



        if (!categoryRepository.existsById(id)) {
            throw new ResourceNotFound("Category not found: "+id);
        }

        try {
            categoryRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataBaseException("Integrity violation");
        }

        categoryRepository.deleteById(id);
    }

}
