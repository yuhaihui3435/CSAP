<template>
    <div>
        <Modal
                v-model="visitModal"
                @on-visible-change="vChange"
                :mask-closable="false"
        >
            <p slot="header">
                <Icon type="information-circled"></Icon>
                <span>{{modalTitle}}</span>
            </p>
            <Form ref="formValidate" :label-width="80" :model="visit" :rules="ruleValidate">
                <FormItem label="出诊日期" prop="visitDate">
                    <DatePicker v-model="visit.visitDate" type="date" v-bind:disabled="!isAdd"  :options="options" placeholder="请选择日期" style="width: 300px"  format="yyyy-MM-dd"></DatePicker>
                </FormItem>
                <FormItem label="医生" prop="dId">
                    <Select v-model="visit.dId" placeholder="请输入..." style="width: 300px" v-bind:disabled="!isAdd" >
                        <Option v-for="item in drList" :value="item.id" :key="item.id">{{ item.name }}</Option>
                    </Select>
                </FormItem>
                <FormItem label="出诊地址" prop="hosp">
                    <Select v-model="visit.hosp" placeholder="请输入..." style="width: 300px">
                        <Option v-for="item in hospList" :value="item.id" :key="item.id">{{ item.title }}</Option>
                    </Select>
                </FormItem>
                <FormItem label="开始时间"  style="width: 300px" >
                    <Row>
                        <Col span="12">
                    <Select v-model="bHH">
                        <Option v-for="item in HHList" :value="item" :key="item">{{item}}</Option>
                    </Select>
                        </Col>
                        <Col span="12">
                        <Select v-model="bMM">
                        <Option v-for="item in MMList" :value="item" :key="item">{{item}}</Option>
                    </Select>
                        </Col>
                    </Row>
                </FormItem>
                <FormItem label="结束时间"  style="width: 300px">
                    <Row>
                        <Col span="12">
                        <Select v-model="eHH">
                            <Option v-for="item in HHList" :value="item" :key="item">{{item}}</Option>
                        </Select>
                        </Col>
                        <Col span="12">
                        <Select v-model="eMM">
                            <Option v-for="item in MMList" :value="item" :key="item">{{item}}</Option>
                        </Select>
                        </Col>
                    </Row>
                </FormItem>
                <FormItem label="数量" prop="num">
                    <InputNumber  :min="1" v-model="visit.num" style="width: 300px"></InputNumber>
                </FormItem>
                <FormItem label="状态" prop="status" v-show="!isAdd">
                    <Select v-model="visit.status" placeholder="请输入..." style="width: 300px">
                        <Option v-for="item in statusList" :value="item.value" :key="item.value">{{ item.label }}</Option>
                    </Select>
                </FormItem>
            </Form>
            <div slot="footer">
                <Button type="success" :loading="modalLoading" @click="save">保存</Button>
                <Button type="error" @click="visitModal=false">关闭</Button>
            </div>
        </Modal>

    </div>
</template>


<script>
    import {mapState} from 'vuex'
    import consts from '../../../libs/consts'
    import {formatDate} from '../../../libs/date'
    export default {
        name: 'visitForm',
        computed: {
            ...mapState({
                'visit': state => state.visit.visit,
                'drList':state=>state.visit.drList,
                'hospList':state=>state.visit.hospList,
            })
        },
        methods: {
            open(title, isAdd){
                this.isAdd = isAdd
                this.modalTitle = title;
                this.visitModal=true;

                if(isAdd){
                    this.$store.commit('visit_set',{});
                }else{

                }
                this.modalLoading=false;
            },
            vChange(b){
                if (!b) {
                    this.$refs['formValidate'].resetFields()
                }
            },
            save(){
                let vm = this;
                this.modalLoading = true;
                this.$refs['formValidate'].validate((valid) => {
                    if (valid) {
                        vm.visit.visitBAt=vm.bHH+':'+vm.bMM
                        vm.visit.visitEAt=vm.eHH+':'+vm.eMM
                        if(!vm.visit.num)vm.visit.num=1
                        vm.visit.visitDate=formatDate(vm.visit.visitDate,'yyyy-MM-dd')
                        let action='save';
                        if(!vm.isAdd)
                            action='update';
                        this.$store.dispatch('visit_save',action).then((res) => {
                            this.modalLoading = false;
                            if (res && res == 'success') {
                                vm.visitModal = false;
                                this.$store.dispatch('visit_list')
                            }
                        })
                    } else {
                        this.modalLoading = false;
                    }
                })
            },

        },
        data () {

            return {
                self: this,
                visitModal: false,
                isAdd: true,
                modalTitle:'新增出诊计划',
                modalLoading: false,
                bHH:'08',
                bMM:'00',
                eHH:'17',
                eMM:'00',
                HHList:consts.HH,
                MMList:consts.MM,
                statusList:consts.status,
                ruleValidate: {
                    visitDate: [
                        {type: 'date', required: true, message: '出诊日期不能为空', trigger: 'blur'},
                    ],
                    dId: [
                        {type: 'integer', required: true, message: '请选择出诊医生', trigger: 'blur'},
                    ],
                },
                options: {
                    disabledDate (date) {
                        return date && date.valueOf() <Date.now();
                    }
                },
            }
        }
    }

</script>

<style lang="less">
    @import '../../../styles/common.less';
</style>