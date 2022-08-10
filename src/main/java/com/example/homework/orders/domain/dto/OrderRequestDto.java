package com.example.homework.orders.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
@Builder
public class OrderRequestDto {

    @ApiModelProperty(value = "회원 이름")
    @NotBlank
    @Size(min = 1, max = 20)
    private final String memberName;

    @ApiModelProperty(value = "제품명")
    @NotBlank
    @Size(min = 1, max = 100)
    private final String itemName;

    @ApiModelProperty(value = "결제 일시")
    private final LocalDateTime orderDate;
}
