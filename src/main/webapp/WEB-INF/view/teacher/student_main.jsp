<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE HTML>
<html lang="en">
<head>
<title>学生管理界面</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@include file="/WEB-INF/common/common.jsp"%>
<script src="${basePath}/static/bootstarp-table/extensions/export/bootstrap-table-export.min.js" type="text/javascript"></script>
<link href="${basePath}/static/bootstarp-table/bootstrap-table.min.css" rel="stylesheet" type="text/css"/>
<script src="${basePath}/static/bootstarp-table/bootstrap-table.min.js" type="text/javascript"></script>
<script src="${basePath}/static/bootstarp-table/locale/bootstrap-table-zh-CN.min.js" type="text/javascript"></script>
<script type="text/javascript" src="${basePath }/static/layer/layer.js"></script>
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
            <div class="panel-heading"><font color="green">学生管理</font> | <a href="${basePath}/word/wordMain.do">单词管理</a></div>
            <div class="panel-body">
                <div id="toolbar">
                    <a href="#" id="add_btn" class="btn btn-primary" role="button">
                        <i class="glyphicon glyphicon-plus"></i>添加
                    </a>
                    <a href="#" id="batch_add_btn" class="btn btn-primary" role="button">
                        <i class="glyphicon glyphicon-trash"></i>批量导入
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
  
  
<!-- 单个添加 -->
<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
   aria-labelledby="myModalLabel" aria-hidden="true" >
   <div class="modal-dialog" style="width:300px;">
      <div class="modal-content">
         <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal"
               aria-hidden="true">x
            </button>
            <h4 class="modal-title">
                       	添加学生                   
            </h4>
         </div>
         <div class="modal-body">
          <form id="add_student_form" method="post" enctype="multipart/form-data">
              <input type="hidden" name="id" id="id"/>
   			   <table border="0" width="100%" cellpadding="3" style="border-collapse:separate; border-spacing:0px 10px;">
   			  <tr>
   			     <td colspan="1">学号:</td>
      			<td colspan="2"><input id="loginName" type="text" name="loginName"/></td>
      		   </tr> 
      		   <tr>
      		     <td colspan="1">姓名:</td>
      		     <td colspan="2"><input type="text" id="name"  name="name"/></td>
      		   </tr>
      		    <tr>
      		     <td rowspan="3" colspan="1">性别:</td>
      		     <td rowspan="3" colspan="2">
      		        <select id="gender" name="gender">
      		         <option name="gender" value="">-请选择-</option>
      		         <option name="gender" value="男">--男--</option>
      		         <option name="gender" value="女">--女--</option>
      		        </select>
      		     </td>
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
                                   批量添加学生                  
            </h4>
         </div>
         <div class="modal-body">
          <form id="batch_import_form" method="post" enctype="multipart/form-data">
   			   <table border="0" width="100%" cellpadding="3" style="border-collapse:separate; border-spacing:0px 10px;">
   			  <tr>
   			     <td colspan="1"></td>
      			 <td colspan="2"><input type="file" name="file" /></td>
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
	        url: '${basePath}/person/listStudentPage.do',
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
	                title: '学号',
	                field: 'loginName',
	                align: 'center'
	            }, 
	            {
	                title: '姓名',  
	                field: 'name',  
	                align: 'center'
	            },
	            {
	                title: '性别',
	                field: 'gender',
	                align: 'center'
	            },
                {
                    title: '背诵单词数量',
                    field: 'remeberCount',
                    align: 'center'
                },
	            {
	                title: '管理',
	                field: 'id',
	                align: 'center',
	                formatter: function (value, row, index) {//自定义显示，这三个参数分别是：value该行的属性，row该行记录，index该行下标
	                   return '<a href="#" onclick="deleteStudent('+value+')" >删除</a>&nbsp |&nbsp <a href="#" onclick="editStudent('+value+')" >修改</a>';
	                }
	            }
	        ]
	    });
		//隐藏id列
		$table.bootstrapTable('hideColumn', 'id');
		
	//单个添加
	$("#add_btn").click(function(){
		$('#myModal').modal({
			keyboard: true,
			backdrop:'static'
		});
	});
		
	//批量添加
	$("#batch_add_btn").click(function(){
		$('#myModal2').modal({
			keyboard: true,
			backdrop:'static'
		});
	});
	
	
	//按钮添加事件
	$("#submit_add_ok").bind("click",addStudent);
	
	function addStudent(){
		$("#submit_add_ok").unbind("click",addStudent);
		  //请求后台数据
		  var appData={    
		  	  url:'${basePath}/person/saveStudent.do',
		  	  resetForm: true, 
		  	  type:'POST',
		  	  dataType : 'json',
		  	  beforeSubmit: function(){
		  	       return checkParams();//这里可以自定义参数检查器
		  	   },
		  	  success:function(result){
		  		$("#submit_add_ok").bind("click",addStudent);
		  	      if(result.code==200){
		  	    	$table.bootstrapTable("refresh");
		  	    	$('#myModal').modal('hide');
		  	      }else{
		  	    	 layer.msg(result.msg);
		  	      }   	
		  	   },
		  	  error:function(result){
		  		$("#submit_add_ok").bind("click",addStudent);
		           layer.msg("出现错误");
		  	   }
		  }; 
		  
		  $("#add_student_form").ajaxSubmit(appData);  
	}
	
	function checkParams(){
		var loginName = $("#loginName").val();
		var name = $("#name").val();
		var gender = $("#gender").val();
		if(loginName==null||loginName==""){
			layer.msg("请输入学号");
			return false;
		}
		if(name==null||name==""){
			layer.msg("请输入姓名");
			return false;
		}
		if(gender==null||gender==""){
			layer.msg("请选择性别");
			return false;
		}
		return true;
	}
	
	//删除学生
    function deleteStudent(id){
    	$.post("${basePath}/person/deleteStudent.do",{"id":id},function(result){
    		if(result.code==200){
    			$table.bootstrapTable("refresh");
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
			url:'${basePath}/person/listStudentPage.do',
			query:{
				search:keyWord
			}
	    });
    }
    
    //编辑学生信息
    function editStudent(id){
    	$.post("${basePath}/person/getPersonById.do",{"id":id},function(result){
    		if(result.code==200){
    			$('#myModal').modal({
    				keyboard: true,
    				backdrop:'static'
    			});
    			var person = result.data;
    			$("#loginName").val(person.loginName);
    			$("#name").val(person.name);
    			$("#gender").val(person.gender);
    			$("#id").val(person.id)
    		}else{
    			layer.msg(result.msg);
    		}
    	})
    }
    
    
    $("#batch_submit_add").bind("click",batchImport)
    
    function batchImport(){
    	$("#batch_submit_add").unbind("click",batchImport);
    	//请求后台数据
		  var appData={    
		  	  url:'${basePath}/person/batchImport.do',
		  	  resetForm: true, 
		  	  type:'POST',
		  	  dataType : 'json',
		  	  beforeSubmit: function(){
		  	       return function(){//这里可以自定义参数检查器
		  	    	   var value = $("#file").val();
		  	           if(value==null){
		  	        	   layer.msg("请选择文件");
		  	        	   return false;
		  	           }
		  	           return true;
		  	       };
		  	   },
		  	  success:function(result){
		  		$("#batch_submit_add").bind("click",batchImport);
		  	      if(result.code==200){
		  	        $('#myModal2').modal('hide');
		  	    	$table.bootstrapTable("refresh");
		  	      }else{
		  	    	 layer.msg(result.msg);
		  	      }   	
		  	   },
		  	  error:function(result){
		  		$("#batch_submit_add").bind("click",batchImport);
		           layer.msg("出现错误");
		  	   }
		  }; 
		  
		  $("#batch_import_form").ajaxSubmit(appData); 
    }
    
  </script> 
  

</body>
</html>