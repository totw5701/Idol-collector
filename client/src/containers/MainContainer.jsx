import Columns from '../components/Columns';
import Jumbotron from '../components/Jumbotron';

function MainContainer({ data }) {
  return (
    <>
      <Jumbotron />

      { /* 이미지와 내부메뉴 */ }
      <Columns  pageName = 'main' />
    </>
  );
}

export default MainContainer;