import { ADD_LIKE, REMOVE_LIKE, ADD_VIEW } from './types'

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

