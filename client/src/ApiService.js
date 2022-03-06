import axios from 'axios'

class ApiService {

/* 루트 페이지 */
  getHome() { // 로그인시 메인페이지 post
    return axios.get( '/api/home' )
  }

  getHomePage( page ) { //위페이지에서 화면에 보이는 카드 페이지 수 param해서 지정
    return axios.get( '/api/home/'+ page )
  }

  getSearch( param ) { //태그로 검색한 결과 페이지
    return axios.get( '/api/search/', param )
  }

  getSearchPage( page, param ) { //위 페이지에서 화면에 보인는 개수 지정
    return axios.get( '/api/search/'+page, param )
  }


/* 카드 */

  postCard( newCard ) { // 카드 생성
    return axios.post( '/api/cards', newCard )
  }

  getCardId( id ) { // 카드 상세정보
    return axios.get( '/api/cards/'+id )
  }

  delCardId( id ) { // 카드 삭제
    return axios.delete( '/api/cards/'+id )
  }

  getCardImage( fileName ) { // 이미지 파일 받아오기
    return axios.get( '/api/cards/image/'+fileName )
  }

  putCardLike( id ) { // 카드 좋아요
    return axios.patch( '/api/cards/like/'+id )
  }

  putCardScrap( id ) { // 카드 스크랩
    return axios.put( '/api/cards/scrap/'+id )
  }

  delCardUnscrap( id ) { // 카드 스크랩 취소
    return axios.delete( '/api/cards/scrap/'+id )
  }

  putCardUpdate(form) { // 카드 수정
    return axios.patch( '/api/cards', form )
  }

/* 댓글 */

  postCmt( comment ) { // 댓글 달기
    return axios.post( '/api/comments', comment )
  }

  delCmtId( id ) { // 댓글 삭제
    return axios.delete( '/api/comments/'+id )
  }

  putCmtUpdate( comment) { // 댓글 수정
    return axios.patch( '/api/comments', comment )
  }

  putCmtLike( id ) { // 좋아요
    return axios.patch( '/api/comments/like/'+id )
  }

/* 대댓글 */

  postNCmt( nComment ) { // 대댓글 생성
    return axios.post( '/api/n-comments', nComment )
  }

  delNCmtId( id ) { // 대댓글 삭제
    return axios.delete( '/api/n-comments/'+id )
  }

  putNCmtLike( id ) { // 대댓글 좋아요
    return axios.patch( '/api/n-comments/like/'+id )
  }

  putNCmtUpdate( nComment ) { // 대댓글 수정
    return axios.patch( '/api/n-comments', nComment )
  }



}

export default new ApiService()