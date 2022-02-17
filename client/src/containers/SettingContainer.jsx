import React, { useRef, useState } from 'react';
import { useSelector, useDispatch } from 'react-redux';
import styled from 'styled-components';
import member from '../data/dummyMember';
import { MdCached } from 'react-icons/md';

function SettingContainer() {
  const nicknameRef = useRef();
  const data = useSelector(({ memberReducer }) => {
    return memberReducer;
  });
  // console.log(data)

  const [changeUserInfo, setchangeUserInfo] = useState(false);
  const [isPhotoSelected, setIsPhotoSelected] = useState(false);
  const [nickname, setNickname] = useState(data[0].name);
  const [isUserInfo, setUserInfo] = useState(data[0]);
  const [selectedPhoto, setSelectedPhoto] = useState({
    photo: null,
    photoPreview: null,
  });

  const handelChangeUserInfo = () => {
    setchangeUserInfo(!changeUserInfo);
    setNickname(nickname);
  };

  const changeUserNickname = e => {
    setNickname(e.target.value);
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
    <SettingWrap>
      <SettingLeft>
        {isPhotoSelected ? (
          <>
            <input
              style={{ display: 'none' }}
              id="card-change"
              type="file"
              accept="image/*"
              onChange={handleSelectPhoto}
            />
            {changeUserInfo ? (
              <>
                <ChangeBtn htmlFor="card-change">
                  <MdCached size="30" />
                </ChangeBtn>
              </>
            ) : null}
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
            <SelectProfileLabel htmlFor="card-input">
              <img src="images/회원개인정보-사진.png" alt="Createing card" />
            </SelectProfileLabel>
            <h3>권장파일사양: </h3>
          </>
        )}
      </SettingLeft>
      <CenterLine />
      <SettingRight>
        <h1>회원 개인정보</h1>
        <InfoField>
          {changeUserInfo ? (
            <>
              <h3>닉네임</h3>
              <span></span>
              <input
                type="text"
                placeholder={nickname}
                value={nickname}
                ref={nicknameRef}
                onChange={e => changeUserNickname(e)}
              ></input>
            </>
          ) : (
            <>
              <h3>닉네임</h3>
              <span></span>
              <h4>{nickname}</h4>
            </>
          )}
        </InfoField>
        <InfoField>
          <h3>가입일</h3>
          <span></span>
          <h4>{isUserInfo.dateOfBirth.slice(0, 10)}</h4>
        </InfoField>
        <InfoField>
          <h3>이메일</h3>
          <span></span>
          <h4>{isUserInfo.email}</h4>
        </InfoField>
        <ModifyBtn onClick={handelChangeUserInfo}>
          {changeUserInfo ? '수정완료' : '수정하기'}
        </ModifyBtn>
      </SettingRight>
    </SettingWrap>
  );
}

export default SettingContainer;

const SettingWrap = styled.div`
  width: 90%;
  max-width: 1440px;
  height: 70vh;
  margin: 3.5rem auto;

  display: flex;

  border-top: none;
  border-left: none;
  border-radius: 30px;
  box-shadow: 5px 5px 5px 0px rgba(0, 0, 0, 0.2);
`;

const SettingLeft = styled.div`
  position: relative;
  width: 49%;
  height: 100%;

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
  background: white;
  border-radius: 30px;

  img {
    max-width: 70%;
    max-height: 70%;
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
  }
`;

const ChangeBtn = styled.label`
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

const SelectProfileLabel = styled.label`
  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);

  &:hover {
    cursor: pointer;
    opacity: 0.8;
  }

  img {
    width: 10rem;
  }
`;

const CenterLine = styled.div`
  width: 2px;
  height: 100%;
  background: #b580d1;
  margin: auto;
`;

const SettingRight = styled.div`
  position: relative;
  width: 49%;
  height: 100%;

  display: flex;
  flex-direction: column;

  h1 {
    font-size: 2.5rem;
    margin: 3rem auto;
  }

  input {
    width: 80%;
    margin: 1rem auto;
    padding: 4px 10px;
    font-size: 1.2rem;
    border: 1px #b580d1 solid;
    background: #f0efef;
  }
`;

const InfoField = styled.div`
  display: flex;
  align-items: center;
  margin: 1.5rem 4rem;

  h3 {
    font-size: 1.1em;
  }

  span {
    margin: 0 2.5rem;
    width: 2px;
    background: #b580d1;
    height: 100%;
  }
`;

const ModifyBtn = styled.button`
  position: absolute;
  right: 3rem;
  bottom: 3rem;
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
