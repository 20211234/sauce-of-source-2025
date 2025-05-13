package com.example.sauceofsource.persistence;

import com.example.sauceofsource.model.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, String> {
    // userId로 상품 목록 조회
    List<ProductEntity> findByUserId(String userId);

    // title로 상품 검색
    List<ProductEntity> findByTitle(String title);

    // userId와 title로 상품 검색
    List<ProductEntity> findByUserIdAndTitle(String userId, String title);

    // title로 상품 삭제
    void deleteByTitle(String title);
}