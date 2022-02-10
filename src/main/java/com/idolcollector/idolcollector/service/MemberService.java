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
import com.idolcollector.idolcollector.file.FileStore;
import com.idolcollector.idolcollector.web.dto.blame.BlameRequestDto;
import com.idolcollector.idolcollector.web.dto.file.UploadFile;
import com.idolcollector.idolcollector.web.dto.member.MemberDetailDto;
import com.idolcollector.idolcollector.web.dto.member.MemberSaveRequestDto;
import com.idolcollector.idolcollector.web.dto.member.MemberUpdateRequestDto;
import com.idolcollector.idolcollector.web.dto.member.MemberUpdateServiceDto;
import com.idolcollector.idolcollector.web.dto.notice.NoticeResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final NoticeRepository noticeRepository;
    private final HttpSession httpSession;
    private final BlameRepository blameRepository;

    private final FileStore fileStore;

    @Value("${url.base}")
    private String url;

    @Transactional
    public Long join(MemberSaveRequestDto form) {

        Member save = memberRepository.save(new Member(MemberRole.USER, form.getNickName(), form.getEmail(), form.getPwd(), form.getName(), form.getPicture(), form.getDateOfBirth()));
        return save.getId();
    }


    @Transactional
    public Long update(MemberUpdateRequestDto form) throws IOException {
        Member member = memberRepository.findById((Long) httpSession.getAttribute("loginMember"))
                .orElseThrow(() -> new CNotLoginedException());

        UploadFile uploadFile = fileStore.storeProFile(form.getProfile());
        String picture = url + uploadFile.getStoreFileName();

        return member.update(new MemberUpdateServiceDto(form.getNickName(), form.getEmail(), picture));
    }


    public MemberDetailDto findById(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(CMemberNotFoundException::new);

        return new MemberDetailDto(member);
    }

    public Member findEntityById(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(CMemberNotFoundException::new);

        return member;
    }

    @Transactional
    public List<NoticeResponseDto> noticeConfirm() {
        Member member = memberRepository.findById((Long) httpSession.getAttribute("loginMember"))
                .orElseThrow(() -> new CNotLoginedException());

        List<Notice> notices = member.getNotices();

        List<NoticeResponseDto> list = new ArrayList<>();
        for (Notice notice : notices) {
            list.add(new NoticeResponseDto(notice));
        }
        noticeRepository.deleteAll(member.getNotices());

        return list;
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

    public MemberDetailDto testMember() {
        Member member = new Member(MemberRole.USER, "SessionLoginGuy", "email", "1111", "pressLike", "dsfsdfdsfdsf", LocalDateTime.now());
        memberRepository.save(member);

        return new MemberDetailDto(member);
    }
}
