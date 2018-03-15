<template>
    <div>
        <Modal
                v-model="carouselSettingModal"
                @on-visible-change="vChange"
                :mask-closable="false"
        >
            <p slot="header">
                <Icon type="information-circled"></Icon>
                <span>{{modalTitle}}</span>
            </p>
            <Form ref="formValidate" :label-width="80" :model="carouselSetting" :rules="ruleValidate">
                <FormItem label="名称" prop="name">
                    <Input v-model="carouselSetting.name" placeholder="请输入..." style="width: 300px"></Input>
                </FormItem>
                <FormItem label="简介" prop="summary">
                    <Input v-model="carouselSetting.summary" placeholder="请输入..." style="width: 300px"></Input>
                </FormItem>
                <FormItem label="目标连接" prop="linkTo">
                    <Input v-model="carouselSetting.linkTo" placeholder="请输入..." style="width: 300px"></Input>
                </FormItem>
                <FormItem label="轮播图片" prop="pic">
                    <Input v-model="carouselSetting.pic" placeholder="请输入..." style="width: 300px"></Input>
                </FormItem>
                <FormItem label="所属区域" prop="area">
                    <Input v-model="carouselSetting.area" placeholder="请输入..." style="width: 300px"></Input>
                </FormItem>
                <FormItem label="状态" prop="status" v-model="!isAdd">
                    <Input v-model="carouselSetting.status" placeholder="请输入..." style="width: 300px"></Input>
                </FormItem>
            </Form>
            <div slot="footer">
                <Button type="success" :loading="modalLoading" @click="save">保存</Button>
                <Button @click="reset" v-show="isAdd">重置</Button>
                <Button type="error" @click="carouselSettingModal=false">关闭</Button>
            </div>
        </Modal>

    </div>
</template>


<script>
    import {mapState} from 'vuex'

    export default {
        name: 'carouselSettingForm',
        computed: {
            ...mapState({
                'carouselSetting': state => state.carouselSetting.carouselSetting,
            })
        },
        methods: {
            open(title, isAdd){
                this.isAdd = isAdd
                this.modalTitle = title;
                this.roleModal=true;
                if(isAdd)
                    this.$store.commit('carouselSetting_set',{});
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
                        this.$store.dispatch('carouselSetting_save',action).then((res) => {
                            this.modalLoading = false;
                            if (res && res == 'success') {
                                vm.roleModal = false;
                                this.$store.dispatch('carouselSetting_list')
                            }
                        })
                    } else {
                        this.modalLoading = false;
                    }
                })
            },
            reset(){
                this.$store.dispatch('carouselSetting_set',{});
            }

        },
        data () {
            return {
                self: this,
                carouselSettingModal: false,
                isAdd: true,
                modalTitle: '新增轮播数据',
                modalLoading: false,
                ruleValidate: {
                    name: [
                        {type: 'string', required: true, message: '名称不能为空', trigger: 'blur'},
                        {type: 'string', max: 50, message: '名称长度不能超过50', trigger: 'blur'}
                    ],
                    summary: [
                        {type: 'string', required: true, message: '简介不能为空', trigger: 'blur'},
                        {type: 'string', max: 255, message: '简介长度不能超过255', trigger: 'blur'}
                    ],
                    linkTo: [
                        {type: 'string', max: 255, message: '目标链接长度不能超过255', trigger: 'blur'}
                    ],
                    area: [
                        {type: 'string', required: true, message: '所属轮播区域不能为空', trigger: 'blur'},
                    ],
                }
            }
        }
    }

</script>

<style lang="less">
    @import '../../../styles/common.less';
</style>