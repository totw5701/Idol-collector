package com.idolcollector.idolcollector.service;

import com.idolcollector.idolcollector.domain.blame.Blame;
import com.idolcollector.idolcollector.domain.blame.BlameRepository;
import com.idolcollector.idolcollector.domain.member.Member;
import com.idolcollector.idolcollector.domain.member.MemberRepository;
import com.idolcollector.idolcollector.domain.member.MemberRole;
import com.idolcollector.idolcollector.domain.notice.Notice;
import com.idolcollector.idolcollector.domain.notice.NoticeRepository;
import com.idolcollector.idolcollector.web.dto.blame.BlameRequestDto;
import com.idolcollector.idolcollector.web.dto.member.MemberResponseDto;
import com.idolcollector.idolcollector.web.dto.member.MemberSaveRequestDto;
import com.idolcollector.idolcollector.web.dto.member.MemberUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;


@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final NoticeRepository noticeRepository;
    private final HttpSession httpSession;
    private final BlameRepository blameRepository;

    @Transactional
    public Long join(MemberSaveRequestDto form) {

        Member save = memberRepository.save(new Member(MemberRole.USER, form.getNickName(), form.getEmail(), form.getPwd(), form.getName(), form.getPicture(), form.getDateOfBirth()));
        return save.getId();
    }


    @Transactional
    public Long update(MemberUpdateRequestDto form) {
        Member member = memberRepository.findById(form.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다. id=" + findById(form.getMemberId())));

        // 세션 사용자 일치 확인.

        return member.update(form);
    }


    public MemberResponseDto findById(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다. id=" + id));

        return new MemberResponseDto(member);
    }

    public Member findEntityById(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다. id=" + id));

        return member;
    }

    @Transactional
    public void noticeConfirm() {
        Member member = memberRepository.findById((Long) httpSession.getAttribute("loginMember"))
                .orElseThrow(() -> new IllegalArgumentException("로그인이 필요한 서비스입니다."));

        noticeRepository.deleteAll(member.getNotices());
    }

    @Transactional
    public Blame blame(BlameRequestDto form) {
        Member member = memberRepository.findById((Long) httpSession.getAttribute("loginMember"))
                .orElseThrow(() -> new IllegalArgumentException("로그인이 필요한 서비스입니다."));

        Member targetMember = memberRepository.findById(form.getTargetMember())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다. id = " + form.getTargetMember()));


        Blame save = blameRepository.save(new Blame(member, targetMember, form.getTargetId(), form.getType(), form.getMessage()));
        return save;
    }

    public MemberResponseDto testMember() {
        Member member = new Member(MemberRole.USER, "SessionLoginGuy", "email", "1111", "pressLike", "dsfsdfdsfdsf", LocalDateTime.now());
        memberRepository.save(member);

        return new MemberResponseDto(member);
    }
}
