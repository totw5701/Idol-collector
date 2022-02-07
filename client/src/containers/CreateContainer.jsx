import { useState, useEffect } from 'react';
import styled, { keyframes, css } from 'styled-components';
import CancelIcon from '@mui/icons-material/Cancel';
import ApiService from '../ApiService'
import axios from 'axios'


//null검사, reg검사






function CreateContainer() {

  const [isPhotoSelected, setIsPhotoSelected] = useState(false);
  const [title, setTitle] = useState();
  const [description, setDescription] = useState();
  const [alt, setAlt] = useState();
  const [selectedPhoto, setSelectedPhoto] = useState({
    photo: null,
    photoPreview: null,
  });
  const [tags, setTags] = useState([])

  const [regFail,setRegFail] = useState({})//{ [cardDB id명]: 해당 id의 값이 true,false인지 }

  // const handleCreate = async () => {
  //   const formdata = new FormData();
  //   formdata.append('newcard', )
  // }

  useEffect(() => {
    console.log(regFail)
  },[regFail])


 const regFailSwitch = (el) => {
    setRegFail({...regFail, [el.id]: false }) // 에러: 왜 photo 제외 값은 안넣어지지? 덮어씌워지나? 그래놓고 true로는 잘바뀌네...?
    setTimeout(()=>{
       setRegFail({...regFail, [el.id]: true })
    },1000)

 }

const regObj = {
  title: { rule: /^[ㄱ-ㅎ가-힣a-zA-Z0-9 ]{1,10}$/, msg: '카드 title은 10자 이내로 입력해주세요!' },
  description: { rule: /^[ㄱ-ㅎ가-힣a-zA-Z0-9 ]{1,30}$/, msg: '카드 설명은 30자 내로 입력해주세요'},
  alt: { rule: /^[ㄱ-ㅎ가-힣a-zA-Z0-9 ]/, msg: '카드 이미지 alt값을 입력해주세요!' },
  tags: { rule: /^[a-zA-Zㄱ-ㅎ가-힣]/, msg: '태그는 띄어쓰기 없이 한글 영어만 가능' },
  photo: { rule: /[ㄱ-ㅎ가-힣a-zA-Z0-9 ]/, msg: '사진을 등록해주세요' }
}
    //console.log( title, description, alt, tags,selectedPhoto)

const isNull = (el) => {
  if(el.value == null)  { //undefined까지 걸러낼거라서 === 대신 == 사용
    console.log('Null!! '+ el.id + '값을 입력하셔야 합니다!')
  }

  return (el.value == null)
}



const regTest  = () => { // handleCreateCard 데이터 cardDB 유효성 검사
  //console.log( title, description, alt, tags,selectedPhoto)
  const cardDB = [ { id: 'title', value: title },{id: 'description', value: description },{id: 'alt', value: alt },
                 {id: 'tags', value: tags },{id: 'photo', value: selectedPhoto.photo } ]

  //console.log(cardDB)

  cardDB.map((el) => {

    //console.log(el)
    //console.log(regObj[el.id].rule.test(el.value))


    //태그 유효성 검사
    if(el.id === 'tags'){
       //태그 배열 크기 검사
       if( tags.length === 0 || tags.length >5 ) {
           console.log('태그는 최대 5개까지 입력! ')
           regFailSwitch(el)
       }else{ //태그 1~5개 입력시 각각의 태그 유효성 검사
         tags.map((tag) => {
           if(!regObj['tags'].rule.test(tag) ){
             console.log(regObj['tags'].msg)
             regFailSwitch(el)
           }
         })
       }
    }else{ // 태그 외 나머지 유효성 검사
      if( isNull(el) || !regObj[el.id].rule.test(el.value) ){
        console.log(regObj[el.id].msg)
        regFailSwitch(el)
      }
    }
  })// cardDB 반복문 끝

}

  const handleCreateCard = async () => {


  //console.log( title, description, alt, tags,selectedPhoto)


    /* 사진 첨부 검사 ( 추후 유효성검사 )*/
    if(selectedPhoto.photo !== null){

      const newCard = new FormData();

      newCard.append('attachFile', selectedPhoto.photo )
      newCard.append('title', title )
      newCard.append('content', description )
      newCard.append('tags', tags )

      //console.log(newCard.get('attachFile'))

      ApiService.postCard(newCard)
      .then( (result)=> { console.log('card/create 성공') } )
      .catch( (err) => { console.log(err+ 'card/create axios실패!') } )

    }else{
      alert('사진을 첨부해주세요')
    }
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

{ regFail.title === false

  ? (<InputFailField regFail = { regFail.title }>
      <input
      type="text"
      id="title"
      placeholder="카드 타이틀을 입력하세요 (10자)"
      required
      onChange={handleTitle}
      />
    </InputFailField>)
  : (<InputField>
      <input
      type="text"
      placeholder="카드 타이틀을 입력하세요 (10자)"
      required
      onChange={handleTitle}
      />
    </InputField>)
}

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
              placeholder="태그를 넣어 주세요 (최대 5개, 띄어쓰기 없이 한글 영어만 가능)"
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
        <CreateBtn onClick={() => {regTest()}}>만들기</CreateBtn>
      </CreateRight>
    </CreateWrap>
  );
}

export default CreateContainer;


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
  }
  input::placeholder {
    color: ${ props => props? 'black': 'red'}
  }

  ${ props => props == false && css`
      animation: ${ warning }  1s ease;
  `}


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
  }
`;

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
`;

// 참고한 사이트
// https://codeat21.com/react-image-upload-and-preview/
// https://chooworld.com/2021/05/26/react%EC%97%90%EC%84%9C-%ED%8C%8C%EC%9D%BC%EC%98%AC%EB%A6%AC%EA%B8%B0-%EA%B8%B0%EB%8A%A5-%EA%B5%AC%ED%98%84-2021-04-16-%EA%B8%88/
