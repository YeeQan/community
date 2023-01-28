let publicKey;

jQuery.extend({

    /**
     * 初始化首页
     */
    initIndex: function () {
        $.initTop();
        $.initFooter();
        var requestJson = {
            pageNum: 1,
            pageSize: 20
        };
        $.postIndexPage(requestJson);
    },

    /**
     * 初始化顶部栏
     */
    initTop: function () {
        $.ajax({
            type: "POST",
            url: "/community/user/loginFlag",
            success: function (result) {
                if (result == null || result.code !== "2000") {
                    $("#header").load(
                        "/community/common/header-non-logined"
                    );
                } else {
                    $("#header").load(
                        "/community/common/header-logined"
                    );
                }
            },
        });
    },

    /**
     * 初始化底部内容
     */
    initFooter: function () {
        $("#footer").load("/community/common/footer");
    },

    /**
     * 初始化个人资料
     */
    initProfile: function () {
        $.initTop();
        $.initFooter();
        $.ajax({
            type: "POST",
            url: "/community/user/userInfo/get",
            success: function (result) {
                if (result == null) {
                    $.snack('error', '系统错误，请稍后再试！', 3000)
                } else {
                    if (result.code !== "2000") {
                        $.snack('error', result.description, 3000)
                    } else {
                        var userInfo = result.data[0];

                        $("#personal-headPortrait-img").attr("src", userInfo.headPortrait)
                        $("#personal-username").val(userInfo.username)
                        $("#personal-birthday").val(userInfo.birthday)
                        $("#personal-sex  option[value=" + userInfo.sex + "]").attr("selected", true)
                        $("#personal-city").val(userInfo.city)
                        $("#personal-introduction").val(userInfo.introduction)
                        $("#personal-school").val(userInfo.school)
                        $("#personal-major").val(userInfo.major)
                        $("#personal-company").val(userInfo.company)
                        $("#personal-position").val(userInfo.position)

                        $("#personal-headPortrait-post").change(function () {
                            var file = $("#personal-headPortrait-post").get(0).files[0];
                            if (file != null) {
                                var formData = new FormData();
                                formData.append("file", file);
                                $.ajax({
                                    type: "POST",
                                    data: formData,
                                    url: "/community/user/headPortrait/upload",
                                    contentType: false,
                                    processData: false,
                                    success: function (result) {
                                        if (result == null) {
                                            $.snack('error', '系统错误，请稍后再试！', 3000)
                                        } else {
                                            if (result.code !== "2000") {
                                                $.snack('error', result.description, 3000)
                                            } else {
                                                var uri = result.data[0];
                                                $.snack('success', "图片上传成功", 3000)
                                                $("#personal-headPortrait-post").val('');
                                                $("#personal-headPortrait-img").attr("src", uri)
                                            }
                                        }
                                    },
                                });
                            }
                        })

                        $("#personal-info-commit").click(function () {
                            var requestJson = {
                                username: $("#personal-username").val(),
                                birthday: $("#personal-birthday").val(),
                                sex: $("#personal-sex").val(),
                                city: $("#personal-city").val(),
                                introduction: $("#personal-introduction").val(),
                                school: $("#personal-school").val(),
                                major: $("#personal-major").val(),
                                company: $("#personal-company").val(),
                                position: $("#personal-position").val(),
                            };
                            $.ajax({
                                type: "POST",
                                dataType: "json",
                                contentType: "application/json",
                                data: JSON.stringify(requestJson),
                                url: "/community/user/userInfo/save",
                                success: function (result) {
                                    if (result == null) {
                                        $.snack('error', '系统错误，请稍后再试！', 3000)
                                    } else {
                                        if (result.code !== "2000") {
                                            $.snack('error', result.description, 3000)
                                        } else {
                                            $.snack('success', "保存成功", 3000)
                                        }
                                    }
                                },
                            });
                        })
                    }
                }
            },
        });
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
                var tags = "";
                let $select = $("#select-tags");
                $.each($select.children(), function (index, tag) {
                    var $tag = $(tag)
                    console.log(index + ":" + $tag.attr("data-id"))
                    tags += $tag.attr("data-id")
                    console.log(tags)
                    if ($select.length + 1 !== index) {
                        tags += ","
                    }
                    console.log(tags)
                })
                var requestJson = {
                        topicTitle: $("#topic-title").val(),
                        topicContent: editor.getMarkdown(),
                        tags: tags
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
        $.ajax({
            type: "POST",
            url: "/community/tag/list",
            success: function (result) {
                if(result === null) {
                    $.snack('error', '系统错误，请稍后再试！', 3000)
                } else {
                    if (result.code !== "2000") {
                        $.snack('error', result.description, 3000)
                    } else {
                        $.each(result.data, function (index, tag) {
                            let $tagList = $("#tag-list");
                            var tagHtml =
                                "<a class='badge-tag m-1' href='javascript:void(0)'>" + tag.name + "</a>"
                            $tagList.append(tagHtml)
                            $tagList.children().eq(-1).click(function () {
                                var $selectTags = $("#select-tags");
                                if($selectTags.children().length >= 5) {
                                    $.snack('warning', '最多添加5个标签', 3000)
                                } else {
                                    var flag = Boolean(true)
                                    $.each($selectTags.children(), function (index, selectTag) {
                                        var $selectTag = $(selectTag)
                                        if (tag.id === $selectTag.attr("data-id")) {
                                            $.snack('warning', '请不要重复添加标签', 3000)
                                            flag = false
                                        }
                                    })
                                    if (flag) {
                                        var selectTagHtml =
                                            "<button type='button' class='btn btn-sm btn-outline-secondary mx-1' id='tag-" + tag.id + "' data-id='" + tag.id + "'>" + tag.name + "&nbsp;×</button>";
                                        $selectTags.append(selectTagHtml)
                                        $("#tag-" + tag.id).click(function () {
                                            $("#tag-" + tag.id).remove()
                                        })
                                    }
                                }
                            })
                        });
                    }
                }
            }
        })
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
                    $.snack('error', '系统错误，请稍后再试！', 3000)
                } else {
                    if (result.code !== "2000") {
                        $.snack('error', result.description, 3000)
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
                    $.snack('error', '系统错误，请稍后再试！', 3000)
                } else {
                    if (result.code !== "2000") {
                        $.snack('error', result.description, 3000)
                    } else {
                        var headPortrait = result.data[0].headPortrait
                        var homepageId = result.data[0].homepageId
                        var username = result.data[0].username
                        var createTime = result.data[0].createTime
                        $("#user-headportrait").attr("src", headPortrait)
                        $("#homepage").attr("href", "/community/u/" + homepageId)
                        sessionStorage.setItem("userHeadportrait", headPortrait)
                        sessionStorage.setItem("username", username)
                        sessionStorage.setItem("createTime", createTime)
                        sessionStorage.setItem("homepageId", homepageId)
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
        // 获取公钥对密码加密
        $.ajax({
            type: "GET",
            url: "/community/publicKey",
            success: function (result) {
                console.log(JSON.stringify(result))
                if (result != null && result.code === "2000") {
                    publicKey = result.data[0];
                } else {
                    console.log("获取公钥失败,不做加密处理")
                }
            },
        });
        // 初始化注册按钮
        $("#register-button").click(function () {
            var password = $("#registerPassword").val();
            if(publicKey) {
                var encrypt = new JSEncrypt();
                encrypt.setPublicKey(publicKey);
                password = encrypt.encrypt(password);
            }
            var requestJson = {
                account: $("#registerAccount").val(),
                username: $("#registerName").val(),
                password: password
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
            var password = $("#loginPassword").val();
            if(publicKey) {
                var encrypt = new JSEncrypt();
                encrypt.setPublicKey(publicKey);
                password = encrypt.encrypt(password);
            }
            var requestJson = {
                account: $("#loginAccount").val(),
                password: password,
                publicKey: publicKey,
            };
            console.log(requestJson)
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
     * 初始化个人主页
     */
    initHomepage: function (homepageId) {

        $.initTop()
        $.initFooter()

        var requestJson = {
            homepageId: homepageId
        };

        $.ajax({
            type: "POST",
            url: "/community/user/homepage",
            contentType: "application/json",
            dataType: "json",
            data: JSON.stringify(requestJson),
            success: function (result) {
                if (result == null) {
                    $.snack('error', '系统错误，请稍后再试！', 3000)
                } else {
                    if (result.code !== "2000") {
                        $.snack('error', result.description, 3000)
                    } else {
                        var userHomepage = result.data[0]

                        $("title").text(userHomepage.username + " - Community");

                        var userInfoHtml =
                            "<div class='text-center my-lg-3'>" +
                            "   <a href='javascript:void(0)'>" +
                            "       <img src='" + userHomepage.headPortrait + "' alt='头像' width='160' height='160' class='rounded-circle'>" +
                            "   </a>" +
                            "</div>" +
                            "<div class='text-center my-lg-3'>" +
                            "   <h3>" + userHomepage.username + "</h3>" +
                            "</div>" +
                            "<div class='text-center my-lg-3'>" +
                            (userHomepage.self ?
                                "<a href='/community/user/setting/profile' role='button' class='btn btn-block btn-outline-success'>编辑个人资料</a>" :
                                "<a href='javascript:void(0)' role='button' class='btn btn-block btn-outline-success'>关注Ta</a>" +
                                "<a href='javascript:void(0)' role='button' class='btn btn-block btn-outline-success'>发私信</a>"
                            ) +
                            "</div>" +
                            "<div class='text-center my-lg-3'>" +
                            "   <p class='text-secondary'>" + userHomepage.joinTime + "&nbsp;加入</p>" +
                            "</div>"
                        $("#homepage-user-info").html(userInfoHtml)

                        $.renderDynamicList(homepageId)
                        $("#dynamic").click(function () {
                            $("#homepage-list").clear()
                            $.renderDynamicList(homepageId)
                        })
                    }
                }
            },
        });
    },

    renderDynamicList: function (homepageId) {
        var requestJson = {
            homepageId: homepageId
        };
        $.ajax({
            type: "POST",
            url: "/community/user/dynamic/list",
            contentType: "application/json",
            dataType: "json",
            data: JSON.stringify(requestJson),
            success: function (result) {
                if (result == null) {
                    $.snack('error', '系统错误，请稍后再试！', 3000)
                } else {
                    if (result.code !== "2000") {
                        $.snack('error', result.description, 3000)
                    } else {
                        var dynamicList = result.data
                        $.each(dynamicList, function (index, dynamic) {
                            var dynamicHtml;
                            var type = dynamic.type
                            if("1" === type) {
                                dynamicHtml =
                                    "<div class='py-3 list-group-item'>" +
                                    "   <div class='small text-secondary mb-2'>" +
                                    "       <span>发布了讨论&nbsp;&nbsp;" + dynamic.createTime + "</span>" +
                                    "   </div>" +
                                    "   <div class='my-3'>" +
                                    "       <a href='/community/topic/view/" + dynamic.targetId + "'>" +
                                    "           <h5 class='text-body'>" + dynamic.targetContent + "</h5>" +
                                    "       </a>" +
                                    "   </div>" +
                                    "   <div>" + dynamic.sourceContent + "</div>" +
                                    "</div>"
                            } else if("2" === type) {
                                dynamicHtml =
                                    "<div class='py-3 list-group-item'>" +
                                    "   <div class='small text-secondary mb-2'>" +
                                    "       <span>发表了评论&nbsp;&nbsp;" + dynamic.createTime + "</span>" +
                                    "   </div>" +
                                    "   <div class='my-3'>" +
                                    "       <a href='/community/topic/view/" + dynamic.targetId + "'>" +
                                    "           <h5 class='text-body'>" + dynamic.targetContent + "</h5>" +
                                    "       </a>" +
                                    "   </div>" +
                                    "   <div class='p-3 bg-light border-0'>" +
                                    "       <strong class='text-success'>" + dynamic.sourceCreateUsername + ":&nbsp;</strong>" +
                                    "       <span>" + dynamic.sourceContent + "</span>" +
                                    "   </div>" +
                                    "</div>"
                            }
                            if (dynamicHtml) {
                                $("#homepage-list").append(dynamicHtml);
                            }
                        });
                    }
                }
            },
        });
    },

    /**
     * 获取讨论
     */
    postIndexPage: function (requestJson) {
        $.ajax({
            contentType: "application/json",
            type: "POST",
            url: "/community/topic/page",
            dataType: "json",
            data: JSON.stringify(requestJson),
            success: function (result) {
                if (result == null) {
                    $.snack('error', '系统错误，请稍后再试！', 3000)
                } else {
                    if (result.code !== "2000") {
                        $$.snack('error', result.description, 3000)
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
                "           <span>回复</span>" +
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
                "               <div class='d-flex flex-wrap mt-1'>" +
                "                   <div class='d-flex flex-wrap' id='topic-tags-" + topic.id + "'></div>" +
                "                   <div class='font-size-14 d-flex justify-content-end flex-fill'>" +
                "                       <a href='" + "/community/u/" + topic.createUserHomepageId + "'>" +
                "                           <span class='text-success'>" + topic.createUserName + "</span>" +
                "                           <img class='rounded-circle' width='26' height='26' alt='头像' src='" + topic.createUserHeadPortrait + "'>" +
                "                       </a>" +
                "                       <span class='text-secondary'>&nbsp;于&nbsp;" + topic.createTime + "&nbsp;发布</span>" +
                "                   </div>" +
                "               </div>" +
                "           </div>" +
                "       </div>" +
                "   </div>" +
                "</div>"
            $topicList.append(html);
            $.each(topic.tagVOList, function (index, tagVO) {
                $("#topic-tags-" + topic.id).append("<a class='badge-tag mx-1' href='javascript:void(0)'>" + tagVO.name + "</a>")
            })
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
                    pageNum: 1,
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
                    $.snack('error', '系统错误，请稍后再试！', 3000)
                } else {
                    if (result.code !== "2000") {
                        $.snack('error', result.description, 3000)
                    } else {
                        var data = result.data[0];
                        console.log(data);
                        $("title").text(data.topicTitle + " - 码客Mark");
                        var topicDetail =
                            "<div class='card p-lg-3'>" +
                            "   <div class='card-body'>" +
                            "       <h2 class='card-title'>" + data.topicTitle + "</h2>" +
                            "       <div class='py-lg-2'>" +
                            "           <a href='" + "/community/u/" + data.createUserHomepageId + "'>" +
                            "               <img class='rounded-circle' width='32' height='32' alt='头像' src='" + data.createUserHeadPortrait + "'>" +
                            "               <strong class='text-success'>&nbsp;" + data.createUserName + "</strong>" +
                            "           </a>" +
                            "           <span class='text-secondary'>&nbsp;&nbsp;发布于&nbsp;" + data.createTime + "</span>" +
                            "       </div>" +
                            "       <div class='card-text py-lg-2' id='topic-content'>" +
                            "           <textarea style='display:none' id='topic-textarea'></textarea>" +
                            "       </div>" +
                            "       <div class='py-lg-2' id='topic-detail-tags-" + data.createUserHomepageId + "'></div>" +
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
                        $.each(data.tagVOList, function (index, tagVO) {
                            $("#topic-detail-tags-" + data.createUserHomepageId).append("<a class='badge-tag mx-1' href='javascript:void(0)'>" + tagVO.name + "</a>")
                        })

                        commentCount = data.commentCount

                        var commentArea =
                            "<div class='card' id='comment-list'>" +
                            "   <div class='justify-content-between card-header bg-white'>" +
                            "       <strong><span id='comment-count'>" + commentCount + "</span>&nbsp;个评论</strong>" +
                            "   </div>" +
                            "</div>"
                        $("#comment-area").append(commentArea)

                        $.each(data.commentVoList, function (index, comment) {

                            var commentHtml =
                                "<div class='list-group-item'>" +
                                "   <div class='p-lg-3'>" +
                                "       <div class='py-lg-2'>" +
                                "           <a href='/community/u/" + comment.createUserHomepageId + "'>" +
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
                                "                   <button type='button' class='btn btn-sm btn-primary pull-right' id='reply-other-commit-" + comment.commentId + "' ta-id=''>提交评论</button>" +
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
                                var requestJson
                                var $replyOtherCt = $("#reply-other-content-" + comment.commentId);
                                var at = $replyOtherCt.val().charAt(0);
                                if (at === '@' && $(this).attr("ta-id")) {
                                    var colonIndex = $replyOtherCt.val().indexOf('：');
                                    if(colonIndex === -1) {
                                        $.snack('error', '评论格式错误,请重新输入', 3000)
                                        $replyOtherCt.val("")
                                        return;
                                    }
                                    requestJson = {
                                        parentId: comment.commentId,
                                        replyTaId: $(this).attr("ta-id"),
                                        commentContent: $replyOtherCt.val().substring(colonIndex + 1, $replyOtherCt.length),
                                        type: "3"
                                    };
                                } else {
                                    requestJson = {
                                        parentId: comment.commentId,
                                        commentContent: $replyOtherCt.val(),
                                        type: "2"
                                    };
                                }
                                $(this).attr("ta-id", null)
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
                                                var comment1Html
                                                if(comment1.type === '2') {
                                                    comment1Html =
                                                        "<div class='bg-light border-0 rounded' id='reply-other-success-" + comment.commentId + "'>" +
                                                        "   <div class='py-lg-1 px-lg-3'>" +
                                                        "       <div>" +
                                                        "           <a href='/community/u/" + comment1.createUserHomepageId + "'>" +
                                                        "               <strong class='text-success small'>" + comment1.createUsername + ":&nbsp;</strong>" +
                                                        "           </a>" +
                                                        "           <span class='small'>" + comment1.commentContent + "</span>" +
                                                        "       </div>" +
                                                        "       <div class='card-text'>" +
                                                        "           <span class='text-secondary small'>发布于&nbsp;" + comment.createTime + "</span>" +
                                                        "       </div>" +
                                                        "   <div>" +
                                                        "</div>"
                                                } else if(comment1.type === '3') {
                                                    comment1Html =
                                                        "<div class='bg-light border-0 rounded' id='reply-other-success-" + comment.commentId + "'>" +
                                                        "   <div class='py-lg-1 px-lg-3'>" +
                                                        "       <div>" +
                                                        "           <a href='/community/u/" + comment1.createUserHomepageId + "'>" +
                                                        "               <strong class='text-success small'>" + comment1.createUsername + ":&nbsp;</strong>" +
                                                        "           </a>" +
                                                        "           <span class='small'>" +
                                                        "               <a href='/community/u/" + comment1.replyHomepageId + "'>" +
                                                        "                   <strong class='text-success small'>" +
                                                        "                       @" + comment1.replyUsername +
                                                        "                   </strong>" +
                                                        "               </a>" + "&nbsp;" + comment1.commentContent +
                                                        "           </span>" +
                                                        "       </div>" +
                                                        "       <div class='card-text'>" +
                                                        "           <span class='text-secondary small'>发布于&nbsp;" + comment.createTime + "</span>" +
                                                        "       </div>" +
                                                        "   <div>" +
                                                        "</div>"
                                                }
                                                $("#reply-area-" + comment.commentId).append(comment1Html)
                                                $("#comment-count").html(commentCount + 1)
                                                $("html,body").animate({scrollTop:$("#reply-other-success-" + comment.commentId).offset().top},1000);
                                            }
                                        }
                                    },
                                });
                            });

                            $.each(comment.commentVoList, function (index, comment1) {
                                var comment1Html
                                if(comment1.type === '2') {
                                    comment1Html =
                                        "<div class='bg-light border-0 rounded'>" +
                                        "   <div class='py-lg-1 px-lg-3'>" +
                                        "       <div>" +
                                        "           <a href='/community/u/" + comment1.createUserHomepageId + "'>" +
                                        "               <strong class='text-success small'>" + comment1.createUsername + ":&nbsp;</strong>" +
                                        "           </a>" +
                                        "           <span class='small'>" + comment1.commentContent + "</span>" +
                                        "       </div>" +
                                        "       <div class='card-text'>" +
                                        "           <a href='javascript:void(0)' class='text-secondary small' id='reply-ta-show-" + comment1.commentId + "'>回复&nbsp;&nbsp;</a>" +
                                        "           <span class='text-secondary small'>发布于&nbsp;" + comment.createTime + "</span>" +
                                        "       </div>" +
                                        "   <div>" +
                                        "</div>"
                                } else if(comment1.type === '3') {
                                    comment1Html =
                                        "<div class='bg-light border-0 rounded'>" +
                                        "   <div class='py-lg-1 px-lg-3'>" +
                                        "       <div>" +
                                        "           <a href='/community/u/" + comment1.createUserHomepageId + "'>" +
                                        "               <strong class='text-success small'>" + comment1.createUsername + ":&nbsp;</strong>" +
                                        "           </a>" +
                                        "           <span class='small'>" +
                                        "               <a href='/community/u/" + comment1.replyHomepageId + "'>" +
                                        "                   <strong class='text-success small'>" +
                                        "                       @" + comment1.replyUsername +
                                        "                   </strong>" +
                                        "               </a>" + "&nbsp;" + comment1.commentContent +
                                        "           </span>" +
                                        "       </div>" +
                                        "       <div class='card-text'>" +
                                        "           <a href='javascript:void(0)' class='text-secondary small' id='reply-ta-show-" + comment1.commentId + "'>回复&nbsp;&nbsp;</a>" +
                                        "           <span class='text-secondary small'>发布于&nbsp;" + comment.createTime + "</span>" +
                                        "       </div>" +
                                        "   <div>" +
                                        "</div>"
                                }
                                $("#reply-area-" + comment.commentId).append(comment1Html)
                                $("#reply-ta-show-" + comment1.commentId).click(function () {
                                    let $replyOther = $("#reply-other-" + comment.commentId);
                                    $replyOther.show()
                                    $("#reply-other-commit-" + comment.commentId).attr("ta-id", comment1.commentId)
                                    $("#reply-other-content-" + comment.commentId).val("@" + comment1.createUsername + "：")
                                    $("html,body").animate({scrollTop:$replyOther.offset().top},1000);
                                });
                            })
                        })

                        $("#show-reply-topic").click(function () {
                            $("#reply-topic").toggle();
                        });

                        $("#reply-topic-commit").click(function () {
                            var requestJson = {
                                parentId: data.id,
                                commentContent: $("#reply-comment-content").val(),
                                type: "1"
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
                                            $("#comment-count").html(commentCount + 1)
                                            $("#comment-list").append(commentHtml)
                                            $("#show-reply-other-" + comment.commentId).click(function () {
                                                $("#reply-other-" + comment.commentId).toggle();
                                            });

                                            $("#reply-other-commit-" + comment.commentId).click(function () {
                                                var requestJson = {
                                                    parentId: comment.commentId,
                                                    commentContent: $("#reply-other-content-" + comment.commentId).val(),
                                                    type: "2"
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
                                                                    "           <a href='/community/u/" + comment1.createUserHomepageId + "'>" +
                                                                    "               <strong class='text-success small'>" + comment1.createUsername + ":&nbsp;</strong>" +
                                                                    "           </a>" +
                                                                    "           <span class='small'>" + comment1.commentContent + "</span>" +
                                                                    "       </div>" +
                                                                    "       <div class='card-text'>" +
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