import { useEffect } from 'react'
import { useParams } from 'react-router-dom';
import Detail from '../components/Detail';
import { useSelector,useDispatch } from 'react-redux';
import ApiService from '../ApiService'
import {
  GET_DETAIL
   } from '../redux/modules/types'
function DetailContainer() {

  let data = useSelector( ({postReducer}) => { return postReducer });

  const { cardId } = useParams();

  const db = data.find(d => d.id === Number(cardId));
  //console.log(db);

/* 카드를 분리하면 cardId 달라질 때마다 card state가 변경되니까... 계속 초기화됨
  const dispatch = useDispatch();

  useEffect(()=>{
    dispatch({type: GET_DETAIL, payload: db })
    console.log(db)
  },[])

  const card = useSelector ( ({cardReducer}) => { return cardReducer })
 */

  return <Detail card ={db}/>;
}

export default DetailContainer;
