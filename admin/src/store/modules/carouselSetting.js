import kit from '../../libs/kit';
const  carouselSetting={
    state: {
        carouselSettingList:[],
        totalPage:0,
        pageNumber:1,
        totalRow:0,
        pageSIze:15,
        carouselSetting:{},
        areas:[]
    },
    mutations: {
        set_carouselSetting_list(state,page){
            state.carouselSettingList=page.list
            state.totalPage=page.totalPage
            state.pageNumber=page.pageNumber
            state.pageSize=page.pageSize
            state.totalRow=page.totalRow
        },
        carouselSetting_set(state,obj){
            if(obj !=undefined)
                state.carouselSetting=kit.clone(obj);
        },
        set_dr(state,obj){
            state.areas=obj.areasList
        }
    },
    actions:{
        carouselSetting_list:function ({ commit,state },param) {
            this._vm.$axios.post('/w01/list',param).then((res)=>{
                commit('set_carouselSetting_list',res)
            });
        },
        carouselSetting_save:function ({ commit,state },action) {
            let vm=this._vm;
            return new Promise(function (resolve, reject) {
                vm.$axios.post('/w01/'+action, state.carouselSetting).then((res) => {
                    resolve(res.resCode);
                })
            });
        },
        carouselSetting_del:function ({ commit,state },param) {
            let vm=this._vm;
            return new Promise(function (resolve, reject) {
                vm.$axios.post('/w01/del', param).then((res) => {
                    resolve(res.resCode);
                });
            });
        },
        carouselSetting_res_load:function ({ commit,state },param) {
            let vm=this._vm;
            return new Promise(function (resolve, reject) {
                vm.$axios.post('/w01/dr', param).then((res) => {
                    commit('set_dr',res)
                    resolve()
                });
            });
        },
    }
}
export default carouselSetting