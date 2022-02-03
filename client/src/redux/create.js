import { createStore,applyMiddleware } from 'redux';
import { composeWithDevTools } from 'redux-devtools-extension';
import reducer from './modules/reducer';
import ReduxThunk from 'redux-thunk'

const create = createStore(reducer, composeWithDevTools(applyMiddleware(ReduxThunk)));

/* reducer: combineReducer({안의 reducer들}),*/
/* composeWithDevTools(): 리덕스 개발 도구
    현재 store의 상태를 개발자 도구에서 조회가능(action, dispatch 기록)
*/
export default create;
