package com.example.homework.members.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@Builder
public class MemberRequestDto {

	@ApiModelProperty(value = "이름")
	private final String name;
}
