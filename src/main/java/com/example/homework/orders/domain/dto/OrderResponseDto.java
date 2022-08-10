package com.example.homework.orders.domain.dto;

import com.example.homework.members.domain.entity.Member;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
@Builder
public class OrderResponseDto {

    @ApiModelProperty(value = "주문 id")
    private final Long id;

    @ApiModelProperty(value = "주문 회원")
    @JsonIgnore
    private final Member member;

    @ApiModelProperty(value = "제품명")
    private final String itemName;

    @ApiModelProperty(value = "결제일시")
    private final LocalDateTime orderDate;
}
