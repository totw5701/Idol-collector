import { useRef, useState, useEffect } from 'react';
import { Link, useHistory } from 'react-router-dom';
import { useSelector, useDispatch } from 'react-redux';
import styled,{ css } from 'styled-components';
import { ArrowForwardIos, ArrowForward } from '@mui/icons-material';
import CancelIcon from '@mui/icons-material/Cancel';
import ChatBubbleIcon from '@mui/icons-material/ChatBubble';
import TextareaAutosize from 'react-textarea-autosize';
import MoreHorizIcon from '@mui/icons-material/MoreHoriz';
import FavoriteIcon from '@mui/icons-material/Favorite';
import Columns from './Columns';
import ApiService from '../ApiService'
import Comment from './Comment'
import NComment from './NComment'
import Update from './Update'
import Validator from '../Validator'


function Detail({ card }) {

  const data = useSelector ( ({postReducer}) => { return postReducer } )
  const member = useSelector ( ({memberReducer}) => { return memberReducer})

  //console.log(member)

  const history = useHistory();
  const [isShow, setIsShow] = useState(false) // 댓글 보기 스위치
  const [isNCmt, setIsNCmt] = useState(false) // 댓글 작성칸 스위치
  const [isUpCmt, setIsUpCmt] = useState(false) // 댓글 수정창 스위치
  const [isUpNCmt, setIsUpNCmt] = useState(false) // 대댓글 수정창 스위치
  const [isReNCmt, setIsReNCmt] = useState(false) // 대댓글 작성칸 스위치
  const [didScrap, setDidScrap] = useState(false) // 스크랩 언스크랩 버튼 스위치
  const [isUpdate, setIsUpdate] = useState(false) // 카드 수정 창 스위치

  const [title, setTitle] = useState()
  const [content, setContent] = useState()
  const [postId, setPostId] = useState(card.id)
  const [tag,setTag] = useState()

  const [tags,setTags] = useState([])

  const [cmt,setCmt] = useState() // 댓글,대댓글 담을 state

  const dispatch = useDispatch();

  const inputRef = useRef();

  const toggleShow = () => setIsShow(prev => !prev)

  const toggleNCmt =() => setIsNCmt(prev => !prev)

  const toggleReNCmt = () => setIsReNCmt(prev => !prev)

  const handlePage = () => history.push('/');

  const addTag = () => {
    if(tags.length<5){
      setTags([...tags,tag])
    }
  }

  const handleCloseTag = tag => { //태그 닫기
    setTags(tags.filter( cur => cur !== tag))
  }

/* 유효성 검사 */
  const regTest = () => {

    const cardDB =
      [ { id: 'title', value: title },
        { id: 'description', value: content },
        { id: 'tags', value: tags }
      ]

    console.log(cardDB)

    const validator = new Validator(cardDB,tags)

    if(validator.regTest()){
      handleUpdateCard(title,content,postId)
    }
  }


  const handleUpdateCard = (title,content,postId) => { // 카드 수정
    ApiService.putCardUpdate(
      {
        title: title,
        content: content,
        postId: postId,
        tags: tags
      }
    )
    .then((result) => {
      alert('카드 수정이 완료됐습니다!')
    })
    .catch((err) => {
      console.log('putCardUpdate axios 에러! '+err )
    })

  }

  const handleCmtSubmit = e => { // 댓글 등록
    //inputRef.current.style.height = '39px';
    e.preventDefault() // submit할 떄 새로고침 방지
    let comment = { content: inputRef.current.value, postId: card.id }
    console.log(comment)
    if(inputRef.current.value == null || inputRef.current.value === ''){
      alert('내용을 입력해주세요!')
    }else{
      ApiService.postCmt( { content: inputRef.current.value, postId: card.id } )
      .then((result) => {
        console.log('댓글 달기 성공')
      }).catch((err) => {
        console.log('postCmt axios 에러!'+ err )
      })
    }
  };

  const handleDelCmt = e => { // 댓글 삭제
    //console.log(e.target.value) //cmt.id

    ApiService.delCmtId(Number(e.target.value))
    .then((result) => {
      console.log('댓글 삭제 완료')
    })
    .catch((err)=> {
      console.log('delCmtId axios 에러!'+ err )
    })

  }

  const handleCmtUpdate = id => { // 댓글 수정
    console.log({ id: Number(id), content: cmt })

    if(cmt ==null){
      alert('내용을 입력해주세요!')
    }else{
      ApiService.putCmtUpdate({ id: Number(id), content: cmt })
      .then((result) => {
        console.log('댓글 수정 완료')
        setCmt(null)// 값 입력 후 cmt state 비워주기
      })
      .catch((err)=> {
        console.log('putCmtUpdate axios 에러!'+ err )
      })
    }
  }

  const handleCmtLike = id => { //댓글 좋아요
    // console.log(id) //cmt.id
    ApiService.putCmtLike(Number(id))
    .then((result) => {
      console.log('댓글 좋아요 완료')
    })
    .catch((err)=> {
      console.log('putCmtLike axios 에러!'+ err )
    })

  }

  const handleNCmtSubmit = id => { //대댓글 등록

    let nComment = {  commentId: id ,content: cmt }
    console.log(nComment)

    if(cmt ==null){
      alert('내용을 입력해주세요!')
    }else{
      ApiService.postNCmt({ commentId: Number(id) ,content: cmt })
      .then((result) => {
         console.log('대댓글 등록 완료')
         setCmt(null)// 값 입력 후 cmt state 비워주기
      })
      .catch((err) => {console.log('postNCmt axios 에러! '+err )})
    }

  }

  const handleDelNCmt = id => { // 대댓글 삭제
    //console.log(id) //nCmt.id

    ApiService.delNCmtId(Number(id))
    .then((result) => {
      console.log('대댓글 삭제 완료')
    })
    .catch((err)=> {
      console.log('delNCmtId axios 에러!'+ err )
    })

  }

  const handleNCmtLike = id => { // 대댓글 좋아요
    //console.log(id) //nCmt.id

    ApiService.putNCmtLike(Number(id))
    .then((result) => {
      console.log('대댓글 좋아요 완료')
    })
    .catch((err)=> {
      console.log('putNCmtLike axios 에러!'+ err )
    })

  }

  const handleNCmtUpdate = id => { // 대댓글 수정
    //console.log({ id: Number(id), content: cmt })
    if(id == null || id === '' ){
      alert('내용을 입력해주세요!')
    }else{
      ApiService.putNCmtUpdate({ id: Number(id), content: cmt })
      .then((result) => {
        console.log('대댓글 수정 완료')
        setCmt(null)
      })
      .catch((err)=> {
        console.log('putNCmtUpdate axios 에러!'+ err )
      })
    }
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

      ApiService.putCardLike(card.id)
      .then((result) => {
        console.log('카드 좋아요 완료')
      })
      .catch((err) => {
        console.log('putCardLike axios 에러! '+err )
      })
    }

  const handleScrap = () => { // 카드 스크랩

    ApiService.putCardScrap(card.id)
    .then((result) => {
      console.log('카드 스크랩 완료')
    })
    .catch((err) => {
      console.log('putCardScrap axios 에러! '+err )
    })
    setDidScrap(true)
  }

  const handleUnScrap = () => { // 카드 스크랩 취소

    ApiService.delCardUnscrap(card.id)
    .then((result) => {
      console.log('카드 스크랩 취소 완료')
    })
    .catch((err) => {
      console.log('delCardUnscrap axios 에러! '+err )
    })
    setDidScrap(false)
  }

  useEffect(() => {
    console.log(isUpdate)
  },[isUpdate])


  return (
    <DetailBase>

      {!card
        ? (
           <span>Loading...</span>
          )
        : (
        <DetailBlock>
      { /* 이미지와 내부메뉴 */ }
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

      { /* 이미지 아래칸 */ }
          <Info>
            <Wrapper>
              <TitleInfo>{card.title}</TitleInfo>
              <InfoButton onClick = { handleLike }>
                <img src="/images/라이크.png" alt="좋아요 버튼" />
              </InfoButton>
            </Wrapper>

            <Wrapper>
              <UserInfo>{card.authorNickName}</UserInfo>
              { !didScrap
                ? <InfoButton onClick = { handleScrap }>
                    <img src="/images/스크랩.png" alt="스크랩 버튼" />
                  </InfoButton>
                : <InfoButton onClick = { handleUnScrap }>
                     스크랩 취소
                  </InfoButton>
              }
            </Wrapper>
            <UserInfo as='p'>{card.content}</UserInfo>
            <SmallUserInfo>
              <span>업로드 날짜</span>
              <span>{card.createDate}</span>
            </SmallUserInfo>
            <SmallUserInfo>
                <span>카드태그</span>
                { card.tags.slice(0,2).map((tag) =>
                  <span value={tag}>{tag}</span>
                )}
            </SmallUserInfo>


      { /* 카드 수정 메뉴 */ }
      { member.id === card.authorId && (
        <UpdateBtn onClick={()=>{ setIsUpdate(true) }}> 카드 수정 </UpdateBtn>
      )}
        <UpdateBtn onClick={()=>{ setIsUpdate(true) }}> 카드 수정 </UpdateBtn>
      { /* 카드 수정 모달창 */ }

        <UpdatePage isUpdate = { isUpdate } >
          <Update card = { card } isUpdate = { isUpdate } setIsUpdate = { setIsUpdate }/>
        </UpdatePage>

      { /* 댓글 토글 */ }
            <CommentWrapper>
              <h3>댓글</h3>
              <span>{ card.comments.length }개</span>
              <CommentButton onClick={toggleShow} isShow={isShow}>
                <ArrowForwardIos />
              </CommentButton>
            </CommentWrapper>

      { /* Comment 댓글 리스트
           isShow (댓글몇개) 스위치가 true && 코멘트가 존재하는 경우만 */ }

            {!isShow && card.comments.length> 0 &&(
              <Comment comments = { card.comments }/>
            )}

      { /* 카드에 대한 댓글 입력 */ }
            <CommentForm onSubmit = { handleCmtSubmit }>
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

          </Info>
        </DetailBlock>
      )}
      <BackButton onClick={handlePage}>
        <ArrowForward />
      </BackButton>
      <Line />

      <Announcement>비슷한 순간들을 확인하세요</Announcement>
      <Columns pageName = 'main' />
    </DetailBase>
  );
}

export default Detail;

const BtnBgColor = '#b580d1';
const textColor = '#fff';
const modalBgColor = '#2b2b2b';
{/*  const greyBgColor = rgba(143, 143, 143, 0.15); */}
const InfoBgColor = '#fff';
const borderColor = '#e2e2e2';


const TitleInfo = styled.div`
  display: flex;
  align-items: center;
  width: 100%;
  min-height: 50px;
  margin-top: 8px;
  padding: 10px;
  border-radius: 6px;
  background-color: #fff;
  text-align: left;
  font-size: 28px;
  font-weight: bolder;
`;
const UpdateBtn = styled.div`
  width: 27%;
  height: 40px;
  margin: 20px 0 10px auto;
  padding-top: 10px;
  text-align: center;
  font-size: 17px;
  background: ${ BtnBgColor };
  color: ${ textColor };
  border-radius: 7px;
  cursor: pointer;
`;

const UpdatePage = styled.div`

  ${ props => props.isUpdate && css`
    z-index: 1;
    position: fixed;
    top: 0;
    right: 0;
    bottom: 0;
    left: 0;
    background: ${ modalBgColor };
  `}

`;


const ButtonItem = styled.div`
  position: absolute;
  top: 85%;
  left: 80%;

  @media screen and (max-width: 1100px) {

  position: relative;
    top: 0%;
    left: 0%;
    text-align: right;
    margin: 4px 30px 0 0;
  }

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

const DetailBase = styled.section`
  position: relative;
  padding: 50px 30px;
  text-align: center;
  > span {
    font-size: 20px;
  }

  ${ props => props.isUpdate && css`
    position: fixed;
    top: 0;
    right: 0;
    bottom: 0;
    left: 0;
    background: ${ modalBgColor };
  `}
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
  justify-content: space-between;
  overflow: hidden;
  width: 1016px;
  min-height: 400px;
  margin: 30px auto 50px;
  border-radius: 32px;
  box-shadow: 5px 5px 8px rgba(0, 0, 0, 0.3);

  @media screen and (max-width: 1015px) {
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

  @media screen and (max-width: 1015px) {
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
  width: 500px;
  min-height: 100%;
  padding: 30px 30px 20px 30px;
  border-radius: 0 32px 32px 0;
  background-color: ${ InfoBgColor }

  @media screen and(max-width: 1015px) {
    width: 100%;
    border-radius: 0;
  }
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

const SmallUserInfo = styled(UserInfo)`
  height: 34px;
  min-height: 0;
  font-size: 14px;

{/*   & + & { //SmallUserInfo 사이에 넣는 보라색 선
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
  } */}

  > span:nth-of-type(1) {
    font-weight: 600;
    margin-right: 10px;
    width: 120px;
  }

  > span:nth-of-type(2), span:nth-of-type(3), span:nth-of-type(4), span:nth-of-type(5), span:nth-of-type(6){

    margin-right: 5px;
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


const CommentForm = styled.form``;

const CommentFormItem = styled(CommentItem)`
  width: 100%;

  > button {
    background-color: #b580d1;
    width: 50px;
    height: 40px;
    margin-top: 12px;
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
  margin-top: 12px;
  margin-left: 10px;
  padding: 10px;
  border-radius: 6px;
  line-height: 1.4;
  border: 1px solid ${ borderColor };
`;
