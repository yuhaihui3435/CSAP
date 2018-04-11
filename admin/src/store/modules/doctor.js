import kit from '../../libs/kit';
const  doctorInfo={
    state: {
        doctorInfoList:[],
        totalPage:0,
        pageNumber:1,
        totalRow:0,
        pageSIze:15,
        doctorInfo:{},
        diseaseList:[],
        opModelList:[],
        drTitleList:[],
        rApiList:[],
        hospList:[],
    },
    mutations: {
        set_doctorInfo_list(state,page){
            state.doctorInfoList=page.list
            state.totalPage=page.totalPage
            state.pageNumber=page.pageNumber
            state.pageSize=page.pageSize
            state.totalRow=page.totalRow
        },
        doctorInfo_set(state,obj){
            if(obj !=undefined)
                state.doctorInfo=Object.assign({},obj);
        },
        set_dr(state,obj){
            state.drTitleList=obj.drTitleList.map((val,key,arr)=>{
                return {value:val.id,label:val.title}
            })
            state.opModelList=obj.opModelList
            state.diseaseList=obj.diseaseList
            state.hospList=obj.hospList

        },
        set_rApi_list(state,list){
            state.rApiList=list

        }
        
    },
    actions:{
        doctorInfo_list:function ({ commit,state },param) {
            this._vm.$axios.post('/mt00/list',param).then((res)=>{
                commit('set_doctorInfo_list',res)
            });
        },
        doctorInfo_save:function ({ commit,state },action) {
            let vm=this._vm;
            return new Promise(function (resolve, reject) {
                vm.$axios.post('/mt00/'+action, state.doctorInfo).then((res) => {
                    resolve(res.resCode);
                })
            });
        },
        doctorInfo_del:function ({ commit,state },param) {
            let vm=this._vm;
            return new Promise(function (resolve, reject) {
                vm.$axios.post('/mt00/del', param).then((res) => {
                    resolve(res.resCode);
                });
            });
        },
        doctorInfo_res_load:function ({ commit,state },param) {
            let vm=this._vm;
            return new Promise(function (resolve, reject) {
                vm.$axios.post('/mt00/dr', param).then((res) => {
                    commit('set_dr',res)
                    resolve()
                });
            });
        },
        doctorInfo_top:function ({ commit,state },param) {
            let vm=this._vm;
            return new Promise(function (resolve, reject) {
                vm.$axios.post('/mt00/top', param).then((res) => {
                    resolve()
                });
            });
        },
        doctorInfo_cancel_top:function ({ commit,state },param) {
            let vm=this._vm;
            return new Promise(function (resolve, reject) {
                vm.$axios.post('/mt00/cancelTop', param).then((res) => {
                    resolve()
                });
            });
        },
        rApi_list:function ({ commit,state },param) {
            let vm=this._vm;
            return new Promise(function (resolve, reject) {
                vm.$axios.post('/mt02/list', param).then((res) => {
                    commit('set_rApi_list', res)
                    resolve();
                });
            });
        },
        rApi_save:function ({ commit,state },param) {
            let vm=this._vm;
            return new Promise(function (resolve, reject) {
                vm.$axios.post('/mt02/save', param).then((res) => {
                    resolve(res)
                });
            });
        },
        rApi_del:function ({ commit,state },param) {
            let vm=this._vm;
            return new Promise(function (resolve, reject) {
                vm.$axios.post('/mt02/del', param).then((res) => {
                    resolve(res)
                });
            });
        },
    }
}
export default doctorInfo