<template>
    <div>
        <Row>
            <Col span="24">
            <Card>
                <p slot="title">
                    <Icon type="gear-a"></Icon>
                    参数设置
                </p>
                <Form :label-width="80">
                    <Tabs>
                        <Tab-pane label="网站基础信息" icon="flag">

                            <FormItem label="网站名称">
                                <Input v-model="param.siteName" placeholder="请输入..." style="width: 300px"></Input>
                            </FormItem>
                            <FormItem label="网站标题">
                                <Input v-model="param.siteTitle" placeholder="请输入..." style="width: 300px"></Input>
                            </FormItem>
                            <FormItem label="网站副标题">
                                <Input v-model="param.siteSubheading" placeholder="请输入..." style="width: 300px"></Input>
                            </FormItem>
                            <FormItem label="网站描述">
                                <Input v-model="param.siteDescription" placeholder="请输入..."
                                       style="width: 300px"></Input>
                            </FormItem>
                            <FormItem label="EMAIL">
                                <Input v-model="param.siteEmail" placeholder="请输入..." style="width: 300px"></Input>
                            </FormItem>
                            <FormItem label="网站地址">
                                <Input v-model="param.siteDomain" placeholder="请输入..." style="width: 300px"></Input>
                            </FormItem>
                            <FormItem label="网站备案">
                                <Input v-model="param.siteICP" placeholder="请输入..." style="width: 300px"></Input>
                            </FormItem>
                            <FormItem label="网站电话">
                                <Input v-model="param.sitePhone" placeholder="请输入..." style="width: 300px"></Input>
                            </FormItem>
                            <FormItem label="地址">
                                <Input v-model="param.siteAddress" placeholder="请输入..." style="width: 300px"></Input>
                            </FormItem>
                            <FormItem label="SEO">
                                <Input v-model="param.siteSEO" type="textarea" style="width: 300px"
                                       :autosize="{minRows: 5,maxRows: 5}" placeholder="请输入..."></Input>
                            </FormItem>

                        </Tab-pane>
                        <Tab-pane label="图片服务器信息" icon="image">
                            <FormItem label="AK">
                                <Input v-model="param.AK" placeholder="请输入..." style="width: 300px"></Input>
                            </FormItem>
                            <FormItem label="SK">
                                <Input v-model="param.SK" placeholder="请输入..." style="width: 300px"></Input>
                            </FormItem>
                            <FormItem label="存储空间名">
                                <Input v-model="param.qn_bucket" placeholder="请输入..." style="width: 300px"></Input>
                            </FormItem>
                            <FormItem label="图片链接">
                                <Input v-model="param.qn_url" placeholder="请输入..." style="width: 300px"></Input>
                            </FormItem>


                        </Tab-pane>
                        <Tab-pane label="短信设置" icon="android-textsms">
                            <FormItem label="AK">
                                <Input v-model="param.smsAppKey" placeholder="请输入..." style="width: 300px"></Input>
                            </FormItem>
                            <FormItem label="AS">
                                <Input v-model="param.smsAppSecret" placeholder="请输入..." style="width: 300px"></Input>
                            </FormItem>
                            <FormItem label="url">
                                <Input v-model="param.smsUrl" placeholder="请输入..." style="width: 300px"></Input>
                            </FormItem>
                            <FormItem label="短信模板">
                                <Input v-model="param.smsTemplate" placeholder="请输入..." style="width: 300px"
                                       type="textarea" :autosize="{minRows: 5,maxRows: 5}"></Input>
                            </FormItem>

                        </Tab-pane>
                        <Tab-pane label="EMAIL设置" icon="email">
                            <FormItem label="smtp">
                                <Input v-model="param.smtp" placeholder="请输入..." style="width: 300px"></Input>
                            </FormItem>
                            <FormItem label="邮件地址">
                                <Input v-model="param.emailName" placeholder="请输入..." style="width: 300px"></Input>
                            </FormItem>
                            <FormItem label="密码">
                                <Input v-model="param.emailPassword" placeholder="请输入..." style="width: 300px"></Input>
                            </FormItem>
                            <FormItem label="模板">
                                <Input v-model="param.emailTemplate" placeholder="请输入..." style="width: 300px"
                                       type="textarea" :autosize="{minRows: 5,maxRows: 5}"></Input>
                            </FormItem>

                        </Tab-pane>
                        <Tab-pane label="其它设置" icon="settings">
                            <FormItem label="微信公众账号二维码图片">
                                <Input v-model="param.wx_pic" placeholder="请输入..." style="width: 300px"></Input>
                            </FormItem>
                            <FormItem label="图片上传最大值">
                                <Input v-model="param.pic_maxSize" placeholder="请输入..." style="width: 300px"></Input>
                            </FormItem>
                            <FormItem label="单笔卡申请最大制卡数量">
                                <Input v-model="param.createCardMaxCount" placeholder="请输入..." style="width: 300px"></Input>
                            </FormItem>

                        </Tab-pane>
                    </Tabs>
                    <FormItem>
                    <Button type="success" :loading="buttonLoading" icon="checkmark-round" @click="saveData">
                        <span v-if="!buttonLoading">保存</span>
                        <span v-else>数据通信中...</span>
                    </Button>
                    </FormItem>
                </Form>
            </Card>
            </Col>
        </Row>
    </div>
</template>


<script>
    import {mapState} from 'vuex'
    export default {
        name:'adminParam',
        data () {
            return {
                buttonLoading: false,
            }
        },
        methods:{
            saveData:function(){
                this.$store.dispatch('save_param')
            }
        },
        components: {

        },
        computed: {
            ...mapState({
                'param':state =>state.param.map
            })
        },
        mounted () {
            this.$store.dispatch('param_list')
        }
    }
</script>
<style lang="less">
    @import '../../../styles/common.less';
</style>