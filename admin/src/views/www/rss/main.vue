<template>
    <div>
        <Row>
            <Col span="24">
            <Card>
                <p slot="title">
                    <Icon type="help-buoy"></Icon>
                    广播列表
                </p>
                <Row>
                    <Col span="8">
                    <Button type="primary" icon="android-add" @click="add">新增广播</Button>
                    <Button type="primary" icon="search" @click="refresh">刷新</Button>
                    </Col>
                </Row>
                <Row class="margin-top-10">
                    <Table :context="self" border :data="rssList" :columns="tableColums" stripe></Table>
                </Row>
                <div style="margin: 10px;overflow: hidden">
                    <div style="float: right;">
                        <Page :total="total" :current="pageNumber" :page-size="pageSize" @on-change="changePage" show-total show-elevator></Page>
                    </div>
                </div>
            </Card>
            </Col>
        </Row>
        <rssForm ref="rf"></rssForm>
    </div>
</template>

<script>
    import {mapState} from 'vuex'
    import rssForm from './form.vue'
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
                title: '您确定要删除这个广播信息吗？'
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
                'rssList': state => state.rss.rssList,
                'totalPage': state => state.rss.totalPage,
                'total': state => state.rss.totalRow,
                'pageNumber': state => state.rss.pageNumber,
                'pageSize': state => state.rss.pageSize,
                'rss': state => state.rss.rss,
            })
        },
        methods: {
            del(i){
                this.$store.dispatch('rss_del',{'id':i}).then((res)=>{
                    if (res && res == 'success') {
                        this.$store.dispatch('rss_list')
                    }
                });
            },
            edit(rss){
                this.$store.commit('rss_set',rss);
                this.$refs.rf.open('编辑广播数据',false);
            },
            add(){
                this.$refs.rf.open('新增广播数据',true)
            },
            changePage(pn){
                this.$store.dispatch('rss_list',{pn:pn})
            },
            refresh(){
                this.$store.dispatch('rss_list')
            }
        },
        mounted (){
            this.$store.dispatch('rss_list')
        },
        components: {
            rssForm: rssForm,
        },
        data () {
            return {
                self: this,
                tableColums: [

                    {
                        title: '内容',
                        key: 'content',
                        width:400
                    },
                    {
                        title: '过期时间',
                        key: 'expiredAtTxt',
                    },
                    {
                        title: '目标链接',
                        key: 'url',
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