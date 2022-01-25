package com.idolcollector.idolcollector.service;

import com.idolcollector.idolcollector.EntityMaker;
import com.idolcollector.idolcollector.advice.exception.CAccessDeniedException;
import com.idolcollector.idolcollector.advice.exception.CPostNotFoundException;
import com.idolcollector.idolcollector.domain.comment.Comment;
import com.idolcollector.idolcollector.domain.comment.CommentRepository;
import com.idolcollector.idolcollector.domain.like.LikeType;
import com.idolcollector.idolcollector.domain.like.Likes;
import com.idolcollector.idolcollector.domain.like.LikesRepository;
import com.idolcollector.idolcollector.domain.member.Member;
import com.idolcollector.idolcollector.domain.member.MemberRepository;
import com.idolcollector.idolcollector.domain.member.MemberRole;
import com.idolcollector.idolcollector.domain.nestedcomment.NestedComment;
import com.idolcollector.idolcollector.domain.nestedcomment.NestedCommentRepository;
import com.idolcollector.idolcollector.domain.notice.Notice;
import com.idolcollector.idolcollector.domain.notice.NoticeRepository;
import com.idolcollector.idolcollector.domain.notice.NoticeType;
import com.idolcollector.idolcollector.domain.post.Post;
import com.idolcollector.idolcollector.domain.post.PostRepository;
import com.idolcollector.idolcollector.domain.posttag.PostTag;
import com.idolcollector.idolcollector.domain.posttag.PostTagRepository;
import com.idolcollector.idolcollector.domain.scrap.Scrap;
import com.idolcollector.idolcollector.domain.scrap.ScrapRepository;
import com.idolcollector.idolcollector.domain.tag.Tag;
import com.idolcollector.idolcollector.domain.tag.TagRepository;
import com.idolcollector.idolcollector.domain.trending.Trending;
import com.idolcollector.idolcollector.domain.trending.TrendingRepository;
import com.idolcollector.idolcollector.domain.trending.TrendingType;
import com.idolcollector.idolcollector.file.FileStore;
import com.idolcollector.idolcollector.web.dto.comment.CommentResponseDto;
import com.idolcollector.idolcollector.web.dto.post.HomePostListResponseDto;
import com.idolcollector.idolcollector.web.dto.post.PostResponseDto;
import com.idolcollector.idolcollector.web.dto.post.PostSaveRequestDto;
import com.idolcollector.idolcollector.web.dto.post.PostUpdateRequestDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.idolcollector.idolcollector.EntityMaker.*;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.InstanceOfAssertFactories.OPTIONAL;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MockPostServiceTest {

    @InjectMocks
    PostService postService;

    @Mock TagService tagService;

    @Mock PostRepository postRepository;
    @Mock MemberRepository memberRepository;
    @Mock TagRepository tagRepository;
    @Mock ScrapRepository scrapRepository;
    @Mock LikesRepository likesRepository;
    @Mock TrendingRepository trendingRepository;
    @Mock NoticeRepository noticeRepository;

    @Mock FileStore fileStore;
    @Mock HttpSession httpSession;

    @Test
    void 생성() throws IOException {
        // Given
        Post post = generatePost();
        List<String> tags = new ArrayList<>();
        tags.add("tag1");
        tags.add("tag2");

        Optional<Member> op = Optional.of(generateMember());

        doReturn(1L).when(httpSession).getAttribute(any());
        doReturn(op).when(memberRepository).findById(1L);
        doReturn(generateUploadFile()).when(fileStore).storeFile(any(MultipartFile.class));
        doReturn(post).when(postRepository).save(any(Post.class));
        doNothing().when(tagService).createPostTag(tags ,post);

        // When
        File img = new File("./src/test/resources/test.png");
        MockMultipartFile mf = new MockMultipartFile("image","test.png", "img", new FileInputStream(img));
        PostSaveRequestDto form = new PostSaveRequestDto("제목", "카드 내용", mf, tags);

        postService.create(form);

        //verify
        verify(memberRepository, times(1)).findById(any());
        verify(fileStore, times(1)).storeFile(any());
        verify(postRepository, times(1)).save(any());
        verify(tagService, times(1)).createPostTag(any(), any());
    }

    @Test
    void 상세보기() {
        // Given
        Post post = generatePost();
        Member sessionMember = generateMember();

        // When
        doReturn(1L).when(httpSession).getAttribute(any());
        doReturn(Optional.of(sessionMember)).when(memberRepository).findById(1L);
        doReturn(Optional.of(post)).when(postRepository).findById(any());
        doReturn(Optional.empty()).when(likesRepository).findLikeByMemberIdPostId(any(), any(), any());
        doReturn(Optional.of(new Scrap(sessionMember, post))).when(scrapRepository).findScrapByMemberIdPostId(any(), any());

        PostResponseDto detail = postService.detail(post.getId());

        // Then
        assertThat(detail.getAuthorNickName()).isEqualTo("testMember");
        assertThat(detail.getTitle()).isEqualTo("test card");
        assertThat(detail.getStoreFileName()).isEqualTo("ste");
        assertThat(detail.getComments().size()).isEqualTo(4);
        assertThat(detail.getComments().get(0).getContent()).contains("comment");
        assertThat(detail.getTags().size()).isEqualTo(2);
        assertThat(detail.getTags().get(0).getName()).contains("tag");
        assertThat(detail.isDidScrap()).isTrue();
    }

    @Test
    void 수정() throws IOException {
        // Given
        Post post = generatePost();
        Member sessionMember = generateMember();

        doReturn(1L).when(httpSession).getAttribute(any());
        doReturn(Optional.of(post)).when(postRepository).findById(any());
        doReturn(Optional.of(post.getMember())).when(memberRepository).findById(1L);

        doNothing().when(tagRepository).deleteAllByPostId(any());
        List<PostTag> postTags = post.getPostTags();
        while (postTags.size() > 0) postTags.remove(0);

        doNothing().when(tagService).createPostTag(any(), any());
        for (int i = 0; i < 3; i++) postTags.add(new PostTag(post, new Tag("updated tag"+i)));

        // When
        PostUpdateRequestDto form = new PostUpdateRequestDto(post.getId(), "updated title", "updated description", new ArrayList<String>());
        postService.update(form);

        // Then
        assertThat(post.getTitle()).isEqualTo("updated title");
        assertThat(post.getContent()).isEqualTo("updated description");
        assertThat(post.getPostTags().size()).isEqualTo(3);
    }

    @Test
    void 수정_실패_작성자_아님() throws IOException {
        // Given
        Post post = generatePost();
        Member sessionMember = generateMember();
        post.getMember().test(1L); sessionMember.test(2L);

        doReturn(1L).when(httpSession).getAttribute(any());
        doReturn(Optional.of(post)).when(postRepository).findById(any());
        doReturn(Optional.of(sessionMember)).when(memberRepository).findById(1L);

        // Then
        PostUpdateRequestDto form = new PostUpdateRequestDto(post.getId(), "updated title", "updated description", new ArrayList<String>());
        assertThatThrownBy(() -> {
            postService.update(form);
        }).isInstanceOf(CAccessDeniedException.class);

        // Verify
        verify(tagRepository, times(0)).deleteAllByPostId(any());
        verify(tagService, times(0)).createPostTag(any(), any());
    }

    @Test
    void 삭제() {
        // Given
        Post post = generatePost();
        Member sessionMember = generateMember();

        doReturn(1L).when(httpSession).getAttribute(any());
        doReturn(Optional.of(post)).when(postRepository).findById(any());
        doReturn(Optional.of(post.getMember())).when(memberRepository).findById(1L);
        doNothing().when(postRepository).delete(any(Post.class));

        // When
        postService.delete(1L);

        // Verify
        verify(memberRepository, times(1)).findById(any());
        verify(postRepository, times(1)).findById(any());
        verify(postRepository, times(1)).delete(any());
    }

    @Test
    void 삭제_실패_작성자_아님() {
        // Given
        Post post = generatePost();
        Member sessionMember = generateMember();
        post.getMember().test(1L); sessionMember.test(2L);

        doReturn(1L).when(httpSession).getAttribute(any());
        doReturn(Optional.of(post)).when(postRepository).findById(1L);
        doReturn(Optional.of(sessionMember)).when(memberRepository).findById(1L);

        // Then
        assertThatThrownBy(() -> {
            postService.delete(1L);
        }).isInstanceOf(CAccessDeniedException.class);

        // Verify
        verify(memberRepository, times(1)).findById(any());
        verify(postRepository, times(1)).findById(any());
        verify(postRepository, times(0)).delete(any(Post.class));
    }


    @Test
    void 좋아요_성공() {
        // Given
        Post post = generatePost();
        Member sessionMember = generateMember();

        doReturn(1L).when(httpSession).getAttribute(any());
        doReturn(Optional.of(post)).when(postRepository).findById(any());
        doReturn(Optional.of(post.getMember())).when(memberRepository).findById(1L);
        doReturn(Optional.empty()).when(likesRepository).findLikeByMemberIdPostId(post.getId(), sessionMember.getId(), LikeType.POST);
        doReturn(new Likes()).when(likesRepository).save(any(Likes.class));
        doReturn(new Notice()).when(noticeRepository).save(any(Notice.class));
        doReturn(new Trending()).when(trendingRepository).save(any(Trending.class));

        // When
        int likes = postService.like(post.getId());

        // Then
        assertThat(likes).isEqualTo(1);

        // Verify
        verify(likesRepository, times(1)).save(any());
        verify(noticeRepository, times(1)).save(any());
        verify(trendingRepository, times(1)).save(any());
    }

    @Test
    void 좋아요_실패_중복() {
        // Given
        Post post = generatePost();
        Member sessionMember = generateMember();

        doReturn(1L).when(httpSession).getAttribute(any());
        doReturn(Optional.of(post)).when(postRepository).findById(any());
        doReturn(Optional.of(post.getMember())).when(memberRepository).findById(1L);
        doReturn(Optional.of(new Likes())).when(likesRepository).findLikeByMemberIdPostId(post.getId(), sessionMember.getId(), LikeType.POST);

        // Then
        assertThatThrownBy(() -> {
            postService.like(post.getId());
        }).isInstanceOf(IllegalArgumentException.class);

        // Verify
        verify(likesRepository, times(0)).save(any());
        verify(noticeRepository, times(0)).save(any());
        verify(trendingRepository, times(0)).save(any());
    }

    @Test
    void 스크랩() {
        // Given
        Post post = generatePost();
        Member sessionMember = generateMember();

        doReturn(1L).when(httpSession).getAttribute(any());
        doReturn(Optional.of(post)).when(postRepository).findById(any());
        doReturn(Optional.of(sessionMember)).when(memberRepository).findById(1L);
        doReturn(new Scrap()).when(scrapRepository).save(any());

        doReturn(new Notice()).when(noticeRepository).save(any());
        doReturn(new Trending()).when(trendingRepository).save(any());

        // When
        Long scrap = postService.scrap(post.getId());

        // Verify
        verify(postRepository, times(1)).findById(any());
        verify(memberRepository, times(1)).findById(any());
        verify(scrapRepository, times(1)).save(any());
        verify(noticeRepository, times(1)).save(any());
        verify(trendingRepository, times(1)).save(any());
    }
}