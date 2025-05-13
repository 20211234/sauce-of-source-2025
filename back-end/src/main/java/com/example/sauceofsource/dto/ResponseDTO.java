package com.example.sauceofsource.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ResponseDTO<T> {
    // 에러 메시지를 담을 필드
    private String error;

    // 응답 데이터를 담을 필드 (제네릭 타입 사용)
    private List<T> data;
}
