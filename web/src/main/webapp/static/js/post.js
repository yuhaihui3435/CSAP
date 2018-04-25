;(function ( ){
    var Post=Post||{}
    Post.loadByPlate=function (taxId,searchKey) {
        $("#postList").show();
        $("#addPost").hide();
        searchKey=searchKey==undefined?'':searchKey;
        let param={'taxId':taxId,'search':searchKey};
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

    Post.toAddPost=function () {
        $("#addPost").show();
        $("#postList").hide();
        $('#check-code').code_Obj({
            codeLength: 5
        });
    }
    
    Post.titleFocus=function (elem) {
        $(elem).parent().addClass("layui-this");
        $(elem).parent().siblings().removeClass("layui-this");
    }
    //暴露给window
    window['Post'] = Post;
})();