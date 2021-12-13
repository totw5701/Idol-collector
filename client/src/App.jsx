import styled from 'styled-components';
import GlobalStyle from './common/GlobalStyle';
import Jumbotron from './components/Jumbotron';
import Nav from './components/Nav';

function App() {
  return (
    <>
      <GlobalStyle />
      <Nav />
      <Jumbotron />
      <TestText>
        Lorem ipsum dolor sit amet consectetur, adipisicing elit. Error
        blanditiis nisi quas impedit cupiditate aut. Soluta, accusamus.
        Reiciendis, cupiditate natus? Dolor dignissimos pariatur nemo ad,
        aspernatur tempora dolorem sequi dolore! Lorem ipsum dolor sit amet
        consectetur, adipisicing elit. Error blanditiis nisi quas impedit
        cupiditate aut. Soluta, accusamus. Reiciendis, cupiditate natus? Dolor
        dignissimos pariatur nemo ad, aspernatur tempora dolorem sequi dolore!
        Lorem ipsum dolor sit amet consectetur, adipisicing elit. Error
        blanditiis nisi quas impedit cupiditate aut. Soluta, accusamus.
        Reiciendis, cupiditate natus? Dolor dignissimos pariatur nemo ad,
        aspernatur tempora dolorem sequi dolore! Lorem ipsum dolor sit amet
        consectetur, adipisicing elit. Error blanditiis nisi quas impedit
        cupiditate aut. Soluta, accusamus. Reiciendis, cupiditate natus? Dolor
        dignissimos pariatur nemo ad, aspernatur tempora dolorem sequi dolore!
      </TestText>
    </>
  );
}

export default App;

const TestText = styled.p`
  font-size: 4rem;
`;
