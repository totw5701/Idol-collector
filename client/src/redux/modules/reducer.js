import { combineReducers } from 'redux'
import { ADD_LIKE, REMOVE_LIKE, ADD_VIEW, GET_MEMBER } from './types'
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
const photo = {}

// const userReducer = ( state = member, action) => {
//   switch(action.type) {
//     case USER_INFO:
//       return {
//         ...state,
//         userData: action.payload
//       }
//       default: return state
//   }
// }


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

}

/* bundle: mypage 카드집 */
const bundleReducer = ( state = bundle, action = { type: '' } ) => {
    let copy = [...bundle]

    return state
}

/* 회원관리 */
const memberReducer = ( state = member, action = {type: ''} ) => {
  let copy = {...member}

  switch(action.type){

    case GET_MEMBER:  return action.payload

  }

  return state

}

const userPhoto = (state = photo, action) => {
  
}


/* reduder들 combine */
const reducer = combineReducers({ 
  postReducer, 
  bundleReducer, 
  memberReducer,
});

export default reducer
