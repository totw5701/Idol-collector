import styled from 'styled-components';
import Jumbotron from './components/Jumbotron';
import Nav from './components/Nav';

function App() {
  return (
    <>
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
