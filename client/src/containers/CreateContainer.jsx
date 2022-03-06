import { useState, useEffect } from 'react';
import styled, { keyframes, css } from 'styled-components';
import CancelIcon from '@mui/icons-material/Cancel';
import ApiService from '../ApiService'
import axios from 'axios'
import Validator from '../Validator'
import CreateModal from '../components/CreateModal'
import { useHistory } from 'react-router-dom'
import { useDispatch } from 'react-redux'
import { addCard } from '../redux/modules/actions'

//null검사, reg검사



function CreateContainer() {

  const [showModal,setShowModal] = useState(false)
  const [isPhotoSelected, setIsPhotoSelected] = useState(false)
  const [title, setTitle] = useState()
  const [description, setDescription] = useState()
  const [cardId,setCardId] = useState()
  const [alt, setAlt] = useState()
  const [selectedPhoto, setSelectedPhoto] = useState({
    photo: null,
    photoPreview: null,
  });
  const [tags, setTags] = useState([])
  const history = useHistory()
  const dispatch = useDispatch()

/* 유효성 검사 */
  const cardDB = [ { id: 'title', value: title },{id: 'description', value: description },{id: 'alt', value: alt },
                 {id: 'tags', value: tags },{id: 'photo', value: selectedPhoto.photo } ]
  //console.log(cardDB)

  const regTest = () => {
    const validator = new Validator(cardDB,tags)

    if(validator.regTest()){
      handleCreateCard()
    }
  }


  const handleCreateCard = async () => { // 카드 만들기

      //console.log( title, description, alt, tags,selectedPhoto)

      const newCard = new FormData();

      newCard.append('attachFile', selectedPhoto.photo )
      newCard.append('title', title )
      newCard.append('content', description )
      newCard.append('tags', tags )

      //console.log(newCard.get('attachFile')) // newCard FormData는 출력안돼,get(key)로 값출력

      addCard(newCard).then((result) => {
        dispatch(result);
        //console.log(result);
        setCardId(result.payload.id);
        setShowModal(true);
      })

  };

  const handleAlt = e => {
    setAlt(e.target.value);
  };

  const handleDescription = e => {
    setDescription(e.target.value);
  };

  const handleTitle = e => {
    setTitle(e.target.value);
  };

  const handleInputTag = e => {
    e.preventDefault();
    setTags([...tags, e.target.tags.value]);

  };

  const handleCloseTag = index => {
    setTags(tags.filter((cur, i) => i !== index));
  };

  const handleDeletePhoto = () => {
    setIsPhotoSelected(false);
  };

  const handleSelectPhoto = e => {
    setIsPhotoSelected(true);
    // photo에 이미지파일, photoPreview에 상대경로로 미리보기
    setSelectedPhoto({
      ...selectedPhoto,
      photo: e.target.files[0],
      photoPreview: URL.createObjectURL(e.target.files[0]),
    });
  };

  return (
    <CreateWrap>
      <CreateLeft>
        {isPhotoSelected ? (
          <>
            <DeleteBtn onClick={handleDeletePhoto}>
              <img src="images/휴지통.png" alt="delete preview card button" />
            </DeleteBtn>
            <PhotoHolder>
              <img src={selectedPhoto.photoPreview} alt="selected card" />
            </PhotoHolder>
          </>
        ) : (
          <>
            <input
              style={{ display: 'none' }}
              id="card-input"
              type="file"
              accept="image/*"
              onChange={handleSelectPhoto}
            />
            <SelectImgLabel htmlFor="card-input">
              <img src="images/이미지.png" alt="Createing card" />
            </SelectImgLabel>
            <h3>권장파일사양: </h3>
          </>
        )}
      </CreateLeft>

      <CenterLine />

      <CreateRight>

        <InputField>
          <input
            type="text"
            placeholder="카드 타이틀을 입력하세요 (10자)"
            required
            onChange={handleTitle}
          />
        </InputField>
        <InputField>
          <input
            type="text"
            placeholder="카드를 간단하게 설명해주세요 (30자)"
            required
            onChange={handleDescription}
          />
        </InputField>
        <InputField>
          <input
            type="text"
            placeholder="alt 텍스트를 추가하여 스크린리더가 읽게 해주세요 (선택, 30자)"
            onChange={handleAlt}
          />
        </InputField>
        <InputField >
          {/* 태그 기능 작업할 때 정규식으로 한글 영어만 가져오기 (띄어쓰기, 특수문자, 숫자 제거) */}
          <form onSubmit={handleInputTag}>
            <input
              type="text"
              name='tags'
              placeholder="최대 5개 띄어쓰기 없이 15자이내 한글 영어만 가능"
              required
            />
            <button type='onSubmit'>태그등록</button>
          </form>
        </InputField>
          {tags && (
            <TagField>
              {tags.map((cur, i) => (
                <Tag key={i}>
                  {cur}
                  <CancelIcon onClick={() => handleCloseTag(i)} />
            </Tag>
          ))}
          </TagField>
        )}
        <CreateBtn onClick={() => {
          regTest()
        }}>만들기</CreateBtn>
      </CreateRight>

    {/* 카드 등록 완료 모달 */}
      <Back showModal={showModal} >
        <CreateModal showModal={showModal} setShowModal={setShowModal} preview = {selectedPhoto.photoPreview} cardId = {cardId}/>
      </Back>
    </CreateWrap>
  );
}

export default CreateContainer;


const greyColor = '#e0e0e0';
const yesBgColor = '#ED1E79';
const noBgColor = '#e0e0e0';
const modalBgColor = '#2b2b2b';
{/*  const greyBgColor = rgba(143, 143, 143, 0.15); */}


const ButtonItem = styled.div`
  position: absolute;
  bottom: 10px;
  width: 100%;
  display: flex;
  justify-content: flex-end;
  margin: 10px 0 10px 0;
  @media screen and (max-width: 1100px) {

  }

`;

const NoBtn = styled.button`
  width: 120px;
  height: 40px;
  background: #e0e0e0;
  font-size: 17px;
  font-weight: 800;
  border-radius: 30px;
  margin-right: 20px;
`;

const YesBtn = styled.button`
  width: 120px;
  height: 40px;
  background: ${ yesBgColor };
  color: white;
  font-size: 17px;
  font-weight: 800;
  border-radius: 30px;
  margin-right: 30px;
`;

const Back = styled.div`

  ${ props => props.showModal && css`
    width: 100%;
    height: 100%;
    position: fixed;
    right: 0;
    left: 0;
    top; 0;
    bottom: 0;
    z-index: 1;
    background: ${ modalBgColor };

  `}
`;


const warning = keyframes`
  0% { transform: translateX(-5px); }
  25% { transform: translateX(5px); }
  50% { transform: translateX(-5px); }
  75% { transform: translateX(5px); }
`

const CreateWrap = styled.div`
  width: 90%;
  height: 70vh;
  margin: 3.5rem auto;

  display: flex;
  justify-content: center;

  @media screen and (max-width: 750px) {
    flex-direction: column;
    margin: 0 auto 0 auto;
  }

`;

/////////////////
// 업로드 왼쪽 영역
const CreateLeft = styled.div`
  width: 50%;
  height: 100%;

  position: relative;

  border-top: none;
  border-left: none;
  border-radius: 30px;
  box-shadow: 5px 5px 5px 0px rgba(0, 0, 0, 0.2);

  h3 {
    color: #6f6f6f;
    position: absolute;
    left: 50%;
    top: 80%;
    transform: translate(-50%);
  }

  @media screen and (max-width: 750px) {
    width: 100%;
    height: 40%;
  }

`;

const PhotoHolder = styled.div`
  position: absolute;
  width: 100%;
  height: 100%;
  background: #d6d5d5;
  border-radius: 30px;

  img {
    max-width: 99.8%;
    max-height: 100%;
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
  }
`;

const SelectImgLabel = styled.label`
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);

  &:hover {
    cursor: pointer;
    opacity: 0.8;
  }

  img {
    height: 4rem;
    width: 5rem;
  }
`;

const DeleteBtn = styled.div`
  position: absolute;
  right: 1rem;
  top: 1rem;
  z-index: 1;

  padding: 0.2rem;
  background: #fff;
  border-radius: 10px;

  &:hover {
    cursor: pointer;
    opacity: 0.8;
  }

  img {
    width: 1.5rem;
    height: 1.5rem;
  }
`;

const CenterLine = styled.div`
  width: 2px;
  height: 100%;
  background: #b580d1;
  margin: auto 3rem;

  @media screen and (max-width: 750px) {
    display: none;
  }
`;

/////////////////
// 업로드 오른쪽 영역
const CreateRight = styled.div`
  position: relative;
  display: flex;
  flex-direction: column;
  align-content: flex-start;

  width: 40%;
  height: 100%;

  @media screen and (max-width: 750px) {
    width: 100%;
    height: 50%;
  }

`;

const InputField = styled.div`
  width: 100%;
  border: 2px solid #b580d1;
  padding: 1rem 1.5rem;
  border-radius: 2rem;
  margin: 1.5rem auto;

  input {
    width: 100%;
    font-size: 0.9rem;
    border: none;
    background: none;

    &:focus {
      outline: none;
    }

    @media screen and (max-width: 750px) {

      font-size: 12px;
    }
  }
  input::placeholder {
    color: ${ props => props? 'black': 'red'}
  }

  ${ props => props == false && css`
      animation: ${ warning }  1s ease;
  `}

  @media screen and (max-width: 750px) {
    margin: 25px auto 0 auto;
  }

`;

const InputFailField = styled.div`
  width: 100%;
  border: 2px solid red;
  padding: 1rem 1.5rem;
  border-radius: 2rem;
  margin: 1.5rem auto;

  input {
    width: 100%;
    font-size: 0.9rem;
    border: none;
    background: none;

    &:focus {
      outline: none;
    }
  }

  input::placeholder {
    color: red;
  }
  animation: ${ warning }  1s ease;
`;

const TagField = styled.div`
  display: flex;
  flex-wrap: wrap;
  @media screen and (max-width) {

  }
`;

const Tag = styled.span`
    position: relative;
    padding: 0.4rem 0.6rem;
    border: 1px solid #b580d1;
    border-radius: 1rem;
    margin-right: 1rem;

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
   @media screen and (max-width: 750px) {
     margin-top: 20px;
   }
`

const CreateBtn = styled.button`
  position: absolute;
  right: 0;
  bottom: 0;
  border: none;
  border-radius: 1.2rem;

  font-size: 1.2rem;
  padding: 0.4rem 1rem;
  color: #fff;
  background: #b580d1;

  &:hover {
    cursor: pointer;
    opacity: 0.8;
  }

  @media screen and (max-width: 750px) {
    position: relative;
    width: 100px;
    margin: 20px 0 0 auto;
  }

`;

// 참고한 사이트
// https://codeat21.com/react-image-upload-and-preview/
// https://chooworld.com/2021/05/26/react%EC%97%90%EC%84%9C-%ED%8C%8C%EC%9D%BC%EC%98%AC%EB%A6%AC%EA%B8%B0-%EA%B8%B0%EB%8A%A5-%EA%B5%AC%ED%98%84-2021-04-16-%EA%B8%88/
