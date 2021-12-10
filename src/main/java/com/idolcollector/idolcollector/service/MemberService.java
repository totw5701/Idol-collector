package com.idolcollector.idolcollector.service;

import com.idolcollector.idolcollector.domain.member.Member;
import com.idolcollector.idolcollector.domain.member.MemberRepository;
import com.idolcollector.idolcollector.domain.rank.Ranks;
import com.idolcollector.idolcollector.domain.rank.RanksRepository;
import com.idolcollector.idolcollector.web.dto.member.MemberResponseDto;
import com.idolcollector.idolcollector.web.dto.member.MemberSaveRequestDto;
import com.idolcollector.idolcollector.web.dto.member.MemberUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final RanksRepository ranksRepository;

    @Transactional
    public Long join(MemberSaveRequestDto form) {
        Ranks rank = ranksRepository.findByRole("ROLL_USER");

        Member save = memberRepository.save(new Member(rank, form.getNickName(), form.getEmail(), form.getPwd(), form.getName(), form.getPicture(), form.getDateOfBirth()));
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

        // 댓글 ,태그, 알림 등 넣기.??

        return new MemberResponseDto(member);
    }

    public MemberResponseDto findByNickName(String nickName) {
        Member member = memberRepository.findByNickName(nickName)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다. nickName=" + nickName));

        // 댓글 ,태그, 알림 등 넣기.??

        return new MemberResponseDto(member);
    }

    public MemberResponseDto findByEmail(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 회원입니다. email=" + email));

        // 댓글 ,태그, 알림 등 넣기.??

        return new MemberResponseDto(member);
    }

}
