<template>
    <div>
        <Modal
                v-model="doctorInfoModal"
                @on-visible-change="vChange"
                :mask-closable="false"
                width="900"
                :styles="{top: '20px'}"
        >
            <p slot="header">
                <Icon type="information-circled"></Icon>
                <span>{{modalTitle}}</span>
            </p>
            <Form ref="formValidate" :label-width="80" :model="doctorInfo" :rules="ruleValidate">
                <Row>
                    <Col span="11">
                    <FormItem label="名称" prop="name">
                        <Input v-model="doctorInfo.name" placeholder="请输入..." style="width: 300px"></Input>
                    </FormItem>
                    </Col><Col span="11">
                    <FormItem label="性别" prop="sex">
                        <Select v-model="doctorInfo.sex" placeholder="请输入..." style="width: 300px">
                            <Option v-for="item in sexList" :value="item.value" :key="item.value">{{ item.label }}</Option>
                        </Select>
                    </FormItem>
                    </Col>
                </Row>


                <Row>
                    <Col span="11">
                <FormItem label="电话1" prop="tel1">
                    <Input v-model="doctorInfo.tel1" placeholder="请输入..." style="width: 300px" ></Input>
                </FormItem>
                    </Col><Col span="11">
                <FormItem label="电话2" prop="tel2">
                    <Input v-model="doctorInfo.tel2" placeholder="请输入..." style="width: 300px" ></Input>
                </FormItem>
                    </Col>
                </Row>
                <!--<FormItem label="电话3" prop="tel3">-->
                    <!--<Input v-model="doctorInfo.tel3" placeholder="请输入..." style="width: 300px" ></Input>-->
                <!--</FormItem>-->
                <Row>
                    <Col span="11">
                <FormItem label="EMAIL" prop="email">
                    <Input v-model="doctorInfo.email" placeholder="请输入..." style="width: 300px" ></Input>
                </FormItem>
                    </Col><Col span="11">
                <FormItem label="微信号" prop="weixin">
                    <Input v-model="doctorInfo.weixin" placeholder="请输入..." style="width: 300px" ></Input>
                </FormItem>
                    </Col>
                </Row>
                <Row>
                    <Col span="11">
                <FormItem label="执照号" prop="licenseNo">
                    <Input v-model="doctorInfo.licenseNo" placeholder="请输入..." style="width: 300px" ></Input>
                </FormItem>
                    </Col><Col span="11">
                <FormItem label="在职医院" prop="hosp">
                    <Select v-model="doctorInfo.hospital" placeholder="请选择..." style="width: 300px" >
                        <Option v-for="item in hospList" :value="item.id" :key="item.id">{{ item.title }}</Option>
                    </Select>
                </FormItem>
                    </Col>
                </Row>


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
                <Row>
                    <Col span="11">
                <FormItem label="擅长疾病" prop="diseases">
                    <Select v-model="diseases" placeholder="请输入..." style="width: 300px" multiple>
                        <Option v-for="item in diseaseList" :value="item.id" :key="item.id">{{ item.title }}</Option>
                    </Select>
                </FormItem>
                </Col><Col span="11">
                <FormItem label="手术方式" prop="opModels">
                    <Select v-model="opModels" placeholder="请输入..." style="width: 300px" multiple>
                        <Option v-for="item in opModeList" :value="item.id" :key="item.id">{{ item.title }}</Option>
                    </Select>
                </FormItem>
                </Col>
                </Row>
                <Row>
                    <Col span="11">
                <FormItem label="职称" prop="drTitles">
                    <Select v-model="drTitles" placeholder="请输入..." style="width: 300px" multiple>
                        <Option v-for="item in drTitleList" :value="item.value" :key="item.value">{{ item.label }}</Option>
                    </Select>
                </FormItem>
                    </Col><Col span="11">
                <FormItem label="状态" prop="status" v-show="!isAdd">
                    <Select v-model="doctorInfo.status" placeholder="请输入..." style="width: 300px">
                        <Option v-for="item in statusList" :value="item.value" :key="item.value">{{ item.label }}</Option>
                    </Select>
                </FormItem>
                    </Col>
                </Row>
                <FormItem label="简介" prop="summary">
                    <Input v-model="doctorInfo.summary" placeholder="请输入..."  type="textarea" :rows="4"></Input>
                </FormItem>

                <FormItem label="详细介绍" prop="introduction">
                    <div id="editorElem" style="text-align:left"></div>
                    <!--<Input v-model="doctorInfo.introduction" placeholder="请输入..." type="textarea" :rows="14" style="width: 300px"></Input>-->
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
    import E from 'wangeditor'

    var editor=null;
    export default {
        name: 'doctorInfoForm',
        computed: {
            ...mapState({
                'doctorInfo': state => state.doctorInfo.doctorInfo,
                'uploadPicMaxSize': state => state.uploadPicMaxSize,
                'drTitleList':state=>state.doctorInfo.drTitleList,
                'opModeList':state=>state.doctorInfo.opModeList,
                'diseaseList':state=>state.doctorInfo.diseaseList,
                'hospList':state=>state.doctorInfo.hospList,
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
                    editor.txt.html(this.doctorInfo.introduction)
                    if(this.doctorInfo.dT_C_IDS!=""){
                        this.doctorInfo.dT_C_IDS.split(",").forEach((val,key,arr)=>{
                            this.drTitles.push(parseInt(val))
                            });
                    }
                    if(this.doctorInfo.dT_A_IDS!=""){
                        this.doctorInfo.dT_A_IDS.split(",").forEach((val,key,arr)=>{
                            this.diseases.push(parseInt(val))
                        });
                    }
                    if(this.doctorInfo.dT_B_IDS!=""){
                        this.doctorInfo.dT_B_IDS.split(",").forEach((val,key,arr)=>{
                            this.opModels.push(parseInt(val))
                        });
                    }
                    this.showImg=true;
                    this.imgBase64Data=this.doctorInfo.avatar
                }
                this.modalLoading=false;
            },
            vChange(b){
                if (!b) {
                    this.$refs['formValidate'].resetFields()
                    this.showImg=false
                    this.drTitles=[]
                    this.diseases=[]
                    this.opModels=[]
                }
            },
            save(){
                let vm = this;
                this.modalLoading = true;
                this.$refs['formValidate'].validate((valid) => {
                    if (valid) {

                        vm.doctorInfo.drTitles=vm.drTitles.join(",")
                        vm.doctorInfo.diseases=vm.diseases.join(",")
                        vm.doctorInfo.opModels=vm.opModels.join(",")

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
                    tel1: [
                        {type: 'string', max: 50, message: '电话1长度不能超过50', trigger: 'blur'}
                    ],
                    tel2: [
                        {type: 'string', max: 50, message: '电话2长度不能超过50', trigger: 'blur'}
                    ],
                    tel3: [
                        {type: 'string', max: 50, message: '电话3长度不能超过50', trigger: 'blur'}
                    ],
                    email: [
                        {type: 'email', message: 'email格式不正确',  trigger: 'blur'},
                        {type: 'string', message: 'email长度不能超过200', max: 200, trigger: 'blur'}
                    ],
                    hospital: [
                        {type: 'string', message: '医院长度不能超过200', max: 200, trigger: 'blur'}
                    ],
                }
            }
        },
        mounted () {
            editor = new E('#editorElem')
            editor.customConfig.onchange = (html) => {
                this.$store.commit('set_dr_introduction',html)
            }
            editor.customConfig.uploadImgShowBase64 = true
            editor.create()

        },

        destroyed () {
            editor=null;
        }
    }

</script>

<style lang="less">
    @import '../../../styles/common.less';
</style>