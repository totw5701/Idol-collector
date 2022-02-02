import React from 'react';
import styled from 'styled-components';

const SettingContainer = () => {
  return (
    <SettingWrap>
      <SettingLeft>
        <input
          style={{ display: 'none' }}
          id="profile-input"
          type="file"
          accept="image/*"
          // onChange={handleSelectPhoto}
        />
        <SelectProfileLabel htmlFor="profile-input">
          <img src="images/회원개인정보-사진.png" alt="Createing profile" />
        </SelectProfileLabel>
        <h3>권장파일사양: </h3>
      </SettingLeft>
      <CenterLine />
      <SettingRight>
        <h1>회원 개인정보</h1>
        <input type="text" placeholder="닉네임을 수정하세요" />
        <InfoField>
          <h3>닉네임</h3>
          <span></span>
          <h4>nickname</h4>
        </InfoField>
        <InfoField>
          <h3>가입일</h3>
          <span></span>
          <h4>2021. 12. 25.</h4>
        </InfoField>
        <InfoField>
          <h3>이메일</h3>
          <span></span>
          <h4>abc@naver.com</h4>
        </InfoField>
        <ModifyBtn>수정하기</ModifyBtn>
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
    width: 12rem;
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
