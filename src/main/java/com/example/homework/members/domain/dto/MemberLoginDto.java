package com.example.homework.members.domain.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor
@Getter
@Builder
public class MemberLoginDto {

	@ApiModelProperty(value = "이름", required = true, example = "Name")
	@NotBlank
	@Size(min = 1, max = 20)
	private final String name;

	@ApiModelProperty(value = "비밀번호", required = true, example = "Aa!123456789")
	@NotBlank
	private final String password;
}
