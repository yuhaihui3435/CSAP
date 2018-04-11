<template>
    <div>
        <Row>
            <Col span="24">
            <Card>
                <p slot="title">
                    <Icon type="help-buoy"></Icon>
                    出诊计划列表
                </p>
                <Row>
                    <Col span="8">
                    <Button type="primary" icon="android-add" @click="add">新增出诊计划</Button>
                    </Col>
                    <Col span="16"  align="right">
                    <DatePicker type="daterange" v-model="param.visitDate"  placeholder="请选择日期" style="width: 300px"  format="yyyy-MM-dd"></DatePicker>
                    <Select v-model="param.status" placeholder="请选择状态..." style="width: 100px" :clearable="true">
                        <Option v-for="item in statusList" :value="item.value" :key="item.value">{{ item.label }}</Option>
                    </Select>
                    <Select v-model="param.dId" placeholder="请选择医生..." style="width: 100px" :clearable="true">
                        <Option v-for="item in drList" :value="item.id" :key="item.id">{{ item.name }}</Option>
                    </Select>
                    <Select v-model="param.hosp" placeholder="请选择地点..."  :clearable="true" style="width: 300px">
                        <Option v-for="item in hospList" :value="item.title" :key="item.title">{{ item.title }}</Option>
                    </Select>
                    <span @click="search" style="margin: 0 10px;"><Button type="primary" icon="search">搜索</Button></span>
                    </Col>
                </Row>
                <Row class="margin-top-10">
                    <Table :context="self" border :data="visitList" :columns="tableColums" stripe></Table>
                </Row>
                <div style="margin: 10px;overflow: hidden">
                    <div style="float: right;">
                        <Page :total="total" :current="pageNumber" :page-size="pageSize" @on-change="changePage" show-total show-elevator></Page>
                    </div>
                </div>
            </Card>
            </Col>
        </Row>
        <visitForm ref="rf"></visitForm>
    </div>
</template>

<script>
    import {mapState} from 'vuex'
    import visitForm from './form.vue'
    import consts from '../../../libs/consts'

    export default {

        computed: {
            ...mapState({
                'visitList': state => state.visit.visitList,
                'totalPage': state => state.visit.totalPage,
                'total': state => state.visit.totalRow,
                'pageNumber': state => state.visit.pageNumber,
                'pageSize': state => state.visit.pageSize,
                'visit': state => state.visit.visit,
                'drList': state => state.visit.drList,
                'hospList':state=>state.visit.hospList,
            })
        },
        methods: {
            del(i){
                this.$store.dispatch('visit_del',{'id':i}).then((res)=>{
                    if (res && res == 'success') {
                        this.$store.dispatch('visit_list')
                    }
                });
            },
            edit(cls){
                this.$store.commit('visit_set',cls);
                this.$refs.rf.open('编辑出诊计划数据',false);
            },
            add(){
                this.$refs.rf.open('新增出诊计划数据',true)
            },
            changePage(pn){
                this.param.pn=pn;
                this.$store.dispatch('visit_list',this.param)
            },
            search(){
                this.$store.dispatch('visit_list',this.param)
            },
        },
        mounted (){
            let vm=this;
            this.$store.dispatch('visit_res_load').then(function () {
                    vm.$store.dispatch('visit_list')
                }
            )
        },
        components: {
            visitForm: visitForm,
        },
        data () {
            return {
                param:{visitDate:'',dId:'',status:'',hosp:''},
                self: this,
                statusList:consts.status,
                tableColums: [
                    {
                        title: '出诊日期',
                        key: 'visitDateTxt',
                    },
                    {
                        title: '医生名称',
                        key: 'drName',
                    },
                    {
                        title: '出诊地点',
                        key: 'hospTxt',
                    },
                    {
                        title: '看诊数量',
                        key: 'num',
                    },
                    {
                        title: '开始时间',
                        key: 'visitBAt',
                    },
                    {
                        title: '结束时间',
                        key: 'visitEAt',
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
                            let btns=[consts.editBtn(this,h,param),consts.delBtn(this,h,param)]
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