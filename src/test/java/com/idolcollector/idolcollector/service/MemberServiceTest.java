package com.idolcollector.idolcollector.service;

import com.idolcollector.idolcollector.EntityMaker;
import com.idolcollector.idolcollector.domain.blame.BlameRepository;
import com.idolcollector.idolcollector.domain.member.Member;
import com.idolcollector.idolcollector.domain.member.MemberRepository;
import com.idolcollector.idolcollector.domain.notice.NoticeRepository;
import com.idolcollector.idolcollector.file.FileStore;
import com.idolcollector.idolcollector.web.dto.member.MemberUpdateRequestDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;

import static com.idolcollector.idolcollector.EntityMaker.*;
import static com.idolcollector.idolcollector.EntityMaker.generateUploadFile;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

@ExtendWith(MockitoExtension.class)
public class MemberServiceTest {

    @InjectMocks MemberService memberService;

    @Mock MemberRepository memberRepository;
    @Mock NoticeRepository noticeRepository;
    @Mock HttpSession httpSession;
    @Mock BlameRepository blameRepository;

    @Mock FileStore fileStore;

    @Test
    void 회원_정보_수정() throws IOException {
        // Given
        Member member = generateMember();

        Optional<Member> memberOp = Optional.of(member);

        doReturn(1L).when(httpSession).getAttribute(any());
        doReturn(memberOp).when(memberRepository).findById(1L);
        //doReturn(generateUploadFile()).when(fileStore).storeProFile(any(MultipartFile.class));

        File img = new File("./src/test/resources/imgs/profile/test.png");
        MockMultipartFile mf = new MockMultipartFile("image","test.png", "img", new FileInputStream(img));

        MemberUpdateRequestDto form = new MemberUpdateRequestDto("upNick", "upEmail@email.com", mf);

        // When
        Long update = memberService.update(form);

        Assertions.assertThat(member.getPicture()).contains("store file name");
    }

}
