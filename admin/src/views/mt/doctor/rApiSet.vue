<template>
    <div>
        <Modal
                v-model="rApiModal"
                @on-visible-change="vChange"
                :mask-closable="false"
        >
            <p slot="header">
                <Icon type="information-circled"></Icon>
                <span>{{modalTitle}}</span>
            </p>
            <Form ref="formValidate" :label-width="80" :model="rApi" :rules="ruleValidate">
                <FormItem label="名称" prop="name">
                    <Input v-model="rApi.name" placeholder="请输入..." style="width: 300px"></Input>
                </FormItem>
                <FormItem label="网址" prop="url">
                    <Input v-model="rApi.url" placeholder="请输入网址..." style="width: 300px"  >
                    <Select v-model="select1" slot="prepend" style="width: 80px">
                        <Option value="http://">http://</Option>
                        <Option value="https://">https://</Option>
                    </Select>
                    </Input>
                </FormItem>
            </Form>
            <div slot="footer">
                <Button type="success" :loading="modalLoading" @click="save">保存</Button>
                <Button type="error" @click="rApiModal=false">关闭</Button>
            </div>
            <Row class="margin-top-10">
                <Table :context="self" border :data="rApiList" :columns="tableColums" stripe></Table>
            </Row>
            <!--<div style="margin: 10px;overflow: hidden">-->
                <!--<div style="float: right;">-->
                    <!--<Page :total="total" :current="pageNumber" :page-size="pageSize" @on-change="changePage" show-total show-elevator></Page>-->
                <!--</div>-->
            <!--</div>-->
        </Modal>
    </div>
</template>


<script>
    import {mapState} from 'vuex'
    import consts from '../../../libs/consts'
    export default {
        name: 'rApiForm',
        computed: {
            ...mapState({
                'rApiList':state=>state.doctorInfo.rApiList,
            })
        },
        methods: {
//            changePage(pn){
//                this.sp.pn=pn;
//                this.$store.dispatch('rApi_list',this.sp)
//            },
            open(drId){
                this.rApiModal=true;
                this.rApi.drId=drId
            },
            vChange(b){
                if (!b) {
                    this.$refs['formValidate'].resetFields();
                    this.rApi={name:'',url:'',drId:''}
                }
            },
            save(){
                let vm = this;
                this.modalLoading = true;
                this.$refs['formValidate'].validate((valid) => {
                    if (valid) {
                        this.rApi.url=this.select1+this.rApi.url
                        this.$store.dispatch('rApi_save',vm.rApi).then((res) => {
                            this.modalLoading = false;
                            if (res && res.resCode == 'success') {
                                this.$store.dispatch('rApi_list',vm.rApi)
                            }
                        })
                    } else {
                        this.modalLoading = false;
                    }
                })
            },
            del(id){
                this.$store.dispatch('rApi_del',{id:id}).then((res) => {
                    this.modalLoading = false;
                    if (res && res.resCode == 'success') {
                        this.$store.dispatch('rApi_list',this.rApi)
                    }
                })
            }


        },
        data () {

            return {
                select1:'http://',
                rApi:{name:'',url:'',drId:''},
                self: this,
                rApiModal: false,
                modalTitle:'外部挂号网址设置',
                modalLoading: false,
                sp:{pn:1},
                ruleValidate: {
                    name: [
                        {type: 'string', required: true, message: '名称不能为空', trigger: 'blur'},
                        {type: 'string', max: 200, message: '名称长度不能超过200', trigger: 'blur'}
                    ],
                    url: [
                        {type: 'string', required: true, message: '挂号网址不能为空', trigger: 'blur'},
                    ],

                },
                tableColums: [

                    {
                        title: '名称',
                        key: 'name',
                    },
                    {
                        title: '网址',
                        key: 'url',
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