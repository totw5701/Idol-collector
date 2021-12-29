import Columns from '../components/Columns';
import Jumbotron from '../components/Jumbotron';

function MainContainer({ data }) {
  return (
    <>
      <Jumbotron />
      <Columns data={data} />
    </>
  );
}

export default MainContainer;
