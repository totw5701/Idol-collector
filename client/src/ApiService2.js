import axios from 'axios';

class ApiService2 {

  /* 회원 */

  postBlameUser() {
    // 신고
    return axios.post('/api/member/blame');
  }

  getUserImage(fileName) {
    // image 보기
    return axios.get('/api/member/image' + fileName);
  }

  getAnotherUser(id, page) {
    // user 정보보기
    return axios.get('/api/member/member' + id, page);
  }

  getUserInfo(page) {
    // 내 정보 보기
    return axios.get('/api/member/mypage' + page);
  }

  patchUserInfo() {
    // 내 정보 변경
    return axios.put('/api/member/mypage');
  }

  getNotice() {
    // 알림확인
    return axios.get('/api/member/notice');
  }

  /* 카드집 */

  getNotice(id) {
    // 카드집 정보
    return axios.get('/api/bundle' + id);
  }
    // 카드집에 카드추가
  postAddCardBundle() {
    return axios.post('/api/bundle/add-card')
  }
    // 카드집 생성
  postCreateCardBundle() {
    return axios.post('/api/bundle/create')
  }
    // 카드집 삭제
  delCardBundle() {
    return axios.post('/api/bundle/delete-card')
  }
}

export default new ApiService2();
