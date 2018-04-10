import kit from '../../libs/kit';
const  visit={
    state: {
        visitList:[],
        totalPage:0,
        pageNumber:1,
        totalRow:0,
        pageSIze:15,
        visit:{},
        drList:[],
    },
    mutations: {
        set_visit_list(state,page){
            state.visitList=page.list
            state.totalPage=page.totalPage
            state.pageNumber=page.pageNumber
            state.pageSize=page.pageSize
            state.totalRow=page.totalRow
        },
        visit_set(state,obj){
            if(obj !=undefined)
                state.visit=Object.assign({},obj);
        },
        set_dr(state,obj){
            state.drList=obj.drList

        }
      

    },
    actions:{
        visit_list:function ({ commit,state },param) {
            this._vm.$axios.post('/mt01/list',param).then((res)=>{
                commit('set_visit_list',res)
            });
        },
        visit_save:function ({ commit,state },action) {
            let vm=this._vm;
            return new Promise(function (resolve, reject) {
                vm.$axios.post('/mt01/'+action, state.visit).then((res) => {
                    resolve(res.resCode);
                })
            });
        },
        visit_del:function ({ commit,state },param) {
            let vm=this._vm;
            return new Promise(function (resolve, reject) {
                vm.$axios.post('/mt01/del', param).then((res) => {
                    resolve(res.resCode);
                });
            });
        },
        visit_res_load:function ({ commit,state },param) {
            let vm=this._vm;
            return new Promise(function (resolve, reject) {
                vm.$axios.post('/mt01/dr', param).then((res) => {
                    commit('set_dr',res)
                    resolve()
                });
            });
        },
    }
}
export default visit