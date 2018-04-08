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
                    <Input v-model="carouselSetting.summary" placeholder="请输入..." style="width: 300px" type="textarea" :rows="4"></Input>
                </FormItem>
                <FormItem label="目标连接" prop="linkTo">
                    <Input v-model="carouselSetting.linkTo" placeholder="必须输入http://或者https://" style="width: 300px"></Input>
                </FormItem>
                <FormItem label="轮播图片" prop="imgBase64Data">
                    <Upload
                            action=""
                            :before-upload="handleUpload"
                            :max-size="uploadPicMaxSize"
                    >
                        <span>选择图片上传&nbsp;&nbsp;</span>
                        <Button type="ghost" icon="ios-cloud-upload-outline">上传文件</Button>
                    </Upload>
                </FormItem>
                <FormItem>
                    <img :src="imgBase64Data"   v-if="showImg"
                         style="width: 100px;height: 100px">
                </FormItem>
                <FormItem label="所属区域" prop="area">
                    <Select v-model="carouselSetting.area" placeholder="请输入..." style="width: 300px">
                        <Option v-for="item in areas" :value="item.text" :key="item.text">{{ item.title }}</Option>
                    </Select>
                </FormItem>
                <FormItem label="状态" prop="status" v-show="!isAdd">
                    <Select v-model="carouselSetting.status" placeholder="请输入..." style="width: 300px">
                        <Option v-for="item in statusList" :value="item.value" :key="item.value">{{ item.label }}</Option>
                    </Select>
                </FormItem>
            </Form>
            <div slot="footer">
                <Button type="success" :loading="modalLoading" @click="save">保存</Button>
                <Button type="error" @click="carouselSettingModal=false">关闭</Button>
            </div>
        </Modal>

    </div>
</template>


<script>
    import {mapState} from 'vuex'
    import consts from '../../../libs/consts'
    export default {
        name: 'carouselSettingForm',
        computed: {
            ...mapState({
                'carouselSetting': state => state.carouselSetting.carouselSetting,
                'areas':state=>state.carouselSetting.areas,
                'uploadPicMaxSize': state => state.uploadPicMaxSize,
            })
        },
        methods: {
            open(title, isAdd){
                this.isAdd = isAdd
                this.modalTitle = title;
                this.carouselSettingModal=true;
                if(isAdd){
                    this.$store.commit('carouselSetting_set',{});
                    this.imgBase64Data='';
                    this.showImg=false;
                }else{
                    this.showImg=true;
                    this.imgBase64Data=this.carouselSetting.pic
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
                        this.$store.dispatch('carouselSetting_save',action).then((res) => {
                            this.modalLoading = false;
                            if (res && res == 'success') {
                                vm.carouselSettingModal = false;
                                this.$store.dispatch('carouselSetting_list')
                            }
                        })
                    } else {
                        this.modalLoading = false;
                    }
                })
            },
            handleUpload(file){
                let vm = this;
                let name=file.name;
                let ary=name.split(".");
                let format=['jpg', 'png', 'jpeg', 'gif', 'bmp', 'svg'];
                let fileSize=file.size;
                if(format.indexOf(ary[1].toLowerCase())==-1){
                    this.$Notice.warning({
                        title: '文件格式不正确',
                        desc: '文件 >>' + file.name + '<< 格式不正确，请选择jpg,png,jpeg,gif,bmp文件。'
                    });
                    return false;
                }
                if(parseInt(fileSize)>parseInt(this.uploadPicMaxSize)){
                    this.$Notice.warning({
                        title: '文件大小错误',
                        desc: '文件 >>' +file.name+'<< 过大，不能超过' + parseInt(vm.uploadPicMaxSize)/1024/1024 + "M"
                    });
                    return false;
                }
                let reader = new FileReader();
                reader.onload = function () {
                    vm.imgBase64Data = this.result;
                    vm.showImg = true
                    vm.carouselSetting['imgBase64Data']=this.result;
                    vm.$store.commit('carouselSetting_set',vm.carouselSetting)
                }
                reader.readAsDataURL(file)
                return false;
            },

        },
        data () {
            const validateImg=(rule,value,callback)=>{
                if(this.isAdd&&(value==''||value == undefined)){
                    callback(new Error("请选择轮播图片"))
                }else{
                    callback();
                }
            };
            return {
                self: this,
                carouselSettingModal: false,
                isAdd: true,
                showImg:false,
                modalTitle:'新增轮播数据',
                modalLoading: false,
                imgBase64Data:'',
                statusList:consts.status,
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
                        {type: 'url',  message: '目标链接格式不正确', trigger: 'blur'},
                        {type: 'string', max: 255, message: '目标链接长度不能超过255', trigger: 'blur'}
                    ],
                    area: [
                        {type: 'string', required: true, message: '所属轮播区域不能为空', trigger: 'blur'},
                    ],
                    imgBase64Data: [
                        {validator:validateImg},
                    ],
                }
            }
        }
    }

</script>

<style lang="less">
    @import '../../../styles/common.less';
</style>