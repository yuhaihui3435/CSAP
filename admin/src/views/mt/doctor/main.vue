<template>
    <div>
        <Row>
            <Col span="24">
            <Card>
                <p slot="title">
                    <Icon type="help-buoy"></Icon>
                    医生列表
                </p>
                <Row>
                    <Col span="4">
                    <Button type="primary" icon="android-add" @click="add">新增医生</Button>
                    </Col>
                    <Col span="20"  align="right">
                    <Input v-model="param.name" placeholder="请输入姓名..." style="width: 200px"/>
                    <Input v-model="param.email" placeholder="请输入EMAIL..." style="width: 200px"/>
                    <Input v-model="param.tel" placeholder="请输入电话..." style="width: 200px"/>
                    <Select v-model="param.hosp" placeholder="请选择医院..." style="width: 300px" :clearable="true">
                        <Option v-for="item in hospList" :value="item.id" :key="item.id">{{ item.title }}</Option>
                    </Select>
                        <Select v-model="param.sex" placeholder="请选择性别..." style="width: 100px" :clearable="true">
                            <Option v-for="item in sexList" :value="item.value" :key="item.value">{{ item.label }}</Option>
                        </Select>


                        <Select v-model="param.status" placeholder="请选择状态..." style="width: 100px" :clearable="true">
                            <Option v-for="item in statusList" :value="item.value" :key="item.value">{{ item.label }}</Option>
                        </Select>

                    <span @click="search" style="margin: 0 10px;"><Button type="primary" icon="search">搜索</Button></span>
                    </Col>
                </Row>
                <Row class="margin-top-10">
                    <Table :context="self" border :data="doctorInfoList" :columns="tableColums" stripe></Table>
                </Row>
                <div style="margin: 10px;overflow: hidden">
                    <div style="float: right;">
                        <Page :total="total" :current="pageNumber" :page-size="pageSize" @on-change="changePage" show-total show-elevator></Page>
                    </div>
                </div>
            </Card>
            </Col>
        </Row>
        <drForm ref="rf"></drForm>
        <rApiSet ref="ras"></rApiSet>
    </div>
</template>

<script>
    import {mapState} from 'vuex'
    import drForm from './form.vue'
    import rApiSet from './rApiSet.vue'
    import consts from '../../../libs/consts'
    const editBtn=(vm,h,param)=>{
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
    const rApiBtn=(vm,h,param)=>{
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
                    vm.rApiSet(param.row)
                }
            }
        }, '外部挂号网址设置')
    };
    const delBtn=(vm,h,param)=>{
        return h('Poptip', {
            props: {
                confirm: '',
                title: '您确定要删除这个医生信息吗？'
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
    const topBtn=(vm,h,param)=>{
        return h('Poptip', {
            props: {
                confirm: '',
                title: '您确定要置顶这个医生信息吗？'
            },
            style: {
                marginRight: '5px'
            },
            on: {
                'on-ok': () => {
                    vm.top(param.row.id)
                }
            }
        }, [h('Button', {
            props: {
                type: 'error',
                size: 'small'
            }
        }, '置顶')]);
    };
    const cancel_topBtn=(vm,h,param)=>{
        return h('Poptip', {
            props: {
                confirm: '',
                title: '您确定要取消置顶这个医生信息吗？'
            },
            style: {
                marginRight: '5px'
            },
            on: {
                'on-ok': () => {
                    vm.cancel_top(param.row.id)
                }
            }
        }, [h('Button', {
            props: {
                type: 'error',
                size: 'small'
            }
        }, '取消置顶')]);
    };

    export default {

        computed: {
            ...mapState({
                'doctorInfoList': state => state.doctorInfo.doctorInfoList,
                'totalPage': state => state.doctorInfo.totalPage,
                'total': state => state.doctorInfo.totalRow,
                'pageNumber': state => state.doctorInfo.pageNumber,
                'pageSize': state => state.doctorInfo.pageSize,
                'doctorInfo': state => state.doctorInfo.doctorInfo,
                'hospList': state => state.doctorInfo.hospList,
            })
        },
        methods: {
            del(i){
                this.$store.dispatch('doctorInfo_del',{'ids':i}).then((res)=>{
                    if (res && res == 'success') {
                        this.$store.dispatch('doctorInfo_list')
                    }
                });
            },
            edit(cls){
                this.$store.commit('doctorInfo_set',cls);
                this.$refs.rf.open('编辑医生数据',false);
            },
            add(){
                this.$refs.rf.open('新增医生数据',true)
            },
            changePage(pn){
                this.param.pn=pn;
                this.$store.dispatch('doctorInfo_list',this.param)
            },
            search(){
                this.$store.dispatch('doctorInfo_list',this.param)
            },
            top(id){
                let vm=this
                this.$store.dispatch('doctorInfo_top',{id:id}).then(()=>{
                    vm.search()
                })
            },
            cancel_top(id){
                let vm=this
                this.$store.dispatch('doctorInfo_cancel_top',{id:id}).then(()=>{
                    vm.search()
                })
            },
            rApiSet(row){
                let vm=this
                this.$store.dispatch('rApi_list',{drId:row.id}).then(()=>{
                    this.$refs.ras.open(row.id)
                })

            }
        },
        mounted (){
            let vm=this;
            this.$store.dispatch('doctorInfo_res_load').then(function () {
                    vm.$store.dispatch('doctorInfo_list')
                }
            )
        },
        components: {
            drForm: drForm,
            rApiSet:rApiSet
        },
        data () {
            return {
                param:{name:'',email:'',tel:'',sex:'',status:'',hosp:''},
                self: this,
                sexList:consts.sexList,
                statusList:consts.status,
                tableColums: [

                    {
                        title: '名称',
                        key: 'name',
                    },
                    {
                        title: '性别',
                        key: 'sexTxt',
                    },
                    {
                        title: '联系电话',
                        key: 'tel1',
                    },
                    {
                        title: 'EMAIL',
                        key: 'email',
                    },
                    {
                        title: '微信号',
                        key: 'weixin',
                    },
                    {
                        title: '医院',
                        key: 'hospTxt',
                    },
                    {
                        title: '执照号',
                        key: 'licenseNo',
                    },

                    {
                        title: '状态',
                        key: 'statusTxt',
                    },
                    {
                        title: '操作',
                        key: 'action',
                        width: 200,
                        align: 'center',
                        render:(h,param)=>{
                            let btns=[editBtn(this,h,param),delBtn(this,h,param),rApiBtn(this,h,param)]
                            if(param.row.ifTop!='0')
                                btns.push(topBtn(this,h,param))
                            else
                                btns.push(cancel_topBtn(this,h,param))
                            return h('div', btns)
                        }
                    }

                ]
            }
        }
    }
</script>
<style lang="less">
    @import '../../../styles/common.less';
</style>