package com.example.sauceofsource.dto;

import com.example.sauceofsource.model.ProductEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductDTO {
    private String id;
    private String title;
    private int price;
    private int spicyLevel;
    private String baseType;
    private String userId;

    // Entity를 DTO로 변환하는 생성자
    public ProductDTO(final ProductEntity entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.price = entity.getPrice();
        this.spicyLevel = entity.getSpicyLevel();
        this.baseType = entity.getBaseType();
        this.userId = entity.getUserId();
    }

    // DTO를 Entity로 변환하는 메서드
    public static ProductEntity toEntity(final ProductDTO dto) {
        return ProductEntity.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .price(dto.getPrice())
                .spicyLevel(dto.getSpicyLevel())
                .baseType(dto.getBaseType())
                .userId(dto.getUserId())
                .build();
    }
}