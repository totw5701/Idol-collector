import { useRef, useState, useEffect } from 'react'
import { Link, useHistory } from 'react-router-dom'
import { useSelector, useDispatch } from 'react-redux'
import styled,{ css } from 'styled-components'
import moment from 'moment'
import { ArrowForwardIos, ArrowForward } from '@mui/icons-material'
import TextareaAutosize from 'react-textarea-autosize'
import Columns from './Columns'
import ApiService from '../ApiService'
import Comment from './Comment'
import Update from './Update'
import Validator from '../Validator'
import {
  addLike,
  removeCard,
  addCmt,
  addScrap,
  removeScrap
   } from '../redux/modules/actions'
import { ADD_CMT } from '../redux/modules/types'
import domtoimage from 'dom-to-image';
import { saveAs } from 'file-saver';


function Detail({card}){

  const member = useSelector ( ({memberReducer}) => { return memberReducer.userData })
  //console.log(card)
  //console.log(member)
  const history = useHistory();
  const [isShow, setIsShow] = useState(false) // 댓글 보기 스위치
  const [isNCmt, setIsNCmt] = useState(false) // 댓글 작성칸 스위치
  const [isUpCmt, setIsUpCmt] = useState(false) // 댓글 수정창 스위치
  const [isUpNCmt, setIsUpNCmt] = useState(false) // 대댓글 수정창 스위치
  const [isReNCmt, setIsReNCmt] = useState(false) // 대댓글 작성칸 스위치
  const [didScrap, setDidScrap] = useState(false) // 스크랩 언스크랩 버튼 스위치
  const [isUpdate, setIsUpdate] = useState(false) // 카드 수정 창 스위치

  const [limit,setLimit] = useState(1) // 댓글 배열 slice 끝값 설정
  const [likeCnt,setLikeCnt] = useState(false) //좋아요 회원당 1번만 가능
  const limitEnd = card.comments != null? card.comments.length : 0


  const createDate = moment(card.createDate).format('YYYY-MM-DD')
  const likes = Number(card.likes).toString().replace(/\B(?=(\d{3})+(?!\d))/g,',')

  const [title, setTitle] = useState()
  const [content, setContent] = useState()
  const [postId, setPostId] = useState(card.id)
  const [tag,setTag] = useState()

  const [tags,setTags] = useState([])

  const [cmt,setCmt] = useState() // 댓글,대댓글 담을 state

  const dispatch = useDispatch();

  const inputRef = useRef();
  const imgRef = useRef();

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

/* 댓글 페이징 */
  const cmtPage = () => {
    if(limit+3> limitEnd){
      setLimit(limitEnd)
    }else{
      setLimit(limit+3)
    }
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
    //console.log(comment)

    if(inputRef.current.value == null || inputRef.current.value === ''){
      alert('내용을 입력해주세요!')
    }else{
      addCmt(inputRef.current.value, card.id).then((result) => {
        dispatch(result)
      })
    }

  };

  const handleDelCard = () => { // 카드 삭제
    dispatch(removeCard(card.id));
    handlePage();

  }

  const handleDownload = () => { // 카드 이미지 다운로드
    //console.log(card)
    //console.log(card.storeFileName)
    ApiService.getCardImage('card.storeFileName')
    .then((result) => {
      console.log(result.data.data)//카드 다운로드 링크
/*       const link = document.createElement('a');
      const url = 'card/0';
      link.href = url;
      link.download = '';
      document.body.appendChild(link);
      link.click((e)=>{e.preventDefault()});// 해당 링크 자동 클릭 후 다운로드 진행 새로고침 방지
      link.remove();// 다운로드 후 링크 삭제 */

      domtoimage
        .toBlob(imgRef.current)
        .then((blob) => {
          saveAs(blob,'cardFile.png');
        });

    })
    .catch((err) => {
      console.log('getCardImage axios 실패! '+err )
    })

  }
/* 좋아요 */

  const handleAddLike = () => {
      if(!likeCnt){
        dispatch(addLike(card.id));
        //setLikeCnt(true); 회원당 1번만 좋아요 누를 수 있도록 기능 추가
      }
  }


  const handleScrap = () => { // 카드 스크랩
    addScrap(card.id).then((result) => {
      dispatch(result);
      setDidScrap(true);//스크랩 제대로 실행된 경우만 작동
    })
  }

  const handleUnScrap = () => { // 카드 스크랩 취소
    removeScrap(card.id).then((result) => {
      dispatch(result);
      setDidScrap(false);
    })
  }

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
            <img src={card.storeFileName} alt={`${card.title} 사진`} ref={imgRef}/>
            <Buttons>
              <Button onClick = { handleDelCard }>
                <img src="/images/휴지통.png" alt="삭제" />
              </Button>
              <Button onClick = { handleDownload }>
                <img src="/images/다운로드.png" alt="다운로드" />
              </Button>
              <Button onClick = { handleAddLike }>
                <img src="/images/하트.png" alt="좋아요" />
              </Button>
            </Buttons>
              <Wrapper>
                <span>좋아요</span>
                <span>{likes}</span>
                <span>개</span>
              </Wrapper>
          </ImgBlock>


      { /* 이미지 아래칸 */ }
          <Info>
            <Wrapper>
              <TitleInfo>{card.title}</TitleInfo>
              <InfoButton onClick = { handleAddLike }>
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
                    <img src="/images/unscrap.jpg" alt="언스크랩 버튼" />
                  </InfoButton>
              }
            </Wrapper>
            <UserInfo as='p'>{card.content}</UserInfo>
            <SmallUserInfo>
              <span>업로드 날짜</span>
              <span>{createDate}</span>
            </SmallUserInfo>
            <SmallUserInfo>
                <span>카드태그</span>
                { card.tags.slice(0,2).map((tag) =>
                  <span value={tag.name}>{tag.name}</span>
                )}
            </SmallUserInfo>


      { /* 카드 수정 메뉴 */ }
            { member.id === card.authorId && (
              <UpdateBtn onClick={()=>{ setIsUpdate(true) }}> 카드 수정 </UpdateBtn>
            )}
      { /* 카드 수정 모달창 */ }

            <UpdatePage isUpdate = { isUpdate } >
              <Update card = { card } isUpdate = { isUpdate } setIsUpdate = { setIsUpdate }/>
            </UpdatePage>


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
      { /* 댓글 토글 */ }
            { card.comments.length> 0 &&(
              <CommentWrapper>
              { isShow
                ? <h3>댓글 열기</h3>
                : <h3>댓글 닫기</h3>

              }
                <CommentButton onClick={toggleShow} isShow={isShow}>
                  <ArrowForwardIos />
                </CommentButton>
              </CommentWrapper>
            )}


      { /* Comment 댓글 리스트
           isShow (댓글몇개) 스위치가 true && 코멘트가 존재하는 경우만 */ }

            {!isShow && card.comments.length> 0 &&(
            <>
              <Comment comments = { card.comments } cardId = {card.id} limit = { limit }/>

              { limit < card.comments.length
                ? <button onClick={ cmtPage }> 댓글 더보기 </button>
                : <button onClick={ () => { setLimit(1) }}> 댓글 줄이기 </button>
              }
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
      <Columns pageName = 'main' />
    </DetailBase>
  );
}

export default Detail;

const BtnBgColor = '#b580d1';
const textColor = '#fff';
const modalBgColor = '#2b2b2b';
/*  const greyBgColor = rgba(143, 143, 143, 0.15); */
const InfoBgColor = '#fff';
const borderColor = '#e2e2e2';
const CmtBntColor ='#ED1E79';


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
  @media screen and (max-width: 1012px) {
    font-size: 19px;
    padding: 10px 0 0 0;
  }
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
  @media screen and (max-width: 560px) {
    width: 30%;
    height: 35px;
  }
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
    max-width: 365px;
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
  z-index: 1;
  @media screen and (max-width: 1015px) {
    bottom: 35px;
  }
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
  margin-top: 8px;
  width: 160px;
  > img {
    width: 100%;
    position: relative;
    left: 8px;
    }
`;


const UnScrap = styled(InfoButton)`
  border-radius: 10px;
  font-weight: 500;
  font-size: 16px;
  margin-top: 8px;
  background: ${ BtnBgColor };
  color: ${ textColor };
  @media screen and (max-width: 560px) {
    width: 91px;
    height: 42px;
  }
`;

const Info = styled.div`
  min-height: 100%;
  padding: 10px 30px 20px 30px;
  border-radius: 0 32px 32px 0;
  background-color: ${ InfoBgColor };
  @media screen and (max-width: 560px) {
    padding: 10px 10px 20px 14px;
  }
`;


const Wrapper = styled.div`
  display: flex;
  align-items: center;
  position: relative;
  span {
    text-align: left;
    margin: 10px 0 40px 10px;
    @media screen and (max-width: 1015px) {
      margin: 10px 0 0px 10px;
    }
  }
  > span:nth-of-type(2) {
    font-weight: bold;
  }
  > span:nth-of-type(3) {
    margin: 10px 0 40px 0px;
    @media screen and (max-width: 1015px) {
      margin: 10px 0 0px 0px;
    }
  }
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
  @media screen and (max-width: 560px) {
    padding: 0 5px 0 5px;
  }
`;

const SmallUserInfo = styled(UserInfo)`
  height: 34px;
  min-height: 0;
  font-size: 14px;
/*   & + & { //SmallUserInfo 사이에 넣는 보라색 선
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
  } */
  > span:nth-of-type(1) {
    font-weight: 600;
    margin-right: 10px;
    width: 120px;
  }
  > span:nth-of-type(2), span:nth-of-type(3), span:nth-of-type(4){
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
  margin-top: 20px;
  > * {
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


const CommentForm = styled.form`
  margin-top: 10px;
`;


const CommentFormItem = styled(CommentItem)`
  width: 100%;
  > button {
    background-color: ${ CmtBntColor };
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