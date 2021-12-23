import { useState } from 'react';
import styled from 'styled-components';

function UploadContainer() {
  const [isSelected, setIsSelected] = useState(false);
  const [selectedPhoto, setSelectedPhoto] = useState({
    photo: [],
    photoPreview: null,
  });

  const handleDeletePhoto = e => {
    setIsSelected(false);
    console.log(selectedPhoto);
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
      <UploadRight>Form area</UploadRight>
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

const CenterLine = styled.div`
  width: 2px;
  height: 100%;
  background: #b580d1;
  margin: auto 3rem;
`;

const UploadRight = styled.div`
  width: 40%;
  height: 100%;
  background: lightgrey;
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
