package com.idolcollector.idolcollector.service;

import com.idolcollector.idolcollector.advice.exception.CAccessDeniedException;
import com.idolcollector.idolcollector.advice.exception.CPostNotFoundException;
import com.idolcollector.idolcollector.domain.comment.Comment;
import com.idolcollector.idolcollector.domain.like.LikeType;
import com.idolcollector.idolcollector.domain.like.Likes;
import com.idolcollector.idolcollector.domain.like.LikesRepository;
import com.idolcollector.idolcollector.domain.member.Member;
import com.idolcollector.idolcollector.domain.member.MemberRepository;
import com.idolcollector.idolcollector.domain.member.MemberRole;
import com.idolcollector.idolcollector.domain.nestedcomment.NestedComment;
import com.idolcollector.idolcollector.domain.notice.Notice;
import com.idolcollector.idolcollector.domain.notice.NoticeRepository;
import com.idolcollector.idolcollector.domain.notice.NoticeType;
import com.idolcollector.idolcollector.domain.post.Post;
import com.idolcollector.idolcollector.domain.post.PostRepository;
import com.idolcollector.idolcollector.domain.posttag.PostTag;
import com.idolcollector.idolcollector.domain.scrap.Scrap;
import com.idolcollector.idolcollector.domain.scrap.ScrapRepository;
import com.idolcollector.idolcollector.domain.tag.TagRepository;
import com.idolcollector.idolcollector.domain.trending.Trending;
import com.idolcollector.idolcollector.domain.trending.TrendingRepository;
import com.idolcollector.idolcollector.domain.trending.TrendingType;
import com.idolcollector.idolcollector.file.FileStore;
import com.idolcollector.idolcollector.web.dto.comment.CommentResponseDto;
import com.idolcollector.idolcollector.web.dto.file.UploadFile;
import com.idolcollector.idolcollector.web.dto.nestedcomment.NestedCommentResponseDto;
import com.idolcollector.idolcollector.web.dto.post.HomePostListResponseDto;
import com.idolcollector.idolcollector.web.dto.post.PostResponseDto;
import com.idolcollector.idolcollector.web.dto.post.PostSaveRequestDto;
import com.idolcollector.idolcollector.web.dto.post.PostUpdateRequestDto;
import com.idolcollector.idolcollector.web.dto.tag.TagResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class PostService {

    private final TagService tagService;

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final TagRepository tagRepository;
    private final ScrapRepository scrapRepository;
    private final LikesRepository likesRepository;
    private final TrendingRepository trendingRepository;
    private final NoticeRepository noticeRepository;
    private final FileStore fileStore;

    private final HttpSession httpSession;

    @Transactional
    public PostResponseDto create(@ModelAttribute PostSaveRequestDto form) throws IOException {

        Member member = memberRepository.findById((Long) httpSession.getAttribute("loginMember")).get();

        // ?????? ??????
        UploadFile uploadFile = fileStore.storeFile(form.getAttachFile());

        // Post ??????.
        Post savedPost = postRepository.save(new Post(member, form.getTitle(), form.getContent(), uploadFile.getStoreFileName(), uploadFile.getUploadFileName()));

        // ?????? ??????. // ?????? ???????????? ???????????? ??? ????????????, tagService??? Post??? Member??? ????????? ??????????????? ?????? ??????.
        if(form.getTags() != null){
            tagService.createPostTag(form.getTags(), savedPost);
        }

        return new PostResponseDto(savedPost);
    }


    @Transactional
    public PostResponseDto detail(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(CPostNotFoundException::new);

        Member member = memberRepository.findById((Long) httpSession.getAttribute("loginMember")).get();

        // ????????? ??????.
        post.addView();

        PostResponseDto postResponseDto = new PostResponseDto(post);

        // ?????? dto ??????
        List<Comment> entComments = post.getComments();
        List<CommentResponseDto> commentDto = new ArrayList<>();
        for (Comment comment : entComments) {
            CommentResponseDto dto = new CommentResponseDto(comment);

            // ????????? dto ??????
            List<NestedComment> nComment = comment.getNComment();
            List<NestedCommentResponseDto> nCommentsDto = new ArrayList<>();
            for (NestedComment nestedComment : nComment) {
                NestedCommentResponseDto nestedCommentResponseDto = new NestedCommentResponseDto(nestedComment);
                Optional<Likes> didLikeN = likesRepository.findLikeByMemberIdPostId(nestedComment.getId(), member.getId(), LikeType.NESTED_COMMENT);
                if(didLikeN.isPresent()) nestedCommentResponseDto.didLike();
                nCommentsDto.add(nestedCommentResponseDto);
            }

            dto.setNCommentsDto(nCommentsDto);

            Optional<Likes> didLike = likesRepository.findLikeByMemberIdPostId(comment.getId(), member.getId(), LikeType.COMMENT);
            if(didLike.isPresent()) dto.didLike();
            commentDto.add(dto);
        }
        postResponseDto.setCommentsDto(commentDto);


        // tag dto ??????
        List<PostTag> entPostTags = post.getPostTags();
        List<TagResponseDto> tagDto = new ArrayList<>();
        for (PostTag entPostTag : entPostTags) {
            tagDto.add(new TagResponseDto(entPostTag.getTag()));
        }
        postResponseDto.setTagsDto(tagDto);


        // ????????? ?????? ??????
        Optional<Likes> didLike = likesRepository.findLikeByMemberIdPostId(post.getId(), member.getId(), LikeType.POST);
        if(didLike.isPresent()) postResponseDto.didLike();

        // ????????? ?????? ??????
        Optional<Scrap> didScrap = scrapRepository.findScrapByMemberIdPostId(post.getId(), member.getId());
        if(didScrap.isPresent()) postResponseDto.didScrap();

        return postResponseDto;
    }


    @Transactional
    public PostResponseDto update(PostUpdateRequestDto form) {
        Post post = postRepository.findById(form.getPostId())
                .orElseThrow(CPostNotFoundException::new);

        // ???????????? ?????? ??????
        Member member = memberRepository.findById((Long) httpSession.getAttribute("loginMember")).get();
        if (post.getMember().getId() != member.getId()) {
            throw new CAccessDeniedException();
        }

        post.update(form);

        // tag ??????
        tagRepository.deleteAllByPostId(form.getPostId());
        if (form.getTags() != null) {
            tagService.createPostTag(form.getTags(), post);
        }

        return new PostResponseDto(post);
    }


    @Transactional
    public Long delete(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(CPostNotFoundException::new);

        // ???????????? ?????? ??????
        Member member = memberRepository.findById((Long) httpSession.getAttribute("loginMember")).get();

        if (post.getMember().getId() != member.getId()) {
            throw new CAccessDeniedException();
        }

        postRepository.delete(post);

        fileStore.deleteFile(post.getStoreFileName());
        return id;
    }


    public List<HomePostListResponseDto> scorePostList(Integer page) {
        PageRequest preq = PageRequest.of(page, 15);
        List<Post> result = postRepository.findTrendingAll(LocalDateTime.now().minusDays(7), preq);

        List<HomePostListResponseDto> list = new ArrayList<>();
        for (Post post : result) {
            list.add(new HomePostListResponseDto(post));
        }

        return list;
    }


    public List<HomePostListResponseDto> scorePostListSearch(Integer page, List<String> keywords) {
        PageRequest preq = PageRequest.of(page, 15);

        List<Post> result = postRepository.findTrendingSearch(LocalDateTime.now().minusDays(7), keywords, preq);

        List<HomePostListResponseDto> list = new ArrayList<>();
        for (Post post : result) {
            list.add(new HomePostListResponseDto(post));
        }

        return list;
    }


    public List<HomePostListResponseDto> memberPostList(Long memberId, Integer page) {
        PageRequest preq = PageRequest.of(page, 15);
        List<Post> result = postRepository.findAllInMember(memberId, preq);

        List<HomePostListResponseDto> list = new ArrayList<>();
        for (Post post : result) {
            list.add(new HomePostListResponseDto(post));
        }

        return list;
    }


    @Transactional
    public int like(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(CPostNotFoundException::new);

        Member member = memberRepository.findById((Long) httpSession.getAttribute("loginMember")).get();

        Optional<Likes> isDup = likesRepository.findLikeByMemberIdPostId(post.getId(), member.getId(), LikeType.POST);

        if (isDup.isPresent()) {
            throw new IllegalArgumentException("?????? ???????????? ??????????????????. ?????? id=" + id);
        }

        Likes likes = new Likes(post.getId(), member, LikeType.POST);
        likesRepository.save(likes);

        // Notice ?????????
        noticeRepository.save(new Notice(post.getMember(), member, post, NoticeType.LIKE));

        // ?????? ?????? ?????????
        trendingRepository.save(new Trending(post, TrendingType.LIKE));

        return post.addLike();
    }


    @Transactional
    public PostResponseDto scrap(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(CPostNotFoundException::new);

        Member member = memberRepository.findById((Long) httpSession.getAttribute("loginMember")).get();

        Scrap scrap = new Scrap(member, post);
        Scrap save = scrapRepository.save(scrap);

        // Notice ?????????
        noticeRepository.save(new Notice(post.getMember(), member, post, NoticeType.SCRAP));

        // ?????? ?????? ?????????
        trendingRepository.save(new Trending(post, TrendingType.SCRAP));

        return new PostResponseDto(post);
    }


    @Transactional
    public Long cancelScrap(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(CPostNotFoundException::new);

        Member member = memberRepository.findById((Long) httpSession.getAttribute("loginMember")).get();

        Optional<Scrap> opt = scrapRepository.findScrapByMemberIdPostId(post.getId(), member.getId());

        if(opt.isEmpty()) throw new IllegalArgumentException("??????????????? ?????? ???????????????. id=" + id);

        scrapRepository.delete(opt.get());

        return id;
    }
}
