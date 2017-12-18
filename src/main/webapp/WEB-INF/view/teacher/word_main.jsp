<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE HTML>
<html lang="en">
<head>
<title>单词管理界面</title>
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
            <div class="panel-heading"><a href="${basePath}/person/studentMain.do">学生管理</a> | <font color="green">单词管理</font></div>
            <div class="panel-body">
                <div id="toolbar">
                    <a href="#" id="add_btn" class="btn btn-primary" role="button">
                        <i class="glyphicon glyphicon-plus"></i>添加
                    </a>
                    <a href="#" id="batch_add_btn" class="btn btn-primary" role="button">
                        <i class="glyphicon glyphicon-trash"></i>批量导入
                    </a>
                    <a href="#" id="batch_export_btn" class="btn btn-primary" role="button">
                        <i class="glyphicon glyphicon-trash"></i>批量导出
                    </a>
                    
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
                       	添加单词                   
            </h4>
         </div>
         <div class="modal-body">
          <form id="add_word_form" method="post" enctype="multipart/form-data">
              <input type="hidden" name="id" id="id"/>
   			   <table border="0" width="100%" cellpadding="3" style="border-collapse:separate; border-spacing:0px 10px;">
   			  <tr>
   			     <td colspan="1">英文:</td>
      			<td colspan="2"><input id="englishName" type="text" name="englishName" style="width:200px"/></td>
      		   </tr> 
      		   <tr>
      		     <td colspan="1">中文:</td>
      		     <td colspan="2"><textarea id="chinaName" rows="3" style="width:100%" name="chinaName"></textarea></td>
      		   </tr>
      		    <tr>
      		     <td  colspan="1">补充:</td>
      		     <td  colspan="2">
      		       <textarea style="width:100%;height:100px" name="detail" id="detail"></textarea></td>
      		     </td>
      		   </tr>
      		   <tr>
      		     <td>分类</td>
      		     <td>四级<input id="four_level" type="radio" name="type" value="0"> 六级<input type="radio" id="six_level" name="type" value="1" /> 其他<input type="radio" id="other_level" name="type" value="-1"/></td>
      		   </tr>
               </table>
           </form>
   		 <div style="width:100%;text-align:center" >
   		  <button type="button" id="submit_add_ok" class="btn btn-primary"><span class="glyphicon glyphicon-ok" aria-hidden="true"></span>提交</button>
		 </div>
		
         </div>
      </div>
   </div>
</div> 

<!-- 批量添加 -->
<div class="modal fade" id="myModal2" tabindex="-1" role="dialog"
   aria-labelledby="myModalLabel" aria-hidden="true" >
   <div class="modal-dialog" style="width:300px;">
      <div class="modal-content">
         <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal"
               aria-hidden="true">x
            </button>
            <h4 class="modal-title" >
                                   批量添加单词                  
            </h4>
         </div>
         <div class="modal-body">
          <form id="batch_import_form" method="post" enctype="multipart/form-data">
   			   <table border="0" width="100%" cellpadding="3" style="border-collapse:separate; border-spacing:0px 10px;">
   			  <tr>
   			     <td colspan="1"></td>
      			 <td colspan="2"><input type="file" name="file" /></td>
      		   </tr> 
      		   <tr>
      		     <td>分类</td>
      		     <td>四级<input  type="radio" name="wordType" value="0"> 六级<input type="radio"  name="wordType" value="1" /> 其他<input type="radio"  name="wordType" value="-1"/></td>
      		   </tr>
               </table>
           </form>
   		 <div style="width:100%;text-align:center" >
   		  <button type="button" id="batch_submit_add" class="btn btn-primary"><span class="glyphicon glyphicon-ok" aria-hidden="true"></span>提交</button>
		 </div>
		
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
	                   return '<a href="#" onclick="deleteWord('+value+')" >删除</a>&nbsp |&nbsp <a href="#" onclick="editWord('+value+')" >修改</a>';
	                }
	            }
	        ]
	    });
		
		//隐藏id列
		$table.bootstrapTable('hideColumn', 'id');
		var ue = UE.getEditor('detail');
		//单个添加
		$("#add_btn").click(function(){ 
				$('#myModal').modal({
					keyboard: true,
					backdrop:'static'
				});
		});
		
	   //添加事件
		$("#submit_add_ok").bind("click",addWord);
		
		function addWord(){
			$("#submit_add_ok").unbind("click",addWord);
			//请求后台数据
			  var appData={    
			  	  url:'${basePath}/word/saveWord.do',
			  	  resetForm: true, 
			  	  type:'POST',
			  	  dataType : 'json',
			  	  beforeSubmit: function(){
			  	       return true;//这里可以自定义参数检查器
			  	   },
			  	  success:function(result){
			  		$("#submit_add_ok").bind("click",addWord);
			  	      if(result.code==200){
			  	    	$table.bootstrapTable("refresh");
			  	    	$('#myModal').modal('hide');
			  	      }else{
			  	    	 layer.msg(result.msg);
			  	      }   	
			  	   },
			  	  error:function(result){
			  		$("#submit_add_ok").bind("click",addWord);
			           layer.msg("出现错误");
			  	   }
			  }; 
			  
			  $("#add_word_form").ajaxSubmit(appData);  
		}
		
		
		$("#batch_submit_add").bind("click",batchAddWord);
		
		function batchAddWord(){
			$("#batch_submit_add").unbind("click",batchAddWord);
			//请求后台数据
			  var appData={    
			  	  url:'${basePath}/word/batchImportWord.do',
			  	  resetForm: true, 
			  	  type:'POST',
			  	  dataType : 'json',
			  	  beforeSubmit: function(){
			  	       return true;//这里可以自定义参数检查器
			  	   },
			  	  success:function(result){
			  		$("#batch_submit_add").bind("click",batchAddWord);
			  	      if(result.code==200){
			  	    	$table.bootstrapTable("refresh");
			  	    	$('#myModal2').modal('hide');
			  	      }else{
			  	    	 layer.msg(result.msg);
			  	      }   	
			  	   },
			  	  error:function(result){
			  		$("#batch_submit_add").bind("click",batchAddWord);
			           layer.msg("出现错误");
			  	   }
			  }; 
			  
			  $("#batch_import_form").ajaxSubmit(appData); 
		}
		
		//删除
		function deleteWord(id){
			$.post("${basePath}/word/deleteWord.do",{"id":id},function(result){
				 if(result.code==200){
					$table.bootstrapTable("refresh");
				 }else{
					 layer.msg(result.msg);
				 }
			});
		}
		
		function editWord(id){
			$.post("${basePath}/word/findWordById.do",{"id":id},function(result){
				if(result.code==200){
					var word = result.data;
					$('#myModal').modal('show');
					$("#id").val(word.id);
					$("#englishName").val(word.englishName);
					$("#chinaName").val(word.chinaName);
					$("#detail").val(word.detail);
					
					ue.setContent(word.detail);
					var type = word.type;
					if(type==0){
						$("#four_level").attr("checked",true);
					}else if(type==1){
						$("#six_level").attr("checked",true);
					}else{
						$("#other_level").attr("checked",true);
					}
				}else{
					layer.msg(result.msg);
				}
			});
		}
		
		
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
	   
	 $("#batch_add_btn").bind("click",function(){
		 $('#myModal2').modal({
				keyboard: true,
				backdrop:'static'
			});
	 });


      //批量导出按钮
      $("#batch_export_btn").bind("click", function () {
          window.location.href = "${basePath}/word/exportExcel.do";
      });
  </script> 
  

</body>
</html>