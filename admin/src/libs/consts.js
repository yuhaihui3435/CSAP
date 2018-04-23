import env from '../../build/env';
let consts={

}
consts.env=(env == 'development' ? '/api' : '');
consts.imgUploadUrl=(env == 'development' ? '/api' : '') + '/cmn/act01'
consts.editBtn=(vm,h,param)=>{
    return h('Button', {
        props: {
            type: 'primary',
            size: 'small'
        },
        style: {
            marginRight: '5px'
        },
        on: {
            click: () => {
                vm.edit(param.row)
            }
        }
    }, '编辑')
};
consts.viewBtn=(vm,h,param)=>{
    return h('Button', {
        props: {
            type: 'primary',
            size: 'small'
        },
        style: {
            marginRight: '5px'
        },
        on: {
            click: () => {
                vm.view(param.row.id)
            }
        }
    }, '查看')
};


consts.delBtn=(vm,h,param)=>{
    return h('Poptip', {
        props: {
            confirm: '',
            title: '您确定要删除这条数据吗？'
        },
        style: {
            marginRight: '5px'
        },
        on: {
            'on-ok': () => {
                vm.del(param.row.id)
            }
        }
    }, [h('Button', {
        props: {
            type: 'error',
            size: 'small'
        }
    }, '删除')]);
};


consts.stopBtn=(vm,h,param)=>{
    return h('Poptip', {
        props: {
            confirm: '',
            title: '您确定要禁用这条数据吗？'
        },
        style: {
            marginRight: '5px'
        },
        on: {
            'on-ok': () => {
                vm.stop(param.row.id)
            }
        }
    }, [h('Button', {
        props: {
            type: 'error',
            size: 'small'
        }
    }, '禁用')]);
}
consts.actBtn=(vm,h,param)=>{
    return h('Poptip', {
        props: {
            confirm: '',
            title: '您确定要激活这条数据吗？'
        },
        style: {
            marginRight: '5px'
        },
        on: {
            'on-ok': () => {
                vm.active(param.row.id)
            }
        }
    }, [h('Button', {
        props: {
            type: 'success',
            size: 'small'
        }
    }, '激活')]);
}


consts.status=[{value:'0',label:'正常'},{value:'1',label:'禁用'}]
consts.yon=[{value:'0',label:'是'},{value:'1',label:'否'}]
consts.checkStatus=[{value:'00',label:'审核通过'},{value:'01',label:'等待审核'},{value:'02',label:'审核未通过'}]
consts.sexList=[{value:1,label:'男'},{value:2,label:'女'}]
consts.HH=['06','07','08','09','10','11','12','13','14','15','16','17','18','19','20']
consts.MM=['00','15','30','45']
consts.qn_url='http://images.cichlid.cc/'
export default consts