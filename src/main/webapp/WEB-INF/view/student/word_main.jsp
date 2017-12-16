<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE HTML>
<html lang="en">
<head>
<title>单词库</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@include file="/WEB-INF/common/common.jsp"%>
<script src="${basePath}/static/bootstarp-table/extensions/export/bootstrap-table-export.min.js" type="text/javascript"></script>
<link href="${basePath}/static/bootstarp-table/bootstrap-table.min.css" rel="stylesheet" type="text/css"/>
<script src="${basePath}/static/bootstarp-table/bootstrap-table.min.js" type="text/javascript"></script>
<script src="${basePath}/static/bootstarp-table/locale/bootstrap-table-zh-CN.min.js" type="text/javascript"></script>
<script type="text/javascript" src="${basePath }/static/layer/layer.js"></script>
<script type="text/javascript" src="${basePath}/static/ue/ueditor.config.js"></script>
<script type="text/javascript" src="${basePath}/static/ue/ueditor.all.js"></script>
<script type="text/javascript" src="${basePath}/static/js/jquery-form.js"></script>
<style type="text/css">
    .fixed-table-toolbar .bs-bars{
        width:100%;
    }
</style>
</head>
<body class="page-container-bg-solid">
	<div class="page-wrapper">
    <div class="page-wrapper-row full-height">
        <div class="panel panel-default">
            <div class="panel-heading"><a href="${basePath}/person/wordNoteMain.do">个人单词本</a> | <font color="green">词库</font></div>
            <div class="panel-body">
                <div id="toolbar">
                     <a href="#" id="search_btn" class="btn btn-primary" role="button" style="margin-top: 6px;float:right;height:30px">
                        <i class="glyphicon glyphicon-zoom-in"></i>搜索
                    </a>
                    <input type="text" id="searchText" placeholder="请输入内容" style="margin-top: 6px;margin-right:10px;float:right;height:30px"/>
                </div>
                <table id="table" data-toolbar="#toolbar">
                </table>
            </div>
        </div>
    </div>
    
  </div>
  
 <!-- 单个单词添加-->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
   aria-labelledby="myModalLabel" aria-hidden="true" >
   <div class="modal-dialog" >
      <div class="modal-content">
         <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal"
               aria-hidden="true">x
            </button>
            <h4 class="modal-title">
                       	查看单词详情              
            </h4>
         </div>
         <div class="modal-body">
         
   			   <table border="0" width="100%" cellpadding="3" style="border-collapse:separate; border-spacing:0px 10px;">
   			  <tr>
      			<td ><input type="text" id="englishName" disabled="disabled" style="width:100%"/></td>
      		   </tr> 
      		   <tr>
      		     <td ><textarea disabled="disabled" id="chinaName" style="width:100%"></textarea></td>
      		   </tr>
      		    <tr>
      		     <td  id="detail">
      		     </td>
      		   </tr>
               </table>
         </div>
      </div>
   </div>
</div> 

  
<script type="text/javascript">
   
	  var $table = $('#table');
		$table.bootstrapTable({  
	        url: '${basePath}/word/listWordPage.do',
	        method: 'post',
	        contentType: "application/x-www-form-urlencoded",
	        dataType: "json",  
	        striped: true,//设置为 true 会有隔行变色效果  
	        undefinedText: "空",//当数据为 undefined 时显示的字符  
	        pagination:true, //分页 
	        pageNumber:1,//如果设置了分页，首页页码   
	        pageSize:10,//如果设置了分页，页面数据条数
	        pageList:[10, 20, 30, 40,50,60],  //如果设置了分页，设置可供选择的页面数据条数。设置为All 则显示所有记录。
	        paginationPreText:'‹',//指定分页条中上一页按钮的图标或文字,这里是<  
	        paginationNextText:'›',//指定分页条中下一页按钮的图标或文字,这里是>  
	        singleSelect:false,//设置True 将禁止多选  
	        data_local:"zh-US",//表格汉化  
	        cache:false,
	        sidePagination:"server", //服务端处理分页  
	        queryParams:myPage,  
	        idField:"id",//指定主键列
	        columns:[
	        	{
	        	  checkbox:true
	        	},
	            {
	                title: 'id',//表的列名
	                field: 'id',//json数据中rows数组中的属性名  
	                align: 'center'//水平居中 
	            },
	            {
	                title: '英文',
	                field: 'englishName',
	                align: 'center'
	            }, 
	            {
	                title: '中文',  
	                field: 'chinaName',  
	                align: 'center'
	            },
	            {
	                title: '管理',
	                field: 'id',
	                align: 'center',
	                formatter: function (value, row, index) {//自定义显示，这三个参数分别是：value该行的属性，row该行记录，index该行下标
	                   return '<a href="#" onclick="addWord('+value+')" >添加到个人单词本中</a>&nbsp |&nbsp <a href="#" onclick="detailWord('+value+')" >详情</a>';
	                }
	            }
	        ]
	    });
		
		//隐藏id列
		$table.bootstrapTable('hideColumn', 'id');
		
		 //查询按钮
	    $("#search_btn").bind("click",reload);
	    function reload(){
	    	var keyWord = $("#searchText").val();
	    	$('#table').bootstrapTable('refresh',{
				url:'${basePath}/word/listWordPage.do',
				query:{
					search:keyWord
				}
		    });
	    }
	   
	    function addWord(id){
	    	$.post("${basePath}/wordNote/saveNoteWord.do",{"wordId":id,"answer":0},function(reuslt){
	    		if(result.code==200){
	    			layer.msg("添加成功");
	    		}
	    	})
	    }
	    
	    
	  function detailWord(id){
		  $.post("${basePath}/word/findWordById.do",{"id":id},function(result){
			  if(result.code==200){
				  var data = result.data;
				  $("#englishName").val(data.englishName);
				  $("#chinaName").val(data.chinaName);
				  $("#detail").html(data.detail);
				  
				  $('#myModal').modal({
						keyboard: true,
						backdrop:'static'
					});
			  }else{
				  layer.msg(result.msg);
			  }
		  });
	  }
	
  </script> 
  

</body>
</html>