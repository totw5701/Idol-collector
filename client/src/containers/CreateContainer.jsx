import { useState } from 'react';
import styled from 'styled-components';

function UploadContainer() {
  const [isSelected, setIsSelected] = useState(false);
  const [selectedPhoto, setSelectedPhoto] = useState({
    photo: [],
    photoPreview: null,
  });
  const [tag, setTag] = useState(['bts', 'stb', 'sbt']);

  const handleDeletePhoto = () => {
    setIsSelected(false);
  };

  const handleSelectPhoto = e => {
    setIsSelected(true);
    setSelectedPhoto({
      ...selectedPhoto,
      photo: e.target.files[0],
      photoPreview: URL.createObjectURL(e.target.files[0]),
    });
  };

  return (
    <UploadWrap>
      <UploadLeft>
        {isSelected ? (
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
              <img src="images/이미지.png" alt="uploading card" />
            </SelectImgLabel>
            <h3>권장파일사양: </h3>
          </>
        )}
      </UploadLeft>
      <CenterLine />
      <UploadRight>
        <InputField>
          <input type="text" placeholder="카드 타이틀을 입력하세요 (10자)" />
        </InputField>
        <InputField>
          <input type="text" placeholder="카드 타이틀을 입력하세요 (30자)" />
        </InputField>
        <InputField>
          <input
            type="text"
            placeholder="alt 텍스트를 추가하여 스크린리더가 읽게 해주세요 (선택, 30자)"
          />
        </InputField>
        <InputField>
          <input type="text" placeholder="태그를 넣어 주세요 (최대 5개)" />
        </InputField>
        {tag && (
          <TagField>
            {tag.map(cur => (
              <h3>
                <span>❌</span>
                {cur}
              </h3>
            ))}
          </TagField>
        )}
        <CreateBtn>만들기</CreateBtn>
      </UploadRight>
    </UploadWrap>
  );
}

export default UploadContainer;

const UploadWrap = styled.div`
  width: 90%;
  height: 70vh;
  margin: 3.5rem auto;

  display: flex;
  justify-content: center;
`;

/////////////////
// 업로드 왼쪽 영역
const UploadLeft = styled.div`
  width: 40%;
  height: 100%;

  position: relative;

  border-top: none;
  border-left: none;
  border-radius: 30px;
  -webkit-box-shadow: 5px 5px 5px 0px rgba(0, 0, 0, 0.2);
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
const UploadRight = styled.div`
  position: relative;
  display: flex;
  flex-direction: column;
  align-items: center;

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
    font-size: 1rem;
    border: none;
    background: none;

    &:focus {
      outline: none;
    }
  }
`;

const TagField = styled.div`
  display: flex;

  h3 {
    position: relative;
    padding: 0.4rem 0.6rem;
    border: 1px solid #b580d1;
    border-radius: 1rem;
    margin: auto 0.5rem;

    span {
      cursor: pointer;
      position: absolute;
      right: -10px;
      top: -2px;
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
