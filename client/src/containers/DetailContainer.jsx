import { useParams } from 'react-router-dom';
import Detail from '../components/Detail';
import { useSelector } from 'react-redux';
import ApiService from '../ApiService'

function DetailContainer() {

 let data = useSelector( ({postReducer}) => { return postReducer });

  const { cardId } = useParams();
  const db = data;

  const card = db.find(data => data.id === Number(cardId));
  //console.log(card);
/*  더미 말고 axios 쓸때: 이거 안하면 card정보만 담겨있고 member는 없어
  let card;

  ApiService.getCardId(Number(cardId))
  .then((result) => {
    console.log(result)
    card = result.data })
  .catch((err) => {
    console.log('getCardId axios 에러!')
  })
*/

  return <Detail card={card} />;
}

export default DetailContainer;
