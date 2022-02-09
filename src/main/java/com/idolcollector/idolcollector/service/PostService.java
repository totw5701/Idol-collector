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
import java.io.File;
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
    public Long create(@ModelAttribute PostSaveRequestDto form) throws IOException {

        Member member = memberRepository.findById((Long) httpSession.getAttribute("loginMember")).get();

        /**
         * post에서 넘어온 memberId는 언제든 조작될 수있음. 세션에서 받아오는것이 정확하다.
         * Controller에서 세션 memberId와 save form dto로 넘어온 memberId가 맞는지 확인하고 맞으면 service시작
         * 그렇지 않다면 취소하자.
         *
         * 안됨, 실제 DB에서 받아온 post의 memberId와 세션이 일치하는지 확인해야함. service에서 할 수 밖에 없다.
         *
         * 그렇다면 컨트롤에서 save form DTO에 넣어줄까?
         */

        // 사진 저장
        UploadFile uploadFile = fileStore.storeFile(form.getAttachFile());

        // Post 저장.
        Post savedPost = postRepository.save(new Post(member, form.getTitle(), form.getContent(), uploadFile.getStoreFileName(), uploadFile.getUploadFileName()));

        // 태그 저장. // 다른 서비스를 의존하는 좀 그렇지만, tagService는 Post과 Member에 완전히 종속적이니 그냥 하자.
        if(form.getTags() != null){
            tagService.createPostTag(form.getTags(), savedPost);
        }

        return savedPost.getId();
    }

    @Transactional
    public PostResponseDto detail(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(CPostNotFoundException::new);

        Member member = memberRepository.findById((Long) httpSession.getAttribute("loginMember")).get();

        // 조회수 증가.
        post.addView();

        PostResponseDto postResponseDto = new PostResponseDto(post);

        // 댓글 dto 넣기
        List<Comment> entComments = post.getComments();
        List<CommentResponseDto> commentDto = new ArrayList<>();
        for (Comment comment : entComments) {
            CommentResponseDto dto = new CommentResponseDto(comment);

            // 대댓글 dto 넣기
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


        // tag dto 넣기
        List<PostTag> entPostTags = post.getPostTags();
        List<TagResponseDto> tagDto = new ArrayList<>();
        for (PostTag entPostTag : entPostTags) {
            tagDto.add(new TagResponseDto(entPostTag.getTag()));
        }
        postResponseDto.setTagsDto(tagDto);


        // 좋아요 유무 체크
        Optional<Likes> didLike = likesRepository.findLikeByMemberIdPostId(post.getId(), member.getId(), LikeType.POST);
        if(didLike.isPresent()) postResponseDto.didLike();

        // 스크랩 유무 체크
        Optional<Scrap> didScrap = scrapRepository.findScrapByMemberIdPostId(post.getId(), member.getId());
        if(didScrap.isPresent()) postResponseDto.didScrap();

        return postResponseDto;
    }

    @Transactional
    public Long update(PostUpdateRequestDto form) {
        Post post = postRepository.findById(form.getPostId())
                .orElseThrow(CPostNotFoundException::new);

        // 세션유저 일치 확인
        Member member = memberRepository.findById((Long) httpSession.getAttribute("loginMember")).get();
        if (post.getMember().getId() != member.getId()) {
            throw new CAccessDeniedException();
        }

        post.update(form);

        // tag 수정
        tagRepository.deleteAllByPostId(form.getPostId());
        if (form.getTags() != null) {
            tagService.createPostTag(form.getTags(), post);
        }

        return post.getId();
    }

    @Transactional
    public Long delete(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(CPostNotFoundException::new);

        // 세션유저 일치 확인
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
            throw new IllegalArgumentException("이미 좋아요한 게시물입니다. 카드 id=" + id);
        }

        Likes likes = new Likes(post.getId(), member, LikeType.POST);
        likesRepository.save(likes);

        // Notice 만들기
        noticeRepository.save(new Notice(post.getMember(), member, post, NoticeType.LIKE));

        // 추천 기록 테이블
        trendingRepository.save(new Trending(post, TrendingType.LIKE));

        return post.addLike();
    }


    @Transactional
    public Long scrap(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(CPostNotFoundException::new);

        Member member = memberRepository.findById((Long) httpSession.getAttribute("loginMember")).get();

        Scrap scrap = new Scrap(member, post);
        Scrap save = scrapRepository.save(scrap);

        // Notice 만들기
        noticeRepository.save(new Notice(post.getMember(), member, post, NoticeType.SCRAP));

        // 추천 기록 테이블
        trendingRepository.save(new Trending(post, TrendingType.SCRAP));

        return save.getId();
    }

    @Transactional
    public Long cancelScrap(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(CPostNotFoundException::new);

        Member member = memberRepository.findById((Long) httpSession.getAttribute("loginMember")).get();

        Optional<Scrap> opt = scrapRepository.findScrapByMemberIdPostId(post.getId(), member.getId());

        if(opt.isEmpty()) throw new IllegalArgumentException("스크랩하지 않은 카드입니다. id=" + id);

        scrapRepository.delete(opt.get());

        return id;
    }


    /**
     *
     * 사용하지 않는 코드
     *
     */
    public List<HomePostListResponseDto> scorePostListLegacy(Integer page) {

        // 트렌딩 먼저 출력, 트렌딩 모두 출력한 뒤에는 최근 업로드순으로 출력
        Integer count = trendingRepository.trendAnalyByDateCount(LocalDateTime.now().minusDays(7));
        int trendingPageCount = count / 15 + 1;

        List<Post> list = new ArrayList<>();
        if (trendingPageCount > page) {
            PageRequest preq = PageRequest.of(page, 15);
            Page<Post> result = trendingRepository.trendAnalyByDate(LocalDateTime.now().minusDays(7), preq);

            list = result.getContent();
        } else {
            page = page - trendingPageCount;
            PageRequest preq = PageRequest.of(page, 15);
            Page<Post> result = postRepository.findAll(preq);

            list = result.getContent();
        }

        List<HomePostListResponseDto> postList = new ArrayList<>();
        for (Post post : list) {
            postList.add(new HomePostListResponseDto(post));
        }

        return postList;
    }
}
