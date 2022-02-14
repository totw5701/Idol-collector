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
import Validator from '../Validator'


function Update (props) { //card, isUpdate, setIsUpdate
  const card = props.card
  const [title, setTitle] = useState()
  const [content, setContent] = useState()
  const [postId, setPostId] = useState(card.id)
  const [tag,setTag] = useState()

  const [tags,setTags] = useState([])

  const addTag = () => {
    if(tags.length > 4) {
      alert('태그는 최대 5개까지 등록 가능합니다!')
    }else if( !tag.match(/^[a-zA-Zㄱ-ㅎ가-힣]{1,15}$/) ){
      alert('태그는 띄어쓰기 없이 15자 이내 한글로만 입력해주세요!')
    }else{
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

  return (

  <UpdateForm onSubmit = { regTest } isUpdate = { props.isUpdate }>
    <Title>카드 수정</Title>

    <UpdateFormItem>
      <UpdateInfo>
          <Label> 제목
            <Input type='text' name='title' placeholder={ card.title }  onChange = {(e)=> { setTitle(e.target.value) }}/>
          </Label>
          <Label> 설명
            <Input type='text' name='content'  onChange = {(e)=> { setContent(e.target.value) }}/>
          </Label>
          <Label> 태그
            <Input type='text' name='tag' onChange = {(e)=> { setTag(e.target.value) }}/>
            <AddTag onClick = { addTag }>태그등록</AddTag>
          </Label>
          <Label>
          <TagField>
            { tags.length > 0 && (
              tags.map( (tag,idx) =>
                <Tag key={idx}>
                  {tag}
                  <CancelIcon onClick={() => { handleCloseTag(tag) }} />
                </Tag>
              )
            )}
          </TagField>
          </Label>
          <ButtonItem>
            <NoBtn type = 'button' onClick = { ()=>{ props.setIsUpdate(false) }} >취소</NoBtn>
            <YesBtn type = 'button' onClick = { regTest }>완료</YesBtn>
          </ButtonItem>
      </UpdateInfo>
      <UpdateImg src={card.storeFileName} alt={`${card.title} 사진`} />
    </UpdateFormItem>

  </UpdateForm>

  )

}

export default Update

const greyColor = '#e0e0e0';
const yesBgColor = '#b580d1';

const UpdateForm = styled.form`
  display: ${ props => props.isUpdate ? 'block':'none'};
  min-height: 650px;
  width: 1040px;
  position: fixed;
  top: 10%;
  left: 50%;
  transform: translateX( -50%);
  border-radius: 20px;
  background: white;
  @media screen and (max-width: 1100px) {
    top: 15%;
    width: 60%;
    height: auto;
  }

  @media screen and (max-width: 560px) {
    top: 15%;
    width: 95%;
    height: auto;
  }

`;

const Title = styled.div`
  font-size: 40px;
  font-weight: 800;
  margin: 50px auto 20px auto;
`;

const UpdateFormItem = styled.div`
  display: flex;
  justify-content: space-between;
  margin: 40px auto 0 auto;

  @media screen and (max-width: 1100px) {
    display: flex;
    flex-direction: column-reverse;
    align-items: center;
  }
`;

const UpdateInfo = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;

`;

const UpdateImg =styled.img`
  width: 100%;
  height: 100%;
  max-width: 300px;
  max-height: 380px;
  margin: 15px 40px 0 0;
  object-fit: cover;
  border-radius: 10px;

  @media screen and (max-width: 1100px) {
    width: 100%;
    height: 100%;
    margin: 0 auto 0 auto;
  }

`;

const AddTag = styled.div`
  text-align: right;
  cursor: pointer;
  margin: 0 60px 0 0;

  @media screen and (max-width: 1100px) {
    margin: 0 50px 0 0;
  }

}
`;

const Input = styled.input`
  width: 70%;
  height: 70px;
  padding-left: 20px;
  margin: 17px 0 20px 70px;
  border: 3px solid #e0e0e0;
  border-radius: 10px;

  @media screen and (max-width: 1100px) {
    width: 70%;
    height: 50px;
    padding-left: 20px;
    margin: 17px 0 10px 20px;
    border: 3px solid #e0e0e0;
    border-radius: 10px;
  }
`;

const TagField = styled.div`
  display: flex;
  flex-wrap: wrap;
  margin: 25px 0 0 40px;
  max-width: 1000px;

  @media screen and (max-width: 1100px) {
    width: 100%;
  }
`;

const Tag = styled.span`
    position: relative;
    padding: 0.4rem 0.6rem;
    border: 1px solid ${ greyColor };
    border-radius: 1rem;
    margin: 0 1rem 0 0;

      svg {
        position: absolute;
        right: -10px;
        top: -5px;

        font-size: 18px;
        padding: 2px;
        background: #f0f0f0;
        border-radius: 50%;
        cursor: pointer;
      }
  }
`;


const TagsArea = styled(Input)`
  width: 70%;
  height: 70px;
  margin: 0px 0 0px 70px;
  border: 3px solid #e0e0e0;
  border-radius: 10px;

  @media screen and (max-width: 1100px) {
    width: 70%;
    height: 50px;
    padding-left: 20px;
    margin: 0px 0 0px 20px;
    border: 3px solid #e0e0e0;
  }
`;

const Label = styled.label`
  text-align: center;
  font-size: 16px;
  font-weight: bold;

`;

const ButtonItem = styled.div`
  position: absolute;
  top: 85%;
  left: 80%;


  @media screen and (max-width: 1100px) {
    position: relative;
    left: 16%;
    margin: 20px 0 20px 0;
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
  background: ${ yesBgColor };
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
`;