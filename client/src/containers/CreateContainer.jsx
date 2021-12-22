import styled from 'styled-components';

function UploadContainer() {
  return (
    <UploadWrap>
      <UploadLeft>
        <TrashBin src="images/휴지통.png" />
        <h2>업로드 하기</h2>
        <SelectImg src="images/이미지.png" />
        <h3>권장파일사양: </h3>
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

  h2 {
    color: #6f6f6f;
    position: absolute;
    left: 50%;
    top: 35%;
    transform: translate(-50%);
  }

  h3 {
    color: #6f6f6f;
    position: absolute;
    left: 50%;
    top: 80%;
    transform: translate(-50%);
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

const TrashBin = styled.img`
  width: 2rem;
  height: 2rem;

  position: absolute;
  right: 1rem;
  top: 1rem;

  &:hover {
    cursor: pointer;
    opacity: 0.8;
  }
`;

const SelectImg = styled.img`
  height: 5rem;
  width: 6.5rem;

  position: absolute;
  left: 50%;
  top: 50%;
  transform: translate(-50%, -50%);

  &:hover {
    cursor: pointer;
    opacity: 0.8;
  }
`;
