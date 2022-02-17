import { ADD_LIKE, REMOVE_LIKE, ADD_VIEW, GET_MEMBER } from './types'
import ApiService from '../../ApiService'
import axios from 'axios'

export const addLike = (id) => {
  return {
    type: ADD_LIKE,
    id: id
  }
}

export const removeLike = (id) => {
  return {
    type: REMOVE_LIKE,
    id: id
  }
}

export const addView = (id) => {
  return {
    type: ADD_VIEW,
    id: id
  }
}

/* 회원 정보 가져오기 */
export const getMember = async () => {

  let home = await ApiService.getHome()
  .catch((err) => {
    console.log('getMember actions 에러! '+err )
  })

  return {
    type: GET_MEMBER,
    payload: home.data.data.member
  }

}
