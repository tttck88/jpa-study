package com.example.homework.members.controller;

import com.example.homework.members.domain.dto.MemberListResponseDto;
import com.example.homework.members.domain.dto.MemberLoginDto;
import com.example.homework.members.domain.dto.MemberRegisterDto;
import com.example.homework.members.domain.dto.MemberResponseDto;
import com.example.homework.members.domain.enums.Gender;
import com.example.homework.members.repository.MemberRepository;
import com.example.homework.orders.domain.dto.OrderRequestDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;

import javax.servlet.http.HttpSession;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class MembersApiControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void init() throws Exception {
        // 테스트 회원 등록
        MemberRegisterDto memberRegisterDto = MemberRegisterDto.builder()
            .name("testerOne")
            .nickName("nickname")
            .password("Aa!123456789")
            .phoneNum("01088840249")
            .email("tttck88@gmail.com")
            .gender(Gender.MALE)
            .build();

        // when
        mockMvc.perform(post("/api/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(memberRegisterDto)));
    }

    @Test
    @DisplayName("회원가입 테스트 성공")
    void registerMemberTest() throws Exception {

        // given
        MemberRegisterDto memberRegisterDto = MemberRegisterDto.builder()
                .name("testerTwo")
                .nickName("nickname")
                .password("Aa!123456789")
                .phoneNum("01088840249")
                .email("tttck88@gmail.com")
                .gender(Gender.MALE)
                .build();

        // when
        ResultActions resultActions = mockMvc.perform(post("/api/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(memberRegisterDto)));

        // then
        resultActions.andExpect(status().isOk())
            .andExpect(jsonPath("name", memberRegisterDto.getName()).exists())
            .andDo(print());
    }
    @Test
    @DisplayName("회원가입 테스트 실패 - 중복회원")
    void registerDuplicateMemberTest() throws Exception {

        // given
        MemberRegisterDto memberRegisterDto = MemberRegisterDto.builder()
            .name("testerOne")
            .nickName("nickname")
            .password("Aa!123456789")
            .phoneNum("01088840249")
            .email("tttck88@gmail.com")
            .gender(Gender.MALE)
            .build();

        // when
        ResultActions resultActions = mockMvc.perform(post("/api/members")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(memberRegisterDto)));

        // then
        resultActions.andExpect(status().isBadRequest())
            .andExpect(result -> assertThat(result.getResolvedException() instanceof IllegalStateException))
            .andDo(print());
    }

    @Test
    @DisplayName("회원 로그인 테스트 성공")
    void loginMemberTest() throws Exception {

        // given
        MemberLoginDto memberLoginDto = MemberLoginDto.builder()
            .name("testerOne")
            .password("Aa!123456789")
            .build();

        // when
        ResultActions resultActions = mockMvc.perform(post("/api/members/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(memberLoginDto)));

        // then
        resultActions.andExpect(status().isOk())
            .andDo(print());
    }

    @Test
    @DisplayName("회원 로그인 테스트 실패 - 패스워드 틀림")
    void loginMemberFailTest() throws Exception {

        // given
        MemberLoginDto memberLoginDto = MemberLoginDto.builder()
            .name("testerOne")
            .password("틀린패스워드")
            .build();

        // when
        ResultActions resultActions = mockMvc.perform(post("/api/members/login")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(memberLoginDto)));

        // then
        resultActions.andExpect(status().isBadRequest())
            .andExpect(result -> assertThat(result.getResolvedException() instanceof IllegalStateException))
            .andDo(print());
    }
    
    @Test
    @DisplayName("회원 찾기 테스트 성공")
    void findMemberTest() throws Exception {

        // given
        String name = "testerOne";

        // when
        ResultActions resultActions = mockMvc.perform(get("/api/members/"+name))
                .andExpect(status().isOk())
                .andDo(print());

        // then
        resultActions.andExpect(status().isOk())
            .andExpect(jsonPath("name", name).exists())
            .andDo(print());
    }

    @Test
    @DisplayName("회원 찾기 테스트 실패 - 존재하지 않는 회원")
    void findNoMemberTest() throws Exception {

        // given
        String name = "noMember";

        // when
        ResultActions resultActions = mockMvc.perform(get("/api/members/"+name));

        // then
        resultActions.andExpect(status().isBadRequest())
            .andExpect(result -> assertThat(result.getResolvedException() instanceof IllegalStateException))
            .andDo(print());
    }

    @Test
    @DisplayName("전체 회원 조회 성공")
    void findMembersTest() throws Exception {

        // given
        String name = "testerOne";
        String email = "tttck88@gmail.com";

        // when
        ResultActions resultActions = mockMvc.perform(get("/api/members/").param("name", name).param("email",email));

        // then
        resultActions.andExpect(status().isOk()).andDo(print());
    }
}