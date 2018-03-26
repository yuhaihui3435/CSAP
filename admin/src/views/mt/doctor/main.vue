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
                    <Col span="8">
                    <Button type="primary" icon="android-add" @click="add">新增医生</Button>
                    <Button type="primary" icon="search" @click="refresh">刷新</Button>
                    </Col>
                </Row>
                <Row class="margin-top-10">
                    <Table :context="self" border :data="doctorInfoList" :columns="tableColums" stripe></Table>
                </Row>
                <div style="margin: 10px;overflow: hidden">
                    <div style="float: right;">
                        <Page :total="total" :current="pageNumber" @on-change="changePage" show-total show-elevator></Page>
                    </div>
                </div>
            </Card>
            </Col>
        </Row>
        <clsForm ref="rf"></clsForm>
    </div>
</template>

<script>
    import {mapState} from 'vuex'
    import drForm from './form.vue'
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

    export default {

        computed: {
            ...mapState({
                'doctorInfoList': state => state.doctorInfo.doctorInfoList,
                'totalPage': state => state.doctorInfo.totalPage,
                'total': state => state.doctorInfo.totalRow,
                'pageNumber': state => state.doctorInfo.pageNumber,
                'doctorInfo': state => state.doctorInfo.doctorInfo,
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
                this.$store.dispatch('doctorInfo_list',{pn:pn})
            },
            refresh(){
                this.$store.dispatch('doctorInfo_list')
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
            clsForm: clsForm,
        },
        data () {
            return {
                self: this,
                tableColums: [

                    {
                        title: '名称',
                        key: 'name',
                    },
                    {
                        title: '所属区域',
                        key: 'areaTxt',
                    },
                    {
                        title: '医生图片',
                        key: 'name',
                    },
                    {
                        title: '目标链接',
                        key: 'linkTo',
                    },
                    {
                        title: '操作员',
                        key: 'operTxt',
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
                            return h('div', [
                                editBtn(this,h,param),
                                delBtn(this,h,param),

                            ])
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