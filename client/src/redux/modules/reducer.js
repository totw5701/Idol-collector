import { combineReducers } from 'redux';
import {
  GET_HOME,
  GET_MEMBER,
  USER_CARD,
  USER_SCRAP,
  USER_INFO,
  USER_PHOTO,
  ADD_CARD,
  REMOVE_CARD,
  UPDATE_CARD,
  ADD_LIKE,
  ADD_SCRAP,
  REMOVE_SCRAP,
  ADD_CMT,
  REMOVE_CMT,
  UPDATE_CMT,
  LIKE_CMT,
  ADD_NCMT,
  REMOVE_NCMT,
  UPDATE_NCMT,
  LIKE_NCMT,
  GET_DETAIL
} from './types';
import dummyPost from '../../data/dummyPost';
import dummyBundle from '../../data/dummyBundle';
import dummyMember from '../../data/dummyMember';
import dummyScrap from '../../data/dummyScrap';
import dummyUserCard from '../../data/dummyUserCard';
// import dummyPhoto from '../../images'
import ApiService from '../../ApiService';

const post = dummyPost;//[];
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

/* post: 전체 카드 */
const postReducer = (state = post, action = { type: '' }) => {
  let copy = [...state];

  switch (action.type) {
    case GET_HOME:
      copy = [...copy,...action.payload];
      return copy;

    case ADD_CARD:
      copy.push(action.payload);
      return copy;

    case REMOVE_CARD:
      copy.splice(action.id,1);
      return copy;

    case ADD_CMT:
      copy[action.payload.id].comments.push(action.payload);
      return copy;

    case REMOVE_CMT:
      copy[action.payload.id].comments.splice(action.payload.postId,1);
      return copy;

    default:
      return state;
  }
};


/* Detail: 단일 카드  */

const cardReducer = (state = {} , action = { type: '' }) => {
  let copy = {...state}

  switch(action.type){
    case GET_DETAIL:
      copy = {...action.payload};
      return copy;
    case ADD_LIKE:
      copy.likes++;
      return copy;
    default:
      return copy;
  }
}

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
    case ADD_SCRAP:
      return {...state, userScrap: [...state,...action.payload]}
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
  cardReducer
});

export default reducer;
