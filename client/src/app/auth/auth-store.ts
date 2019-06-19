import { reducer } from './auth-reducer';
import {createStore} from 'redux';
export const authStore = createStore(reducer);