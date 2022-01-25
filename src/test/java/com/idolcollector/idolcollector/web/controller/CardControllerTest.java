package com.idolcollector.idolcollector.web.controller;

import com.idolcollector.idolcollector.domain.post.Post;
import com.idolcollector.idolcollector.file.FileStore;
import com.idolcollector.idolcollector.service.MemberService;
import com.idolcollector.idolcollector.service.PostService;
import com.idolcollector.idolcollector.service.ResponseService;
import com.idolcollector.idolcollector.web.controller.api.CardController;
import com.idolcollector.idolcollector.web.dto.member.MemberDetailDto;
import com.idolcollector.idolcollector.web.dto.post.PostResponseDto;
import com.idolcollector.idolcollector.web.dto.post.PostSaveRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.servlet.http.HttpSession;

import java.io.File;
import java.io.FileInputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import static com.idolcollector.idolcollector.EntityMaker.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
class CardControllerTest {

    @InjectMocks
    CardController cardController;

    @Mock private PostService postService;
    @Mock private FileStore fileStore;
    @Mock private MemberService memberService;
    @Mock private HttpSession httpSession;

    @Spy private ResponseService responseService;

    private MockMvc mockMvc;

    @BeforeEach
    void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(cardController).build();
    }

    @Test
    void 카드_생성() throws Exception {
        // given
        ArrayList<String> tags = new ArrayList<>();
        tags.add("tag1");
        tags.add("tag2");
        tags.add("tag3");

        File img = new File("./src/test/resources/test.png");
        MockMultipartFile mf = new MockMultipartFile("image","test.png", "img", new FileInputStream(img));

        PostSaveRequestDto form = new PostSaveRequestDto("title", "content", mf, tags);
        doReturn(1L).when(postService).create(any(PostSaveRequestDto.class));

        // when
        final ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.multipart("/api/card/create")
                        .file("attachFile", form.getAttachFile().getBytes())
                        .param("title", form.getTitle())
                        .param("content", form.getContent())
                        .param("tags", "tag1")
                        .param("tags", "tag2")
        );

        // then
        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
        String json = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        assertThat(json).contains("성공했습니다.");
    }

    @Test
    void 카드_디테일() throws Exception {
        // Given
        Post post = generatePost();
        doReturn(new PostResponseDto(post)).when(postService).detail(any(Long.class));
        doReturn(new MemberDetailDto(post.getMember())).when(memberService).findById(any());

        // When
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/card/1")
        );

        // Then
        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
        String json = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        assertThat(json).contains("성공했습니다.");
        assertThat(json).contains("test card");
    }

    @Test
    void 카드_스크랩() throws Exception {
        // Given
        doReturn(1L).when(postService).scrap(any(Long.class));

        // When
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.put("/api/card/scrap/1")
        );

        // Then
        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
        String json = mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8);
        assertThat(json).contains("성공했습니다.");
        assertThat(json).contains("1");
    }

    @Test
    void 이미지_불러오기() throws Exception {
        // Given
        doReturn("./src/test/resources/imgs/test.png").when(fileStore).getFullPath(any(String.class));

        // When
        ResultActions resultActions = mockMvc.perform(
                MockMvcRequestBuilders.get("/api/card/image/text.png")
        );

        // Then
        MvcResult mvcResult = resultActions.andExpect(status().isOk()).andReturn();
        int contentLength = mvcResult.getResponse().getContentLength();
        assertThat(contentLength).isEqualTo(55603);
    }
}