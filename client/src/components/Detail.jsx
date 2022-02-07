import { useRef, useState } from 'react';
import { Link, useHistory } from 'react-router-dom';
import styled from 'styled-components';
import { ArrowForwardIos, ArrowForward } from '@mui/icons-material';
import ChatBubbleIcon from '@mui/icons-material/ChatBubble';
import TextareaAutosize from 'react-textarea-autosize';
import MoreHorizIcon from '@mui/icons-material/MoreHoriz';
import FavoriteIcon from '@mui/icons-material/Favorite';
import Columns from './Columns';
import { useSelector, useDispatch } from 'react-redux';
import ApiService from '../ApiService'

function Detail({ card }) {

  const data = useSelector ( ({postReducer}) => { return postReducer } )
  const member = useSelector ( ({memberReducer}) => { return memberReducer})

  //console.log(member)

  const history = useHistory();
  const [isShow, setIsShow] = useState(false)
  const [isNCmt, setIsNCmt] = useState(false)

  const dispatch = useDispatch();

  const inputRef = useRef();

  const toggleShow = () => setIsShow(prev => !prev);

  const toggleNCmt =() => setIsNCmt(prev => !prev);

  const handlePage = () => history.push('/');

  const handleCmtSubmit = e => { // 댓글 등록

    //inputRef.current.style.height = '39px';
    e.preventDefault() // submit할 떄 새로고침 방지
    console.log(inputRef.current.value);

    let comment = { content: inputRef.current.value, postId: card.id }

    ApiService.postCmt(  comment )
    .then((result) => {
      console.log(result)
    }).catch((err) => {
      console.log('postCmt axios 에러!'+ err )
    })
  };

  const handleNCmtSubmit = (e) => {
    e.preventDefault()
    let nComment = {  commentId: e.target[0].value ,content: e.target[1].value }
    console.log(nComment)

  }
  const handleDelCard = () => { // 카드 삭제

    ApiService.delCardId(card.id)
    .then((result) => {
      console.log('카드 삭제완료')
      handlePage()
    })
    .catch((err) => {
      console.log('delCardId axios 에러! '+err )
    })
  }

  const handleDownload = () => { // 카드 이미지 다운로드
    console.log(card)
    console.log(card.storeFileName)
    ApiService.getCardImage(card.storeFileName)
    .then((result) => {
      console.log('카드 이미지 다운로드 완료')
    })
    .catch((err) => {
      console.log('getCardImage axios 실패! '+err )
    })

  }

    const handleLike = () => { // 카드 좋아요


    }

  const handleScrap = () => { // 카드 스크랩

  }

  const handleUnScrap = () => { // 카드 스크랩 취소

  }

  return (
    <DetailBase>
      {!card
        ? (
           <span>Loading...</span>
          )
        : (
        <DetailBlock>
          <ImgBlock>
            <img src={card.storeFileName} alt={`${card.title} 사진`} />
            <Buttons>
              <Button onClick = { handleDelCard }>
                <img src="/images/휴지통.png" alt="삭제" />
              </Button>
              <Button onClick = { handleDownload }>
                <img src="/images/다운로드.png" alt="다운로드" />
              </Button>
              <Button onClick = { handleLike }>
                <img src="/images/하트.png" alt="좋아요" />
              </Button>
            </Buttons>
          </ImgBlock>
          <Info>
            <Wrapper>
              <UserInfo>{card.title}</UserInfo>
              <InfoButton onClick = { handleLike }>
                <img src="/images/라이크.png" alt="좋아요 버튼" />
              </InfoButton>
            </Wrapper>
            <Wrapper>
              <UserInfo>{card.authorNickName}</UserInfo>
              <InfoButton onClick = { handleScrap }>
                <img src="/images/스크랩.png" alt="스크랩 버튼" />
              </InfoButton>
            </Wrapper>
            <UserInfo as="p">{card.content}</UserInfo>
            <Wrapper>
              <SmallUserInfo>
                <span>업로드날짜</span>
                <span>{card.createDate}</span>
              </SmallUserInfo>
              <SmallUserInfo>
                <span>카드태그</span>
                <span>{card.tags[0].name}</span>
              </SmallUserInfo>
            </Wrapper>
            <CommentWrapper>
              <h3>댓글</h3>
              <span>몇 개</span>
              <CommentButton onClick={toggleShow} isShow={isShow}>
                <ArrowForwardIos />
              </CommentButton>
            </CommentWrapper>
            {!isShow && (
              <>
              { card.comments.map((cmt,idx) =>
                <CommentList key={cmt.id}>
                  <CommentItem>
                    <Link to="">
                      <img
                        src="/images/업로더-사진.png"
                        alt={`아이디 이미지`}
                      />
                    </Link>
                    <CommentInfo>
                      <UserLink to="/member/: card.comments[0].authorId" >comments authorId {cmt.authorId}</UserLink>
                      <CommentContent>comments content {cmt.content}</CommentContent>
                    </CommentInfo>

                  </CommentItem>
                  <FavoriteIcon />
                  <ChatBubbleIcon onClick = { toggleNCmt }  isNCmt = {isNCmt}/>
                  <MoreHorizIcon />

                  { cmt.nestedComments.map((nCmt, nIdx) =>

                    (<NCommentForm>
                    <NCommentItem as="div" key={nCmt.id}>
                    <Link to="">
                      <img
                        src="/images/업로더-사진.png"
                        alt={`아이디 이미지`}
                      />
                    </Link>
                    <CommentInfo>
                      <UserLink to="/member/: card.comments[0].authorId" >comments authorId {nCmt.authorId}</UserLink>
                      <CommentContent>comments content {nCmt.content}</CommentContent>
                    </CommentInfo>
                    </NCommentItem>

                    <FavoriteIcon />
                    <ChatBubbleIcon onClick = { toggleNCmt }  isNCmt = {isNCmt}/>
                    <MoreHorizIcon />
                    </NCommentForm>
                    )


                  )}

                { !isNCmt && (
                  <NCommentForm onSubmit = { handleNCmtSubmit }>
                    <NCommentFormItem as="div">
                      <Link to="마이페이지path">
                        <img
                        src="/images/업로더-사진.png"
                        alt={`아이디 이미지`}
                        />
                      </Link>
                      <NCommentInfo>
                        <input
                          type = 'hidden'
                          value = {cmt.id}
                        />
                        <CommentText
                          type = 'text'
                          placeholder = '댓글 추가'

                        />
                      </NCommentInfo>

                      <button onClick = {()=>{ setIsNCmt(false) } }>취소</button>
                      <button type = 'submit'>완료</button>
                    </NCommentFormItem>
                  </NCommentForm>
                  )
                }

                </CommentList>

              )}

                <CommentForm onSubmit={handleCmtSubmit}>
                  <CommentFormItem as="div">
                    <Link to="마이페이지path">
                      <img
                        src="/images/업로더-사진.png"
                        alt={`아이디 이미지`}
                      />
                    </Link>
                    <CommentText
                      type="text"
                      ref={inputRef}
                      placeholder="댓글을 입력하세요."
                    />
                    <button type="submit">완료</button>
                  </CommentFormItem>
                </CommentForm>
              </>

            )}

          </Info>
        </DetailBlock>
      )}
      <BackButton onClick={handlePage}>
        <ArrowForward />
      </BackButton>
      <Line />
      <Announcement>비슷한 순간들을 확인하세요</Announcement>
      <Columns data={data} />
    </DetailBase>
  );
}

export default Detail;

const DetailBase = styled.section`
  position: relative;
  padding: 50px 30px;
  text-align: center;

  > span {
    font-size: 20px;
  }
`;

const BackButton = styled.button`
  position: fixed;
  top: 100px;
  left: 14px;
  width: 40px;
  height: 40px;
  border-radius: 50%;
  transform: rotate(-180deg);

  :hover {
    background-color: #f0f0f0;
  }
`;

const DetailBlock = styled.div`
  display: flex;
  overflow: hidden;
  width: 1016px;
  min-height: 400px;
  margin: 30px auto 50px;
  border-radius: 32px;
  box-shadow: 5px 5px 8px rgba(0, 0, 0, 0.3);

  @media (max-width: 1015px) {
    flex-direction: column;
    width: 100%;
    max-width: 508px;
  }
`;

const ImgBlock = styled.div`
  overflow: hidden;
  position: relative;
  width: 50%;
  border-radius: 32px 0 0 32px;

  > img {
    display: block;
    width: 100%;
  }

  @media (max-width: 1015px) {
    width: 100%;
    border-radius: 0;
  }
`;

const Buttons = styled.div`
  display: flex;
  position: absolute;
  right: 20px;
  bottom: 20px;
`;

const Button = styled.button`
  display: flex;
  align-items: center;
  justify-content: center;
  width: 40px;
  height: 40px;
  border-radius: 50%;
  background-color: #fff;

  & + & {
    margin-left: 6px;
  }

  > img {
    width: 100%;
  }
`;

const InfoButton = styled(Button)`
  overflow: hidden;
  width: 150px;
  margin-top: 8px;
  border-radius: 30px;
  background: none;
`;

const Info = styled.div`
  width: 50%;
  min-height: 100%;
  padding: 30px 30px 20px 30px;
  border-radius: 0 32px 32px 0;
  background-color: rgba(143, 143, 143, 0.15);

  @media (max-width: 1015px) {
    width: 100%;
    border-radius: 0;
  }
`;

const UserInfo = styled.div`
  display: flex;
  align-items: center;
  width: 100%;
  min-height: 50px;
  margin-top: 8px;
  padding: 10px;
  border-radius: 6px;
  background-color: #fff;
  text-align: left;
`;

const Wrapper = styled.div`
  display: flex;
  align-items: center;
  position: relative;
`;

const CommentButton = styled.button`
  display: flex;
  align-items: center;
  width: 30px;
  height: 30px;
  border-radius: 50%;
  transform: ${({ isShow }) => (isShow ? 'rotate(0)' : 'rotate(90deg)')};
  transition: transform 0.1s ease-in-out;

  :hover {
    background-color: #f0f0f0;
  }

  svg {
    transform: translateX(3px);
    font-size: 14px;
  }
`;

const SmallUserInfo = styled(UserInfo)`
  width: 50%;
  height: 34px;
  min-height: 0;
  font-size: 14px;

  & + & {
    ::before {
      content: '';
      position: absolute;
      top: 20%;
      left: 50%;
      transform: translateX(-50%);
      width: 4px;
      height: 80%;
      background-color: #b580d1;
    }
  }

  > span:nth-of-type(1) {
    font-weight: 600;
    margin-right: 10px;
  }
`;


const UserLink = styled(Link)`
  margin-bottom: 8px;
  font-weight: 700;
  font-size: 14px;
`;

const CommentContent = styled.p`
  font-size: 12px;
  text-align: left;
`;

const Line = styled.div`
  width: 90%;
  margin: 0 auto;
  height: 4px;
  background-color: #b580d1;
`;

const Announcement = styled.p`
  margin: 16px 0;
  font-weight: 600;
  font-size: 22px;
`;


const CommentWrapper = styled.div`
  display: flex;
  align-items: center;
  margin-top: 10px;

  > * {
    margin-left: 10px;
    font-size: 18px;
    font-weight: 600;
  }
`;

const CommentList = styled.ul`
  margin-bottom: 50px;
`;

const CommentItem = styled.li`
  display: flex;

  > a {
    margin-top: 8px;

    img {
      width: 48px;
      height: 48px;
    }
  }
`;

const NCommentItem = styled.div`
  display: flex;
  justify-content: flex-end;

  > a {
    margin: 8px 0px 0px 0px;

    img {
      width: 38px;
      height: 38px;
    }
  }



`;

const CommentInfo = styled(UserInfo)`
  flex-direction: column;
  align-items: flex-start;
  position: relative;
  width: 85%;
  margin-left: 10px;
`;

const NCommentInfo = styled(UserInfo)`
  margin: 8px 5px 0 8px;
`;

const CommentForm = styled.form``;

const NCommentForm = styled.form`
  width: 85%;
  height: 50%;
  margin: 0 10px 0 auto;
`;

const CommentFormItem = styled(CommentItem)`
  width: 100%;

  > button {
    background-color: #b580d1;
    width: 50px;
    height: 40px;
    margin-top: 10px;
    margin-left: 10px;
    border-radius: 25px;
    color: #fff;
  }
`;

const NCommentFormItem = styled(NCommentItem)`
  width: 100%
  margin-right: 0px;

  > button {
    background-color: #b580d1;
    width: 50px;
    height: 40px;
    margin-top: 10px;
    margin-left: 10px;
    border-radius: 25px;
    color: #fff;
  }
`;

const CommentText = styled(TextareaAutosize)`
  resize: none;
  outline: none;
  border: none;
  flex: 1;
  margin-top: 10px;
  margin-left: 10px;
  padding: 10px;
  border-radius: 6px;
  line-height: 1.4;
`;
