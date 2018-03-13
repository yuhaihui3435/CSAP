
import kit from '../../libs/kit';
const cards = {
    state: {
        cardsList: [],
        cardsList_00: [],
        cardsList_01: [],
        totalPage: 0,
        pageNumber: 1,
        totalRow: 0,
        cards: {},
        cardtypeList: [],
        salesmenList:[],
        cardapplyList:[],
        electronicPolicy:[],
        ds00List:[],
        ds01List:[],
        ds0100List:[],
    },
    mutations: {
        set_cards_page(state, page){
            state.cardsList = page.list
            state.totalPage = page.totalPage
            state.totalRow = page.totalRow
            state.pageNumber = page.pageNumber
        },
        set_cards_page_00(state, page){
            state.cardsList_00 = page.list

            for(var i=0;i<state.cardsList_00.length;i++){
                let c=state.cardsList_00[i];
                c['_disabled']=c.disabled;
            }

            state.totalPage = page.totalPage
            state.totalRow = page.totalRow
            state.pageNumber = page.pageNumber
        },
        set_cards_page_01(state, page){
            state.cardsList_01 = page.list

            for(var i=0;i<state.cardsList_01.length;i++){
                let c=state.cardsList_01[i];
                c['_disabled']=c.disabled;
            }

            state.totalPage = page.totalPage
            state.totalRow = page.totalRow
            state.pageNumber = page.pageNumber
        },
        set_cards(state, obj){
            if (obj != undefined)
                state.cards = Object.assign({},obj);
            else {
                state.cards = {};
            }
        },
        set_electronicPolicy(state, list){

                state.electronicPolicy = list;

        },
        set_cards_dataReady(state, obj){
            state.cardtypeList = obj.cardtypeList;
            state.salesmenList = obj.salesmenList;
        },
        set_ds_dataReady(state,obj){
            state.cardtypeList = obj.cardtypeList;
            state.salesmenList = obj.salesmenList;
        },
        set_cards_cardapply_list(state,list){
            state.cardapplyList=list
        },
        set_ds00_page(state,page){
            if(page.list) {
            state.ds00List = page.list
            let adc=0;
            let all={name:'合计:',id:'-1'};
            let auc=0;
            let aac=0;
            let aec=0;
            let aaec=0;

                for (let i = 0; i < state.ds00List.length; i++) {
                    adc = adc + state.ds00List[i].dCount;
                    auc = auc + state.ds00List[i].uCount;
                    aac = aac + state.ds00List[i].aCount;
                    aec = aec + state.ds00List[i].eCount;
                    aaec = aaec + state.ds00List[i].aeCount;
                }

                if (page.list.length > 0) {
                    all['dCount'] = adc;
                    all['uCount'] = auc;
                    all['aCount'] = aac;
                    all['eCount'] = aec;
                    all['aeCount'] = aaec;
                    state.ds00List.push(all);
                }


                state.totalPage = page.totalPage
                state.totalRow = page.totalRow
                state.pageNumber = page.pageNumber
            }else{
                state.ds00List=[];
                state.totalPage = 0
                state.totalRow = 0
                state.pageNumber = 1
            }
        },
        set_ds01_page(state,page){
            if(page.list) {
                state.ds01List = page.list
                let adc = 0;
                let all = {name: '合计:', id: '-1'};
                let auc = 0;
                let aac = 0;
                let aec = 0;
                let aaec = 0;
                for (let i = 0; i < state.ds01List.length; i++) {
                    adc = adc + state.ds01List[i].dCount;
                    auc = auc + state.ds01List[i].uCount;
                    aac = aac + state.ds01List[i].aCount;
                    aec = aec + state.ds01List[i].eCount;
                    aaec = aaec + state.ds01List[i].aeCount;
                }
                if (page.list.length > 0) {
                    all['dCount'] = adc;
                    all['uCount'] = auc;
                    all['aCount'] = aac;
                    all['eCount'] = aec;
                    all['aeCount'] = aaec;
                    all['_disableExpand']=true;
                    state.ds01List.push(all);
                }

                state.totalPage = page.totalPage
                state.totalRow = page.totalRow
                state.pageNumber = page.pageNumber
            }else{
                state.ds01List=[];
                state.totalPage = 0
                state.totalRow = 0
                state.pageNumber = 1
            }
        },
        set_ds0100_page(state,list){
            state.ds0100List = list
        }

    },
    actions: {
        cards_page: function ({commit, state}, param) {
            this._vm.$axios.post('/c03/page', param).then((res) => {
                commit('set_cards_page', res)
            });
        },
        cards_page_00: function ({commit, state}, param) {
            this._vm.$axios.post('/c03/page', param).then((res) => {
                commit('set_cards_page_00', res)
            });
        },
        cards_page_01: function ({commit, state}, param) {
            this._vm.$axios.post('/c03/page', param).then((res) => {
                commit('set_cards_page_01', res)
            });
        },
        cards_get: function ({commit, state}, param) {
            let vm = this._vm;
            return new Promise(function (resolve, reject) {
                vm.$axios.post('/c03/get', param).then((res) => {
                    commit('set_cards', res.cards)
                    commit('set_electronicPolicy', res.electronicPolicy)
                    resolve()
                });
            });
        },
        cards_dataReady: function ({commit, state}, param) {
            let vm = this._vm;
            return new Promise(function (resolve, reject) {
                vm.$axios.post('/c03/dataReady').then((res) => {
                    commit('set_cards_dataReady', res)
                });
            });
        },
        cards_cardapply_list:function ({commit, state}, param) {
            let vm = this._vm;
            return new Promise(function (resolve, reject) {
                vm.$axios.post('/c01/listByCardtypeId', param).then((res) => {
                    commit('set_cards_cardapply_list', res)
                });
            });
        },

        cards_lock: function ({commit, state}, param) {
            let vm = this._vm;
            return new Promise(function (resolve, reject) {
                vm.$axios.post('/c03/lock',param).then((res) => {
                    resolve(res)
                });
            });
        },
        cards_unlock: function ({commit, state}, param) {
            let vm = this._vm;
            return new Promise(function (resolve, reject) {
                vm.$axios.post('/c03/unlock',param).then((res) => {
                    resolve(res)
                });
            });
        },
        cards_unAct:function ({commit, state}, param) {
            let vm = this._vm;
            return new Promise(function (resolve, reject) {
                vm.$axios.post('/c03/unAct',param).then((res) => {
                    resolve(res)
                });
            });
        },
        cards_act_export:function ({commit, state}, param) {
            let vm = this._vm;
            return new Promise(function (resolve, reject) {
                vm.$axios.post('/c03/exportActCards',param).then((res) => {
                    resolve(res)
                });
            });
        },
        car_cards_act_export:function ({commit, state}, param) {
            let vm = this._vm;
            return new Promise(function (resolve, reject) {
                vm.$axios.post('/c03/exportActCarCards',param).then((res) => {
                    resolve(res)
                });
            });
        },
        ds00_page:function ({commit, state}, param) {
            this._vm.$axios.post('/c04/ds00', param).then((res) => {
                commit('set_ds00_page', res)
            });
        },
        ds01_page:function ({commit, state}, param) {
            this._vm.$axios.post('/c04/ds01', param).then((res) => {
                commit('set_ds01_page', res)
            });
        },
        ds0100_list:function ({commit, state}, param) {
            let vm = this._vm;
            return new Promise(function (resolve, reject) {

                vm.$axios.post('/c04/ds0100', param).then((res) => {
                    // commit('set_ds0100_page', res)
                    resolve(res)
                });
            });
        },
        ds_dataReady: function ({commit, state}, param) {
            let vm = this._vm;
            return new Promise(function (resolve, reject) {
                vm.$axios.post('/c04/dataReady').then((res) => {
                    commit('set_ds_dataReady', res)
                });
            });
        },

    }
}
export default cards