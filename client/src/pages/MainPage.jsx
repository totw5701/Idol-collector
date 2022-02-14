import MainContainer from '../containers/MainContainer';
import styled from 'styled-components'

function MainPage({ data }) {
  return (
    <Wrapper>
      <MainContainer data={data} />
    </Wrapper>
  );
}

export default MainPage;

const Wrapper = styled.div`

`;