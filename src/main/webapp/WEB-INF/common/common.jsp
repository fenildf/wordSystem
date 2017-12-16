<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
    String basePath = request.getContextPath();
    request.setAttribute("basePath", basePath);
%>
<meta charset="utf-8"/>
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta content="width=device-width, initial-scale=1" name="viewport"/>
<!-- BEGIN GLOBAL MANDATORY STYLES -->
<link href="http://fonts.googleapis.com/css?family=Open+Sans:400,300,600,700&subset=all" rel="stylesheet"
      type="text/css"/>
<link href="${basePath}/static/assets/global/plugins/font-awesome/css/font-awesome.min.css" rel="stylesheet"
      type="text/css"/>
<link href="${basePath}/static/assets/global/plugins/simple-line-icons/simple-line-icons.min.css" rel="stylesheet"
      type="text/css"/>
<link href="${basePath}/static/assets/global/plugins/bootstrap/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
<link href="${basePath}/static/assets/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css" rel="stylesheet"
      type="text/css"/>
<!-- END GLOBAL MANDATORY STYLES -->
<!-- BEGIN PAGE LEVEL PLUGINS -->
<link href="${basePath}/static/assets/global/plugins/select2/css/select2.min.css" rel="stylesheet" type="text/css"/>
<link href="${basePath}/static/assets/global/plugins/select2/css/select2-bootstrap.min.css" rel="stylesheet"
      type="text/css"/>
<!-- END PAGE LEVEL PLUGINS -->
<!-- BEGIN THEME GLOBAL STYLES -->
<link href="${basePath}/static/assets/global/css/components.min.css" rel="stylesheet" id="style_components"
      type="text/css"/>
<link href="${basePath}/static/assets/global/css/plugins.min.css" rel="stylesheet" type="text/css"/>
<!-- BEGIN THEME LAYOUT STYLES -->
<link href="${basePath}/static/assets/layouts/layout3/css/layout.min.css" rel="stylesheet" type="text/css"/>
<link href="${basePath}/static/assets/layouts/layout3/css/themes/default.min.css" rel="stylesheet" type="text/css"
      id="style_color"/>
<link href="${basePath}/static/assets/layouts/layout3/css/custom.min.css" rel="stylesheet" type="text/css"/>
<!-- END THEME GLOBAL STYLES -->
<link rel="shortcut icon" href="${basePath}/favicon.ico"/>
<link href="${basePath}/static/bootstarp-table/bootstrap-table.min.css" rel="stylesheet" type="text/css"/>
<link href="${basePath}/static/css/jquery.datetimepicker.css" rel="stylesheet" type="text/css"/>
<link href="${basePath}/static/css/common.css" rel="stylesheet" type="text/css"/>


<!-- BEGIN CORE PLUGINS -->
<script src="${basePath}/static/assets/global/plugins/jquery.min.js" type="text/javascript"></script>
<script src="${basePath}/static/assets/global/plugins/bootstrap/js/bootstrap.min.js" type="text/javascript"></script>
<script src="${basePath}/static/assets/global/plugins/js.cookie.min.js" type="text/javascript"></script>
<script src="${basePath}/static/assets/global/plugins/jquery-slimscroll/jquery.slimscroll.min.js"
        type="text/javascript"></script>
<script src="${basePath}/static/assets/global/plugins/jquery.blockui.min.js" type="text/javascript"></script>
<script src="${basePath}/static/assets/global/plugins/bootstrap-switch/js/bootstrap-switch.min.js"
        type="text/javascript"></script>
<!-- END CORE PLUGINS -->
<!-- BEGIN PAGE LEVEL PLUGINS -->
<script src="${basePath}/static/assets/global/plugins/jquery-validation/js/jquery.validate.min.js"
        type="text/javascript"></script>
<script src="${basePath}/static/assets/global/plugins/jquery-validation/js/additional-methods.min.js"
        type="text/javascript"></script>
<script src="${basePath}/static/assets/global/plugins/select2/js/select2.full.min.js" type="text/javascript"></script>
<!-- END PAGE LEVEL PLUGINS -->
<!-- BEGIN THEME GLOBAL SCRIPTS -->
<script src="${basePath}/static/assets/global/scripts/app.min.js" type="text/javascript"></script>
<!-- END THEME GLOBAL SCRIPTS -->
<script src="${basePath}/static/bootstarp-table/bootstrap-table.min.js" type="text/javascript"></script>
<script src="${basePath}/static/bootstarp-table/locale/bootstrap-table-zh-CN.min.js" type="text/javascript"></script>
<script src="${basePath}/static/bootstarp-table/extensions/export/bootstrap-table-export.min.js"
        type="text/javascript"></script>
<script src="${basePath}/static/layer/layer.js" type="text/javascript"></script>
<script src="http://static.runoob.com/assets/jquery-validation-1.14.0/dist/jquery.validate.min.js"></script>
<script type="text/javascript" src="${basePath}/static/js/common/jquery-form.js"></script>
<script type="text/javascript" src="${basePath}/static/js/common/build/jquery.datetimepicker.full.js"></script>
<script type="text/javascript" src="${basePath}/static/js/common/common.js"></script>
<script>
    var sysDict;
    $(document).ready(function () {
        $('#clickmewow').click(function () {
            $('#radio1003').attr('checked', 'checked');
        });
        if (typeof(sysDict) == 'undefined' || sysDict == '') {
            $.ajax({
                url: "${basePath}/dict/getSystemDict",
                dataType: 'json',
                type: "post",
                traditional: true,
                data: {},
                success: function (result) {
                    sysDict = result;
                    console.log(sysDict);
                }
            });
        }
    });

    function myPage(params) {
        var limit = params.limit;
        params.pageNum = params.offset / limit + 1;
        params.pageSize = limit;
        return params;
    }


</script>
