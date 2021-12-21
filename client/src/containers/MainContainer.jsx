import styled from 'styled-components';
import Jumbotron from '../components/Jumbotron';

function MainContainer() {
  return (
    <>
      <Jumbotron />
      <MainArea>main</MainArea>
    </>
  );
}

export default MainContainer;

const MainArea = styled.main`
  height: 140vh;
  background: lightyellow;
`;
