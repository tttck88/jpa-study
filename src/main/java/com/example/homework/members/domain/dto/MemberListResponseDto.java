package com.example.homework.members.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
@Getter
@Builder
public class MemberListResponseDto {
	private final List<MemberResponseDto> memberList;
}
