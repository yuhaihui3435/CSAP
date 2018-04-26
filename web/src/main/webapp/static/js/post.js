;(function ( ){
    var Post=Post||{}
    Post.loadByPlate=function (taxId,searchKey,pn) {
        $("#postList").show();
        $("#addPost").hide();
        $("#postView").hide();
        searchKey=searchKey==undefined?'':searchKey;
        pn=pn==undefined?1:pn;
        let param={'taxId':taxId,'search':searchKey,'pn':pn};
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
        let content=editor2.txt.html();


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
                    $("#postView").empty();
                    $("#postList").hide();
                    $("#postView").show();
                    let userRes=Cookies.get('userRes');
                    if(userRes!=undefined)userRes= eval ("(" + userRes+ ")")
                    let userName=Cookies.get('userName');
                    $("#post_view_tmpl").tmpl(data,{
                        hasDel:function (postAuthorName) {
                            return Bee.ArrayUtils.exist(userRes,'/post/del')||userName==postAuthorName;
                        },
                        hasSetTop:function () {
                            return Bee.ArrayUtils.exist(userRes,'/post/setTop');
                        },
                        hasSetEssence:function () {
                            return Bee.ArrayUtils.exist(userRes,'/post/setEssence');
                        },
                        hasSetCommentStatus:function (postAuthorName) {
                            return userName==postAuthorName||Bee.ArrayUtils.exist(userRes,'/post/setCommentStatus');
                        },
                        isLZ:function (postAuthorName) {
                            return userName==postAuthorName;
                        }

                    }).appendTo("#postView");




                }

            },
            error:function () {
                swal.close();
                sweetAlert2Error('网络异常，请重试！');
            }
        })
    }

    Post.loadReplys=function (pageNum,postId) {
        function loadReplys(pageNum) {
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
                    let userRes=Cookies.get('userRes');
                    if(userRes!=undefined)userRes= eval ("(" + userRes+ ")")
                    let userName=Cookies.get('userName');
                    $("#post_view_replys_tmpl").tmpl(data,{
                        isLZ:function (postAuthorName) {
                            return userName==postAuthorName;
                        }}).appendTo("#postView");
                    if(data.page.totalRow>0){
                        $("#page").paging({
                            pageNo:data.page.pageNumber,
                            totalPage: data.page.totalPage,
                            totalSize: data.page.totalRow,
                            callback: function(num) {
                                loadReplys(num)
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
    }

    //暴露给window
    window['Post'] = Post;
})();