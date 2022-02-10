import { useRef, useState, useEffect } from 'react';
import { Link, useHistory } from 'react-router-dom';
import styled,{ css } from 'styled-components';
import { ArrowForwardIos, ArrowForward } from '@mui/icons-material';
import CancelIcon from '@mui/icons-material/Cancel';
import ChatBubbleIcon from '@mui/icons-material/ChatBubble';
import TextareaAutosize from 'react-textarea-autosize';
import MoreHorizIcon from '@mui/icons-material/MoreHoriz';
import FavoriteIcon from '@mui/icons-material/Favorite';
import Columns from './Columns';
import { useSelector, useDispatch } from 'react-redux';
import ApiService from '../ApiService'

function NComment({nestedComments}) { // cmt.nestedComments 대댓글 리스트를 받아옴
  //console.log(nestedComments)
  const member = useSelector ( ({memberReducer}) => { return memberReducer})

  const [isShow, setIsShow] = useState(false) // 댓글 보기 스위치
  const [isNCmt, setIsNCmt] = useState(false) // 댓글 작성칸 스위치
  const [isUpCmt, setIsUpCmt] = useState(false) // 댓글 수정창 스위치
  const [isUpNCmt, setIsUpNCmt] = useState(false) // 대댓글 수정창 스위치
  const [isReNCmt, setIsReNCmt] = useState(false) // 대댓글 작성칸 스위치

  const [openEditor,setOpenEditor] = useState('') // 댓글 입력창 위치:  cmt.id 저장 후 해당 id인 댓글 아래만 나타나게

  const [cmtValue,setCmtValue] = useState() // 대댓글 담을 state

  const toggleNCmt =() => setIsNCmt(prev => !prev)

  const toggleReNCmt = () => setIsReNCmt(prev => !prev)

// 대댓글 등록
  const handleNCmtSubmit = id => {

    setOpenEditor('')

    let nComment = {  commentId: id ,content: cmtValue }
    //console.log(nComment)

    if(cmtValue ==null){
      alert('내용을 입력해주세요!')
    }else{
      ApiService.postNCmt({ commentId: Number(id) ,content: cmtValue })
      .then((result) => {
         console.log('대댓글 등록 완료')
         setCmtValue(null)// 값 입력 후 cmt state 비워주기
      })
      .catch((err) => {console.log('postNCmt axios 에러! '+err )})
    }
  }

// 대댓글 삭제
  const handleDelNCmt = id => {
    //console.log(id) //nCmt.id

    ApiService.delNCmtId(Number(id))
    .then((result) => {
      console.log('대댓글 삭제 완료')
    })
    .catch((err)=> {
      console.log('delNCmtId axios 에러!'+ err )
    })
  }

// 대댓글 수정
  const handleNCmtUpdate = id => {
    //console.log({ id: Number(id), content: cmt })
    if(id == null || id === '' ){
      alert('내용을 입력해주세요!')
    }else{
      ApiService.putNCmtUpdate({ id: Number(id), content: cmtValue })
      .then((result) => {
        console.log('대댓글 수정 완료')
        setCmtValue(null)
      })
      .catch((err)=> {
        console.log('putNCmtUpdate axios 에러!'+ err )
      })
    }
  }
// 대댓글 좋아요
  const handleNCmtLike = id => {
    //console.log(id) //nCmt.id

    ApiService.putNCmtLike(Number(id))
    .then((result) => {
      console.log('대댓글 좋아요 완료')
    })
    .catch((err)=> {
      console.log('putNCmtLike axios 에러!'+ err )
    })

  }
 return(
 <>
  { nestedComments.map((nCmt, nIdx) =>
    <NCommentList>
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

  { /* 대댓글 메뉴 */ }
      <ButtonItem>
      { nCmt.nestedComments && nCmt.nestedComments.length> 0
       ? <NCmtToggle>{ nCmt.nestedComments.length }개의 댓글보기</NCmtToggle>
       : <NCmtToggle></NCmtToggle>
      }
      <Menu>
        <FavoriteIcon onClick = {() => { handleNCmtLike(nCmt.id) }} />
        <ChatBubbleIcon onClick = { () => { toggleReNCmt(); setOpenEditor(nCmt.id) }} />
        <MoreHorizIcon />
      </Menu>
  { /* 본인인 경우만 삭제,수정  */ }
      { nCmt.authorId === member.id && (
        <>
        <button type='button' onClick = {() => { handleDelNCmt(nCmt.id) }}>대댓글 삭제</button>
        <button type='button' onClick = {() => {
          setIsUpNCmt(true)
          setOpenEditor(nCmt.id)
        }}>대댓글 수정</button>
        </>
      )}
      </ButtonItem>
      { isUpNCmt && openEditor === nCmt.id && (
      <ItemContainer>
        <NCommentItem as="div">
          <Link to="마이페이지path">
            <img
            src="/images/업로더-사진.png"
            alt={`아이디 이미지`}
            />
          </Link>
          <NCommentInfo>
            <CommentText
              type = 'text'
              placeholder = '대댓글 수정'
              onChange = {(e) => { setCmtValue(e.target.value) }}
            />
          </NCommentInfo>
        </NCommentItem>
          <button type = 'button' onClick = {()=>{ setIsUpNCmt(false); setOpenEditor('') }}>취소</button>
          <button type = 'button' onClick = {() => { handleNCmtUpdate(nCmt.id); setOpenEditor('') }}>완료</button>
      </ItemContainer>
      )}

  { /* 대댓글에 대댓글 달기 */ }

      { isReNCmt && openEditor === nCmt.id && (
      <ItemContainer>
        <NCommentItem as="div">
          <Link to="마이페이지path">
            <img
            src="/images/업로더-사진.png"
            alt={`아이디 이미지`}
            />
          </Link>
          <NCommentInfo>
            <CommentText
              type = 'text'
              placeholder = '대댓글 추가'
              onChange = {(e) => { setCmtValue(e.target.value) }}
            />
          </NCommentInfo>
        </NCommentItem>
          <button type = 'button' onClick = {()=>{ setIsReNCmt(false); setOpenEditor('') }}>취소</button>
          <button type = 'button' onClick = {() => { handleNCmtSubmit(nCmt.id); setOpenEditor('') }}>완료</button>

      </ItemContainer>
      )}

    </NCommentList>
  )}

  </>
 )

}

export default NComment

const Menu = styled.div`
  margin: 6px 10px 0 0;
`;

{/* Comment 컴포넌트보다 margin 더줌 */}
const NCmtToggle = styled.button`
  margin: 0 0 0 80px;
  font-weight: bold;
`;

const ButtonItem = styled.div`
  display: flex;
  justify-content: space-between;
  margin: 8px auto 8px auto;
  @media screen and (max-width: 1100px) {

  }

`;
{/* Comment ItemContainer 크기보다 작아*/}
const ItemContainer = styled.div`
  width: 100%;
  height: 50%;
  margin: 0 0 0px auto;
  display: span;

  > button {
    background-color: #b580d1;
    width: 50px;
    height: 40px;
    position: relative;
    top: 10px;
    left: 120px;
    margin: 10px 0 20px 10px;
    border-radius: 25px;
    color: #fff;
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

const NCommentList = styled.ul`
  margin-bottom: 5px;
  width: 85%;
  margin: 0 0 0 auto;
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

const CommentInfo = styled(UserInfo)`
  flex-direction: column;
  align-items: flex-start;
  position: relative;
  width: 90%;
  margin-left: 10px;
`;

const NCommentInfo = styled(UserInfo)`
  margin: 8px 0 0 8px;
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

const NoBtn = styled.button`
  width: 70px;
  height: 50px;
  background: #e0e0e0;
  font-size: 17px;
  font-weight: 800;
  border-radius: 30px;
`;

const YesBtn = styled.button`
  width: 70px;
  height: 50px;
  background: red;
  color: white;
  font-size: 17px;
  font-weight: 800;
  border-radius: 30px;
  margin-left: 20px;
`;