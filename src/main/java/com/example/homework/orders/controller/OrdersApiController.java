package com.example.homework.orders.controller;

import com.example.homework.members.domain.dto.MemberRegisterDto;
import com.example.homework.members.domain.dto.MemberResponseDto;
import com.example.homework.orders.domain.dto.OrderRequestDto;
import com.example.homework.orders.domain.dto.OrderResponseDto;
import com.example.homework.orders.domain.entity.Order;
import com.example.homework.orders.service.OrderService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value="/api/orders")
@RequiredArgsConstructor
public class OrdersApiController {

    private final OrderService orderService;

    @ApiOperation(value = "단일 회원의 주문 목록 조회", response = OrderResponseDto.class)
    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> findOrdersByMember(@RequestParam(value = "memberName") final String memberName) {

        return ResponseEntity.ok(orderService.findOrdersByMember(memberName));
    }

    @ApiOperation(value = "회원 주문", response = OrderResponseDto.class)
    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Order> createOrder(@RequestBody @Valid final OrderRequestDto orderRequestDto) {
        final Order order = orderService.createOrder(orderRequestDto);

        return ResponseEntity.ok(order);
    }

}
