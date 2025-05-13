package com.example.sauceofsource.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="product")
public class ProductEntity {
    // @Id: 기본키(Primary Key) 지정
    // @GeneratedValue: ID 자동 생성 설정
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    private String id;

    // 사용자 ID (본인 이름)
    private String userId;

    // 소스 이름
    private String title;

    // 소스 가격
    private int price;

    // 매운 정도 (1-5)
    private int spicyLevel;

    // 소스 베이스 타입 (마요네즈, 간장, 고추장 등)
    private String baseType;
}