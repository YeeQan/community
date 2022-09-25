jQuery.extend({

    /**
     * 初始化首页
     */
    initIndex: function () {
        $.initTop();
        $.initFooter();
        $.postIndexPage();
    },

    /**
     * 初始化顶部栏
     */
    initTop: function () {
        var token = $.cookie("token");
        if (token) {
            $("#header").load(
                "/community/common/header-logined"
            );
        } else {
            $("#header").load(
                "/community/common/header-non-logined"
            );
        }
    },

    /**
     * 初始化底部内容
     */
    initFooter: function () {
        $("#footer").load("/community/common/footer");
    },

    /**
     * 初始化发布页
     */
    initPublish: function () {
        $.initTop();
        $.initFooter();
        $(function () {
            var editor = editormd("topic-editor", {
                width: "100%",
                height: "500px",
                path: "/community/editor.md/lib/",
                placeholder: "请在这里输入详细描述",
                syncScrolling: "single",
                saveHTMLToTextarea: true,
                emoji: true,
                tex: true, // 开启科学公式TeX语言支持，默认关闭
                flowChart: true, // 开启流程图支持，默认关闭
                sequenceDiagram: true, // 开启时序/序列图支持，默认关闭,
                imageUpload: true,
                imageFormats: ["jpg", "jpeg", "gif", "png", "bmp", "webp"],
            });
            $("#publish-topic").click(function () {
                var requestJson = {
                        topicTitle: $("#topic-title").val(),
                        topicContent: editor.getMarkdown(),
                };
                $.ajax({
                    contentType: "application/json",
                    type: "POST",
                    url: "/community/topic/publish",
                    dataType: "json",
                    data: JSON.stringify(requestJson),
                    success: function (result) {
                        if (result == null) {
                            $.alert({
                                title: "出错啦!",
                                content: "请稍后再试！",
                            });
                        } else {
                            if (result.code !== "2000") {
                                $.alert({
                                    title: "出错啦!",
                                    content: result.description,
                                });
                            } else {
                                var topic = result.data[0];
                                $(location).attr(
                                    "href",
                                    "/community/topic/view/" + topic.id
                                );
                            }
                        }
                    },
                });
            });
        });
    },

    /**
     * 初始化编辑页
     */
    initEdit: function (topicId) {
        // 初始化帖子编辑内容
        var sectionId;
        var requestJson = {
            data: [
                {
                    topicId: topicId
                },
            ],
        };
        $.ajax({
            contentType: "application/json",
            type: "POST",
            url: "/community/topic/info",
            dataType: "json",
            data: JSON.stringify(requestJson),
            success: function (result) {
                if (result == null) {
                    $.alert({
                        title: "出错啦!",
                        content: "请稍后再试！",
                    });
                } else {
                    if (result.code !== "2000") {
                        $.alert({
                            title: "出错啦!",
                            content: result.description,
                        });
                    } else {
                        $("#topicTitle-edit").val(result.data[0].topicTitle)
                        $("#topicDescription-edit").val(result.data[0].topicContent)
                        sectionId = result.data[0].section
                        console.log(sectionId)
                    }
                }
            },
        });
        // 初始化专栏
        $.post("/community/section/list", function (result) {
            if (result == null) {
                $.alert({
                    title: "出错啦!",
                    content: "请稍后再试！",
                });
            } else {
                if (result.code !== "2000") {
                    $.alert({
                        title: "出错啦!",
                        content: result.description,
                    });
                } else {
                    var selectSectionEdit = $("#selectSection-edit");

                    $.each(result.data, function (index, section) {
                        var $option = $("<option/>", {
                            value: section.sectionId,
                            html: section.sectionName,
                        });
                        if (section.sectionId === sectionId) {
                            $option.attr("selected", "selected")
                        }
                        selectSectionEdit.append($option);
                    });
                }
            }
        });
        $(function () {
            var editor = editormd("topicEditor", {
                width: "100%",
                height: "500px",
                path: "/community/editor.md/lib/",
                placeholder: "请在这里输入详细描述",
                syncScrolling: "single",
                saveHTMLToTextarea: true,
                emoji: true,
                tex: true, // 开启科学公式TeX语言支持，默认关闭
                flowChart: true, // 开启流程图支持，默认关闭
                sequenceDiagram: true, // 开启时序/序列图支持，默认关闭,
                imageUpload: true,
                imageFormats: ["jpg", "jpeg", "gif", "png", "bmp", "webp"],
            });
            $("#editTopic").click(function () {
                var requestJson = {
                    data: [
                        {
                            topicId: topicId,
                            topicTitle: $("#topicTitle-edit").val(),
                            topicContent: editor.getMarkdown(),
                            section: $("#selectSection-edit").val(),
                        },
                    ],
                };
                $.ajax({
                    contentType: "application/json",
                    type: "POST",
                    url: "/community/topic/edit",
                    dataType: "json",
                    data: JSON.stringify(requestJson),
                    success: function (result) {
                        if (result == null) {
                            $.alert({
                                title: "出错啦!",
                                content: "请稍后再试！",
                            });
                        } else {
                            if (result.code !== "2000") {
                                $.alert({
                                    title: "出错啦!",
                                    content: result.description,
                                });
                            } else {
                                var topic = result.data[0];
                                $(location).attr(
                                    "href",
                                    "/community/topic/view/" + topic.topicId
                                );
                            }
                        }
                    },
                });
            });
        });
    },

    /**
     * 初始化已登录顶部栏
     */
    initLoginedHeader: function () {
        // 获取用户信息
        $.ajax({
            contentType: "application/json",
            type: "POST",
            url: "/community/user/loginInfo",
            success: function (result) {
                if (result == null) {
                    $.alert({
                        title: "出错啦!",
                        content: "请稍后再试！",
                    });
                } else {
                    if (result.code !== "2000") {
                        $.alert({
                            title: "出错啦!",
                            content: result.description,
                        });
                    } else {
                        var headPortrait = result.data[0].headPortrait;
                        $("#userDropDown-button").html("<img class='rounded-circle' width='32' height='32' src='" + headPortrait + "' alt='头像'>")
                    }
                }
            },
        });
        // 初始化退出按钮
        $("#logout").click((function () {
            $.ajax({
                contentType: "application/json",
                type: "POST",
                url: "/community/user/logout",
                success: function (result) {
                    if (result == null) {
                        $.alert({
                            title: "出错啦!",
                            content: "请稍后再试！",
                        });
                    } else {
                        if (result.code !== "2000") {
                            $.alert({
                                title: "出错啦!",
                                content: result.description,
                            });
                        } else {
                            location.reload();
                        }
                    }
                },
            })
        }))
    },

    /**
     * 初始化未登录顶部栏
     */
    initNonLoginedHeader: function () {
        // 初始化注册按钮
        $("#register-button").click(function () {
            var requestJson = {
                account: $("#registerAccount").val(),
                username: $("#registerName").val(),
                password: $("#registerPassword").val()
            };
            console.log(requestJson);
            $.ajax({
                contentType: "application/json",
                type: "POST",
                url: "/community/user/register",
                dataType: "json",
                data: JSON.stringify(requestJson),
                success: function (result) {
                    if (result == null) {
                        $.alert({
                            title: "出错啦!",
                            content: "请稍后再试！",
                        });
                    } else {
                        if (result.code !== "2000") {
                            var registerErrorMsg = 
                                "<div class='alert alert-danger alert-dismissible fade show'>" +
                                "   <button type='button' class='close' data-dismiss='alert'>&times;</button>" +
                                "   <span>" + result.description + "</span>" +
                                "</div>"
                            $("#registerErrorMsg").html(registerErrorMsg)
                        } else {
                            location.reload();
                        }
                    }
                },
            });
        });
        // 初始化登录按钮
        $("#login-button").click(function () {
            var requestJson = {
                account: $("#loginAccount").val(),
                password: $("#loginPassword").val()
            };
            $.ajax({
                contentType: "application/json",
                type: "POST",
                url: "/community/user/login",
                dataType: "json",
                data: JSON.stringify(requestJson),
                success: function (result) {
                    if (result == null) {
                        $.alert({
                            title: "出错啦!",
                            content: "请稍后再试！",
                        });
                    } else {
                        if (result.code !== "2000") {
                            var loginErrorMsg =
                                "<div class='alert alert-danger alert-dismissible fade show'>" +
                                "   <button type='button' class='close' data-dismiss='alert'>&times;</button>" +
                                "   <span>" + result.description + "</span>" +
                                "</div>"
                            $("#loginErrorMsg").html(loginErrorMsg)
                        } else {
                            location.reload();
                        }
                    }
                },
            });
        });
    },

    /**
     * 渲染个人主页帖子列表
     */
    renderUserDynamicList: function (dataList) {
        $("#dynamic-list").empty()
        $.each(dataList, function (index, data) {
            var $dynamicDiv = $("<div/>", {
                class: "row border border-bottom-0 border-left-0 border-right-0 community-ele-border-color-grey"
            })
            var $dynamicContent = $("<div/>", {
                class: "col-lg-12 my-lg-3"
            })
            var $dynamicMedia = $("<div/>", {
                class: "media"
            })
            var $mediaImg = $("<img/>", {
                src: data.headPortrait,
                class: "mr-lg-3 rounded-circle community-ele-width-50px community-ele-height-50px"
            })

            var $mediaBody
            if (data.dynamicType === "1") {
                $mediaBody = $("<div/>", {
                    class: "media-body"
                }).append($("<h6/>", {
                    class: "font-weight-bold",
                    html: data.createUsername
                }))
                    .append($("<p/>", {
                        class: "small text-black-50",
                        html: data.relativeDate
                    }))
                    .append($("<p/>", {
                        html: "发布了帖子&nbsp;"
                    }).append($("<span/>", {
                        class: "font-weight-bold",
                        html: data.targetName
                    })))
                    .append($("<p/>", {
                        html: data.dynamicContent
                    }));
            } else if (data.dynamicType === "2") {
                $mediaBody = $("<div/>", {
                    class: "media-body"
                }).append($("<h6/>", {
                    class: "font-weight-bold",
                    html: data.createUsername
                }))
                    .append($("<p/>", {
                        class: "small text-black-50",
                        html: data.relativeDate
                    }))
                    .append($("<p/>", {
                        html: "发布了对&nbsp;"
                    }).append($("<span/>", {
                        class: "font-weight-bold",
                        html: data.targetName
                    })).append($("<span/>", {
                        html: "&nbsp;的评论"
                    })))
                    .append($("<p/>", {
                        html: data.dynamicContent
                    }));
            } else if (data.dynamicType === "3") {
                $mediaBody = $("<div/>", {
                    class: "media-body"
                }).append($("<h6/>", {
                    class: "font-weight-bold",
                    html: data.createUsername
                }))
                    .append($("<p/>", {
                        class: "small text-black-50",
                        html: data.relativeDate
                    }))
                    .append($("<p/>", {
                        html: "点赞了帖子&nbsp;"
                    }).append($("<span/>", {
                        class: "font-weight-bold",
                        html: data.targetName
                    })))
                    .append($("<p/>", {
                        html: data.dynamicContent
                    }));
            } else if (data.dynamicType === "4") {
                $mediaBody = $("<div/>", {
                    class: "media-body"
                }).append($("<h6/>", {
                    class: "font-weight-bold",
                    html: data.createUsername
                }))
                    .append($("<p/>", {
                        class: "small text-black-50",
                        html: data.relativeDate
                    }))
                    .append($("<p/>", {
                        html: data.dynamicContent
                    }));
            }

            $dynamicMedia.append($mediaImg).append($mediaBody)
            var $dynamicInfo = $("<div/>", {
                class: "row border border-bottom-0 border-left-0 border-right-0 community-ele-border-color-grey"
            })
            var $dynamicLike = $("<div/>", {
                class: "col-lg-4 text-center mt-lg-3 border border-top-0 border-left-0 border-bottom-0 community-ele-border-color-grey"
            }).append($("<a/>", {
                href: "javascript:void(0)",
                class: "text-black-50 small"
            }).append($("<i/>", {
                class: "bi bi-hand-thumbs-up"
            })).append($("<span/>", {
                html: "&nbsp;&nbsp;赞"
            })))
            var $dynamicConment = $("<div/>", {
                class: "col-lg-4 text-center mt-lg-3 border border-top-0 border-left-0 border-bottom-0 community-ele-border-color-grey"
            }).append($("<a/>", {
                href: "javascript:void(0)",
                class: "text-black-50 small"
            }).append($("<i/>", {
                class: "bi bi-chat-left-dots"
            })).append($("<span/>", {
                html: "&nbsp;&nbsp;评论"
            })))
            var $dynamicShare = $("<div/>", {
                class: "col-lg-4 text-center mt-lg-3"
            }).append($("<a/>", {
                href: "javascript:void(0)",
                class: "text-black-50 small"
            }).append($("<i/>", {
                class: "bi bi-share"
            })).append($("<span/>", {
                html: "&nbsp;&nbsp;分享"
            })))
            $dynamicInfo.append($dynamicLike).append($dynamicConment).append($dynamicShare)
            $dynamicContent.append($dynamicMedia).append($dynamicInfo)
            $dynamicDiv.append($dynamicContent)
            $("#dynamic-list").append($dynamicDiv)
        })
    },

    /**
     * 初始化个人主页
     */
    initHomepage: function (homepageId) {
        var requestJson = {
            data: [
                {
                    homepageId: homepageId,
                },
            ],
        };
        $.ajax({
            contentType: "application/json",
            type: "POST",
            url: "/community/user/person",
            dataType: "json",
            data: JSON.stringify(requestJson),
            success: function (result) {
                if (result == null) {
                    $.alert({
                        title: "出错啦!",
                        content: "请稍后再试！",
                    });
                } else {
                    if (result.code !== "2000") {
                        $.alert({
                            title: "出错啦!",
                            content: result.description,
                        });
                    } else {
                        var data = result.data[0]
                        $("#homepageHeadPortrait").attr("src", data.headPortrait)
                        $("#homepageUsername").html(data.username)
                        $.renderUserDynamicList(data.userDynamicVOList)
                        $("#upload-headPortrait-button").click(function () {
                            var file = $('#headPortraitMultiple').get(0).files[0]
                            var formData = new FormData();
                            formData.append('file', file);
                            $.ajax({
                                type: "post",
                                data: formData,
                                contentType : false,
                                processData : false,
                                url: "/community/user/upload/headPortrait",
                                success: function (result) {
                                    if (result == null) {
                                        $.alert({
                                            title: "出错啦!",
                                            content: "请稍后再试！",
                                        });
                                    } else {
                                        if (result.code !== "2000") {
                                            $.alert({
                                                title: "出错啦!",
                                                content: result.description,
                                            });
                                        } else {
                                            location.reload();
                                        }
                                    }
                                }
                            })
                        });
                    }
                }
            },
        })
    },

    /**
     * 获取评论通知分页
     */
    postNotificationPage: function (requestJson) {
        $.ajax({
            contentType: "application/json",
            type: "POST",
            url: "/community/notification/page",
            dataType: "json",
            data: JSON.stringify(requestJson),
            success: function (result) {
                if (result == null) {
                    $.alert({
                        title: "出错啦!",
                        content: "请稍后再试！",
                    });
                } else {
                    if (result.code !== "2000") {
                        $.alert({
                            title: "出错啦!",
                            content: result.description,
                        });
                    } else {
                        $.renderNotification(result);
                        $.setNotificationPage(result);
                    }
                }
            },
        });
    },

    /**
     * 获取讨论
     */
    postIndexPage: function () {
        var requestJson = {
            pageNum: 1,
            pageSize: 20
        };
        $.ajax({
            contentType: "application/json",
            type: "POST",
            url: "/community/topic/page",
            dataType: "json",
            data: JSON.stringify(requestJson),
            success: function (result) {
                if (result == null) {
                    $.alert({
                        title: "出错啦!",
                        content: "请稍后再试！",
                    });
                } else {
                    if (result.code !== "2000") {
                        $.alert({
                            title: "出错啦!",
                            content: result.description,
                        });
                    } else {
                        $.renderIndexPage(result);
                        $.setTopicPagination(result);
                    }
                }
            },
        });
    },

    /**
     * 渲染讨论列表
     */
    renderIndexPage: function (pageInfo) {
        var $topicList = $("#topic-list");
        $topicList.empty();
        $.each(pageInfo.data[0].list, function (index, topic) {
            var html =
                "<div class='px-lg-3 py-lg-4 border border-top-0 border-left-0 border-right-0'>" +
                "   <div class='media'>" +
                "       <div class='num-card mr-lg-1 align-self-center " + (topic.commentCount === 0 ? 'text-secondary' : 'num-card-comment') + "'>" +
                "           <span>" + topic.commentCount + "</span>" +
                "           <span>回答</span>" +
                "       </div>" +
                "       <div class='num-card mr-lg-2 align-self-center " + (topic.viewCount < 100 ? 'text-secondary' : 'num-card-read') + "'>" +
                "           <span>" + topic.viewCount + "</span>" +
                "           <span>阅读</span>" +
                "       </div>" +
                "       <div class='media-body'>" +
                "           <div>" +
                "               <a href='/community/topic/view/" + topic.id + "'>" +
                "                   <span class='text-body'>" + topic.topicTitle + "</span>" +
                "               </a>" +
                "               <div class='text-right'>" +
                "                   <a class='text-success' href='javascript:void(0)'>" + topic.createUserName + "</a>" +
                "                   <span class='text-secondary'>于&nbsp;" + topic.createTime + "&nbsp;发布</span>" +
                "               </div>" +
                "           </div>" +
                "       </div>" +
                "   </div>" +
                "</div>"
            $topicList.append(html);
        });
    },

    /**
     * 渲染讨论分页
     */
    setTopicPagination: function (pageInfo) {
        // 当前页
        var currentPage = pageInfo.data[0].pageNum;
        // 得到总页数
        var pageCount = pageInfo.data[0].pages;
        // 所有导航页号
        var navigatepageNums = pageInfo.data[0].navigatepageNums;

        var $pagination = $("#topic-pagination");

        $pagination.empty();

        if (pageCount > 0) {

            var pageFirst = "<li class='page-item'><a class='page-link' href='javascript:void(0)'>首页</a></li>"
            var $pageFirst = $(pageFirst).click(function () {
                var requestJson = {
                    pageNum: 0,
                    pageSize: 20
                };
                $.postIndexPage(requestJson);
            })

            var pagePrevious = "<li class='page-item'><a class='page-link' href='javascript:void(0)'>&lt;上一页</a></li>"
            var $pagePrevious = $(pagePrevious).click(function () {
                var requestJson = {
                    pageNum: currentPage - 1,
                    pageSize: 20
                };
                $.postIndexPage(requestJson);
            })

            var pageNext = "<li class='page-item'><a class='page-link' href='javascript:void(0)'>下一页&gt;</a></li>"
            var $pageNext = $(pageNext).click(function () {
                var requestJson = {
                    pageNum: currentPage + 1,
                    pageSize: 20
                };
                $.postIndexPage(requestJson);
            })

            var pageLast = "<li class='page-item'><a class='page-link' href='javascript:void(0)'>尾页</a></li>"
            var $pageLast = $(pageLast).click(function () {
                var requestJson = {
                    pageNum: 99999,
                    pageSize: 20
                };
                $.postIndexPage(requestJson);
            })

            $pagination
                .append($pageFirst)
                .append($pagePrevious)
                .append($pageNext)
                .append($pageLast)

            // 总页数小于等于5，直接加载
            if (pageCount <= 5) {
                $.each(navigatepageNums, function (index, num) {
                    var pageItem
                    if(num === currentPage) {
                        pageItem = "<li class='page-item active'><a class='page-link' href='javascript:void(0)'>" + num + "</a></li>"
                    } else {
                        pageItem = "<li class='page-item'><a class='page-link' href='javascript:void(0)'>" + num + "</a></li>"
                    }
                    var $pageItem = $(pageItem).click(function () {
                        var requestJson = {
                            pageNum: num,
                            pageSize: 20
                        };
                        $.postIndexPage(requestJson);
                    })
                    $pageNext.before($pageItem);
                });
            } else {
                // 总页数大于5
                var sliceArray;
                // 当前页小于等于3，显示前5页
                if (currentPage <= 3) {
                    sliceArray = navigatepageNums.slice(0, 5);
                } else if (currentPage >= pageCount - 2) {
                    sliceArray = navigatepageNums.slice(pageCount - 5);
                } else {
                    sliceArray = navigatepageNums.slice(currentPage - 3, currentPage + 2);
                }
                $.each(sliceArray, function (index, num) {
                    var pageItem;
                    if (num === currentPage) {
                        pageItem = "<li class='page-item active'><a class='page-link' href='javascript:void(0)'>" + num + "</a></li>"
                    } else {
                        pageItem = "<li class='page-item'><a class='page-link' href='javascript:void(0)'>" + num + "</a></li>"
                    }
                    var $pageItem = $(pageItem).click(function () {
                        var requestJson = {
                            pageNum: num,
                            pageSize: 20
                        };
                        $.postIndexPage(requestJson);
                    })
                    $pageNext.before($pageItem);
                });
            }
        }
    },

    /**
     * 初始化帖子内容
     */
    initTopic: function (topicId) {
        $.initTop()
        $.initFooter()
        var requestJson = {
            topicId: topicId
        };
        $.ajax({
            contentType: "application/json",
            type: "POST",
            url: "/community/topic/visit",
            dataType: "json",
            data: JSON.stringify(requestJson),
            success: function (result) {
                if (result == null) {
                    $.alert({
                        title: "出错啦!",
                        content: "请稍后再试！",
                    });
                } else {
                    if (result.code !== "2000") {
                        $.alert({
                            title: "出错啦!",
                            content: result.description,
                        });
                    } else {
                        var data = result.data[0];

                        $("title").text(data.topicTitle + "- Community");
                        var topicDetail =
                            "<div class='card p-lg-3'>" +
                            "   <div class='card-body'>" +
                            "       <h2 class='card-title'>" + data.topicTitle + "</h2>" +
                            "       <div class='py-lg-2'>" +
                            "           <a href='#'>" +
                            "               <img class='rounded-circle' width='32' height='32' alt='头像' src='" + data.headPortrait + "'>" +
                            "               <strong class='text-success'>&nbsp;" + data.createUserName + "</strong>" +
                            "           </a>" +
                            "           <span class='text-secondary'>&nbsp;&nbsp;发布于&nbsp;" + data.createTime + "</span>" +
                            "       </div>" +
                            "       <div class='card-text py-lg-2' id='topic-content'>" +
                            "           <textarea style='display:none' id='topic-textarea'></textarea>" +
                            "       </div>" +
                            "       <div class='py-lg-2'>" +
                            "           <a href='javascript:void(0)' class='text-secondary' id='show-reply-topic'>回复</a>&nbsp;&nbsp;" +
                            "           <span class='text-secondary'>阅读&nbsp;" + data.viewCount + "</span>" +
                            "       </div>" +
                            "       <div class='bg-light border-0 card' id='reply-topic' style='display: none'>" +
                            "           <div class='card-body'>" +
                            "               <div class='media-body'>" +
                            "                   <div class='mb-lg-3'>" +
                            "                       <textarea class='form-control form-control-sm' style='resize: none' rows='3' placeholder='友善的评论是交流的起点' id='reply-comment-content'></textarea>" +
                            "                   </div>" +
                            "               </div>" +
                            "               <div>" +
                            "                   <button type='button' class='btn btn-sm btn-primary pull-right'  id='reply-topic-commit'>提交评论</button>" +
                            "               </div>" +
                            "           </div>" +
                            "       </div>" +
                            "   </div>" +
                            "</div>"
                        $("#topic-detail").append(topicDetail)
                        $("#topic-textarea").val(data.topicContent);
                        editormd.markdownToHTML("topic-content", {
                            htmlDecode: "style,script,iframe",
                            emoji: true,
                            taskList: true,
                            tex: true, // 默认不解析
                            flowChart: true, // 默认不解析
                            sequenceDiagram: true, // 默认不解析
                        });
                        // 去掉 editormd 的默认样式
                        $("#topic-content").removeClass("editormd-html-preview")

                        commentCount = data.commentCount

                        var commentArea =
                            "<div class='card' id='comment-list'>" +
                            "   <div class='justify-content-between card-header bg-white'>" +
                            "       <strong><span id='comment-count'>" + commentCount + "</span>&nbsp;个评论</strong>" +
                            "   </div>" +
                            "</div>"
                        $("#comment-area").append(commentArea)

                        $.each(data.commentVOList, function (index, comment) {

                            var commentHtml =
                                "<div class='list-group-item'>" +
                                "   <div class='p-lg-3'>" +
                                "       <div class='py-lg-2'>" +
                                "           <a>" +
                                "               <img class='rounded-circle' width='32' height='32' alt='头像' src='" + comment.headPortrait + "'>" +
                                "               <strong class='text-success'>&nbsp;" + comment.createUsername + "</strong>" +
                                "           </a>" +
                                "           <span class='text-secondary'>&nbsp;&nbsp;发布于&nbsp;" + comment.createTime + "</span>" +
                                "       </div>" +
                                "       <div class='py-lg-2'>" + comment.commentContent + "</div>" +
                                "       <div class='py-lg-2' id='show-reply-other-" + comment.commentId + "'>" +
                                "           <a href='javascript:void(0)' class='text-secondary'>回复</a>" +
                                "       </div>" +
                                "       <div class='bg-light border-0 card' id='reply-other-" + comment.commentId + "' style='display: none'>" +
                                "           <div class='card-body'>" +
                                "               <div class='media-body'>" +
                                "                   <div class='mb-lg-3'>" +
                                "                       <textarea class='form-control form-control-sm' style='resize: none' rows='1' placeholder='友善的评论是交流的起点' id='reply-other-content-" + comment.commentId + "'></textarea>" +
                                "                   </div>" +
                                "               </div>" +
                                "               <div>" +
                                "                   <button type='button' class='btn btn-sm btn-primary pull-right' id='reply-other-commit-" + comment.commentId + "'>提交评论</button>" +
                                "               </div>" +
                                "           </div>" +
                                "       </div>" +
                                "       <div id='reply-area-" + comment.commentId + "' class='mt-lg-2'></div>" +
                                "   </div>" +
                                "</div>"

                            $("#comment-list").append(commentHtml)

                            $("#show-reply-other-" + comment.commentId).click(function () {
                                $("#reply-other-" + comment.commentId).toggle();
                            });

                            $("#reply-other-commit-" + comment.commentId).click(function () {
                                var requestJson = {
                                    parentId: comment.commentId,
                                    commentContent: $("#reply-other-content-" + comment.commentId).val()
                                };
                                $.ajax({
                                    contentType: "application/json",
                                    type: "POST",
                                    url: "/community/comment/publish",
                                    dataType: "json",
                                    data: JSON.stringify(requestJson),
                                    success: function (result) {
                                        if (result == null) {
                                            $.alert({
                                                title: "出错啦!",
                                                content: "请稍后再试！",
                                            });
                                        } else {
                                            if (result.code !== "2000") {
                                                $.alert({
                                                    title: "出错啦!",
                                                    content: result.description,
                                                });
                                            } else {
                                                $.snack('success', '评论成功', 3000)
                                                var comment1 = result.data[0];
                                                $("#reply-other-content-" + comment.commentId).val("")
                                                var comment1Html =
                                                    "<div class='bg-light border-0 rounded' id='reply-other-success-" + comment.commentId + "'>" +
                                                    "   <div class='py-lg-1 px-lg-3'>" +
                                                    "       <div>" +
                                                    "           <a href='javascript:void(0)'>" +
                                                    "               <strong class='text-success'>" + comment1.createUsername + ":&nbsp;</strong>" +
                                                    "           </a>" +
                                                    "           <span class='small'>" + comment1.commentContent + "</span>" +
                                                    "       </div>" +
                                                    "       <div class='card-text'>" +
                                                    "           <a href='javascript:void(0)' class='text-secondary small'>回复&nbsp;&nbsp;</a>" +
                                                    "           <span class='text-secondary small'>发布于&nbsp;" + comment.createTime + "</span>" +
                                                    "       </div>" +
                                                    "   <div>" +
                                                    "</div>"
                                                $("#reply-area-" + comment.commentId).append(comment1Html)
                                                $("#comment-count").html(commentCount + 1)
                                                $("html,body").animate({scrollTop:$("#reply-other-success-" + comment.commentId).offset().top},1000);
                                            }
                                        }
                                    },
                                });
                            });

                            $.each(comment.commentVOList, function (index, comment1) {
                                var comment1Html =
                                    "<div class='bg-light border-0 rounded'>" +
                                    "   <div class='py-lg-1 px-lg-3'>" +
                                    "       <div>" +
                                    "           <a href='javascript:void(0)'>" +
                                    "               <strong class='text-success'>" + comment1.createUsername + ":&nbsp;</strong>" +
                                    "           </a>" +
                                    "           <span class='small'>" + comment1.commentContent + "</span>" +
                                    "       </div>" +
                                    "       <div class='card-text'>" +
                                    "           <a href='javascript:void(0)' class='text-secondary small'>回复&nbsp;&nbsp;</a>" +
                                    "           <span class='text-secondary small'>发布于&nbsp;" + comment.createTime + "</span>" +
                                    "       </div>" +
                                    "   <div>" +
                                    "</div>"
                                $("#reply-area-" + comment.commentId).append(comment1Html)
                            })
                        })

                        $("#show-reply-topic").click(function () {
                            $("#reply-topic").toggle();
                        });

                        $("#reply-topic-commit").click(function () {
                            var requestJson = {
                                parentId: data.id,
                                commentContent: $("#reply-comment-content").val()
                            };
                            $.ajax({
                                contentType: "application/json",
                                type: "POST",
                                url: "/community/comment/publish",
                                dataType: "json",
                                data: JSON.stringify(requestJson),
                                success: function (result) {
                                    if (result == null) {
                                        $.alert({
                                            title: "出错啦!",
                                            content: "请稍后再试！",
                                        });
                                    } else {
                                        if (result.code !== "2000") {
                                            $.alert({
                                                title: "出错啦!",
                                                content: result.description,
                                            });
                                        } else {
                                            $.snack('success', '评论成功', 3000)
                                            var comment = result.data[0];
                                            $("#reply-comment-content").val("")
                                            var commentHtml =
                                                "<div class='list-group-item' id='reply-topic-suceess-" + comment.commentId + "'>" +
                                                "   <div class='p-lg-3'>" +
                                                "       <div class='py-lg-2'>" +
                                                "           <a>" +
                                                "               <img class='rounded-circle' width='32' height='32' alt='头像' src='" + comment.headPortrait + "'>" +
                                                "               <strong class='text-success'>&nbsp;" + comment.createUsername + "</strong>" +
                                                "           </a>" +
                                                "           <span class='text-secondary'>&nbsp;&nbsp;发布于&nbsp;" + comment.createTime + "</span>" +
                                                "       </div>" +
                                                "       <div class='py-lg-2'>" + comment.commentContent + "</div>" +
                                                "       <div class='py-lg-2'>" +
                                                "           <a href='javascript:void(0)' class='text-secondary'>回复</a>" +
                                                "       </div>" +
                                                "   </div>" +
                                                "</div>"
                                            $("#comment-count").html(commentCount + 1)
                                            $("#comment-list").append(commentHtml)
                                            $("html,body").animate({scrollTop:$("#reply-topic-suceess-" + comment.commentId).offset().top},1000);
                                        }
                                    }
                                },
                            });
                        });
                    }
                }
            },
        });
    },
});