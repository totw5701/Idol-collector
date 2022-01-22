package com.idolcollector.idolcollector.web.controller;

import com.idolcollector.idolcollector.service.BundleService;
import com.idolcollector.idolcollector.service.MemberService;
import com.idolcollector.idolcollector.service.PostService;
import com.idolcollector.idolcollector.service.ResponseService;
import com.idolcollector.idolcollector.web.dto.member.MemberDetailDto;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class PageApiControllerTest {

    @InjectMocks PageApiController pageApiController;

    @Mock private PostService postService;
    @Mock private MemberService memberService;
    @Mock private BundleService bundleService;

    @Mock private HttpSession httpSession;

    @Spy private ResponseService responseService;

    private MockMvc mockMvc;

    @BeforeEach
    void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(pageApiController).build();
    }

    @Test
    void 홈페이지_접근() throws Exception {
        // given
        List<HomePostListResponseDto> cards = new ArrayList<>();
        HomePostListResponseDto dto1 = new HomePostListResponseDto(generatePost());
        HomePostListResponseDto dto2 = new HomePostListResponseDto(generatePost());
        cards.add(dto1);
        cards.add(dto2);

        doReturn(cards).when(postService).scorePostList(any());
        doReturn(new MemberDetailDto(generateMember())).when(memberService).findById(any());

        // When
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/home")
        );

        // Then
        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
        String json = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        assertThat(json).contains("성공했습니다.");
        assertThat(json).contains("testMember", "test card");
        assertThat(json).doesNotContain("steve");
    }

    @Test
    void 태그_검색() throws Exception {
        // given
        List<HomePostListResponseDto> cards = new ArrayList<>();
        HomePostListResponseDto dto1 = new HomePostListResponseDto(generatePost());
        HomePostListResponseDto dto2 = new HomePostListResponseDto(generatePost());
        cards.add(dto1);
        cards.add(dto2);

        doReturn(cards).when(postService).scorePostListSearch(any(Integer.class), any(List.class));
        doReturn(new MemberDetailDto(generateMember())).when(memberService).findById(any());

        // When
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/search")
                        .param("keywords", "tag")
                        .param("keywords", "tag2")
        );

        // Then
        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
        String json = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        assertThat(json).contains("성공했습니다.");
        assertThat(json).contains("testMember", "test card");
        assertThat(json).doesNotContain("steve");
    }
}