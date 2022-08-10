package com.example.homework.members.domain.dto;

import com.example.homework.members.domain.enums.Gender;
import com.example.homework.orders.domain.entity.Order;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
@Builder
public class MemberResponseDto {

	@ApiModelProperty(value = "회원 id")
	private final Long id;

	@ApiModelProperty(value = "이름")
	private final String name;

	@ApiModelProperty(value = "별명")
	private final String nickName;

	@ApiModelProperty(value = "전화번호")
	private final String phoneNum;

	@ApiModelProperty(value = "이메일")
	private final String email;

	@ApiModelProperty(value = "성별")
	private final Gender gender;

	@ApiModelProperty(value = "주문목록")
	private final List<Order> orders;

	@ApiModelProperty(value = "최근주문")
	private final Order recentOrder;
}
