package com.example.homework.members.service;

import com.example.homework.members.domain.entity.Member;
import com.example.homework.members.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

	@InjectMocks
	private MemberService memberService;

	@Mock
	private MemberRepository memberRepository;

	@Test
	@DisplayName("암호화 테스트")
	void encodePasswordTest() {
		// given
		String rawPassword = "1234";
		String salt = memberService.getSalt();

		// when
		String encodedPassword = memberService.encodePassword(salt,rawPassword);

		// then
		assertThat(encodedPassword).isNotEmpty();
		System.out.println("encodedPassword = " + encodedPassword);
	}

	@Test
	@DisplayName("로그인시 암호화된 비밀번호 테스트")
	void checkPassword() {
		// given
		String name = "tester";
		String rawPassword = "1234";
		String salt = memberService.getSalt();
		String encodedPassword = memberService.encodePassword(salt,rawPassword);

		Member member = Member.builder()
			.name(name)
			.password(encodedPassword)
			.salt(salt)
			.build();

		List members = new ArrayList<>();
		members.add(member);

		doReturn(members).when(memberRepository).findMember(any(String.class));

		// when
		List<Member> member1 = memberRepository.findMember(name);

		// then
		assertThat(member1.get(0).getPassword()).isEqualTo(encodedPassword);
	}
}