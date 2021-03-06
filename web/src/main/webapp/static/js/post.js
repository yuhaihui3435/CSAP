;(function () {
    var Post = Post || {}
    Post.isRefresh = "no";
    Post.info = {}
    Post.query = {};
    Post.loadByPlate = function (pn) {

        $("#postList").show();
        $("#addPost").hide();
        $("#post-view").hide();
        pn = pn == undefined ? 1 : pn;
        let param = Post.query;
        param.pn = pn;
        if (Post.isRefresh == 'yes') {
            let cacheData = getJSONLocalCache("post_load_param");
            param = cacheData != '' ? cacheData : param
            Post.resetTitleFocus(param.taxId);
            Post.resetQFocus(param.q);
            Post.resetQ1Focus(param.q_1);
        } else {
            setJSONLocalCache("post_load_param", param)
        }
        setLocalCache('currView', 'list');

        sweetAlert2Loading('数据加载中...');
        if (Post.isRefresh == 'yes') Post.isRefresh = 'no';
        return new Promise(function (resolve, reject) {
            $.ajax({
                type: 'POST',
                url: $('#ctx').val() + '/post/list',
                data: param,
                dataType: 'json',
                success: function (data) {
                    resolve()
                    swal.close();
                    $("#post-area").empty();
                    if (data.topList == undefined)
                        data.topList = '';
                    $("#post_tmpl").tmpl(data).appendTo("#post-area");
                    if (data.page.totalRow > 0) {
                        let laypage = layui.laypage;
                        laypage.render({
                            elem: 'pageDiv',
                            theme: '#1E9FFF',
                            count: data.page.totalRow,
                            limit: data.page.pageSize,
                            jump: function (obj, first) {

                                //首次不执行
                                if (!first) {
                                    Post.loadByPlate(obj.curr);
                                }
                            }
                        })
                    }
                },
                error: function () {
                    resolve()
                    swal.close();
                    sweetAlert2Error('网络异常，请重试！');
                }
            })

        })



    }

    Post.toAddPost = function () {
        $("#addPost").show();
        $("#postList").hide();
        $("#post-view").hide();
        $('#check-code').code_Obj({
            codeLength: 5
        });
    }

    Post.titleFocus = function (elem) {
        $(elem).parent().addClass("layui-this");
        $(elem).parent().siblings().removeClass("layui-this");
    }

    Post.resetTitleFocus = function (plateId) {
        if (plateId == undefined)return
        $('#plate_' + plateId).parent().addClass("layui-this");
        $('#plate_' + plateId).parent().siblings().removeClass("layui-this");
    }

    Post.qFocus = function (elem) {
        elem=$("#"+elem.id);
        $(elem).addClass("layui-this");
        $(elem).siblings().removeClass("layui-this");
        Post.resetQ1Focus(Post.query.q_1)
    }

    Post.q1Focus = function (elem) {
        elem=$("#"+elem.id);
        $(elem).addClass("layui-this");
        $(elem).siblings().removeClass("layui-this");
        Post.resetQFocus(Post.query.q)
    }

    Post.resetQFocus = function (id) {
        if (id == undefined)return
        $('#q_' + id).addClass("layui-this");
        $('#q_' + id).siblings().removeClass("layui-this");
    }

    Post.resetQ1Focus = function (id) {
        if (id == undefined)return
        $('#q_1_' + id).addClass("layui-this");
        $('#q_1_' + id).siblings().removeClass("layui-this");
    }


    Post.addPost = function () {
        let verCode = $("#verCode").val();
        verCode = verCode.toUpperCase()
        if (verCode == '') {
            sweetAlert2Error('请填写验证码!');
            return false;
        }
        let checkCode = $("#data_code").attr("data-value");
        checkCode = checkCode.toUpperCase()
        if (verCode != checkCode) {
            sweetAlert2Error('验证码不正确!')
            return false;
        }

        let taxId = $("#postTax").val();
        let title = $("#postTitle").val();
        let content = postAddEditor.txt.html();


        if (content == '' || content == '<p><br></p>') {
            sweetAlert2Error('发表的内容不能未空!')
            return false
        }

        let param = {'title': title, 'taxId': taxId, 'content': content}
        sweetAlert2Loading('数据保存中...');
        $.ajax({
            type: 'POST',
            url: $('#ctx').val() + '/post/save',
            data: param,
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

    Post.view = function (postId) {
        setLocalCache("currView", 'view');
        setLocalCache("postId", postId)
        sweetAlert2Loading('数据加载中...');
        $.ajax({
            type: 'POST',
            url: $('#ctx').val() + '/post/' + postId,
            data: '',
            dataType: 'json',
            success: function (data) {
                swal.close();

                if (data == undefined || data == '') {
                    sweetAlert2Error('没有查询到数据!')
                } else {
                    postViewEditor.txt.clear()
                    $("#viewDiv").empty();
                    $("#replyDiv").empty();
                    $("#postList").hide();
                    $("#post-view").show();

                    Post.info = Object.assign({}, data.postInfo)

                    if(Post.info.commentStatus!='0'){
                        $('#replyEditor').hide();
                    }else{
                        $('#replyEditor').show();
                    }


                    User.bindData(data.user, data.userReses, data.userRoles)

                    $("#post_view_tmpl").tmpl(data).appendTo("#viewDiv");
                    //加载回复数据
                    Post.loadReplys(1, postId)

                }

            },
            error: function () {
                swal.close();
                sweetAlert2Error('网络异常，请重试！');
            }
        })
    }

    Post.del=function (postId) {
        let lId=layer.confirm('确定要删除该发布内容吗？', {
            btn: ['确认', '取消'] //按钮
        }, function () {
            layer.close(lId)
            sweetAlert2Loading('数据处理中...');
            $.ajax({
                type: 'POST',
                url: $('#ctx').val() + '/post/del',
                data: {'id': postId},
                dataType: 'json',
                success: function (data) {
                    swal.close();
                    if (data && data.resCode == 'success') {
                        $("#post-view").hide();
                        $("#postList").show();
                        Post.loadByPlate()
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

    Post.changeCommentStatus=function (postId,status) {
        let msg=status=='0'?'开启':'关闭'
        let lId=layer.confirm('确定要'+msg+'评论吗？', {
            btn: ['确认', '取消'] //按钮
        }, function () {
            layer.close(lId)
            sweetAlert2Loading('数据处理中...');
            $.ajax({
                type: 'POST',
                url: $('#ctx').val() + '/post/changeCommentStatus',
                data: {'id': postId,'status':status},
                dataType: 'json',
                success: function (data) {
                    swal.close();
                    if (data && data.resCode == 'success') {
                        Post.view(postId)
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

    Post.changeIfTop=function (postId,status) {
        let msg=status=='0'?'执行置顶':'取消置顶'
        let lId=layer.confirm('确定要'+msg+'操作吗？', {
            btn: ['确认', '取消'] //按钮
        }, function () {
            layer.close(lId)
            sweetAlert2Loading('数据处理中...');
            $.ajax({
                type: 'POST',
                url: $('#ctx').val() + '/post/changeIfTop',
                data: {'id': postId,'status':status},
                dataType: 'json',
                success: function (data) {
                    swal.close();
                    if (data && data.resCode == 'success') {
                        Post.view(postId)
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

    Post.changeIfEssence=function (postId,status) {
        let msg=status=='0'?'设置精华':'取消精华'
        let lId=layer.confirm('确定要'+msg+'操作吗？', {
            btn: ['确认', '取消'] //按钮
        }, function () {
            layer.close(lId)
            sweetAlert2Loading('数据处理中...');
            $.ajax({
                type: 'POST',
                url: $('#ctx').val() + '/post/changeIfEssence',
                data: {'id': postId,'status':status},
                dataType: 'json',
                success: function (data) {
                    swal.close();
                    if (data && data.resCode == 'success') {
                        Post.view(postId)
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

    Post.loadReplys = function (pageNum, postId) {

        pageNum = pageNum == undefined ? 1 : pageNum
        let param = {'targetObj': 'postInfo', 'targetId': postId, 'pn': pageNum};
        sweetAlert2Loading('评论数据加载中...');
        $.ajax({
            type: 'POST',
            url: $('#ctx').val() + '/reply/list',
            data: param,
            dataType: 'json',
            success: function (data) {
                swal.close();
                $("#replyDiv").empty();
                User.bindData(data.user, data.userReses, data.userRoles)
                data.postInfo = Post.info;
                $("#post_view_replys_tmpl").tmpl(data).appendTo("#replyDiv");
                if (data.page.totalRow > 0) {
                    $("#page").paging({
                        pageNo: data.page.pageNumber,
                        totalPage: data.page.totalPage,
                        totalSize: data.page.totalRow,
                        callback: function (num) {
                            Post.loadReplys(num, postId)
                        }
                    })
                }

            },
            error: function () {
                swal.close();
                sweetAlert2Error('网络异常，请重试！');
            }
        })

    }


    Post.replySomeone = function (replyTargetId, nickname, userId) {
        let replyTargetTxt = $("#postReplyContent_" + replyTargetId).html();
        let text = "<pre>回复：<a href=\"" + $('#ctx').val() + "/userHome/" + userId + "\" target=\"_blank\">" + nickname + "</a>";
        text = text + replyTargetTxt + "</pre>";
        text = text.replace('<p><br></p>', '');
        text = text + '<p><br></p>'
        $("#replyId").val(replyTargetId)
        postViewEditor.txt.clear();
        postViewEditor.txt.html(text);
        console.info(postViewEditor.txt.html())
        //$('html, body').animate({scrollTop: $('#postReplyContent').offset().top}, 1000)
        document.getElementById('postReplyContent').scrollIntoView();
    }


    Post.fastReply = async function fastReply(replyId, nickname) {

        let fastReplyEditor = null;
        // await swal({
        //     // input: 'textarea',
        //     html: '<div id="fastReplyDiv" style="text-align: left"></div>',
        //     title: nickname != undefined ? "回复：" + nickname : '',
        //     inputPlaceholder: '请输入回复的内容',
        //     showCancelButton: true,
        //     confirmButtonText: '提交',
        //     showLoaderOnConfirm: true,
        //     cancelButtonText: '取消',
        //     allowOutsideClick: false,
        //     width: '37rem',
        //     onOpen: function () {
        //         var E = window.wangEditor
        //         fastReplyEditor = new E('#fastReplyDiv')
        //         fastReplyEditor.customConfig.showLinkImg = false
        //         fastReplyEditor.customConfig.menus = wangEditor_only_font
        //         fastReplyEditor.customConfig.uploadImgHooks = wangEditor_upload_hooks
        //         fastReplyEditor.customConfig.zIndex = 100
        //         fastReplyEditor.customConfig.uploadFileName = 'file'
        //         fastReplyEditor.customConfig.uploadImgMaxSize = 0.3 * 1024 * 1024
        //         fastReplyEditor.customConfig.uploadImgServer = $('#ctx').val()+'/cmn/act01'
        //         fastReplyEditor.create();
        //
        //     },
        //     preConfirm: (text) => {
        //         return new Promise((resolve) => {
        //             let text = fastReplyEditor.txt.html();
        //             if (text == '<p><br></p>') {
        //                 swal.showValidationError(
        //                     '回复内容不能为空'
        //                 )
        //             }
        //             resolve()
        //         })
        //     },
        //     allowOutsideClick: () => !swal.isLoading()
        // }).then((result) => {
        //     if (result.value) {
        //         let objName = $("#replyObjName").val();
        //         let objId = $("#replyObjId").val();
        //         let text = fastReplyEditor.txt.html();
        //         // text = filterXSS(text);
        //         let param = {'content': text, 'targetId': objId, 'targetObj': objName, 'replyId': replyId}
        //         $.ajax({
        //             type: 'POST',
        //             url: $('#ctx').val() + '/reply/save',
        //             data: param,
        //             dataType: 'json',
        //             success: function (data) {
        //                 document.documentElement.style.overflow = "scroll";
        //                 if (data && data.resCode == 'success') {
        //                     // sweetAlert2Success()
        //                     swal(
        //                         data.resMsg
        //                     ).then((result) => {
        //                         Post.loadReplys(1,Post.info.id)
        //                     })
        //                 } else {
        //                     sweetAlert2Error(data.resMsg)
        //                 }
        //             },
        //             error: function () {
        //                 sweetAlert2Error('网络异常，请重试！');
        //             }
        //         })
        //     }
        // })
        let id = layer.open({
            type: 1,
            title: nickname != undefined ? "回复：" + nickname : '',
            closeBtn: false,
            // area:['500px', '450px'],
            id: 'post_fastReply',
            resize: false,
            content: '<div style="margin-right: 10px;margin-left: 10px;height: 350px"><div id="fastReplyDiv" style="text-align: left;"></div></div>',
            btn: ['提交回复', '取消'],
            btnAlign: 'c',
            skin: 'fastReply',
            success: function (layero, index) {
                var E = window.wangEditor
                fastReplyEditor = new E('#fastReplyDiv')
                fastReplyEditor.customConfig.showLinkImg = false
                fastReplyEditor.customConfig.menus = wangEditor_only_font
                fastReplyEditor.customConfig.uploadImgHooks = wangEditor_upload_hooks
                fastReplyEditor.customConfig.zIndex = 100
                fastReplyEditor.customConfig.uploadFileName = 'file'
                fastReplyEditor.customConfig.uploadImgMaxSize = 0.3 * 1024 * 1024
                fastReplyEditor.customConfig.uploadImgServer = $('#ctx').val() + '/cmn/act01'
                fastReplyEditor.create();
            },
            yes: function () {
                let objName = $("#replyObjName").val();
                let objId = $("#replyObjId").val();
                let text = fastReplyEditor.txt.html();
                sweetAlert2Loading('数据保存中...')
                let param = {'content': text, 'targetId': objId, 'targetObj': objName, 'replyId': replyId}
                $.ajax({
                    type: 'POST',
                    url: $('#ctx').val() + '/reply/save',
                    data: param,
                    dataType: 'json',
                    success: function (data) {
                        swal.close()
                        if (data && data.resCode == 'success') {
                            layer.close(id)
                            Post.loadReplys(1, Post.info.id)
                        } else {
                            sweetAlert2Error(data.resMsg)
                        }
                    },
                    error: function () {
                        swal.close()
                        sweetAlert2Error('网络异常，请重试！');
                    }
                })

            }
        })


    }


    Post.setBestReply = function (replyId) {
        let lId=layer.confirm('确定要设置该回复为最佳答案吗？', {
            btn: ['确认', '取消'] //按钮
        }, function () {
            layer.close(lId)
            sweetAlert2Loading('数据处理中...')
            $.ajax({
                type: 'POST',
                url: $('#ctx').val() + '/reply/setBestReply',
                data: {'id': replyId},
                dataType: 'json',
                success: function (data) {
                    swal.close()
                    if (data && data.resCode == 'success') {
                        Post.view(Post.info.id)
                        // sweetAlert2Error(data.resMsg)
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


    Post.delReply = function (replyId) {

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
                        Post.view(Post.info.id)
                        // sweetAlert2Error(data.resMsg)
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


    Post.hasDel = function () {
        return User.hasRes('/post/del') || Post.isLZ();
    },
        Post.hasSetTop = function () {
            return User.hasRes('/post/changeIfTop');
        },
        Post.hasSetEssence = function () {
            return User.hasRes('/post/changeIfEssence');
        },
        Post.hasSetCommentStatus = function () {
            return Post.isLZ() || User.hasRes('/post/changeCommentStatus');
        },
        Post.isLZ = function () {
            return User.getInfo().nickname == Post.info.oper.nickname;
        }

    //暴露给window
    window['Post'] = Post;
})();