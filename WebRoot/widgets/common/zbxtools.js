/**
 * 中百信EasyUI工具类
 * @author 郝启敏
 **/
var ZBXTools = {};
/**
 *显示进度条 
 *@param msg 进度条需要提示的信息
 **/
ZBXTools.showProcess=function(msg){
	$.messager.progress({"msg":msg,text:''});
}

/**
 *关闭进度条
 **/
ZBXTools.hideProcess = function(){
	$.messager.progress('close');
}

/**
 *启用linkbutton按钮 
 * @param selector jquery选择器
 **/
ZBXTools.enableButton=function(selector){
	$(selector).linkbutton('enable');
}

/**
 *禁用linkbutton按钮 
 * @param selector jquery选择器
 **/
ZBXTools.disableButton=function(selector){
	$(selector).linkbutton('disable');
}

