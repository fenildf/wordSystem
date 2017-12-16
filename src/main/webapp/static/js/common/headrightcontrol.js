//根据class属性来控制是否显示
var admins = document.getElementsByClassName("admin");

var index = user.lastIndexOf("roleId=");
var lastIndex = user.lastIndexOf("]");
var roleId = user.substring(index+7,lastIndex);
if(roleId!="1"){
 for(var i in admins){
	 $(admins[i]).hide();
 }
}