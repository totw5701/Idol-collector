import axios from 'axios'

class ApiService {

  /* 로그인시 메인페이지 post */
  getHome() {
    return axios.get( '/api/home' )
  }
  /* 새로운 카드 생성 */
  postCreate(newCard) {
    return axios.post( '/api/card/create', newCard )
  }
}

export default new ApiService()