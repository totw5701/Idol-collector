import React from 'react';
import styled from 'styled-components';
import MycardList from './MycardList';

const UserInfo = () => {
  return (
    <>
      <UserInfoContainer>
        <UserInfoInit>
          <UserPhoto>
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
          </UserPhoto>
          <UserInfoText>
            <UserMainName>
              <h2>Seungmin Shin</h2>
            </UserMainName>
            <UserSubName>
              <h3>@Ethan_seungmin</h3>
            </UserSubName>
            <UserCardNumber>
              <h2>카드 1개</h2>
            </UserCardNumber>
          </UserInfoText>
        </UserInfoInit>
      </UserInfoContainer>
    </>
  );
};


export default UserInfo;


const UserInfoContainer = styled.section`
    width: 100%;
`;

const UserInfoInit = styled.div`
  width: 100%;
`;

const UserPhoto = styled.div`
  position: relative;
  display: flex;
  align-items: center;
  top: 90px;
  width: 100%;
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
    width: 7.5rem;
  }
`;

const UserInfoText = styled.section`
  width: 100%;
  display: flex;
  flex-direction: column;
`

const UserMainName = styled.div`
  position: relative;
  display: flex;
  justify-content: center;
  top: 180px;
  text-align: center;
  font-size: 2rem;
  font-weight: bold;
`;

const UserSubName = styled.div`
  position: relative;
  display: flex;
  justify-content: center;
  top: 190px;
  color: gray;
  text-align: center;
  font-size: 0.9rem;
  font-weight: lighter;
`

const UserCardNumber = styled.div`
  position: relative;
  display: flex;
  justify-content: center;
  top: 220px;
  text-align: center;
  font-size: 1.2rem;
  font-weight: bolder;
`