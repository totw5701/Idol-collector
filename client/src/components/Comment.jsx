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
import NComment from './NComment'
import
{ removeCmt,
  updateCmt,
  likeCmt

} from '../redux/modules/actions'


function Comment(props) {
 //comments(card.comments 카드의 코멘트들 배열 props로 받아 올 예정), limit(댓글 slice 끝 수)
  const member = useSelector ( ({memberReducer}) => { return memberReducer.userData }) //console.log(member)
  const comments = props.comments
  const cardId = props.cardId
  const dispatch = useDispatch()

  const limit = props.limit
  const [isShow, setIsShow] = useState(false) // 댓글 보기 스위치
  const [isNCmt, setIsNCmt] = useState(false) // 댓글 작성칸 스위치
  const [isUpCmt, setIsUpCmt] = useState(false) // 댓글 수정창 스위치
  const [showNCmt, setShowNCmt] = useState(false) // 대댓글 보여주기 스위치
  const [isUpNCmt, setIsUpNCmt] = useState(false) // 대댓글 수정창 스위치
  const [isReNCmt, setIsReNCmt] = useState(false) // 대댓글 작성칸 스위치
  const [isEditMenu,setIsEditMenu] =useState(false) //댓글 삭제, 수정 햄버거 스위치

  const [openEditor,setOpenEditor] = useState('') // 댓글 입력창 위치:  cmt.id 저장 후 해당 id인 댓글 아래만 나타나게

  const [cmtValue,setCmtValue] = useState()// 댓글 수정시 쓸 state

  const toggleNCmt =() => setIsNCmt(prev => !prev)
  const toggleEdit = () => setIsEditMenu(prev => !prev)

  const [nCmtLimit,setNCmtLimit] = useState(1) // 대댓글  페이징
/* 대댓글 페이징 */
  const nCmtPage = (nCmtLimitEnd) => {
    if(nCmtLimit+3> nCmtLimitEnd){
      setNCmtLimit(nCmtLimitEnd)
    }else{
      setNCmtLimit(nCmtLimit+3)
    }
  }


//대댓글 등록
  const handleNCmtSubmit = e => {

    setOpenEditor('')

    let nComment = {  commentId: Number(e.target[0].value) ,content: e.target[1].value }
    console.log(nComment)

    if(nComment.content == null){
      alert('내용을 입력해주세요!')
    }else{
      ApiService.postNCmt(nComment)
      .then((result) => {
         console.log('대댓글 등록 완료')
         setCmtValue(null)// 값 입력 후 cmt state 비워주기

      })
      .catch((err) => {console.log('postNCmt axios 에러! '+err )})
    }

  }

// 댓글 삭제
  const handleDelCmt = id => {
    //console.log(id) //cmt.id
    removeCmt(cardId,id).then((result) => {
      dispatch(result);
    })
  }

// 댓글 수정
  const handleCmtUpdate = id => {
    //console.log({ id: Number(id), content: cmtValue })
    if(cmtValue ==null){
      alert('내용을 입력해주세요!')
    }else{
      setCmtValue(null)// 값 입력 후 cmt state 비워주기
      updateCmt(cardId,id,cmtValue).then((result) => {
        dispatch(result);
      })
    }
  }

//댓글 좋아요
  const handleCmtLike = id => {
    // console.log(id) //cmt.id
    likeCmt(cardId,id).then((result) => dispatch(result))

  }

//대댓글 숨기기 토글
const nCmtToggle = () =>  setShowNCmt(prev => !prev )


  return (
  <>
  { comments.slice(0,limit).map((cmt,idx) =>

    <CommentList key={cmt.id}>

  { /* 댓글 내용 */ }
      <CommentItem>
        <Link to="">
          <img
            src="/images/업로더-사진.png"
            alt={`아이디 이미지`}
          />
        </Link>
        <CommentInfo>
          <UserLink to= {`/member/${cmt.authorId}`}>{cmt.authorId}</UserLink>
          <CommentContent> { cmt.content }</CommentContent>
        </CommentInfo>
      </CommentItem>

  { /* 댓글 메뉴  좋아요, 대댓, 삭제, 수정*/ }
      <ButtonItem>
      { cmt.nestedComments && cmt.nestedComments.length> 0
        ? <NCmtToggle onClick = { () =>{
             nCmtToggle();
             setOpenEditor(cmt.id);
          }}>
            { showNCmt && openEditor === cmt.id ? cmt.nestedComments.length+'개 댓글 숨기기' : cmt.nestedComments.length+'개 댓글 보기' }
         </NCmtToggle>

        : <NCmtToggle></NCmtToggle>
      }
        <Menu>
          <li>
            <FavoriteIcon type = 'button' onClick = { () => { handleCmtLike(cmt.id) }} />
          </li>
          <li>
            <ChatBubbleIcon onClick = { () => {
              toggleNCmt();
              setOpenEditor(cmt.id);
            }}/>
          </li>

  { /* 본인 댓글만 삭제수정가능 */ }

           { cmt.authorId === member.id &&(
             <li>
              <MoreHorizIcon onClick = {() => {
                setOpenEditor(cmt.id);
                toggleEdit();
              }}/>
            </li>
           )}

        </Menu>
      </ButtonItem>

  { /* 삭제수정 버튼 모달  */ }
      { isEditMenu && openEditor === cmt.id  &&(
      <EditMenu>
        <EditBtn type='button' onClick = {() =>{ handleDelCmt(cmt.id); toggleEdit(); }} >삭제</EditBtn>
        <EditBtn type='button' onClick = {() => {
           setOpenEditor(cmt.id);
           setIsUpCmt(true);
           toggleEdit();
        }}>수정</EditBtn>
      </EditMenu>
      )}

  { /* 댓글 수정칸  */ }
        { isUpCmt && openEditor === cmt.id && (
         <NCommentForm >
           <ItemContainer>
             <NCommentFormItem as="div">
               <Link to="마이페이지path">
                 <img
                   src="/images/업로더-사진.png"
                    alt={`아이디 이미지`}
                    />
               </Link>
               <CommentText
                 type="text"
                 placeholder= { cmt.content }
                 onChange = {(e) => { setCmtValue(e.target.value) }}
               />
             </NCommentFormItem>

             <NoBtn type='button' onClick = { () => { setIsUpCmt(false); setOpenEditor(''); }}>취소</NoBtn>
             <YesBtn type='button' onClick = { () => { setIsUpCmt(false); handleCmtUpdate(cmt.id); setOpenEditor('') }}>완료</YesBtn>
            </ItemContainer>
         </NCommentForm >
        )}

  { /* 대댓글 입력칸 */ }

        { isNCmt && openEditor === cmt.id && (
          <NCommentForm onSubmit = { handleNCmtSubmit } >
            <ItemContainer>
            <NCommentFormItem as="div">
              <Link to="마이페이지path">
                <img
                src="/images/업로더-사진.png"
                alt={`아이디 이미지`}
                />
              </Link>
                <input
                  type = 'hidden'
                  value = {cmt.id}
                />
                <CommentText
                  type = 'text'
                  placeholder = '댓글 추가'
                />
            </NCommentFormItem>

              <NoBtn type='button' onClick = {()=>{ setIsNCmt(false) } }>취소</NoBtn>
              <YesBtn type ='onSubmit'>완료</YesBtn>
            </ItemContainer>
          </NCommentForm>

        )}

  { /* 대댓글 리스트 컴포넌트 */ }
        { showNCmt && openEditor === cmt.id &&(
        <>
           <NComment nestedComments = { cmt.nestedComments } nCmtLimit = { nCmtLimit }/>

           { nCmtLimit < cmt.nestedComments.length
             && (<button onClick={ () => { nCmtPage(cmt.nestedComments.length) }}> 대댓글 더보기 </button>)

           }
        </>
        )}


    </CommentList>

  )}
  </>
  )//return 끝

}

export default Comment

const borderColor = '#e2e2e2';
const shadowColor = 'rgba(0, 0, 0, 0.3)';
const hoverColor = '#f0f0f0';
const noBgColor = '#e0e0e0';
const yesBgColor = '#ED1E79';

const EditBtn = styled.button`
  height: 50%;
  padding: 6px 0 6px 0;
  font-size: 15px;
  border-radius: 10px;
  :hover {
     background: ${ hoverColor };
  }
`;


const EditMenu = styled.div`
  z-index: 2;
  width: 25%;
  height: 70px;
  margin: 0 0 0 auto;
  display: flex;
  flex-direction: column;
  border-radius: 10px;
  box-shadow: 5px 5px 10px ${ shadowColor };

`;


const NoBtn = styled.button`
  width: 70px;
  height: 50px;
  background: ${ noBgColor };
  border-radius: 30px;
`;

const YesBtn = styled.button`
  width: 70px;
  height: 50px;
  background: ${ yesBgColor };
  color: white;
  border-radius: 30px;
  margin-left: 20px;
`;


const Menu = styled.div`
  display: flex;
  justify-content: flex-end;

 > li {
    cursor: pointer;
    border-radius: 50px;
    margin: 0 0 0 4px;
 }

 > li:hover {
    background: ${ hoverColor };
 }

`;
const NCmtToggle = styled.button`
  margin: 0 0 0 70px;
  font-weight: bold;
`;

const ItemContainer = styled.div`
  width: 100%;
  height: 50%;
  margin: 0 0 0 auto;
  display: span;

  > button {
    width: 50px;
    height: 40px;
    position: relative;
    top: 10px;
    left: 120px;
    margin: 10px 0 20px 10px;
    border-radius: 25px;
    @media screen and (max-width: 1014px) {
      top: 10px;
      left: 25%;
    }
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
    background-color: ${ hoverColor };
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
  margin-bottom: 10px;
`;

const CommentItem = styled.div`
  display: flex;
  justify-content: flex-end;
  margin-right: 0px;

  > a {
    margin-top: 8px;

    img {
      width: 48px;
      height: 48px;
    }
  }
`;

{/* 여기선 댓글창 버튼이 옆에오게 함 */}
const NCommentItem = styled.div`
  display: flex;
  justify-content: flex-end;
  margin-right: 0px;

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
  width: 85%;
  margin-left: 10px;
  border: 1px solid ${ borderColor };
`;

const NCommentInfo = styled(UserInfo)`
  margin: 8px 5px 0 8px;
`;

const CommentForm = styled.form``;

const NCommentForm = styled.form`
  width: 85%;
  height: 50%;
  margin: 0 0 0 auto;
`;

const CommentFormItem = styled(CommentItem)`
  width: 100%;
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

const NCommentFormItem = styled(NCommentItem)`
  width: 100%;
  margin-right: 0px;
  display: flex;
  justify-content: flex-end;



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
  margin-top: 9px;
  margin-left: 10px;
  padding: 10px;
  border-radius: 6px;
  line-height: 1.4;
  border: 1px solid ${ borderColor };
`;

const ButtonItem = styled.div`
  display: flex;
  justify-content: space-between;
  margin: 8px auto 8px auto;



`;