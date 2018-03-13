/**
 * Created by yuhaihui on 2017/12/9.
 */
import kit from '../../libs/kit';
const  salesmen={
    state: {
        salesmenList:[],
        totalPage:0,
        pageNumber:1,
        totalRow:0,
        salesmen:{},
        saleTypeList:[],
        saleLevelList:[],
        allSalesmenList:[],
    },
    mutations: {
        set_salesmen_page(state,page){
            state.salesmenList=page.list
            state.totalPage=page.totalPage
            state.pageNumber=page.pageNumber
            state.totalRow=page.totalRow
        },
        set_salesmen_list(state,list){
            state.salesmenList=list
            state.allSalesmenList=list;
        },
        set_salesmen(state,obj){
            if(obj !=undefined)
                state.salesmen=kit.clone(obj);
        },
        set_salesmen_dataReady(state,obj){
            state.saleTypeList=obj.saleTypeList;
            state.saleLevelList=obj.saleLevelList;
            state.allSalesmenList=obj.allSalesmenList;
        },
    },
    actions:{
        salesmen_page:function ({ commit,state },param) {
            this._vm.$axios.post('/sl00/page',param).then((res)=>{
                commit('set_salesmen_page',res)
            });
        },
        salesmen_save:function ({ commit,state },action) {
            let vm=this._vm;
            return new Promise(function (resolve, reject) {
                vm.$axios.post('/sl00/'+action, state.salesmen).then((res) => {
                    resolve(res.resCode);
                });
            });
        },
        salesmen_del:function ({ commit,state },param) {
            let vm=this._vm;
            return new Promise(function (resolve, reject) {
                vm.$axios.post('/sl00/del', param).then((res) => {
                    resolve(res.resCode);
                });
            });
        },
        salesmen_updateStatus:function ({ commit,state },param) {
            let vm=this._vm;
            return new Promise(function (resolve, reject) {
                vm.$axios.post('/sl00/updateStatus', param).then((res) => {
                    resolve(res.resCode);
                });
            });
        },
        salesmen_list:function ({ commit,state },param) {
            let vm=this._vm;
            return new Promise(function (resolve, reject) {
                vm.$axios.post('/sl00/list', param).then((res) => {
                    commit('set_salesmen_list',res)
                    resolve();
                });
            });
        },
        salesmen_get:function ({ commit,state },param) {
            let vm=this._vm;
            return new Promise(function (resolve, reject) {
                vm.$axios.post('/sl00/get', param).then((res) => {
                    res.type=res.type+'';
                    res.level=res.level+'';
                    res.pId=res.pId+'';
                    commit('set_salesmen',res)
                    resolve()
                });
            });
        },

        salesmen_dataReady:function ({ commit,state },param) {
            let vm=this._vm;
            return new Promise(function (resolve, reject) {
                vm.$axios.post('/sl00/dataReady').then((res) => {
                    commit('set_salesmen_dataReady',res)
                });
            });
        },


    }
}
export default salesmen
