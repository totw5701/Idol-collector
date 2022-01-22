package com.idolcollector.idolcollector.web.controller;

import com.idolcollector.idolcollector.service.BundleService;
import com.idolcollector.idolcollector.service.MemberService;
import com.idolcollector.idolcollector.service.PostService;
import com.idolcollector.idolcollector.service.ResponseService;
import com.idolcollector.idolcollector.web.controller.api.MemberController;
import com.idolcollector.idolcollector.web.dto.bundle.BundleResponseDto;
import com.idolcollector.idolcollector.web.dto.member.MemberDetailDto;
import com.idolcollector.idolcollector.web.dto.notice.NoticeResponseDto;
import com.idolcollector.idolcollector.web.dto.post.HomePostListResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.servlet.http.HttpSession;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static com.idolcollector.idolcollector.web.controller.EntityMaker.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class MemberControllerTest {

    @InjectMocks
    private MemberController memberController;

    @Mock private MemberService memberService;
    @Mock private PostService postService;
    @Mock private BundleService bundleService;

    @Mock private HttpSession httpSession;

    @Spy private ResponseService responseService;

    private MockMvc mockMvc;

    @BeforeEach
    void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(memberController).build();
    }


    @Test
    void 알림_확인() throws Exception {


        // Given
        ArrayList<NoticeResponseDto> notices = new ArrayList<>();
        notices.add(new NoticeResponseDto(generateNotice()));
        notices.add(new NoticeResponseDto(generateNotice()));

        doReturn(notices).when(memberService).noticeConfirm();

        // When
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/member/notice")
        );

        // Then
        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
        String json = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        assertThat(json).contains("성공했습니다.");
        assertThat(json).contains("testMember", "test card");

    }

    @Test
    void 마이페이지() throws Exception {
        // Given
        List<HomePostListResponseDto> cards = new ArrayList<>();
        HomePostListResponseDto dto1 = new HomePostListResponseDto(generatePost());
        HomePostListResponseDto dto2 = new HomePostListResponseDto(generatePost());
        cards.add(dto1);
        cards.add(dto2);

        List<BundleResponseDto> bundles = new ArrayList<>();
        BundleResponseDto bundle = new BundleResponseDto(generateBundle());
        bundles.add(bundle);

        doReturn(new MemberDetailDto(generateMember())).when(memberService).findById(any());
        doReturn(cards).when(postService).memberPostList(any(), any());
        doReturn(bundles).when(bundleService).findAllInMember(any());

        // When
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/member/mypage")
        );

        // Then
        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
        String json = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        assertThat(json).contains("성공했습니다.");
        assertThat(json).contains("testMember", "test card", "test bundle", "steve");
    }

    @Test
    void 회원정보() throws Exception {
        // Given
        List<HomePostListResponseDto> cards = new ArrayList<>();
        HomePostListResponseDto dto1 = new HomePostListResponseDto(generatePost());
        HomePostListResponseDto dto2 = new HomePostListResponseDto(generatePost());
        cards.add(dto1);
        cards.add(dto2);

        List<BundleResponseDto> bundles = new ArrayList<>();
        BundleResponseDto bundle = new BundleResponseDto(generateBundle());
        bundles.add(bundle);

        doReturn(new MemberDetailDto(generateMember())).when(memberService).findById(any());
        doReturn(cards).when(postService).memberPostList(any(), any());
        doReturn(bundles).when(bundleService).findAllInMember(any());

        // When
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/member/member/1")
        );

        // Then
        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
        String json = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        assertThat(json).contains("성공했습니다.");
        assertThat(json).contains("testMember", "test card", "test bundle");
        assertThat(json).doesNotContain("steve", "notice");
    }
}