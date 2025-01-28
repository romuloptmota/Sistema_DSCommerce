package com.devsuperior.dscommerce.Services;

import com.devsuperior.dscommerce.Services.exceptions.DataBaseExcepitions;
import com.devsuperior.dscommerce.Services.exceptions.ResourseNotFoundExcepitions;
import com.devsuperior.dscommerce.dto.ProductDTO;
import com.devsuperior.dscommerce.entities.Product;
import com.devsuperior.dscommerce.repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    //GET ONE
    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        Product product = repository.findById(id).orElseThrow(
                () ->new ResourseNotFoundExcepitions("Recurso não encontrado"));
        return new ProductDTO(product);
    }

    //GET ALL
    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll(Pageable pageable) {
        Page<Product> result = repository.findAll(pageable);
        return result.map(x -> new ProductDTO(x));
    }

    //POST
    @Transactional
    public ProductDTO insert(ProductDTO dto) {
        Product entity = new Product();
        copyDtotoEntity(dto, entity);
        entity = repository.save(entity);
        return new ProductDTO(entity);
    }

    //PUT
    @Transactional
    public ProductDTO update(Long id, ProductDTO dto) {
        try {
            Product entity = repository.getReferenceById(id);
            copyDtotoEntity(dto, entity);
            entity = repository.save(entity);
            return new ProductDTO(entity);
        }catch (EntityNotFoundException e) {
            throw new ResourseNotFoundExcepitions("Recurso não encontrado");
        }

    }

    //DELETE
    @Transactional(propagation = Propagation.SUPPORTS) //propagation para DataIntegrityViolationException, pois h2
    public void delete(Long id) {
        if(!repository.existsById(id)) {
            throw new ResourseNotFoundExcepitions("Recurso não encontrado");
        }
        try {
            repository.deleteById(id);
            //Caso tente apagar algum produto vinculado a algum pedido
        }catch (DataIntegrityViolationException e) {
            throw new DataBaseExcepitions("Falha na integridade referencial");
        }
    }

    //AUX
    private void copyDtotoEntity(ProductDTO dto, Product entity) {

        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setImgUrl(dto.getImgUrl());

    }
}
