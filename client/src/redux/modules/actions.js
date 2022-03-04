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
import ApiService from '../../ApiService';
import ApiService2 from '../../ApiService2';
import axios from 'axios';

export const getHome = async () => {
  let request = await ApiService.getHome().then((result => console.log(result)))
  .catch(err => { console.log(err)})

/*  return {
    type: GET_HOME,
    payload: request.data.data.cards
  }  */
  return {
    type: GET_HOME,
    payload:  [  {
                 id: 25,
                 authorNickName: 'nickname20',
                 authorId: 'id20',
                 title: '버논',
                 content: '세븐틴 버논',
                 createDate: '2021-12-21',
                 views: 2000,
                 likes: 200,
                 storeFileName:
                   'http://file2.instiz.net/data/cached_img/upload/2017/12/10/22/ea157bc4974420076209cce9df096bd9.jpg',
               }]
  }

}

export const addLike = id => {
  ApiService.putCardLike(id)
    .then(result => {
      console.log('카드 좋아요 완료');
    })
    .catch(err => {
      console.log('putCardLike axios 에러! ' + err);
    });
  return {
    type: ADD_LIKE,
    id: id,
  };
};

export const removeLike = id => {
  return {
    type: REMOVE_LIKE,
    id: id,
  };
};

export const addView = id => {
  return {
    type: ADD_VIEW,
    id: id,
  };
};

/* 회원 정보 가져오기 */
export const getMember = async () => {
  let request = await ApiService.getHome().catch(err => {
    console.log('getMember actions 에러! ' + err);
  });

  return {
    type: GET_MEMBER,
    payload: request.data.data.member,
  };
};

// 마이페이지 내 유저가 생성한 카드 보이기
export const getUserCard = async () => {
  const request = await ApiService2.getCardBundleInfo().then(res => {
    console.log(res);
  });
  return {
    type: USER_CARD,
    payload: request,
  };
};

// 마이페이지 내 유저가 스크랩한 카드 보이기
export const getUserScrap = async () => {
  const request = await ApiService2.getCardBundleInfo().then(res => {
    console.log(res);
  });
  return {
    type: USER_SCRAP,
    payload: request,
  };
};

// setting page 내 사용자 정보
export const getUserInfo = async page => {
  const request = await ApiService2.getUserInfo(page).then(res => {
    console.log(res);
  });
  return {
    type: USER_INFO,
    payload: request,
  };
};

// setting page 내 사용자 이미지

export const getUserPhoto = async fileName => {
  const request = await ApiService2.getUserImage(fileName).then(res => {
    console.log(res);
  });
  return {
    type: USER_PHOTO,
    payload: request,
  };
};
