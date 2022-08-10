package com.example.homework.members.domain.dto;

import com.example.homework.members.domain.enums.Gender;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.Pattern;

@RequiredArgsConstructor
@Getter
@Builder
public class MemberRegisterDto {

	@ApiModelProperty(value = "이름", notes = "한글, 영문 대소문자만 허용", required = true, example = "Name")
	@NotBlank
	@Size(min = 1, max = 20)
	@Pattern(regexp = "^[a-zA-Zㄱ-ㅎ가-힣]*$", message = "한글, 영문 대소문자만 허용")
	private final String name;

	@ApiModelProperty(value = "별명", notes = "영문 소문자만 허용", required = true, example = "nickname")
	@NotBlank
	@Size(min = 1, max = 30)
	@Pattern(regexp = "^[a-z]*$", message = "영문 소문자만 허용")
	private final String nickName;

	@ApiModelProperty(value = "비밀번호", notes = "영문 대문자, 영문 소문자, 특수 문자, 숫자 각 1개 이상씩 포함", required = true, example = "Aa!123456789")
	@NotBlank
	@Pattern(regexp = "(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{10,30}", message = "10~30자리의 영문 대문자, 영문 소문자, 특수 문자, 숫자 각 1개 이상씩 포함")
	private final String password;

	@ApiModelProperty(value = "전화번호", notes = "숫자만 허용", required = true, example = "01012345678")
	@NotBlank
	@Pattern(regexp = "[0-9]{0,20}", message = "20자리 이하의 숫자만 입력 허용")
	private final String phoneNum;

	@ApiModelProperty(value = "이메일", notes = "이메일 형식만 허용", required = true, example = "tttck88@gmail.com")
	@NotBlank
	@Size(min = 1, max = 100)
	@Email
	private final String email;

	@ApiModelProperty(value = "성별", notes = "한글, 영문 대소문자만 허용", required = true, example = "MALE")
	@Enumerated(EnumType.STRING)
	@Column(length = 6)
	private final Gender gender;
}
