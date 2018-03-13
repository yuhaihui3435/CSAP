/**
 * Created by yuhaihui on 2017/12/9.
 */
import kit from "../../libs/kit";
const cardtype = {
    state: {
        cardtypeList: [],
        totalPage: 0,
        pageNumber: 1,
        totalRow: 0,
        cardtype: {},
        cttList: [],
        ioList: [],
    },
    mutations: {
        set_cardtype_page(state, page){
            state.cardtypeList = page.list
            state.totalPage = page.totalPage
            state.pageNumber = page.pageNumber
            state.totalRow = page.totalRow
        },
        set_cardtype_list(state, list){
            state.cardtypeList = list
        },
        set_cardtype(state, obj){
            if (obj != undefined)
                state.cardtype = Object.assign({},obj);
        },
        set_cardtype_dataReady(state, obj){
            state.cttList = obj.cttList;
            state.ioList = obj.ioList;
        },
        set_cardtype_serviceCert(state, txt){
                state.cardtype.serviceCert = txt;
        },
        set_cardtype_protocol(state, txt){
                state.cardtype.protocol = txt;
        },

    },
    actions: {
        cardtype_page: function ({commit, state}, param) {
            this._vm.$axios.post('/c00/page', param).then((res) => {
                commit('set_cardtype_page', res)
            });
        },
        cardtype_save: function ({commit, state}, param) {
            let vm = this._vm;
            let p = kit.clone(state.cardtype);
            // p.serviceCert = param.serviceCert
            // p.protocol = param.protocol
            p.phAgeToplmt=(p.phAgeToplmt == undefined||p.phAgeToplmt=='')?'1':p.phAgeToplmt;
            p.phAgeLowerlmt=(p.phAgeLowerlmt == undefined||p.phAgeLowerlmt=='')?'1':p.phAgeLowerlmt;
            p.ipAgeToplmt=(p.ipAgeToplmt == undefined||p.ipAgeToplmt=='')?'1':p.ipAgeToplmt;
            p.ipAgeLowerlmt=(p.ipAgeLowerlmt == undefined||p.ipAgeLowerlmt=='')?'1':p.ipAgeLowerlmt;
            p.ipAgeToplmtDay=(p.ipAgeToplmtDay == undefined||p.ipAgeToplmtDay=='')?'1':p.ipAgeToplmtDay;
            let uploadList = param.uploadList;
            let fd = new FormData();
            let i=0;
            for(let f of uploadList) {
                fd.append("file"+i, f);
                i++;
            }
            for (let s in p) {
                fd.append(s, p[s])
            }
            let config = {
                headers: {
                    'Content-Type': 'multipart/form-data'  
                }
            };
            return new Promise(function (resolve, reject) {
                vm.$axios.post('/c00/' + param.action, fd, config).then((res) => {
                    resolve(res.resCode);
                });
            });


        },
        cardtype_del: function ({commit, state}, param) {
            let vm = this._vm;
            return new Promise(function (resolve, reject) {
                vm.$axios.post('/c00/del', param).then((res) => {
                    resolve(res.resCode);
                });
            });
        },

        cardtype_del_file: function ({commit, state}, param) {
            let vm = this._vm;
            return new Promise(function (resolve, reject) {
                vm.$axios.post('/c00/delFile', param).then((res) => {
                    resolve(res.resCode);
                });
            });
        },
        cardtype_updateStatus: function ({commit, state}, param) {
            let vm = this._vm;
            return new Promise(function (resolve, reject) {
                vm.$axios.post('/c00/updateStatus', param).then((res) => {
                    resolve(res.resCode);
                });
            });
        },
        cardtype_list: function ({commit, state}, param) {
            let vm = this._vm;
            return new Promise(function (resolve, reject) {
                vm.$axios.post('/c00/list', param).then((res) => {
                    commit('set_cardtype_list', res)
                });
            });
        },
        cardtype_get: function ({commit, state}, param) {
            let vm = this._vm;
            return new Promise(function (resolve, reject) {
                vm.$axios.post('/c00/get', param).then((res) => {
                    res.type = res.type + '';
                    res.category = res.category==undefined?'':res.category + '';
                    res.phAgeToplmt=parseInt(res.phAgeToplmt);
                    res.phAgeLowerlmt=parseInt(res.phAgeLowerlmt);
                    res.ipAgeToplmt=parseInt(res.ipAgeToplmt);
                    res.ipAgeLowerlmt=parseInt(res.ipAgeLowerlmt);
                    res.ipAgeToplmtDay=parseInt(res.ipAgeToplmtDay);
                    res.manyPeople=res.manyPeople;
                    res.peopleCount=parseInt(res.peopleCount);
                    res.finiteEffect=parseInt(res.finiteEffect);
                    res.actCount=parseInt(res.actCount);
                    res.faceVal=res.faceVal+'';
                    commit('set_cardtype', res)
                    resolve()
                });
            });
        },

        cardtype_dataReady: function ({commit, state}, param) {
            let vm = this._vm;
            return new Promise(function (resolve, reject) {
                vm.$axios.post('/c00/dataReady').then((res) => {
                    commit('set_cardtype_dataReady', res)
                });
            });
        },


    }
}
export default cardtype
