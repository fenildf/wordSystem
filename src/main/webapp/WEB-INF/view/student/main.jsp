<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE HTML>
<html lang="en">
<head>
    <title>欢迎使用好记性系统</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <%@include file="/WEB-INF/common/common.jsp" %>
    <script src="${basePath}/static/bootstarp-table/extensions/export/bootstrap-table-export.min.js"
            type="text/javascript"></script>
    <link href="${basePath}/static/bootstarp-table/bootstrap-table.min.css" rel="stylesheet" type="text/css"/>
    <script src="${basePath}/static/bootstarp-table/bootstrap-table.min.js" type="text/javascript"></script>
    <script src="${basePath}/static/bootstarp-table/locale/bootstrap-table-zh-CN.min.js"
            type="text/javascript"></script>
    <script type="text/javascript" src="${basePath }/static/layer/layer.js"></script>
    <script type="text/javascript" src="${basePath}/static/js/common/jquery-form.js"></script>
    <style type="text/css">
        .fixed-table-toolbar .bs-bars {
            width: 100%;
        }
    </style>
</head>
<body class="page-container-bg-solid">
<div class="page-wrapper">
    <div class="page-wrapper-row full-height">
        <div class="panel panel-default">
            <div class="panel-heading"><font color="green">个人单词本</font> | <a
                    href="${basePath}/person/wordDetailMain.do">词库</a></div>
            <div class="panel-body">
                <div id="toolbar">
                    <a href="#" id="begain_btn" class="btn btn-primary" role="button">
                        <i class="glyphicon glyphicon-plus"></i>开始记单词
                    </a>
                    <a href="#" id="search_btn" class="btn btn-primary" role="button"
                       style="margin-top: 6px;float:right;height:30px">
                        <i class="glyphicon glyphicon-zoom-in"></i>搜索
                    </a>
                    <input type="text" id="searchText" placeholder="请输入内容"
                           style="margin-top: 6px;margin-right:10px;float:right;height:30px"/>
                </div>
                <table id="table" data-toolbar="#toolbar">
                </table>
            </div>
        </div>
    </div>

</div>


<!-- 记单词 -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="width:400px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-hidden="true">x
                </button>
                <h4 class="modal-title">
                    记单词
                </h4>
            </div>
            <div class="modal-body">
                <form id="next_word_form" method="post" enctype="multipart/form-data">
                    <input type="hidden" name="wordId" id="wordId"/>
                    <table border="0" width="100%" cellpadding="3"
                           style="border-collapse:separate; border-spacing:0px 10px;">
                        <tr>
                            <td><input id="englishName" type="text" name="englishName" disabled="disabled"/></td>
                        </tr>
                        <tr id="radio_tr">

                        </tr>
                    </table>
                </form>
                <div style="width:100%;text-align:center">
                    <button type="button" id="submit_add_ok" class="btn btn-primary"><span
                            class="glyphicon glyphicon-ok" aria-hidden="true"></span>下一个
                    </button>
                </div>

            </div>
        </div>
    </div>
</div>


<script type="text/javascript">

    var $table = $('#table');
    $table.bootstrapTable({
        url: '${basePath}/wordNote/listWordNote.do',
        method: 'post',
        contentType: "application/x-www-form-urlencoded",
        dataType: "json",
        striped: true,//设置为 true 会有隔行变色效果
        undefinedText: "空",//当数据为 undefined 时显示的字符
        pagination: true, //分页
        pageNumber: 1,//如果设置了分页，首页页码
        pageSize: 10,//如果设置了分页，页面数据条数
        pageList: [10, 20, 30, 40, 50, 60],  //如果设置了分页，设置可供选择的页面数据条数。设置为All 则显示所有记录。
        paginationPreText: '‹',//指定分页条中上一页按钮的图标或文字,这里是<
        paginationNextText: '›',//指定分页条中下一页按钮的图标或文字,这里是>
        singleSelect: false,//设置True 将禁止多选
        data_local: "zh-US",//表格汉化
        cache: false,
        sidePagination: "server", //服务端处理分页
        queryParams: myPage,
        idField: "id",//指定主键列
        columns: [
            {
                checkbox: true
            },
            {
                title: 'id',//表的列名
                field: 'id',//json数据中rows数组中的属性名
                align: 'center'//水平居中
            },
            {
                title: '英文名称',
                field: 'englishName',
                align: 'center'
            },
            {
                title: '中文名称',
                field: 'chinaName',
                align: 'center'
            },
            {
                title: '背诵总次数',
                field: 'showNumber',
                align: 'center'
            },
            {
                title: '正确次数',
                field: 'rightNumber',
                align: 'center'
            },
            {
                title: '管理',
                field: 'id',
                align: 'center',
                formatter: function (value, row, index) {//自定义显示，这三个参数分别是：value该行的属性，row该行记录，index该行下标
                    return '<a href="#" onclick="deleteWordNote(' + value + ')" >删除</a>&nbsp';
                }
            }
        ]
    });
    //隐藏id列
    $table.bootstrapTable('hideColumn', 'id');

    //点击背诵单词
    $("#begain_btn").click(function () {
        $('#myModal').modal({
            keyboard: true,
            backdrop: 'static'
        });
        reloadWord();
    });

    //重新加载单词
    function reloadWord() {
        $.post("${basePath}/word/randomWord.do", {}, function (result) {
            if (result.code == 200) {
                var data = result.data;
                $("#wordId").val(data.wordId);
                $("#englishName").val(data.englishName);
                var str = "";
                var index = getRandomNum(0, 3);
                if (index == 0) {
                    str = '<td><input type="radio" value="1" name="answer"/>' + data.rightChinaName + '<br><input type="radio" value="0" name="answer"/>' + data.falseChinaName1 + '<br><input type="radio" value="0" name="answer"/>' + data.falseChinaName2 + '<br><input type="radio" value="0" name="answer"/>' + data.falseChinaName3 + '</td>';
                } else if (index == 1) {
                    str = '<td><input type="radio" value="0" name="answer"/>' + data.falseChinaName1 + '<br><input type="radio" value="1" name="answer"/>' + data.rightChinaName + '<br><input type="radio" value="0" name="answer"/>' + data.falseChinaName2 + '<br><input type="radio" value="0" name="answer"/>' + data.falseChinaName3 + '</td>';
                } else if (index == 2) {
                    str = '<td><input type="radio" value="0" name="answer"/>' + data.falseChinaName1 + '<br><input type="radio" value="0" name="answer"/>' + data.falseChinaName2 + '<br><input type="radio" value="0" name="answer"/>' + data.rightChinaName + '<br><input type="radio" value="0" name="answer"/>' + data.falseChinaName3 + '</td>';
                } else if (index == 3) {
                    str = '<td><input type="radio" value="0" name="answer"/>' + data.falseChinaName1 + '<br><input type="radio" value="0" name="answer"/>' + data.falseChinaName2 + '<br><input type="radio" value="0" name="answer"/>' + data.falseChinaName3 + '<br><input type="radio" value="1" name="answer"/>' + data.rightChinaName + '</td>';
                }
                	
                $("#radio_tr").html(str)
            } else {
                layer.msg(result.msg);
            }
        })
    }


    $("#submit_add_ok").bind("click", submitAndNext);

    function submitAndNext() {

        $("#submit_add_ok").unbind("click", submitAndNext);
        //请求后台数据
        var appData = {
            url: '${basePath}/wordNote/saveNoteWord.do',
            resetForm: true,
            type: 'POST',
            dataType: 'json',
            beforeSubmit: function () {
                return true;
            },
            success: function (result) {
                $("#submit_add_ok").bind("click", submitAndNext);
                if (result.code == 200) {
                    $table.bootstrapTable("refresh");
                    reloadWord()
                } else {
                    layer.msg(result.msg);
                }
            },
            error: function (result) {
                $("#submit_add_ok").bind("click", submitAndNext);
                layer.msg("出现错误");
            }
        };

        $("#next_word_form").ajaxSubmit(appData);
    }


    //查询按钮
    $("#search_btn").bind("click", reload);

    function reload() {
        var keyWord = $("#searchText").val();
        $('#table').bootstrapTable('refresh', {
            url: '${basePath}/wordNote/listWordNote.do',
            query: {
                search: keyWord
            }
        });
    }


    function deleteWordNote(id) {
        $.post("${basePath}/wordNote/deleteWordNote.do", {"id": id}, function (result) {
            if (result.code == 200) {
                $table.bootstrapTable("refresh");
            } else {
                layer.msg(result.msg);
            }
        });
    }

    //获取随机数字
    function getRandomNum(Min, Max) {
        var Range = Max - Min;
        var Rand = Math.random();
        return (Min + Math.round(Rand * Range));
    }   
</script>


</body>
</html>