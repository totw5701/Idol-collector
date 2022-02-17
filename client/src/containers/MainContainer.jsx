import Columns from '../components/Columns';
import Jumbotron from '../components/Jumbotron';
import styled from 'styled-components'

function MainContainer({ data }) {
  return (
    <Wrapper>
      <Jumbotron />

      { /* 이미지와 내부메뉴 */ }
      <Columns  pageName = 'main' />
    </Wrapper>
  );
}

export default MainContainer;

const Wrapper = styled.div`

`;