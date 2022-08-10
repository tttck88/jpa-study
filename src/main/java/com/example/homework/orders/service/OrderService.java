package com.example.homework.orders.service;

import com.example.homework.members.domain.dto.MemberResponseDto;
import com.example.homework.members.domain.entity.Member;
import com.example.homework.members.service.MemberService;
import com.example.homework.orders.domain.dto.OrderRequestDto;
import com.example.homework.orders.domain.dto.OrderResponseDto;
import com.example.homework.orders.domain.entity.Order;
import com.example.homework.orders.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

	private final MemberService memberService;
	private final OrderRepository orderRepository;

    @Transactional
    public Order createOrder(final OrderRequestDto orderRequestDto) {

	    final MemberResponseDto memberResponseDto = memberService.findMemberByName(orderRequestDto.getMemberName());
	    final Member member = Member.builder()
                .id(memberResponseDto.getId())
                .build();

	    final Order order = Order.builder()
                .member(member)
				.orderNum(createOrderNum())
                .itemName(orderRequestDto.getItemName())
                .orderDate(LocalDateTime.now())
                .build();

        orderRepository.save(order);

        return order;
    }

    public String createOrderNum() {
		UUID uuid = UUID.randomUUID();
		long l = ByteBuffer.wrap(uuid.toString().getBytes()).getLong();

		return Long.toString(l, Character.MAX_RADIX);
	}

	public List<OrderResponseDto> findOrdersByMember(final String name) {
		final List<Order> orders = orderRepository.findOrdersByMember(name);

		final List<OrderResponseDto> orderResponseDtoList = orders.stream().map(o -> OrderResponseDto.builder()
                .id(o.getId())
                .member(o.getMember())
                .itemName(o.getItemName())
                .orderDate(o.getOrderDate())
                .build())
                .collect(Collectors.toList());

        return orderResponseDtoList;
    }
}
