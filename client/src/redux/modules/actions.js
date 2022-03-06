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
  LIKE_NCMT
} from './types';
import ApiService from '../../ApiService';
import ApiService2 from '../../ApiService2';
import axios from 'axios';

/* 루트 페이지 */
  //첫페이지 0
export const getHome = async () => {
  let request = await ApiService.getHome()
  //.then((result => console.log(result)))
  .catch(err => { console.log(err)})

/*  return {
    type: GET_HOME,
    payload: request.data.data.cards
  }  */
  return {
    type: GET_HOME,
    payload: [ {
                 id: 43,
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

 // 회원정보 가져오기
export const getMember = async () => {
  let request = await ApiService.getHome().catch(err => {
    console.log('getMember actions 에러! ' + err);
  });

  return {
    type: GET_MEMBER,
    payload: request.data.data.member,
  };
};


/* 카드 */

// 카드 생성
export const addCard = async (newCard) => {
  const request = await ApiService.postCard(newCard)
  .catch( (err) => { console.log(err+ 'card/create axios실패!') } )

  return {
    type: ADD_CARD,
    payload:   {
                 id: 6,
                 authorNickName: 'nickname6',
                 authorId: 'id6',
                 title: '아이유',
                 content: '아이유 나무위키 사진',
                 createDate: '2021-12-21',
                 views: 600,
                 likes: 60,
                 storeFileName:
                   'https://w.namu.la/s/40de86374ddd74756b31d4694a7434ee9398baa51fa5ae72d28f2eeeafdadf0c475c55c58e29a684920e0d6a42602b339f8aaf6d19764b04405a0f8bee7f598d2922db9475579419aac4635d0a71fdb8a4b2343cb550e6ed93e13c1a05cede75',
               }
  }

/*  return {
    type: ADD_CARD,
    payload: request.data.data
  }*/
}

//카드 삭제
export const removeCard = id => {

  ApiService.delCardId(id)
    .then((result) => {
      console.log('카드 삭제완료')
    })
    .catch((err) => {
      console.log('delCardId axios 에러! '+err )
    })

  return {
    type: REMOVE_CARD,
    id: id
  }

}

// 카드 수정


// 카드 좋아요
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
    id: id
  };
};



// 스크랩
export const addScrap = async (id) => {
/*   const request = await ApiService.putCardScrap(id)
    .catch((err) => {
       console.log('putCardScrap axios 에러! '+err )
     })

 return {
    type: ADD_SCRAP,
    payload: request.data.data
  }*/

  return { //더미
    type: ADD_SCRAP,
    payload: [  {
                 authorId: 3,
                 authorNickName: "회원 별명",
                 createDate: "2022-02-06T15:19:49.146Z",
                 comments: [

                 ],
                 content: "설명은 파란배경 아이돌 사진",
                 didLike: true,
                 didScrap: true,
                 id: 4,
                 likes: 10000,
                 oriFileName: "string",
                 storeFileName:
                'https://img.koreatimes.co.kr/upload/newsV2/images/202108/c6758c3ec6454152bf0d29e969caac1c.jpg/dims/resize/740/optimize',
                tags: [{name:'아무나'},{name:'아이돌'}
                   ],
                 title: "방탄 버터.",
                 views: 12
                 }]
  }

}

// 스크랩 취소
export const removeScrap = async (id) => {
  const request = await ApiService.delCardUnscrap(id)
    .catch((err) => {
      console.log('delCardUnscrap axios 에러! '+err )
    })

  return {
    type: REMOVE_SCRAP,
    payload: request.data.data //아마 스크랩한 카드의 id일듯
  }
}

// 카드 수정

/* 댓글 */

// 댓글 달기
export const addCmt = async (content,cardId) => {
  const request = await ApiService.postCmt( { content: content, postId: cardId } )
    .catch((err) => {
        console.log('postCmt axios 에러!'+ err )
    })

  return {
    type: ADD_CMT,
    cardId: cardId,
    payload: {id: 2, authorId: 2, content: '더미' }//request.data.data
  }
}
// 댓글 삭제
export const removeCmt = async (cardId,cmtId) => {
  const request = ApiService.delCmtId(Number(cmtId))
  .catch((err)=> {
    console.log('delCmtId axios 에러!'+ err )
  })

  return {
    type: REMOVE_CMT,
    cardId: cardId,
    cmtId: cmtId
  }
}
// 댓글 수정
export const updateCmt = async (cardId, cmtId, content) => {

  const request = ApiService.putCmtUpdate({ id: Number(cmtId), content: content })
    .catch((err)=> {
      console.log('putCmtUpdate axios 에러!'+ err )
    })

  return {
    type: UPDATE_CMT,
    cardId: cardId,
    payload:{id: 2, authorId: 2, content: '더미수정' } //request.data.data
  }
}

// 댓글 좋아요
export const likeCmt = async (cardId, cmtId) => {

  const request = ApiService.putCmtLike(Number(cmtId))
    .catch((err)=> {
      console.log('putCmtLike axios 에러!'+ err )
    })

  return {
    type: LIKE_CMT,
    cardId: cardId,
    cmtId: cmtId
  }
}


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
