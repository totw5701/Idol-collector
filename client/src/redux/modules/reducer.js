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

    case ADD_LIKE:
      copy.find(c => c.id === action.id ).likes++;
      return copy;

    case REMOVE_CARD:
      copy.splice(action.id,1);
      return copy;


// cmt,ncmt 모두 post 내의 card에서 접근해야 됨 => dispatch 할 때 card의 id를 cardId로 받아오기
// 댓글, 대댓글... api 분리해서 받아온 뒤 reducer 분리하는 게 좋지 않을까? 아래처럼 데이터를 순회하면 너무 비효율적인데

    case ADD_CMT:
      copy.find(c=> c.id === action.cardId).comments.push(action.payload);
      return copy;

    case REMOVE_CMT:
      let card = copy.find(c=> c.id === action.cardId );
      copy.find(c=> c.id === action.cardId ).comments = card.comments.filter(cmt => cmt.id !== action.cmtId);//댓글, 대댓글 둘다 id가 속성명
      return copy;

    case UPDATE_CMT:
      copy.find(c=> c.id === action.cardId ).comments.find(cmt=> cmt.id === action.cmtId).content = action.content;
      return copy;

    case LIKE_CMT:
      copy.find(c=> c.id === action.cardId ).comments.find(cmt=> cmt.id === action.cmtId).likes++;
      //console.log(copy.find(c=> c.id === action.cardId ).comments);
      return copy;

    case ADD_NCMT:
      copy.find(c=> c.id === action.cardId )
      .comments.find(cmt=> cmt.id === action.cmtId).nestedComments.push(action.payload);
      return copy;

    case REMOVE_NCMT:
      let nComments = copy.find(c=> c.id === action.cardId )
      .comments.find(cmt=> cmt.id === action.cmtId).nestedComments.filter(nCmt => nCmt.id !== action.nCmtId);

      copy.find(c=> c.id === action.cardId )
      .comments.find(cmt=> cmt.id === action.cmtId).nestedComments = nComments;

      return copy;

    case UPDATE_NCMT:

      copy.find(c=> c.id === action.cardId ).comments.find(cmt=> cmt.id === action.cmtId)
      .nestedComments.find(nCmt => nCmt.id === action.nCmtId).content = action.content;

      return copy;

    case LIKE_NCMT:
      copy.find(c=> c.id === action.cardId ).comments.find(cmt=> cmt.id === action.cmtId)
      .nestedComments.find(nCmt => nCmt.id === action.nCmtId).likes++;

      console.log(copy.find(c=> c.id === action.cardId ).comments.find(cmt=> cmt.id === action.cmtId).nestedComments);
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
      console.log(copy)
      console.log('ddfs')
      return copy;
/*    case ADD_LIKE:
      copy.likes++;
      return copy;*/
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
