/**
 * Created by yuhaihui on 2017/12/9.
 */
import kit from "../../libs/kit";
const depot = {
    state: {
        depotList: [],
        totalPage: 0,
        pageNumber: 1,
        totalRow: 0,
        totalPage1: 0,
        pageNumber1: 1,
        totalRow1: 0,
        depot: {},
        depot1: {},
        salesmenList: [],
        cardtypeList: [],
        cardapplyList:[],
        recommendNum:{},
        unlockRecordList:[]
    },
    mutations: {
        set_depot_page(state, page){
            state.depotList = page.list
            state.totalPage = page.totalPage
            state.pageNumber = page.pageNumber
            state.totalRow = page.totalRow
        },
        set_depot_list(state, list){
            state.depotList = list
        },
        set_depot(state, obj){
            let o={};
            if (obj != undefined)
                state.depot = Object.assign(o,obj);
        },
        set_depot1(state, obj){
            let o={};
            if (obj != undefined)
                state.depot1 = Object.assign(o,obj);
        },
        set_depot_dataReady(state, obj){
            state.cardtypeList = obj.cardtypeList;
            state.salesmenList = obj.salesmenList;
        },
        set_depot_cardapply_list(state,list){
            state.cardapplyList=list
        },
        set_depot_recommendNum(state,obj){
            state.recommendNum= Object.assign({},obj)
            Object.assign(state.depot,{bNum:obj.bNum});
        },
        set_depot_unlockRecordList(state,page){
            state.unlockRecordList=page.list;
            state.totalPage1 = page.totalPage
            state.pageNumber1 = page.pageNumber
            state.totalRow1 = page.totalRow
        }


    },
    actions: {
        depot_page: function ({commit, state}, param) {
            this._vm.$axios.post('/c02/page', param).then((res) => {
                commit('set_depot_page', res)
            });
        },
        depot_save: function ({commit, state}, param) {
            let vm = this._vm;
            let p = kit.clone(state.depot);
            return new Promise(function (resolve, reject) {
                vm.$axios.post('/c02/save' , p).then((res) => {
                    resolve(res.resCode);
                });
            });


        },
        depot_update: function ({commit, state}, param) {
            let vm = this._vm;
            return new Promise(function (resolve, reject) {
                vm.$axios.post('/c02/update', param).then((res) => {
                    resolve(res.resCode);
                });
            });
        },
        depot_create: function ({commit, state}, param) {
            let vm = this._vm;
            return new Promise(function (resolve, reject) {
                vm.$axios.post('/c02/actCard', param).then((res) => {
                    resolve(res.resCode);
                });
            });
        },
        depot_cardapply_list:function ({commit, state}, param) {
            let vm = this._vm;
            return new Promise(function (resolve, reject) {
                vm.$axios.post('/c01/listByCardtypeId', param).then((res) => {
                    commit('set_depot_cardapply_list', res)
                });
            });
        },
        depot_cardapply_list1:function ({commit, state}, param) {
            let vm = this._vm;
            return new Promise(function (resolve, reject) {
                vm.$axios.post('/c01/listByCardtypeId', param).then((res) => {
                    resolve(res)
                });
            });
        },
        depot_list: function ({commit, state}, param) {
            let vm = this._vm;
            return new Promise(function (resolve, reject) {
                vm.$axios.post('/c02/list', param).then((res) => {
                    commit('set_depot_list', res)
                });
            });
        },
        depot_get: function ({commit, state}, param) {
            let vm = this._vm;
            return new Promise(function (resolve, reject) {
                vm.$axios.post('/c02/get', param).then((res) => {
                    commit('set_depot', res)
                    resolve()
                });
            });
        },

        depot_dataReady: function ({commit, state}, param) {
            let vm = this._vm;
            return new Promise(function (resolve, reject) {
                vm.$axios.post('/c02/dataReady').then((res) => {
                    commit('set_depot_dataReady', res)
                });
            });
        },
        depot_unlockRecord_list: function ({commit, state}, param) {
            let vm = this._vm;
            return new Promise(function (resolve, reject) {
                vm.$axios.post('/c02/unlockRecord',param).then((res) => {
                    commit('set_depot_unlockRecordList', res)
                    resolve()
                });
            });
        },

        depot_recommendNum: function ({commit, state}, param) {
            let vm = this._vm;
            return new Promise(function (resolve, reject) {
                vm.$axios.post('/c01/recommendNum',param).then((res) => {
                    commit('set_depot_recommendNum', res)
                });
            });
        },
        depot_unlockRecord_save: function ({commit, state}, param) {
            let vm = this._vm;
            return new Promise(function (resolve, reject) {
                vm.$axios.post('/c02/saveUnlockRecord',param).then((res) => {
                    // commit('set_depot_unlockRecordList', res)
                    resolve(res)
                });
            });
        },


    }
}
export default depot
