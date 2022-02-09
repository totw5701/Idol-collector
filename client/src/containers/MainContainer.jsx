import Columns from '../components/Columns';
import Jumbotron from '../components/Jumbotron';

function MainContainer({ data }) {
  return (
    <>
      <Jumbotron />
      <Columns  pageName = 'main' />
    </>
  );
}

export default MainContainer;