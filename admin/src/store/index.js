import Vue from 'vue';
import Vuex from 'vuex';

import app from './modules/app';
import user from './modules/user';
import param from './modules/param';
import role from './modules/role';
import art from './modules/art';
import res from './modules/res';
import tax from './modules/tax';
import home from './modules/home';
import carouselSetting from './modules/carouselSetting';
import doctorInfo from './modules/doctor';
import rss from './modules/rss';
import visit from './modules/visit';
import medicalrecords from './modules/medicalrecords';

Vue.use(Vuex);

const store = new Vuex.Store({
    state: {
        spinShow:false,
        uploadPicMaxSize:5242880,
        ignoreSpinshow:false,

    },
    mutations: {
        upadteSpinshow(state,p){
            if(!this.state.ignoreSpinshow)
                state.spinShow=p;
        },
        updateIgnoreSpinshow(state,p){
            state.ignoreSpinshow=p
        },


    },
    actions: {

    },
    modules: {
        app,
        user,
        param,
        role,
        art,
        res,
        tax,
        home,
        carouselSetting,
        doctorInfo,
        rss,
        visit,
        medicalrecords,
    }
});



export default store;
