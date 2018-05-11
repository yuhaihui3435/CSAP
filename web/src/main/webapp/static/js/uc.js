;(function () {
    var Uc = Uc || {}
    Uc.userInfo={}
    Uc.updateUserInfo=function (data) {
        data.field.sheng=data.field.provid;
        data.field.shi=data.field.cityid;
        data.field.qu=data.field.areaid;

        sweetAlert2Loading('数据保存中...');
        $.ajax({
            type: 'POST',
            url: $('#ctx').val() + '/ad01/update',
            data: data.field,
            dataType: 'json',
            success: function (data) {
                swal.close();
                if (data.resCode == 'success') {
                    sweetAlert2Success(data.resMsg);
                } else {
                    sweetAlert2Error(data.resMsg);
                }
            },
            error: function () {
                swal.close();
                sweetAlert2Error('网络异常，请重试！');
            }
        })

    }



    Uc.delPostinfo=function (postInfoId) {
        let lId=layer.confirm('确定要删除该发布内容吗？', {
            btn: ['确认', '取消'] //按钮
        }, function () {
            layer.close(lId)
            sweetAlert2Loading('数据处理中...');
            $.ajax({
                type: 'POST',
                url: $('#ctx').val() + '/post/del',
                data: {'id': postInfoId},
                dataType: 'json',
                success: function (data) {
                    swal.close();
                    if (data && data.resCode == 'success') {
                        location.reload();
                    } else {
                        sweetAlert2Error(data.resMsg)
                    }

                },
                error: function () {
                    swal.close();
                    sweetAlert2Error('网络异常，请重试！');
                }
            })
        },function () {

        });
    }

    Uc.delReply=function (replyId) {
        let index = layer.confirm('确定要删除该回复吗？', {
            btn: ['确认', '取消'] //按钮
        }, function () {
            layer.close(index)
            sweetAlert2Loading('数据处理中...')
            $.ajax({
                type: 'POST',
                url: $('#ctx').val() + '/reply/del/' + replyId,
                data: {},
                dataType: 'json',
                success: function (data) {
                    swal.close()
                    if (data && data.resCode == 'success') {
                        location.reload();
                    } else {
                        sweetAlert2Error(data.resMsg)
                    }
                },
                error: function () {
                    swal.close()
                    sweetAlert2Error('网络异常，请重试！');
                }
            })
        }, function () {

        });
    }




    window['Uc']=Uc
})()