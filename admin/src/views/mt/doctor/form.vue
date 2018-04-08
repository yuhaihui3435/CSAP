<template>
    <div>
        <Modal
                v-model="doctorInfoModal"
                @on-visible-change="vChange"
                :mask-closable="false"
        >
            <p slot="header">
                <Icon type="information-circled"></Icon>
                <span>{{modalTitle}}</span>
            </p>
            <Form ref="formValidate" :label-width="80" :model="doctorInfo" :rules="ruleValidate">
                <FormItem label="名称" prop="name">
                    <Input v-model="doctorInfo.name" placeholder="请输入..." style="width: 300px"></Input>
                </FormItem>
                <FormItem label="简介" prop="summary">
                    <Input v-model="doctorInfo.summary" placeholder="请输入..." style="width: 300px" type="textarea" :rows="4"></Input>
                </FormItem>
                <FormItem label="性别" prop="sex">
                    <Select v-model="doctorInfo.sex" placeholder="请输入..." style="width: 300px">
                        <Option v-for="item in sexList" :value="item.value" :key="item.value">{{ item.label }}</Option>
                    </Select>
                </FormItem>
                <FormItem label="电话1" prop="tel1">
                    <Input v-model="doctorInfo.tel1" placeholder="请输入..." style="width: 300px" ></Input>
                </FormItem>
                <FormItem label="电话2" prop="tel2">
                    <Input v-model="doctorInfo.tel2" placeholder="请输入..." style="width: 300px" ></Input>
                </FormItem>
                <FormItem label="电话3" prop="tel3">
                    <Input v-model="doctorInfo.tel3" placeholder="请输入..." style="width: 300px" ></Input>
                </FormItem>
                <FormItem label="EMAIL" prop="email">
                    <Input v-model="doctorInfo.email" placeholder="请输入..." style="width: 300px" ></Input>
                </FormItem>
                <FormItem label="微信号" prop="weixin">
                    <Input v-model="doctorInfo.weixin" placeholder="请输入..." style="width: 300px" ></Input>
                </FormItem>
                <FormItem label="执照号" prop="licenseNo">
                    <Input v-model="doctorInfo.licenseNo" placeholder="请输入..." style="width: 300px" ></Input>
                </FormItem>
                <FormItem label="详细介绍" prop="introduction">
                    <Input v-model="doctorInfo.introduction" placeholder="请输入..." type="textarea" :rows="4" style="width: 300px"></Input>
                </FormItem>
                <FormItem label="照片" prop="imgBase64Data">
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
                <FormItem label="擅长疾病" prop="diseases">
                    <Select v-model="diseases" placeholder="请输入..." style="width: 300px" multiple>
                        <Option v-for="item in diseaseList" :value="item.id" :key="item.id">{{ item.title }}</Option>
                    </Select>
                </FormItem>
                <FormItem label="手术方式" prop="opModels">
                    <Select v-model="opModels" placeholder="请输入..." style="width: 300px" multiple>
                        <Option v-for="item in opModelList" :value="item.id" :key="item.id">{{ item.title }}</Option>
                    </Select>
                </FormItem>
                <FormItem label="职称" prop="drTitles">
                    <Select v-model="drTitles" placeholder="请输入..." style="width: 300px" multiple>
                        <Option v-for="item in drTitleList" :value="item.id" :key="item.id">{{ item.title }}</Option>
                    </Select>
                </FormItem>
                <FormItem label="状态" prop="status" v-show="!isAdd">
                    <Select v-model="doctorInfo.status" placeholder="请输入..." style="width: 300px">
                        <Option v-for="item in statusList" :value="item.value" :key="item.value">{{ item.label }}</Option>
                    </Select>
                </FormItem>
            </Form>
            <div slot="footer">
                <Button type="success" :loading="modalLoading" @click="save">保存</Button>
                <Button type="error" @click="doctorInfoModal=false">关闭</Button>
            </div>
        </Modal>

    </div>
</template>


<script>
    import {mapState} from 'vuex'
    import consts from '../../../libs/consts'
    export default {
        name: 'doctorInfoForm',
        computed: {
            ...mapState({
                'doctorInfo': state => state.doctorInfo.doctorInfo,
                'uploadPicMaxSize': state => state.uploadPicMaxSize,
                'drTitleList':state=>state.doctorInfo.drTitleList,
                'opModelList':state=>state.doctorInfo.opModelList,
                'diseaseList':state=>state.doctorInfo.diseaseList,
            })
        },
        methods: {
            open(title, isAdd){
                this.isAdd = isAdd
                this.modalTitle = title;
                this.doctorInfoModal=true;
                if(isAdd){
                    this.$store.commit('doctorInfo_set',{});
                    this.imgBase64Data='';
                    this.showImg=false;
                }else{
                    this.showImg=true;
                    this.imgBase64Data=this.doctorInfo.avatar
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
                        this.$store.dispatch('doctorInfo_save',action).then((res) => {
                            this.modalLoading = false;
                            if (res && res == 'success') {
                                vm.doctorInfoModal = false;
                                this.$store.dispatch('doctorInfo_list')
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
                    vm.doctorInfo['imgBase64Data']=this.result;
                    vm.$store.commit('doctorInfo_set',vm.doctorInfo)
                }
                reader.readAsDataURL(file)
                return false;
            },

        },
        data () {

            return {
                self: this,
                doctorInfoModal: false,
                isAdd: true,
                showImg:false,
                sexList:consts.sexList,
                drTitles:[],
                diseases:[],
                opModels:[],
                modalTitle:'新增医生数据',
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
                }
            }
        }
    }

</script>

<style lang="less">
    @import '../../../styles/common.less';
</style>