import styled from 'styled-components';
import Jumbotron from '../components/Jumbotron';

function MainConitaner() {
  return (
    <>
      <Jumbotron />
      <MainArea>main</MainArea>
    </>
  );
}

export default MainConitaner;

const MainArea = styled.main`
  height: 120vh;
  background: lightyellow;
`;
