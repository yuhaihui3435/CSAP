;(function ( ){
    var Post=Post||{}
    Post.isRefresh="no";
    Post.info={}
    Post.loadByPlate=function (taxId,searchKey,pn) {
        $("#postList").show();
        $("#addPost").hide();
        $("#post-view").hide();
        searchKey=searchKey==undefined?'':searchKey;
        pn=pn==undefined?1:pn;
        let param={'taxId':taxId,'search':searchKey,'pn':pn};
        if(Post.isRefresh=='yes'){
            let cacheData=getJSONLocalCache("post_load_param");
            param=cacheData!=''?cacheData:param
            Post.resetTitleFocus(param.taxId);
        }else{
            setJSONLocalCache("post_load_param",param)
        }

        sweetAlert2Loading('数据加载中...');
        $.ajax({
            type:'POST',
            url:$('#ctx').val()+'/post/list',
            data:param,
            dataType:'json',
            success:function (data) {
                swal.close();
                $("#post-area").empty();
                if(data.topList==undefined)
                    data.topList='';
                $("#post_tmpl").tmpl(data).appendTo("#post-area");
                if(data.page.totalRow>0){
                    let laypage=layui.laypage;
                    laypage.render({
                        elem:'pageDiv',
                        count:data.page.totalRow,
                        limit:data.page.pageSize,
                        jump: function(obj, first){

                            //首次不执行
                            if(!first){
                                Post.loadByPlate(taxId,searchKey,obj.curr);
                            }
                    }
                    })
                }
            },
            error:function () {
                swal.close();
                sweetAlert2Error('网络异常，请重试！');
            }
        })

        if(Post.isRefresh=='yes')Post.isRefresh='no';
    }

    Post.toAddPost=function () {
        $("#addPost").show();
        $("#postList").hide();
        $("#post-view").hide();
        $('#check-code').code_Obj({
            codeLength: 5
        });
    }
    
    Post.titleFocus=function (elem) {
        $(elem).parent().addClass("layui-this");
        $(elem).parent().siblings().removeClass("layui-this");
    }

    Post.resetTitleFocus=function (plateId) {
        $('#plate_'+plateId).parent().addClass("layui-this");
        $('#plate_'+plateId).parent().siblings().removeClass("layui-this");
    }


    Post.addPost=function(){
        let verCode=$("#verCode").val();
        verCode=verCode.toUpperCase()
        if(verCode==''){
            sweetAlert2Error('请填写验证码!');
            return false;
        }
        let checkCode=$("#data_code").attr("data-value");
        checkCode=checkCode.toUpperCase()
        if(verCode!=checkCode){
            sweetAlert2Error('验证码不正确!')
            return false;
        }

        let taxId=$("#postTax").val();
        let title=$("#postTitle").val();
        let content=postAddEditor.txt.html();


        if(content==''||content=='<p><br></p>'){
            sweetAlert2Error('发表的内容不能未空!')
            return false
        }

        let param={'title':title,'taxId':taxId,'content':content}
        sweetAlert2Loading('数据保存中...');
        $.ajax({
            type:'POST',
            url:$('#ctx').val()+'/post/save',
            data:param,
            dataType:'json',
            success:function (data) {
                swal.close();
                if(data.resCode=='success'){
                    sweetAlert2Success(data.resMsg);
                }else{
                    sweetAlert2Error(data.resMsg);
                }
            },
            error:function () {
                swal.close();
                sweetAlert2Error('网络异常，请重试！');
            }
        })
    }

    Post.view=function (postId) {
        sweetAlert2Loading('数据加载中...');
        $.ajax({
            type:'POST',
            url:$('#ctx').val()+'/post/'+postId,
            data:'',
            dataType:'json',
            success:function (data) {
                swal.close();

                if(data==undefined||data==''){
                    sweetAlert2Error('没有查询到数据!')
                }else{
                    postViewEditor.txt.clear()
                    $("#viewDiv").empty();
                    $("#replyDiv").empty();
                    $("#postList").hide();
                    $("#post-view").show();

                    Post.info=Object.assign({},data.postInfo)

                    User.bindData(data.user,data.userReses,data.userRoles)

                    $("#post_view_tmpl").tmpl(data).appendTo("#viewDiv");
                    //加载回复数据
                    Post.loadReplys(1,postId)
                }

            },
            error:function () {
                swal.close();
                sweetAlert2Error('网络异常，请重试！');
            }
        })
    }

    Post.loadReplys=function (pageNum,postId) {

            pageNum=pageNum==undefined?1:pageNum
            let param={'targetObj':'postInfo','targetId':postId,'pn':pageNum};
            sweetAlert2Loading('评论数据加载中...');
            $.ajax({
                type:'POST',
                url:$('#ctx').val()+'/reply/list',
                data:param,
                dataType:'json',
                success:function (data) {
                    swal.close();
                    $("#replyDiv").empty();
                    User.bindData(data.user,data.userReses,data.userRoles)
                    $("#post_view_replys_tmpl").tmpl(data).appendTo("#replyDiv");
                    if(data.page.totalRow>0){
                        $("#page").paging({
                            pageNo:data.page.pageNumber,
                            totalPage: data.page.totalPage,
                            totalSize: data.page.totalRow,
                            callback: function(num) {
                                Post.loadReplys(num,postId)
                            }
                        })
                    }

                },
                error:function () {
                    swal.close();
                    sweetAlert2Error('网络异常，请重试！');
                }
            })

    }
    
    
    Post.replySomeone=function (replyTargetId,nickname,userId) {
        let replyTargetTxt=$("#postReplyContent_"+replyTargetId).html();
        let text="<pre>回复：<a href=\""+$('#ctx').val()+"/userHome/"+userId+"\" target=\"_blank\">"+nickname+"</a><br>";
        text=text+replyTargetTxt+"</pre>";
        postViewEditor.txt.append(text);
        //$('html, body').animate({scrollTop: $('#postReplyContent').offset().top}, 1000)
        document.getElementById('postReplyContent').scrollIntoView();
    }



    Post.hasDel=function () {
        return User.hasRes('/post/del')||Post.isLZ();
    },
    Post.hasSetTop=function () {
        return User.hasRes('/post/setTop');
    },
    Post.hasSetEssence=function () {
        return User.hasRes('/post/setEssence');
    },
    Post.hasSetCommentStatus=function () {
        return Post.isLZ()||User.hasRes('/post/setCommentStatus');
    },
    Post.isLZ=function () {
        return User.getInfo().nickname==Post.info.oper.nickname;
    }

    //暴露给window
    window['Post'] = Post;
})();