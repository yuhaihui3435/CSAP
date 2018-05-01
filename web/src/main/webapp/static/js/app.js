(function ($) {
    "use strict";
    jQuery(document).ready(function ($) {
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
        $('#slider-area').on("translate.owl.carousel", function (e) {
            $(".single-slide h2, .single-slide p").removeClass("animated fadeInUp").css("opacity", "0");
            $(".single-slide .btn-filled,.single-slide .gr-btn").removeClass("animated fadeInDown").css("opacity", "0");
        });

        $('#slider-area').on("translated.owl.carousel", function (e) {
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
        $('.gallery-menu li').on("click", function (event) {
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
        $('#ajax-contact .btn-filled').on("click", function (e) {
            $('#ajax-contact').hide('slow');
        });

        $("#replyBtn").on("click", function (e) {
            let msg = editor2.txt.html();
            msg = filterXSS(msg);
            let objId = $("#replyObjId").val();
            let objName = $("#replyObjName").val();
            let replyId = $("#replyId").val();
            $(this).button('loading');
            let btn = this;
            if (Bee.StringUtils.isBlank(msg) || msg == '<p><br></p>') {
                $(this).button('reset');
                sweetAlert2Error('评论内容不能为空');
                return false;
            } else {
                let param = {'content': msg, 'targetId': objId, 'targetObj': objName, 'replyId': replyId}
                $.ajax({
                    type: 'POST',
                    url: $('#ctx').val() + '/reply/save',
                    data: param,
                    dataType: 'json',
                    success: function (data) {
                        $(btn).button('reset');
                        if (data && data.resCode == 'success') {
                            sweetAlert2Success(data.resMsg)
                            editor2.txt.clear();
                            loadReplys();
                        } else {
                            sweetAlert2Error(data.resMsg)
                        }
                    },
                    error: function () {
                        $(btn).button('reset');
                        sweetAlert2Error('网络异常，请重试！');
                    }
                })
            }
        });

         $(".jieda-accept").on("click",function (e) {
            let replyId=$(this).attr("data");
            alert(replyId)
        })


    });

    jQuery(window).on('load', function (e) {
        $('#preloader-container').fadeOut('slow');
    });

}(jQuery));


$.fn.code_Obj = function (o) {
    var _this = $(this);
    var options = {
        code_l: o.codeLength,//验证码长度
        codeChars: [0, 1, 2, 3, 4, 5, 6, 7, 8, 9,
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
        ],
        codeColors: ['#f44336', '#009688', '#cddc39', '#03a9f4', '#9c27b0', '#5e4444', '#9ebf9f', '#ffc8c4', '#2b4754', '#b4ced9', '#835f53', '#aa677e'],
        code_Init: function () {
            var code = "";
            var codeColor = "";
            var checkCode = _this.find("#data_code");
            for (var i = 0; i < this.code_l; i++) {
                var charNum = Math.floor(Math.random() * 52);
                code += this.codeChars[charNum];
            }
            for (var i = 0; i < this.codeColors.length; i++) {
                var charNum = Math.floor(Math.random() * 12);
                codeColor = this.codeColors[charNum];
            }
            if (checkCode) {
                checkCode.css('color', codeColor);
                checkCode.className = "code";
                checkCode.text(code);
                checkCode.attr('data-value', code);
            }
        }
    };

    options.code_Init();//初始化验证码
    _this.find("#data_code").bind('click', function () {
        options.code_Init();
    });
};


;(function () {
    var User=User||{'info':{},'reses':[],'roles':[]}
    User.setInfo=function (obj) {
        User.info=obj;
    }
    User.getInfo=function () {
        return User.info
    }
    User.setReses=function (array) {
        User.reses=array;
    }
    User.getReses=function () {
        return User.reses;
    }
    User.hasRes=function (tRes) {
        if(User.reses==undefined||User.reses.length==0)return false;
        else return Bee.ArrayUtils.exist(User.reses,tRes);
    }
    User.setRoles=function (array) {
        User.roles=array
    }
    User.getRoles=function () {
        return User.roles
    }
    User.hasRole=function (tRole) {
        if(User.roles==undefined||User.roles.length==0)return false;
        else return Bee.ArrayUtils.exist(User.roles,tRole);
    }
    User.bindData=function (info,userReses,userRoles) {
        User.setInfo(info)
        if(userReses&&userReses!='')
            User.setReses(userReses.split(","))
        if(userRoles&&userRoles!='')
            User.setRoles(userRoles.split(","))
    }

    window['User']=User;
})()

function loadReplys(pageNum) {
    pageNum = pageNum == undefined ? 1 : pageNum
    let objName = $("#replyObjName").val();
    let objId = $("#replyObjId").val();
    let param = {'targetObj': objName, 'targetId': objId, 'pn': pageNum};
    sweetAlert2Loading('评论数据加载中...');
    $.ajax({
        type: 'POST',
        url: $('#ctx').val() + '/reply/list',
        data: param,
        dataType: 'json',
        success: function (data) {
            swal.close();
            $(".comment-area").empty();
            $("#replys_tmpl").tmpl(data).appendTo(".comment-area");
            if (data.page.totalRow > 0) {
                $("#page").paging({
                    pageNo: data.page.pageNumber,
                    totalPage: data.page.totalPage,
                    totalSize: data.page.totalRow,
                    callback: function (num) {
                        loadReplys(num)
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


function sweetAlert2Error(msg) {
    swal({
        type: 'error',
        title: msg,
        // text: msg,
        confirmButtonText: '确定',

    })
}


function sweetAlert2Success(msg) {
    swal({
        type: 'success',
        // title: '成功消息',
        title: msg,
        confirmButtonText: '确定',
    })
}

function sweetAlert2Loading(msg) {
    swal({
        title: msg == undefined ? '数据加载中...' : msg,
        allowOutsideClick: false,
        onOpen: function () {
            swal.showLoading();
        }
    })
}

const wangEditor_terse_menu = [
    'bold',  // 粗体
    'fontSize',  // 字号
    'underline',  // 下划线
    'strikeThrough',  // 删除线
    'justify',  // 对齐方式
    'list',  // 列表
    'link',  // 插入链接
    'quote',  // 引用
    'emoticon',  // 表情
    'image',  // 插入图片
    'video',  // 插入视频
    'undo',  // 撤销
    'redo'  // 重复
]

const wangEditor_only_font = [
    'bold',  // 粗体
    'fontSize',  // 字号
    'fontName',  // 字体
    'italic',  // 斜体
    'underline',  // 下划线
    'strikeThrough',  // 删除线
    'foreColor',  // 文字颜色
    'link',  // 插入链接
    'list',  // 列表
    'justify',  // 对齐方式
    'quote',  // 引用
    'emoticon',  // 表情
    'undo',  // 撤销
    'redo'  // 重复
]
const wangEditor_upload_hooks={
    before: function (xhr, editor, files) {
        sweetAlert2Loading('图片上传处理中，请等待...')
    },
    success: function (xhr, editor, result) {
        swal.close();
        // 图片上传并返回结果，图片插入成功之后触发
        // xhr 是 XMLHttpRequst 对象，editor 是编辑器对象，result 是服务器端返回的结果
    },
    fail: function (xhr, editor, result) {
        swal.close();
        sweetAlert2Error('图片上传成功，但插入失败')
    },
    error: function (xhr, editor) {
        swal.close();
        sweetAlert2Error('图片上传失败')
    },
    timeout: function (xhr, editor) {
        swal.close();
        sweetAlert2Error('图片上传超时')
    },
    customInsert: function (insertImg, result, editor) {
        var url = result.resData
        insertImg(url)
    }
}
async function fastReply(replyId, nickname) {
    let fastReplyEditor = null;
    await swal({
        // input: 'textarea',
        html: '<div id="fastReplyDiv" style="text-align: left"></div>',
        title: nickname != undefined ? "回复：" + nickname : '',
        inputPlaceholder: '请输入回复的内容',
        showCancelButton: true,
        confirmButtonText: '提交',
        showLoaderOnConfirm: true,
        cancelButtonText: '取消',
        allowOutsideClick: false,
        width: '37rem',
        onOpen: function () {
            var E = window.wangEditor
            fastReplyEditor = new E('#fastReplyDiv')
            fastReplyEditor.customConfig.showLinkImg = false
            fastReplyEditor.customConfig.menus = wangEditor_terse_menu
            fastReplyEditor.customConfig.uploadImgHooks = wangEditor_upload_hooks
            fastReplyEditor.customConfig.zIndex = 100
            fastReplyEditor.customConfig.uploadFileName = 'file'
            fastReplyEditor.customConfig.uploadImgMaxSize = 0.3 * 1024 * 1024
            fastReplyEditor.customConfig.uploadImgServer = '#(ctx)/cmn/act01'
            fastReplyEditor.create();
        },
        preConfirm: (text) => {
            return new Promise((resolve) => {
                let text = fastReplyEditor.txt.html();
                if (text == '<p><br></p>') {
                    swal.showValidationError(
                        '回复内容不能为空'
                    )
                }
                resolve()
            })
        },
        allowOutsideClick: () => !swal.isLoading()
    }).then((result) => {
        if (result.value) {
            let objName = $("#replyObjName").val();
            let objId = $("#replyObjId").val();
            let text = fastReplyEditor.txt.html();
            text = filterXSS(text);
            let param = {'content': text, 'targetId': objId, 'targetObj': objName, 'replyId': replyId}
            $.ajax({
                type: 'POST',
                url: $('#ctx').val() + '/reply/save',
                data: param,
                dataType: 'json',
                success: function (data) {
                    if (data && data.resCode == 'success') {
                        // sweetAlert2Success()
                        swal(
                            data.resMsg
                        ).then((result) => {
                            loadReplys(1)
                        })
                    } else {
                        sweetAlert2Error(data.resMsg)
                    }
                },
                error: function () {
                    sweetAlert2Error('网络异常，请重试！');
                }
            })
        }
    })

}

/**
 *
 * 点赞处理
 *
 * @param targetId
 * @param targetObj
 * @param thumbToken
 */
function thumb(targetId, targetObj, thumbToken) {
    let action = thumbToken == 'yes' ? 'thumbDown' : 'thumbUp'
    thumbToken = thumbToken == 'yes' ? 'no' : 'yes';
    $.ajax({
        type: 'POST',
        url: $('#ctx').val() + '/' + action,
        data: {'targetId': targetId, 'targetObj': targetObj},
        dataType: 'json',
        success: function (data) {
            if (data && data.resCode == 'success') {

                if (action == 'thumbUp') {
                    $('#thumb_span').html('<i class="fa fa-thumbs-up"></i>' + data.resData);

                    $('#thumb_span').removeAttr('onClick');
                    $('#thumb_span').off();
                    $('#thumb_span').on("click", function () {
                        thumb(targetId, targetObj, thumbToken)
                    })
                } else {
                    $('#thumb_span').removeAttr('onClick');
                    $('#thumb_span').off();
                    $('#thumb_span').html('<i class="fa fa-thumbs-o-up"></i>' + data.resData);
                    $('#thumb_span').on("click", function () {
                        thumb(targetId, targetObj, thumbToken)
                    })
                }

            } else {
                sweetAlert2Error(data.resMsg)
            }
        },
        error: function () {
            sweetAlert2Error('网络异常，请重试！');
        }
    })
}

function collect(targetId, targetObj, collectToken) {
    let action = collectToken == 'yes' ? 'del' : 'save'
    collectToken = collectToken == 'yes' ? 'no' : 'yes';
    let url = window.location.href;
    $.ajax({
        type: 'POST',
        url: $('#ctx').val() + '/collect/' + action,
        data: {'targetId': targetId, 'targetObj': targetObj, 'title': $(document).attr("title"), 'url': url},
        dataType: 'json',
        success: function (data) {
            if (data && data.resCode == 'success') {

                if (action == 'save') {
                    $('#collect_span').html('<i class="fa fa-heart"></i>' + data.resData);

                    $('#collect_span').removeAttr('onClick');
                    $('#collect_span').off();
                    $('#collect_span').on("click", function () {
                        collect(targetId, targetObj, collectToken)
                    })
                } else {
                    $('#collect_span').removeAttr('onClick');
                    $('#collect_span').off();
                    $('#collect_span').html('<i class="fa fa-heart-o"></i>' + data.resData);
                    $('#collect_span').on("click", function () {
                        collect(targetId, targetObj, collectToken)
                    })
                }

            } else {
                sweetAlert2Error(data.resMsg)
            }
        },
        error: function () {
            sweetAlert2Error('网络异常，请重试！');
        }
    })
}

var setJSONLocalCache=function (key,val) {
    if(Bee.JSONUtils.isJSONObj(val)){
        let jsonStr=JSON.stringify(val)
        setLocalCache(key,jsonStr)
    }
}

var getJSONLocalCache=function (key) {
    let jsonStr=getLocalCache(key)
    return jsonStr&&jsonStr!=''?JSON.parse(jsonStr):''
}

var setLocalCache=function (key,val) {

    if(window.Storage && window.localStorage && window.localStorage instanceof Storage){
        let storage=window.localStorage;
        storage.setItem(key,val);
    }
}

var getLocalCache=function (key) {
    if(window.Storage && window.localStorage && window.localStorage instanceof Storage){
        let storage=window.localStorage;
        return storage.getItem(key);
    }
}



