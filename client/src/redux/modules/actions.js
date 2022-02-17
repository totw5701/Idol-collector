import { ADD_LIKE, REMOVE_LIKE, ADD_VIEW, USER_INFO } from './types'

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

export const userInfo = (id) => {
  return {
    type: USER_INFO,
    id: id
  }
}