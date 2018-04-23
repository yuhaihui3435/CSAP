(function($) {
    "use strict";
    jQuery(document).ready(function($) {
        /*-------------------------------
        STICKY NAVIGATION
        ---------------------------------*/
        $('.header-bottom').sticky({
            topSpacing: 0
        });
        /*-------------------------------
        BOOTSTARP SCROLLSPY
        ---------------------------------*/
        $('body').scrollspy({
            target: '.navbar-collapse',
            offset: 95
        });
        /*-------------------------------
        SMOTH SCROLLING
        ---------------------------------*/
        // $('li.smooth-menu a').bind("click", function(event) {
        //     var $anchor = $(this);
        //     var headerH = '70';
        //     $('html,body').stop().animate({
        //         scrollTop: $($anchor.attr('href')).offset().top - headerH + "px"
        //     }, 1200, 'easeInOutExpo');
        //     event.preventDefault();
        // });
        /*-------------------------------
        NEWS TICKER
        ---------------------------------*/
        $.simpleTicker($('#rss-item'), {
            speed: 1000,
            delay: 3000,
            easing: 'swing',
            effectType: 'roll'
        });
        /*-------------------------------
        SLIDER CAROUSEL
        ---------------------------------*/
        $('#slider-area').owlCarousel({
            items: 1,
            dots: false,
            loop: true,
            nav: true,
            autoplay: true,
            animateOut: 'fadeOut',
            animateIn: 'fadeIn',
            navText: ['<i class="fa fa-long-arrow-left"></i>', '<i class="fa fa-long-arrow-right"></i>']
        });
        /*-------------------------------
        SLIDER EFFECT
        ---------------------------------*/
        $('#slider-area').on("translate.owl.carousel", function(e) {
            $(".single-slide h2, .single-slide p").removeClass("animated fadeInUp").css("opacity", "0");
            $(".single-slide .btn-filled,.single-slide .gr-btn").removeClass("animated fadeInDown").css("opacity", "0");
        });

        $('#slider-area').on("translated.owl.carousel", function(e) {
            $(".single-slide h2, .single-slide p").addClass("animated fadeInUp").css("opacity", "1");
            $(".single-slide .btn-filled,.single-slide .gr-btn").addClass("animated fadeInDown").css("opacity", "1");
        });
        /*-------------------------------
        SLIDER CAROUSEL
        ---------------------------------*/
        $('.slider-wrap').owlCarousel({
            autoPlay: false, //Set AutoPlay to 3 seconds
            items: 1,
            pagination: false,
            navigation: true,
            navigationText: ['<i class="fa fa-angle-left"></i>', '<i class="fa fa-angle-right"></i>']
        });
        /*-------------------------------
        PORTFOLIO ISOTOPE INT
        ---------------------------------*/
        $('.gallery-grid').isotope({
            itemSelector: '.single-gallery',
            layoutMode: 'fitRows'
        });

        /*-------------------------------
        PORTFOLIO ISOTOPE CLICK FUNC
        ---------------------------------*/
        $('.gallery-menu li').on("click", function(event) {
            $(".gallery-menu li").removeClass("active");
            $(this).addClass("active");
            var selector = $(this).attr('data-filter');
            $(".gallery-grid").isotope({
                filter: selector,
                animationOptions: {
                    duration: 750,
                    easing: 'linear',
                    queue: false,
                }
            });
            return false;
        });
        /*-------------------------------
        LIGHTBOX
        ---------------------------------*/
        $('a.pretty').prettyPhoto({
            default_width: 500,
            default_height: 344,
        });
        

        /*-------------------------------
        Service Carousel
        ---------------------------------*/
        $('.testimonial-wrap').owlCarousel({
            autoPlay: true, //Set AutoPlay to 3 seconds
            items: 1,
            dots: true,
            nav: false,
        });
        $('#ajax-contact .btn-filled').on("click", function(e){
            $('#ajax-contact').hide('slow');
        });
        
        $("#replyBtn").on("click",function (e) {
            let msg=editor2.txt.html();
            let objId=$("#replyObjId").val();
            let objName=$("#replyObjName").val();
            let replyId=$("#replyId").val();
            $(this).button('loading');
            let btn=this;
            if(Bee.StringUtils.isBlank(msg)){
                $(this).button('reset');
                sweetAlert2Error('评论内容不能为空');
                return false;
            }else{
                let param={'content':msg,'targetId':objId,'targetObj':objName,'replyId':replyId}
                $.ajax({
                    type:'POST',
                    url:$('#ctx').val()+'/reply/save',
                    data:param,
                    dataType:'json',
                    success:function (data) {
                        $(btn).button('reset');
                        if(data&&data.resCode=='success'){
                            sweetAlert2Success(data.resMsg)
                            editor2.txt.clear();
                            loadReplys();
                        }else{
                            sweetAlert2Error(data.resMsg)
                        }
                    },
                    error:function () {
                        $(btn).button('reset');
                        sweetAlert2Error('网络异常，请重试！');
                    }
                })
            }
        })
    });

    jQuery(window).on('load',function(e) {
        /*-------------------------------
        PRELOADER
        ---------------------------------*/
        $('#preloader-container').fadeOut('slow');
    });

}(jQuery));

function loadReplys(pageNum) {
    pageNum=pageNum==undefined?1:pageNum
    let objName=$("#replyObjName").val();
    let objId=$("#replyObjId").val();
    let param={'targetObj':objName,'targetId':objId,'pn':pageNum};
    sweetAlert2Loading('评论数据加载中...');
    $.ajax({
        type:'POST',
        url:$('#ctx').val()+'/reply/list',
        data:param,
        dataType:'json',
        success:function (data) {
            swal.close();
            $(".comment-area").empty();
            $("#replys_tmpl").tmpl(data).appendTo(".comment-area");
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



function sweetAlert2Error(msg) {
    swal({
        type: 'error',
        title: msg,
        // text: msg,
        confirmButtonText:'确定',

    })
}


function sweetAlert2Success(msg) {
    swal({
        type: 'success',
        // title: '成功消息',
        title: msg,
        confirmButtonText:'确定',
    })
}

function sweetAlert2Loading(msg) {
    swal({
        title:msg==undefined?'数据加载中...':msg,
        allowOutsideClick:false,
        onOpen:function () {
            swal.showLoading();
        }
    })
}

async function fastReply(replyId,nickname) {
    await swal({
        input: 'textarea',
        title:nickname!=undefined?"回复："+nickname:'',
        inputPlaceholder: '请输入回复的内容,长度不超过200字。',
        showCancelButton: true,
        confirmButtonText:'提交',
        showLoaderOnConfirm: true,
        cancelButtonText:'取消',
        allowOutsideClick:false,
        preConfirm: (text) => {
            return new Promise((resolve) => {
                    if(text=='') {
                        swal.showValidationError(
                            '回复内容不能为空'
                        )
                    }if(text.length>200){
                            swal.showValidationError(
                                '回复内容不能超过200字'
                            )
                        }
                    resolve()
            })
        },
        allowOutsideClick: () => !swal.isLoading()
    }).then((result)=>{
        if(result.value){
            let objName=$("#replyObjName").val();
            let objId=$("#replyObjId").val();
            let param={'content':result.value,'targetId':objId,'targetObj':objName,'replyId':replyId}
            $.ajax({
                type:'POST',
                url:$('#ctx').val()+'/reply/save',
                data:param,
                dataType:'json',
                success:function (data) {
                    if(data&&data.resCode=='success'){
                        // sweetAlert2Success()
                        swal(
                            data.resMsg
                        ).then((result)=>{
                                loadReplys(1)
                        })
                    }else{
                        sweetAlert2Error(data.resMsg)
                    }
                },
                error:function () {
                    sweetAlert2Error('网络异常，请重试！');
                }
            })
        }
    })

}

