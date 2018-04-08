import kit from '../../libs/kit';
import {formatDate} from '../../libs/date';
const  rss={
    state: {
        rssList:[],
        totalPage:0,
        pageNumber:1,
        totalRow:0,
        pageSIze:15,
        rss:{},
        areas:[]
    },
    mutations: {
        set_rss_list(state,page){
            state.rssList=page.list
            state.totalPage=page.totalPage
            state.pageNumber=page.pageNumber
            state.pageSize=page.pageSize
            state.totalRow=page.totalRow
        },
        rss_set(state,obj){
            if(obj !=undefined)
                state.rss=Object.assign({},obj);
        },
    },
    actions:{
        rss_list:function ({ commit,state },param) {
            this._vm.$axios.post('/w02/list',param).then((res)=>{
                commit('set_rss_list',res)
            });
        },
        rss_save:function ({ commit,state },action) {
            let vm=this._vm;
            if(state.rss.expiredAt){
                if( state.rss.expiredAt instanceof Date){
                    state.rss.expiredAt=formatDate(state.rss.expiredAt,'yyyy-MM-dd')
                }
            }
            return new Promise(function (resolve, reject) {
                vm.$axios.post('/w02/'+action, state.rss).then((res) => {
                    resolve(res.resCode);
                })
            });
        },
        rss_del:function ({ commit,state },param) {
            let vm=this._vm;
            return new Promise(function (resolve, reject) {
                vm.$axios.post('/w02/del', param).then((res) => {
                    resolve(res.resCode);
                });
            });
        },
    }
}
export default rss