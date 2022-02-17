import { combineReducers } from 'redux'
import { ADD_LIKE, REMOVE_LIKE, ADD_VIEW, USER_INFO } from './types'
import dummyPost from '../../data/dummyPost'
import axiosPost from '../../data/axiosPost'
//import axiosMember from '../../data/axiosMember'
import dummyBundle from '../../data/dummyBundle'
import dummyMember from '../../data/dummyMember'
import ApiService from '../../ApiService'

const post = dummyPost
// const post = axiosPost
const bundle = dummyBundle
const member = dummyMember

const userReducer = ( state = member, action) => {
  switch(action.type) {
    case USER_INFO:
      return {
        ...state,
        userData: action.payload
      }
      default: return state
  }
}


/* post: mainPage 카드들 */
const postReducer = ( state = post, action = { type: '' } ) => {
  let copy = [...state]

  switch(action.type) {

    /* likes */
    case ADD_LIKE:
      copy[action.id].likes++
      return copy

    case REMOVE_LIKE:
      copy[action.id].likes--
      return copy

    /* views */
    case ADD_VIEW:
      copy[action.id].views++
      return copy


    default: return state

  }

}//postReducer

/* bundle: mypage 카드집 */
const bundleReducer = ( state = bundle, action = { type: '' } ) => {
    let copy = [...bundle]

    return state
}//bundleReducer

/* 회원관리 */
const memberReducer = ( state = member, action = {type: ''} ) => {
    let copy = {...member}

    if(action.type === 'axiosMember'){
      return action.payload
    }
    return state

}//memberReducer


/* reduder들 combine */
const reducer = combineReducers({ 
  postReducer, 
  bundleReducer, 
  memberReducer,
  userReducer,
});

export default reducer
