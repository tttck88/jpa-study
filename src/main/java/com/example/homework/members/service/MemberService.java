package com.example.homework.members.service;

import com.example.homework.members.domain.dto.MemberLoginDto;
import com.example.homework.members.domain.dto.MemberRegisterDto;
import com.example.homework.members.domain.dto.MemberResponseDto;
import com.example.homework.members.domain.dto.Paging;
import com.example.homework.members.domain.entity.Member;
import com.example.homework.members.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;

	@Transactional
	public MemberResponseDto registerMember(final MemberRegisterDto memberRegisterDto) {
		validateDuplicateRegister(memberRegisterDto);

		final String salt = getSalt();

		final Member member = Member.builder()
			.name(memberRegisterDto.getName())
			.password(encodePassword(salt, memberRegisterDto.getPassword()))
			.nickName(memberRegisterDto.getNickName())
			.email(memberRegisterDto.getEmail())
			.phoneNum(memberRegisterDto.getPhoneNum())
			.gender(memberRegisterDto.getGender())
			.salt(salt)
			.build();

		memberRepository.save(member);

		final MemberResponseDto memberResponseDto = MemberResponseDto.builder()
				.id(member.getId())
				.name(member.getName())
				.email(member.getEmail())
				.build();

		return memberResponseDto;
	}

	public void validateDuplicateRegister(final MemberRegisterDto memberRegisterDto) {
		final List<Member> memberResponseDto = memberRepository.findMember(memberRegisterDto.getName());
		if(!memberResponseDto.isEmpty()) {
			throw new IllegalStateException("이미 존재하는 회원입니다.");
		}
	}

	public Member loginMember(final MemberLoginDto memberLoginDto) {
		final Member member = checkUser(memberLoginDto);
		checkPassword(memberLoginDto, member);

		return member;
	}

	public Member checkUser(final MemberLoginDto memberLoginDto) {
		final List<Member> members = memberRepository.findMember(memberLoginDto.getName());

		if(members.isEmpty()) {
			throw new IllegalStateException("이름을 확인해주시기 바랍니다.");
		}

		return members.get(0);
	}

	public void checkPassword(final MemberLoginDto memberLoginDto, final Member member) {
		final String salt = member.getSalt();
		final String rawPassword = memberLoginDto.getPassword();

		if(!member.getPassword().equals(encodePassword(salt, rawPassword))) {
			throw new IllegalStateException("이름, 패스워드를 확인해주시기 바랍니다.");
		};
	}

	public String getSalt() {
		try {
			final SecureRandom random = SecureRandom.getInstance("SHA1PRNG");

			byte[] bytes = new byte[16];
			random.nextBytes(bytes);
			return new String(Base64.getEncoder().encode(bytes));
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("회원가입에 실패하였습니다.");
		}
	}

	public String encodePassword(final String salt, final String rawPassword) {
		try {
			final String saltAndRawPassword = salt + rawPassword;

			// salt + raw 패스워드 암호화
			final MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(saltAndRawPassword.getBytes());

			final String encodedPassword = String.format("%064x", new BigInteger(1, md.digest()));

			return encodedPassword;
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new RuntimeException("회원가입에 실패하였습니다.");
		}
	}

	public MemberResponseDto findMemberByName(final String name) {
		final List<Member> members = memberRepository.findMember(name);

		if (members.isEmpty()) {
			throw new IllegalStateException("존재하지 않는 계정입니다.");
		} else {
			final Member member = members.get(0);

			final MemberResponseDto memberResponseDto = MemberResponseDto.builder()
					.id(member.getId())
					.name(member.getName())
					.nickName(member.getNickName())
					.phoneNum(member.getPhoneNum())
					.email(member.getEmail())
					.orders(member.getOrders())
					.build();

			return memberResponseDto;
		}
	}

	public List<MemberResponseDto> findMembers(final String name, final String email, final Paging paging) {
		final Member member = Member.builder()
				.name(name)
				.email(email)
				.build();

		final List<Member> members = memberRepository.findMembers(member, paging);

		final List<MemberResponseDto> memberResponseDtoList = members.stream().map(m -> MemberResponseDto.builder()
				.id(m.getId())
				.name(m.getName())
				.nickName(m.getNickName())
				.phoneNum(m.getPhoneNum())
				.email(m.getEmail())
				.gender(m.getGender())
				.recentOrder(m.getRecentOrder())
				.build())
				.collect(Collectors.toList());

		return memberResponseDtoList;
	}
}
