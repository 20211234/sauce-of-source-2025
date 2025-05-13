package com.example.sauceofsource.service;

import com.example.sauceofsource.model.ProductEntity;
import com.example.sauceofsource.persistence.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ProductService {
    @Autowired
    private ProductRepository repository;

    public String testService() {
        ProductEntity entity = ProductEntity.builder().title("My first Source of Source item").build();

        repository.save(entity);

        ProductEntity savedEntity = repository.findById(entity.getId()).get();

        return savedEntity.getTitle();
    }

    // 상품 생성
    public List<ProductEntity> create(final ProductEntity entity) {
        // 유효성 검사
        validate(entity);

        // 저장
        repository.save(entity);

        log.info("Entity id: {} is saved", entity.getId());

        // 사용자의 모든 상품 목록 반환
        return repository.findByUserId(entity.getUserId());
    }

    // 유효성 검사
    private void validate(ProductEntity entity) {
        if(entity == null) {
            log.warn("Entity cannot be null");
            throw new RuntimeException("Entity cannot be null");
        }

        if(entity.getUserId() == null) {
            log.warn("Entity.getUserId cannot be null");
            throw new RuntimeException("Entity.getUserId cannot be null");
        }
    }

    // 사용자의 모든 상품 조회
    public List<ProductEntity> retrieve(final String userId) {
        return repository.findByUserId(userId);
    }

    // 제목으로 상품 검색
    public List<ProductEntity> searchByTitle(final String title, final String userId) {
        return repository.findByUserIdAndTitle(userId, title);
    }

    // 상품 정보 업데이트
    public List<ProductEntity> update(ProductEntity entity) {
        validate(entity);

        final Optional<ProductEntity> original = repository.findById(entity.getId());

        if (original.isPresent()) {
            ProductEntity product = original.get();

            // 필드 업데이트
            product.setTitle(entity.getTitle());
            product.setPrice(entity.getPrice());
            product.setSpicyLevel(entity.getSpicyLevel());
            product.setBaseType(entity.getBaseType());

            // 저장
            repository.save(product);
            log.info("Entity id: {} is updated", entity.getId());
        } else {
            throw new RuntimeException("Product not found with id: " + entity.getId());
        }

        // 수정 후 전체 제품 리스트 반환
        return repository.findByUserId(entity.getUserId());
    }

    // 상품 삭제
    public List<ProductEntity> delete(final ProductEntity entity) {
        validate(entity);

        try {
            repository.delete(entity);
        } catch (Exception e) {
            log.error("error deleting entity", entity.getId(), e);
            throw new RuntimeException("error deleting entity " + entity.getId());
        }

        return retrieve(entity.getUserId());
    }

    @Transactional
    // title로 상품 삭제
    public void deleteByTitle(final String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new RuntimeException("Title cannot be null or empty");
        }
        repository.deleteByTitle(title);
    }


}