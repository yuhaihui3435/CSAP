<template>
    <div>
        <Row>
            <Col span="24">
            <Card>
                <p slot="title">
                    <Icon type="help-buoy"></Icon>
                    用户医疗档案列表
                </p>
                <Row>

                    <Col span="24"  align="right">
                    <Select v-model="param.diseases" placeholder="请选择病情..." :multiple="true" style="width: 200px" :clearable="true">
                        <Option v-for="item in diseaseList" :value="item.id" :key="item.id">{{ item.title }}</Option>
                    </Select>
                    <Select v-model="param.symptoms" placeholder="请选择病症..." :multiple="true" style="width: 200px" :clearable="true">
                        <Option v-for="item in symptomList" :value="item.id" :key="item.id">{{ item.title }}</Option>
                    </Select>
                    <!--<Select v-model="param.opModels" placeholder="请选择手术方式..." :multiple="true" style="width: 200px" :clearable="true">-->
                        <!--<Option v-for="item in opModelList" :value="item.id" :key="item.id">{{ item.title }}</Option>-->
                    <!--</Select>-->
                    <Select v-model="param.treatEffect" placeholder="请选择效果..." style="width: 100px" :clearable="true">
                        <Option v-for="item in treatEffectList" :value="item.id" :key="item.id">{{ item.title }}</Option>
                    </Select>
                    <!--<Select v-model="param.drIds" placeholder="请选择医生..." :multiple="true" style="width: 200px" :clearable="true">-->
                        <!--<Option v-for="item in drList" :value="item.id" :key="item.id">{{ item.name }}</Option>-->
                    <!--</Select>-->
                    <!--<Select v-model="param.hosp" placeholder="请选择医院..."  :clearable="true" style="width: 300px">-->
                        <!--<Option v-for="item in hospList" :value="item.id" :key="item.id">{{ item.title }}</Option>-->
                    <!--</Select>-->
                    <Select v-model="param.ifCure" placeholder="是否治疗..."  :clearable="true" style="width: 100px">
                        <Option v-for="item in yonList" :value="item.value" :key="item.value">{{ item.label }}</Option>
                    </Select>
                    <span @click="search" style="margin: 0 10px;"><Button type="primary" icon="search">搜索</Button></span>
                    </Col>
                </Row>
                <Row class="margin-top-10">
                    <Table :context="self" border :data="medicalrecordsList" :columns="tableColums" stripe></Table>
                </Row>
                <div style="margin: 10px;overflow: hidden">
                    <div style="float: right;">
                        <Page :total="total" :current="pageNumber" :page-size="pageSize" @on-change="changePage" show-total show-elevator></Page>
                    </div>
                </div>
            </Card>
            </Col>
        </Row>
    </div>
</template>

<script>
    import {mapState} from 'vuex'
    import consts from '../../../libs/consts'

    export default {

        computed: {
            ...mapState({
                'medicalrecordsList': state => state.medicalrecords.medicalrecordsList,
                'totalPage': state => state.medicalrecords.totalPage,
                'total': state => state.medicalrecords.totalRow,
                'pageNumber': state => state.medicalrecords.pageNumber,
                'pageSize': state => state.medicalrecords.pageSize,
                'medicalrecords': state => state.medicalrecords.medicalrecords,
                // 'drList': state => state.medicalrecords.drList,
                // 'hospList':state=>state.medicalrecords.hospList,
                'symptomList':state=>state.medicalrecords.symptomList,
                'diseaseList':state=>state.medicalrecords.diseaseList,
                // 'opModelList':state=>state.medicalrecords.opModelList,
                'treatEffectList':state=>state.medicalrecords.treatEffectList,
            })
        },
        methods: {
            del(i){
                this.$store.dispatch('medicalrecords_del',{'id':i}).then((res)=>{
                    if (res && res == 'success') {
                        this.$store.dispatch('medicalrecords_list')
                    }
                });
            },
            changePage(pn){
                this.param.pn=pn;
                this.search()
            },
            search(){

                let p=Object.assign({},this.param)

                if(p.diseases)
                    p.diseases=this.param.diseases.join(",")
                if(p.symptoms)
                    p.symptoms=this.param.symptoms.join(",")
                // if(p.opModels)
                //     p.opModels=this.param.opModels.join(",")
                // if(p.drIds)
                //     p.drIds=this.param.drIds.join(",")

                this.$store.dispatch('medicalrecords_list',p)
            },
            queryUser(userId){

            }
        },
        mounted (){
            let vm=this;
            this.$store.dispatch('medicalrecords_res_load').then(function () {
                    vm.$store.dispatch('medicalrecords_list')
                }
            )
        },
        components: {

        },
        data () {
            return {
                param:{diseases:[],symptoms:[],treatEffect:'',ifcure:''},
                self: this,
                yonList:consts.yon,
                tableColums: [
                    {
                        title: '姓名',
                        key: 'name',
                    },
                    {
                        title: '性别',
                        key: 'sexTxt',
                    },
                    {
                        title: '病龄',
                        key: 'acne',
                    },
                    {
                        title: '联系电话',
                        key: 'tel',
                    },
                    {
                        title: '邮箱',
                        key: 'email',
                    },
                    {
                        title: '身份证号',
                        key: 'idCard',
                    },
                    {
                        title: '微信',
                        key: 'weixin',
                    },
                    {
                        title: '联系地址',
                        key: 'fullAddress',
                    },
                    {
                        title: '是否治疗',
                        key: 'ifCureTxt',
                    },
                    {
                        title: '治疗方式',
                        key: 'treatmentMhdTxt',
                    },
                    {
                        title: '是否公开',
                        key: 'ifPublicTxt',
                    },

                    {
                        title: '操作',
                        key: 'action',
                        width: 200,
                        align: 'center',
                        render:(h,param)=>{
                            let btns=[consts.delBtn(this,h,param)]
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