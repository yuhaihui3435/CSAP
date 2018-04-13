<style lang="less">
    @import '../../../styles/common.less';
</style>
<template>
    <div>
        <Modal
                v-model="medicalrecordsViewModal"
                :mask-closable="false"
                width="900"
                :styles="{top: '20px'}"
        >
            <p slot="header">
                <Icon type="information-circled"></Icon>
                <span>用户医疗档案详细</span>
            </p>
            <Tabs type="card">
                <TabPane label="医疗档案信息">
                    <Row>
                        <Col span="8">

                        </Col>
                        <Col span="16">

                        </Col>
                    </Row>
                </TabPane>
                <TabPane label="系统用户信息">

                </TabPane>
                <TabPane label="发表的言论">

                </TabPane>
            </Tabs>
        </Modal>
    </div>
</template>
<script>

    import {mapState} from 'vuex'
    export default {
        name:'medicalrecords-view',
        computed: {
            ...mapState({
                'medicalrecords': state => state.medicalrecords.medicalrecords,
            })
        },
        data () {
            return {
                medicalrecordsViewModal: false,
            };
        },
        methods:{
            open(){
                this.medicalrecordsViewModal=true;
            }
        }
    }
</script>