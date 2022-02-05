import { useParams } from 'react-router-dom';
import Detail from '../components/Detail';
import { useSelector } from 'react-redux';

function DetailContainer() {

  let data = useSelector( ({postReducer}) => { return postReducer });

  const { cardId } = useParams();
  const db = data;

  const card = db.find(data => data.id === Number(cardId));
  //console.log(card);

  return <Detail card={card} />;
}

export default DetailContainer;
