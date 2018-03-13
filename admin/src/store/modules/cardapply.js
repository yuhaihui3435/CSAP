/**
 * Created by yuhaihui on 2017/12/9.
 */
import kit from "../../libs/kit";
const cardapply = {
    state: {
        cardapplyList: [],
        totalPage: 0,
        pageNumber: 1,
        totalRow: 0,
        cardapply: {},
        mediaList: [],
        cardtypeList: [],
    },
    mutations: {
        set_cardapply_page(state, page){
            state.cardapplyList = page.list
            state.totalPage = page.totalPage
            state.pageNumber = page.pageNumber
            state.totalRow = page.totalRow
        },
        set_cardapply_list(state, list){
            state.cardapplyList = list
        },
        set_cardapply(state, obj){
            if (obj != undefined)
                state.cardapply = Object.assign({},obj);
        },
        set_cardapply_dataReady(state, obj){
            state.cardtypeList = obj.cardtypeList;
            state.mediaList = obj.mediaList;
        },
    },
    actions: {
        cardapply_page: function ({commit, state}, param) {
            this._vm.$axios.post('/c01/page', param).then((res) => {
                commit('set_cardapply_page', res)
            });
        },
        cardapply_save: function ({commit, state}, param) {
            let vm = this._vm;
            let p = kit.clone(state.cardapply);
            return new Promise(function (resolve, reject) {
                vm.$axios.post('/c01/save' , p).then((res) => {
                    resolve(res.resCode);
                });
            });


        },
        cardapply_update: function ({commit, state}, param) {
            let vm = this._vm;
            return new Promise(function (resolve, reject) {
                vm.$axios.post('/c01/update', param).then((res) => {
                    resolve(res.resCode);
                });
            });
        },
        cardapply_create: function ({commit, state}, param) {
            let vm = this._vm;
            return new Promise(function (resolve, reject) {
                vm.$axios.post('/c01/createCard', param).then((res) => {
                    resolve(res.resCode);
                });
            });
        },

        cardapply_updateStatus: function ({commit, state}, param) {
            let vm = this._vm;
            return new Promise(function (resolve, reject) {
                vm.$axios.post('/c01/update', param).then((res) => {
                    resolve(res.resCode);
                });
            });
        },
        cardapply_list: function ({commit, state}, param) {
            let vm = this._vm;
            return new Promise(function (resolve, reject) {
                vm.$axios.post('/c01/list', param).then((res) => {
                    commit('set_cardapply_list', res)
                });
            });
        },
        cardapply_get: function ({commit, state}, param) {
            let vm = this._vm;
            return new Promise(function (resolve, reject) {
                vm.$axios.post('/c01/get', param).then((res) => {
                    commit('set_cardapply', res)
                    resolve()
                });
            });
        },

        cardapply_dataReady: function ({commit, state}, param) {
            let vm = this._vm;
            return new Promise(function (resolve, reject) {
                vm.$axios.post('/c01/dataReady').then((res) => {
                    commit('set_cardapply_dataReady', res)
                });
            });
        },



    }
}
export default cardapply
