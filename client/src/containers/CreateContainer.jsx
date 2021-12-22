import styled from 'styled-components';

function UploadContainer() {
  return (
    <UploadWrap>
      <UploadLeft>THIS IS AREA WHERE USER SELECT THEIR PHOTO</UploadLeft>
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

  border-top: none;
  border-left: none;
  border-radius: 30px;
  -webkit-box-shadow: 5px 5px 5px 0px rgba(0, 0, 0, 0.2);
  box-shadow: 5px 5px 5px 0px rgba(0, 0, 0, 0.2);
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
