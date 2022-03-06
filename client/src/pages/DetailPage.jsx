import DetailContainer from '../containers/DetailContainer';
import SimilarContainer from '../containers/SimilarContainer';
import { useParams } from 'react-router-dom';
import { useSelector,useDispatch } from 'react-redux';
import ApiService from '../ApiService'

function DetailPage() {

  let data = useSelector( ({postReducer}) => { return postReducer });

  const { cardId } = useParams();

  const db = data.find(d => d.id === Number(cardId));
  //console.log(db);

  return (
    <>
      <DetailContainer db = {db}/>
      <SimilarContainer />
    </>
  );
}

export default DetailPage;
