import styled from 'styled-components';

function TopBtn() {
  const handleTopBtn = () => (document.documentElement.scrollTop = 0);
  return (
    <StyledTopBtn onClick={handleTopBtn}>
      <TopBtnIcon src="/images/TOP.png" />
    </StyledTopBtn>
  );
}

export default TopBtn;

const StyledTopBtn = styled.div`
  position: fixed;
  bottom: 2rem;
  right: 2rem;
  width: 50px;
  height: 50px;

  &:hover {
    cursor: pointer;
  }
`;

const TopBtnIcon = styled.img`
  width: 100%;
  height: 100%;
`;
