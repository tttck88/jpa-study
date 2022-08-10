package com.example.homework.members.controller;

import com.example.homework.members.domain.dto.*;
import com.example.homework.members.domain.entity.Member;
import com.example.homework.members.service.MemberService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value="/api/members")
@RequiredArgsConstructor
public class MembersApiController {

	private final MemberService memberService;
	private final HttpSession session;

	@ApiOperation(value = "회원 가입", response = MemberResponseDto.class)
	@PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MemberResponseDto> registerMember(@RequestBody @Valid final MemberRegisterDto memberRegisterDto) {
			final MemberResponseDto memberResponseDto = memberService.registerMember(memberRegisterDto);

			return ResponseEntity.ok(memberResponseDto);
	}

	@ApiOperation(value = "회원 로그인(인증)")
	@PostMapping("/login")
	public ResponseEntity<Void> loginMember(@RequestBody final MemberLoginDto memberLoginDto) {
		final Member member = memberService.loginMember(memberLoginDto);

		// 세션 추가
		session.setMaxInactiveInterval(60 * 60);
		session.setAttribute("memberId", member.getId());
		session.setAttribute("memberName", member.getName());
		session.setAttribute("memberNickName", member.getNickName());

		return ResponseEntity.ok().build();
	}

	@ApiOperation(value = "회원 로그아웃")
	@PostMapping("/logout")
	public ResponseEntity<Void> logoutMember() {
		// 세션 제거
		session.invalidate();

		return ResponseEntity.ok().build();
	}

	@ApiOperation(value = "단일 회원 상세 정보 조회", response = MemberResponseDto.class)
	@GetMapping("/{name}")
	public ResponseEntity<MemberResponseDto> findMember(@PathVariable("name") final String name) {
		final MemberResponseDto memberResponseDto = memberService.findMemberByName(name);

		return ResponseEntity.ok(memberResponseDto);
	}

	@ApiOperation(value = "여러 회원의 목록 조회", response = MemberResponseDto.class)
	@GetMapping
	public ResponseEntity<MemberListResponseDto> findMembers(
			@RequestParam(value = "name", required = false) final String name
			, @RequestParam(value = "email", required = false) final String email
			, @RequestParam(value = "offset", required = false, defaultValue = "0") final Integer offset
			, @RequestParam(value = "limit", required = false, defaultValue = "10") final Integer limit)
	{
		final Paging paging = Paging.builder()
				.offset(offset)
				.limit(limit)
				.build();

		final MemberListResponseDto memberListResponseDto = MemberListResponseDto.builder()
			.memberList(memberService.findMembers(name, email, paging))
			.build();

		return ResponseEntity.ok(memberListResponseDto);
	}

}
