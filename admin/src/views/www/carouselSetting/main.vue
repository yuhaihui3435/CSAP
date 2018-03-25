<template>
    <div>
        <Row>
            <Col span="24">
            <Card>
                <p slot="title">
                    <Icon type="help-buoy"></Icon>
                    轮播列表
                </p>
                <Row>
                    <Col span="8">
                    <Button type="primary" icon="android-add" @click="add">新增轮播</Button>
                    <Button type="primary" icon="search" @click="refresh">刷新</Button>
                    </Col>
                </Row>
                <Row class="margin-top-10">
                    <Table :context="self" border :data="carouselSettingList" :columns="tableColums" stripe></Table>
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
    import clsForm from './form.vue'
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
                title: '您确定要删除这个轮播信息吗？'
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
                'carouselSettingList': state => state.carouselSetting.carouselSettingList,
                'totalPage': state => state.carouselSetting.totalPage,
                'total': state => state.carouselSetting.totalRow,
                'pageNumber': state => state.carouselSetting.pageNumber,
                'carouselSetting': state => state.carouselSetting.carouselSetting,
            })
        },
        methods: {
            del(i){
                this.$store.dispatch('carouselSetting_del',{'ids':i}).then((res)=>{
                    if (res && res == 'success') {
                        this.$store.dispatch('carouselSetting_list')
                    }
                });
            },
            edit(cls){
                this.$store.commit('carouselSetting_set',cls);
                this.$refs.rf.open('编辑轮播数据',false);
            },
            add(){
                this.$refs.rf.open('新增轮播数据',true)
            },
            changePage(pn){
                this.$store.dispatch('carouselSetting_list',{pn:pn})
            },
            refresh(){
                this.$store.dispatch('carouselSetting_list')
            }
        },
        mounted (){
            let vm=this;
            this.$store.dispatch('carouselSetting_res_load').then(function () {
                    vm.$store.dispatch('carouselSetting_list')
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
                        title: '轮播图片',
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