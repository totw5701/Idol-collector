import styled from 'styled-components';

function Jumbotron() {
  return (
    <StyledJumbotron>
      <Banner>
        {/* 이미지 들어올 곳 */}
        <BannerTitle>최애의 순간을 수집하세요!</BannerTitle>
      </Banner>
    </StyledJumbotron>
  );
}

export default Jumbotron;

const StyledJumbotron = styled.section`
  height: 50vh;
  padding-top: 67px;
`;

const Banner = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
  height: 100%;

  background: lightgreen;
`;

const BannerTitle = styled.p`
  font-size: 5rem;
`;
