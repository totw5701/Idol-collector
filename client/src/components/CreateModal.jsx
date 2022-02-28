import { useState, useEffect } from 'react';
import styled, { keyframes, css } from 'styled-components';
import { useHistory } from 'react-router-dom'


function CreateModal(props){

  const showModal = props.showModal
  const setShowModal = props.setShowModal
  const history = useHistory()

  return(
    <Modal showModal={showModal}>
      <h1>카드 등록 완료</h1>

      <img src = { props.preview }/>
      <p/>
      <button onClick={()=>{setShowModal(false)}}>nono</button>
      <ButtonItem>
      <NoBtn onClick={() => {
        history.push('/')
      }}>홈으로 이동</NoBtn>

      <YesBtn onClick={() => {
        history.push('/card')
      }}>카드 보기</YesBtn>

      </ButtonItem>
    </Modal>

  )
}

export default CreateModal;

const BtnBgColor = '#b580d1';
const textColor = '#fff';
const InfoBgColor = '#fff';
const borderColor = '#e2e2e2';
const CmtBntColor ='#ED1E79';
const greyColor = '#e0e0e0';
const yesBgColor = '#ED1E79';

const Modal = styled.div`
  display: ${ props => props.showModal ? 'block':'none'};
  width: 70%;
  height: 50%;
  background: white;
  border: 1px solid black;
  border-radius: 20px;
  position: absolute;
  top: 20%;
  left: 50%;
  transform: translateX(-50%);
  text-align: center;

  > h1 {
    font-size: 30px;
    font-weight: bold;
    margin: 30px auto 10px auto;
  }

  > img {
    max-width: 70%;
    max-height: 60%;
    object-fit: cover;
    margin: 15px auto 0 auto;
  }

  @media screen and (max-width: 450px) {
    width: 80%;
    height: 40%;

  }

`;


const ButtonItem = styled.div`
  position: absolute;
  bottom: 10px;
  width: 100%;
  display: flex;
  justify-content: flex-end;
  margin: 10px 0 10px 0;


`;

const NoBtn = styled.button`
  width: 120px;
  height: 40px;
  background: #e0e0e0;
  font-size: 17px;
  font-weight: 800;
  border-radius: 30px;
  margin-right: 20px;
  @media screen and (max-width: 450px) {
    font-size: 12px;
    width: 110px;
    height: 40px;
    margin-left: 10px;
    margin-right: 10px;
  }
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
  @media screen and (max-width: 450px) {
    font-size: 12px;
    width: 110px;
    height: 40px;
    margin-right: 10px;
  }
`;