package com.idolcollector.idolcollector.service;

import com.idolcollector.idolcollector.domain.member.Member;
import com.idolcollector.idolcollector.domain.member.MemberRepository;
import com.idolcollector.idolcollector.domain.post.Post;
import com.idolcollector.idolcollector.domain.post.PostRepository;
import com.idolcollector.idolcollector.domain.posttag.PostTagRepository;
import com.idolcollector.idolcollector.domain.tag.TagRepository;
import com.idolcollector.idolcollector.web.dto.comment.CommentResponseDto;
import com.idolcollector.idolcollector.web.dto.post.HomePostListResponseDto;
import com.idolcollector.idolcollector.web.dto.post.PostResponseDto;
import com.idolcollector.idolcollector.web.dto.post.PostSaveRequestDto;
import com.idolcollector.idolcollector.web.dto.post.PostUpdateRequestDto;
import com.idolcollector.idolcollector.web.dto.tag.TagResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class PostService {

    private final CommentService commentService;
    private final TagService tagService;

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final TagRepository tagRepository;
    private final PostTagRepository postTagRepository;

    @Transactional
    public Long create(PostSaveRequestDto form) {
        Member member = memberRepository.findById(form.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다. id=" + form.getMemberId()));

        // Post 저장.
        Post savedPost = postRepository.save(new Post(member, form.getTitle(), form.getContent(), form.getStoreFileName(), form.getOriFileName()));

        // 태그 저장.
        tagService.createPostTag(form.getTags(), savedPost);

        return savedPost.getId();
    }

    public PostResponseDto detail(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다. id=" + id));

        // 조회수 증가.
        post.addLike();

        // 댓글 넣기.
        List<CommentResponseDto> comments = commentService.findAllInPost(id);

        // 태그 넣기.
        List<TagResponseDto> tags = tagService.findAllinPost(id);

        return new PostResponseDto(post, comments, tags);
    }

    @Transactional
    public Long update(PostUpdateRequestDto form) {
        Post post = postRepository.findById(form.getPostId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다. id=" + form.getPostId()));

        post.update(form);

        tagRepository.deleteAllByPostId(form.getPostId());

        System.out.println("form.getTags() = " + form.getTags());
        if (form.getTags() != null) {
            tagService.createPostTag(form.getTags(), post);
        }

        return post.getId();
    }

    @Transactional
    public Long delete(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다. id=" + id));

        postRepository.delete(post);

        return id;
    }

    public HomePostListResponseDto postList() {

        // 1주일내 좋아요를 많이 받은 카드 순으로 출력.

        return new HomePostListResponseDto();
    }

    @Transactional
    public int like(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다. id=" + id));

        return post.addLike();
    }

    @Transactional
    public int view(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다. id=" + id));

        return post.addView();
    }

}
