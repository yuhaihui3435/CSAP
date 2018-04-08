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
            state.drTitleList=obj.drTitleList
            state.opModelList=obj.opModelList
            state.diseaseList=obj.diseaseList

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
    }
}
export default doctorInfo