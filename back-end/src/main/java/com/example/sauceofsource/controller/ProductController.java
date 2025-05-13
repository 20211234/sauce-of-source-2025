package com.example.sauceofsource.controller;

import com.example.sauceofsource.dto.ResponseDTO;
import com.example.sauceofsource.dto.ProductDTO;
import com.example.sauceofsource.model.ProductEntity;
import com.example.sauceofsource.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("product")
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping("/test")
    public ResponseEntity<?> testProduct() {
        String str = service.testService();
        List<String> list = new ArrayList<>();
        list.add(str);
        ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();

        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody ProductDTO dto) {
        try {
            ProductEntity entity = ProductDTO.toEntity(dto);
            entity.setId(null); // ID는 자동 생성
            entity.setUserId("Eunsun Jang"); // 사용자 ID 설정

            List<ProductEntity> entities = service.create(entity);
            List<ProductDTO> dtos = entities.stream().map(ProductDTO::new).collect(Collectors.toList());

            ResponseDTO<ProductDTO> response = ResponseDTO.<ProductDTO>builder().data(dtos).build();
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            ResponseDTO<ProductDTO> response = ResponseDTO.<ProductDTO>builder().error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping
    public ResponseEntity<?> retrieveProductList(@RequestParam(required = false) String title) {
        String userId = "Eunsun Jang";
        List<ProductEntity> entities;
        if (title == null || title.isEmpty()) {
            entities = service.retrieve(userId); // 전체 조회
        } else {
            entities = service.searchByTitle(title, userId); // title로 검색
        }
        List<ProductDTO> dtos = entities.stream().map(ProductDTO::new).collect(Collectors.toList());
        ResponseDTO<ProductDTO> response = ResponseDTO.<ProductDTO>builder().data(dtos).build();
        return ResponseEntity.ok().body(response);
    }

    @PutMapping
    public ResponseEntity<?> updateProduct(@RequestBody ProductDTO dto) {
        try {
            ProductEntity entity = ProductDTO.toEntity(dto);
            entity.setUserId("Eunsun Jang");

            List<ProductEntity> entities = service.update(entity);
            List<ProductDTO> dtos = entities.stream().map(ProductDTO::new).collect(Collectors.toList());

            ResponseDTO<ProductDTO> response = ResponseDTO.<ProductDTO>builder().data(dtos).build();
            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            ResponseDTO<ProductDTO> response = ResponseDTO.<ProductDTO>builder().error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(response);
        }
    }

//
//    @DeleteMapping
//    public ResponseEntity<?> deleteProduct(@RequestBody ProductDTO dto) {
//        try {
//            ProductEntity entity = ProductDTO.toEntity(dto);
//            entity.setUserId("Eunsun Jang");
//
//            List<ProductEntity> entities = service.delete(entity);
//            List<ProductDTO> dtos = entities.stream().map(ProductDTO::new).collect(Collectors.toList());
//
//            ResponseDTO<ProductDTO> response = ResponseDTO.<ProductDTO>builder().data(dtos).build();
//            return ResponseEntity.ok().body(response);
//        } catch (Exception e) {
//            ResponseDTO<ProductDTO> response = ResponseDTO.<ProductDTO>builder().error(e.getMessage()).build();
//            return ResponseEntity.badRequest().body(response);
//        }
//    }

    @DeleteMapping
    public ResponseEntity<?> deleteProduct(@RequestParam String title) {
        try {
            service.deleteByTitle(title);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            ResponseDTO<ProductDTO> response = ResponseDTO.<ProductDTO>builder().error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(response);
        }
    }
}