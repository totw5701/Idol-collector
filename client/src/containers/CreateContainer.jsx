import React from 'react';
import { useState } from 'react';
import styled from 'styled-components';
import CloseIcon from '@material-ui/icons/Close';

const CreateContainer = () => {
  const [isPhotoSelected, setIsPhotoSelected] = useState(false);
  const [title, setTitle] = useState();
  const [description, setDescription] = useState();
  const [alt, setAlt] = useState();
  const [selectedPhoto, setSelectedPhoto] = useState({
    photo: [],
    photoPreview: null,
  });
  const [tag, setTag] = useState([]);

  // const handleCreate = async () => {
  //   const formdata = new FormData();
  //   formdata.append('newcard', )
  // }

  const handleCreateCard = () => {
    console.log(tag, title, description, alt);
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
    setTag([...tag, e.target[0].value]);
    e.target[0].value = '';
  };

  const handleCloseTag = index => {
    setTag(tag.filter((cur, i) => i !== index));
  };

  const handleDeletePhoto = () => {
    setIsPhotoSelected(false);
  };

  const handleSelectPhoto = e => {
    setIsPhotoSelected(true);
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
        <InputField>
          {/* 태그 기능 작업할 때 정규식으로 한글 영어만 가져오기 (띄어쓰기, 특수문자, 숫자 제거) */}
          <form onSubmit={handleInputTag}>
            <input
              type="text"
              placeholder="태그를 넣어 주세요 (최대 5개, 띄어쓰기 없이 한글 영어만 가능)"
              required
            />
          </form>
        </InputField>
        {tag && (
          <TagField>
            {tag.map((cur, i) => (
              <Tag key={i}>
                {cur}
                <CloseIcon onClick={() => handleCloseTag(i)} />
              </Tag>
            ))}
          </TagField>
        )}
        <CreateBtn onClick={handleCreateCard}>만들기</CreateBtn>
      </CreateRight>
    </CreateWrap>
  );
}

export default CreateContainer;

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
