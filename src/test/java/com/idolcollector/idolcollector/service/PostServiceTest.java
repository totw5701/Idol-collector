package com.idolcollector.idolcollector.service;

import com.idolcollector.idolcollector.domain.comment.Comment;
import com.idolcollector.idolcollector.domain.comment.CommentRepository;
import com.idolcollector.idolcollector.domain.like.LikesRepository;
import com.idolcollector.idolcollector.domain.member.Member;
import com.idolcollector.idolcollector.domain.member.MemberRepository;
import com.idolcollector.idolcollector.domain.member.MemberRole;
import com.idolcollector.idolcollector.domain.nestedcomment.NestedComment;
import com.idolcollector.idolcollector.domain.nestedcomment.NestedCommentRepository;
import com.idolcollector.idolcollector.domain.notice.Notice;
import com.idolcollector.idolcollector.domain.notice.NoticeRepository;
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
import com.idolcollector.idolcollector.web.dto.post.HomePostListResponseDto;
import com.idolcollector.idolcollector.web.dto.post.PostResponseDto;
import com.idolcollector.idolcollector.web.dto.post.PostSaveRequestDto;
import com.idolcollector.idolcollector.web.dto.post.PostUpdateRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class PostServiceTest {

    @Autowired
    PostService postService;

    @Autowired PostRepository postRepository;
    @Autowired MemberRepository memberRepository;
    @Autowired TagRepository tagRepository;
    @Autowired PostTagRepository postTagRepository;
    @Autowired CommentRepository commentRepository;
    @Autowired NestedCommentRepository nestedCommentRepository;
    @Autowired ScrapRepository scrapRepository;
    @Autowired LikesRepository likesRepository;
    @Autowired TrendingRepository trendingRepository;
    @Autowired NoticeRepository noticeRepository;
    @Autowired HttpSession httpSession;

    @BeforeEach
    void before() {
        Member member = new Member(MemberRole.USER, "nick", "email", "1111", "steve", "dsfsdfdsfdsf", LocalDateTime.now());
        memberRepository.save(member);
    }

    @Test
    void 페이지_리스트받아오기() {

        // Given
        Member member = memberRepository.findAll().get(0);

        Post post = new Post(member, "title", "conten", "ste", "ori");
        Post p1 = postRepository.save(post);

        for (int i = 0; i < 250; i++) {
            postRepository.save(new Post(member, "test" + i, "content", "ste", "ori"));
        }

        for (int i = 0; i < 30; i++) {
            Post save = postRepository.save(new Post(member, "viewd" + i, "content", "ste", "ori"));
            trendingRepository.save(new Trending(save, TrendingType.LIKE));
        }

        Post post2 = new Post(member, "title2", "conten2", "ste", "ori");
        Post p2 = postRepository.save(post2);


        Trending p1T1 = new Trending(p1, TrendingType.VIEW);
        Trending p1T2 = new Trending(p1, TrendingType.VIEW);
        Trending p1T3 = new Trending(p1, TrendingType.LIKE);
        Trending p1T4 = new Trending(p1, TrendingType.COMMENT);
        Trending p1T5 = new Trending(p1, TrendingType.COMMENT);
        Trending p1T6 = new Trending(p1, TrendingType.COMMENT);
        Trending p1T7 = new Trending(p1, TrendingType.VIEW);

        trendingRepository.save(p1T1);
        trendingRepository.save(p1T2);
        trendingRepository.save(p1T3);
        trendingRepository.save(p1T4);
        trendingRepository.save(p1T5);
        trendingRepository.save(p1T6);
        trendingRepository.save(p1T7);

        Trending p2T1 = new Trending(p2, TrendingType.VIEW);
        Trending p2T2 = new Trending(p2, TrendingType.VIEW);
        Trending p2T3 = new Trending(p2, TrendingType.VIEW);

        trendingRepository.save(p2T1);
        trendingRepository.save(p2T2);
        trendingRepository.save(p2T3);

        // When
        List<HomePostListResponseDto> list = postService.scorePostList(5);

        for (HomePostListResponseDto homePostListResponseDto : list) {
            System.out.println("homePostListResponseDto.getTitle() = " + homePostListResponseDto.getTitle());
        }
    }


    @Test
    void 생성() throws IOException {

        // Given
        Member member = memberRepository.findAll().get(0);

        httpSession.setAttribute("loginMember", member.getId());

        tagRepository.save(new Tag("예쁨"));
        tagRepository.save(new Tag("beautiful"));
        tagRepository.save(new Tag("김소담"));

        List<String> tags = new ArrayList<>();
        tags.add("이하늬");
        tags.add("예쁨");
        tags.add("BEautiful");

        // 사진 파일
        File img = new File("/Users/totheworld/Desktop/idol-collector/test.png");
        MultipartFile mf = new MockMultipartFile("image","test.png", "img",new FileInputStream(img));

        PostSaveRequestDto form = new PostSaveRequestDto(
                "예쁜이하늬",
                "이하늬 예쁘다 헤헤",
                mf,
                tags);

        // When
        Long postId = postService.create(form);

        // Then
        Post findPost = postRepository.findById(postId).get();
        List<Tag> findTags = tagRepository.findAll();
        List<PostTag> findPostTags = postTagRepository.findAll();

        assertThat(findPost.getId()).isEqualTo(postId);
        assertThat(findTags.size()).isEqualTo(4);
        assertThat(findPostTags.size()).isEqualTo(3);
    }

    @Test
    void 상세보기() {

        // Given
        Member member = memberRepository.findAll().get(0);
        httpSession.setAttribute("loginMember", member.getId());

        Post post = postRepository.save(new Post(member, "title", "content", "storeFilename", "oriFileName"));
        Comment comment = commentRepository.save(new Comment(member, post, "content"));
        Comment comment2 = commentRepository.save(new Comment(member, post, "content2"));

        NestedComment nComment = nestedCommentRepository.save(new NestedComment(member, comment, "test"));
        NestedComment nComment2 = nestedCommentRepository.save(new NestedComment(member, comment, "test"));

        // When
        PostResponseDto detail = postService.detail(post.getId());

        // Then
        assertThat(detail.getAuthorId()).isEqualTo(member.getId());
        assertThat(detail.getContent()).isEqualTo(post.getContent());
        assertThat(detail.getComments().get(0).getContent()).isEqualTo(comment.getContent());
        assertThat(detail.getComments().get(0).getNestedComments().get(0).getContent()).isEqualTo(nComment.getContent());
        assertThat(post.getViews()).isEqualTo(1);
    }

    @Test
    void 수정() throws IOException {

        // Given
        Member member = memberRepository.findAll().get(0);

        httpSession.setAttribute("loginMember", member.getId());


        List<String> tags = new ArrayList<>();
        tags.add("이하늬");
        tags.add("예쁨");
        tags.add("BEautiful");

        // 사진 파일
        File img = new File("/Users/totheworld/Desktop/idol-collector/test.png");
        MultipartFile mf = new MockMultipartFile("image","test.png", "img",new FileInputStream(img));

        PostSaveRequestDto saveForm = new PostSaveRequestDto(
                "예쁜이하늬",
                "이하늬 예쁘다 lol 헤헤",
                mf,
                tags);

        Long postId = postService.create(saveForm);

        // When
        List<String> updateTags = new ArrayList<>();
        updateTags.add("이하늬");
        updateTags.add("타짜2");

        PostUpdateRequestDto form = new PostUpdateRequestDto(postId,
                "2하2",
                "타짜에서 나왔구나.",
                updateTags);

        Long updateId = postService.update(form);


        // Then
        Post updated = postRepository.findById(updateId).get();

        assertThat(updated.getTitle()).isEqualTo("2하2");
        assertThat(updated.getContent()).isEqualTo("타짜에서 나왔구나.");
        assertThat(updated.getModifyDate()).isNotEqualTo(updated.getCreateDate());
        assertThat(updated.getOriFileName()).isEqualTo("test.png");
        assertThat(updated.getStoreFileName()).isNotNull();

        List<Tag> findTags = tagRepository.findAllByPostId(updated.getId());

        assertThat(findTags.size()).isEqualTo(2);
        assertThat(findTags.get(0).getName()).isEqualTo("이하늬");
    }

    @Test
    void 삭제() {

        // Given
        Member member = memberRepository.findAll().get(0);
        Post post = postRepository.save(new Post(member, "title", "content", "storeFilename", "oriFileName"));
        Comment comment = commentRepository.save(new Comment(member, post, "content"));
        Comment comment2 = commentRepository.save(new Comment(member, post, "content2"));

        NestedComment nComment = nestedCommentRepository.save(new NestedComment(member, comment, "test"));
        NestedComment nComment2 = nestedCommentRepository.save(new NestedComment(member, comment, "test"));

        // When
        postRepository.delete(post);

        // Then
        assertThatThrownBy(() -> {
            postRepository.findById(post.getId())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글 입니다."));
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 게시글 입니다.");

        assertThatThrownBy(() -> {
            commentRepository.findById(comment.getId())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글 입니다."));
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 댓글 입니다.");

        assertThatThrownBy(() -> {
            commentRepository.findById(comment2.getId())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글 입니다."));
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 댓글 입니다.");

        assertThatThrownBy(() -> {
            commentRepository.findById(nComment.getId())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 대댓글 입니다."));
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 대댓글 입니다.");

        assertThatThrownBy(() -> {
            commentRepository.findById(nComment2.getId())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 대댓글 입니다."));
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 대댓글 입니다.");

    }

    @Test
    void 좋아요() {

        // Given
        Member member = memberRepository.findAll().get(0);
        httpSession.setAttribute("loginMember", member.getId());

        Post post = postRepository.save(new Post(member, "title", "content", "storeFilename", "oriFileName"));

        // When
        postService.like(post.getId());

        // Then
        assertThat(post.getLikes()).isEqualTo(1);

        List<Notice> notices = noticeRepository.findAll();
        assertThat(notices.size()).isEqualTo(1);


    }

    @Test
    void 스크랩() {

        // Given
        Member member = new Member(MemberRole.USER, "nick", "email", "1111", "david", "dsfsdfdsfdsf", LocalDateTime.now());
        memberRepository.save(member);

        httpSession.setAttribute("loginMember", member.getId());

        Post post = postRepository.save(new Post(member, "title", "content", "storeFilename", "oriFileName"));

        // When
        Long scrap = postService.scrap(post.getId());

        // Then
        Scrap scrap1 = scrapRepository.findAll().get(0);

        assertThat(scrap1.getPost().getTitle()).isEqualTo("title");
        assertThat(scrap1.getMember().getNickName()).isEqualTo("nick");

        List<Notice> notices = noticeRepository.findAll();
        assertThat(notices.size()).isEqualTo(1);

    }



}