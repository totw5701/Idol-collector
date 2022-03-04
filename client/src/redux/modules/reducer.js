import { combineReducers } from 'redux';
import {
  ADD_LIKE,
  REMOVE_LIKE,
  ADD_VIEW,
  GET_MEMBER,
  GET_HOME,
  USER_CARD,
  USER_SCRAP,
  USER_INFO,
  USER_PHOTO,
} from './types';
import dummyPost from '../../data/dummyPost';
import axiosPost from '../../data/axiosPost';
import dummyBundle from '../../data/dummyBundle';
import dummyMember from '../../data/dummyMember';
import dummyScrap from '../../data/dummyScrap';
import dummyUserCard from '../../data/dummyUserCard';
// import dummyPhoto from '../../images'
import ApiService from '../../ApiService';

const post = [];
const bundle = dummyBundle;
const member = dummyMember;
const scrap = dummyScrap;
const usercard = dummyUserCard;
const photo = {};

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
const postReducer = (state = post, action = { type: '' }) => {
  let copy = [...state]

  switch (action.type) {
    case GET_HOME:
      state = [...dummyPost,...action.payload];
      //state = action.payload;
      return {...state, data: [...dummyPost,...action.payload] }
      //return {...state, data: action.payload }
    /* likes */
    case ADD_LIKE:
      copy[action.id].likes++;
      return copy;

    case REMOVE_LIKE:
      copy[action.id].likes--;
      return copy;

    /* views */
    case ADD_VIEW:
      copy[action.id].views++;
      return copy;

    default:
      return state;
  }
};

/* bundle: mypage 카드집 */
const bundleReducer = (state = bundle, action = { type: '' }) => {
  let copy = [...bundle];

  return state;
};

// mypage 카드모음
const userCardReducer = (state = usercard, action = { type: '' }) => {
  switch (action.type) {
    case USER_CARD:
      return { ...state, userCard: action.payload };
    default:
      return state;
  }
};

// mypage 스크랩모음
const scrapReducer = (state = scrap, action = { type: '' }) => {
  switch (action.type) {
    case USER_SCRAP:
      return { ...state, userScrap: action.payload };
    default:
      return state;
  }
};

/* 회원관리 */
const memberReducer = (state = member, action = { type: '' }) => {
  switch (action.type) {
    case GET_MEMBER:
      return { ...state, userData: action.payload };
    default:
      return state;
  }
};

const userPhoto = (state = photo, action) => {
  switch (action.type) {
    case USER_PHOTO:
      return {...state, userPhoto: action.payload}
      default:
        return state;
  }
};

/* reduder들 combine */
const reducer = combineReducers({
  postReducer,
  bundleReducer,
  memberReducer,
  userCardReducer,
  scrapReducer,
});

export default reducer;
