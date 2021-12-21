import styled from 'styled-components';
import SearchBar from './SearchBar';

function Jumbotron() {
  return (
    <StyledJumbotron>
      <Banner>
        {/* 이미지 들어올 곳 */}
        <BannerTitle>최애의 순간을 수집하세요!</BannerTitle>
      </Banner>
      <SearchBar />
    </StyledJumbotron>
  );
}

export default Jumbotron;

const StyledJumbotron = styled.section`
  height: 40vh;
  margin-bottom: 100px;
  padding-top: 65px;
`;

const Banner = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
  height: 100%;
`;

const BannerTitle = styled.p`
  font-size: 5rem;
  font-weight: 700;
`;
