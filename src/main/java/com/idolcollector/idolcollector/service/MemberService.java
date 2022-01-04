package com.idolcollector.idolcollector.service;

import com.idolcollector.idolcollector.advice.exception.CMemberNotFoundException;
import com.idolcollector.idolcollector.advice.exception.CNotLoginedException;
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
                .orElseThrow(CMemberNotFoundException::new);

        // 세션 사용자 일치 확인.

        return member.update(form);
    }


    public MemberResponseDto findById(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(CMemberNotFoundException::new);

        return new MemberResponseDto(member);
    }

    public Member findEntityById(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(CMemberNotFoundException::new);

        return member;
    }

    @Transactional
    public void noticeConfirm() {
        Member member = memberRepository.findById((Long) httpSession.getAttribute("loginMember"))
                .orElseThrow(() -> new CNotLoginedException());

        noticeRepository.deleteAll(member.getNotices());
    }

    @Transactional
    public Blame blame(BlameRequestDto form) {
        Member member = memberRepository.findById((Long) httpSession.getAttribute("loginMember"))
                .orElseThrow(() -> new CNotLoginedException());

        Member targetMember = memberRepository.findById(form.getTargetMember())
                .orElseThrow(CMemberNotFoundException::new);


        Blame save = blameRepository.save(new Blame(member, targetMember, form.getTargetId(), form.getType(), form.getMessage()));
        return save;
    }

    public MemberResponseDto testMember() {
        Member member = new Member(MemberRole.USER, "SessionLoginGuy", "email", "1111", "pressLike", "dsfsdfdsfdsf", LocalDateTime.now());
        memberRepository.save(member);

        return new MemberResponseDto(member);
    }
}
