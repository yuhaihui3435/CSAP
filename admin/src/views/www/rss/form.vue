<template>
    <div>
        <Modal
                v-model="rssModal"
                @on-visible-change="vChange"
                :mask-closable="false"
        >
            <p slot="header">
                <Icon type="information-circled"></Icon>
                <span>{{modalTitle}}</span>
            </p>
            <Form ref="formValidate" :label-width="80" :model="rss" :rules="ruleValidate">
                <FormItem label="内容" prop="content">
                    <Input v-model="rss.content" placeholder="请输入..." :rows="4" style="width: 300px" type="textarea"></Input>
                </FormItem>
                <FormItem label="过期日期" prop="expiredAt">
                    <DatePicker type="date" v-model="rss.expiredAt" :options="options" placeholder="请选择日期" @on-change="dateChange" style="width: 300px"  format="yyyy-MM-dd"></DatePicker>
                </FormItem>
                <FormItem label="目标链接" prop="url">
                    <Input v-model="rss.url" placeholder="必须输入http://或者https://" style="width: 300px"></Input>
                </FormItem>
            </Form>
            <div slot="footer">
                <Button type="success" :loading="modalLoading" @click="save">保存</Button>
                <Button type="error" @click="rssModal=false">关闭</Button>
            </div>
        </Modal>

    </div>
</template>


<script>
    import {mapState} from 'vuex'
    import consts from '../../../libs/consts'
    export default {
        name: 'rssForm',
        computed: {
            ...mapState({
                'rss': state => state.rss.rss,
            })
        },
        methods: {
            open(title, isAdd){
                this.isAdd = isAdd
                this.modalTitle = title;
                this.rssModal=true;
                if(isAdd){
                    this.$store.commit('rss_set',{});
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
                        let action='save';
                        if(!vm.isAdd)
                            action='update';
                        this.$store.dispatch('rss_save',action).then((res) => {
                            this.modalLoading = false;
                            if (res && res == 'success') {
                                vm.rssModal = false;
                                this.$store.dispatch('rss_list')
                            }
                        })
                    } else {
                        this.modalLoading = false;
                    }
                })
            },
            dateChange(val){
                if(val!='')
                    this.rss.expiredAt=val;
                else
                    this.rss.expiredAt='';
            }
        },
        data () {
            return {
                self: this,
                rssModal: false,
                isAdd: true,
                modalTitle:'新增广播数据',
                modalLoading: false,
                options: {
                    disabledDate (date) {
                        return date && date.valueOf() < Date.now() - 86400000;
                    }
                },
                ruleValidate: {
                    content: [
                        {type: 'string', required: true, message: '名称不能为空', trigger: 'blur'},
                        {type: 'string', max: 255, message: '名称长度不能超过255', trigger: 'blur'}
                    ],

                    url: [
                        {type: 'url',  message: '目标链接格式不正确', trigger: 'blur'},
                        {type: 'string', max: 255, message: '目标链接长度不能超过255', trigger: 'blur'}
                    ]
                }
            }
        }
    }

</script>

<style lang="less">
    @import '../../../styles/common.less';
</style>