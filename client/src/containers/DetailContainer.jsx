import { useEffect } from 'react'
import { useParams } from 'react-router-dom';
import Detail from '../components/Detail';
import { useSelector,useDispatch } from 'react-redux';
import ApiService from '../ApiService'
import {
  GET_DETAIL
   } from '../redux/modules/types'
function DetailContainer({db}) {

  const dispatch = useDispatch();

  useEffect(()=>{
    dispatch({type: GET_DETAIL, payload: db })
    //console.log(db)
  },[])


  return <Detail/>;
}

export default DetailContainer;
