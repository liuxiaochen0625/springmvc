<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/common/taglibs.jsp" %>
<%@ include file="/common/jquery-ui-inc.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>后台管理系统</title>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />        
        <link rel="stylesheet" id="easyuicssId" type="text/css" href="${ctx}/widgets/jquery-easyui/themes/default/easyui.css">
        <link rel="stylesheet" type="text/css" href="${ctx}/widgets/jquery-easyui/themes/icon.css"> 
        <link rel="stylesheet" type="text/css" href="${ctx}/css/default.css"> 
        <style type="text/css">
            h3 {
                width: 95%;
                border: 1px solid #eee;
                margin: 2px;
                margin-left: 2px;
                height: 20px;
                font-size: 12px;
                font-weight: normal;
                line-height: 20px;
                vertical-align: center;
                text-indent: 5px;
                cursor: pointer;
            }
			.icon-theme{
				background:url('${ctx}/widgets/jquery-easyui/extend/images/theme2.png') no-repeat;
			}
        </style>
        <script type="text/javascript">
        	//关闭进度条
			function closes(){
				$("#Loading").fadeOut("normal",function(){
					$(this).remove();
				});
			}
			var pc;
			$.parser.onComplete = function(content){
				if(pc) clearTimeout(pc);
				pc = setTimeout(closes, 1000);
			}
			
			//主题预览
			function themeView(){
				$("#themeView").window('open');
			}
			
			//切换主题
			function switchTheme(id){
				var cxt="${ctx}/widgets/jquery-easyui/themes/";
				var end = "/easyui.css";
				var classPath = cxt+id+end;
				$("#easyuicssId").attr('href',classPath);
				$("#themeimgId").attr('src',"${ctx}/widgets/jquery-easyui/extend/images/"+id+".jpg");
			}
			
            var maxWindow = 10;
            $(function(){
            	setInterval("document.getElementById('nowTime').innerHTML=new Date().toLocaleString()+' 星期'+'日一二三四五六'.charAt(new Date().getDay());",1000);
                var p = $('body').layout('panel', 'west').panel({
                    onCollapse: function(){
                        //alert('collapse');
                    }
                });
				
				//主题列表树
				$("#themeListTree").tree({
					onClick:function(node){
						//alert(node.id);
						switchTheme(node.id);
					}
				});
                
                //主tab面板
                $('#main').tabs({
                    onSelect: function(){
                        var tab = $('#main').tabs('getSelected');
                        //alert(tab.iframe);						
                    }
                    //tools: [{
					//		text:'主题',
					//		iconCls:'icon-theme',
					//		handler:themeView
					//	},{
                    //    iconCls: 'icon-help',
                    //    handler: function(){
                    //        help();
                    //        //window.location.href='index.html';
                    //    }
                    //    
                    //}]
                });
                //var content = '<iframe scrolling="auto" frameborder="0"  src="${ctx}/widgets/jquery-easyui/extend/about.html" style="width:100%;height:100%;"></iframe>';
                //$('#main').tabs('add', {
                //    title: "简绍",
                //    content: content,
                //    closable: false
                //});
                
                tabCloseEven();
            });
            
            function addTab(title, url){
                var iframeID= Date.parse(new Date());
                var tabslength = $('#main').tabs('tabs').length;
                if (tabslength < maxWindow) {
                    if ($('#main').tabs('exists', title)) {
                        $.messager.confirm('确认', '确定要刷新标签页： <font color=red>' + title + '</font> 吗?', function(flag){
                            if (flag) {
                                var content = '<iframe scrolling="auto"  frameborder="0"  src="' + url + '" style="width:100%;height:100%;"></iframe>';
                                var tab = $('#main').tabs('getTab', title);
                                $('#main').tabs('update', {
                                    tab: tab,
                                    options: {
                                        title: title,
                                        content: content,
                                        closable: true
                                    }
                                });
                                $('#main').tabs('select', title);
                            }
                        });
                        //$('#main').tabs('close',title);
                    }
                    else {
                        var content = '<iframe scrolling="auto"  frameborder="0"  src="' + url + '" style="width:100%;height:100%;"></iframe>';
                        $('#main').tabs('add', {
                            title: title,
                            content: content,
                            closable: true
                        });
                    }
                }
                else {
                    $.messager.alert('信息提示', "您打开窗口太多了,请关闭不用的窗口!", 'info', function(){
                        return false;
                    });
                }
                
                $(".tabs-inner").dblclick(function(){
                    var ti = $(this).children("span").text();
                    if (ti != '简绍') {
                        $('#main').tabs('close', ti);
                    }
                });
                
                $(".tabs-inner").bind('contextmenu', function(e){
                    $('#mm').menu('show', {
                        left: e.pageX,
                        top: e.pageY
                    });
                    var subtitle = $(this).children("span").text();
                    $('#main').data("currtab", subtitle);
                    return false;
                });
            }
            
            function select(){
                $('#leftmenu').accordion('select', 'Title1');
            }
            
            
            function help(){
                $.messager.alert('信息提示', "双击关闭tab页!", 'info');
            }
            
            
            /**关闭tab标签页*/
            function closeTab(){
                var tab = parent.$('#main').tabs('getSelected');
                var tname = tab.panel('options').title;
                parent.$('#main').tabs('close', tname)
            }
        </script>
    </head>
    <body class="easyui-layout" style="overflow-y: hidden"  scroll="no">
        <div id='Loading' style="position:absolute;z-index:1000;top:0px;left:0px;width:100%;height:100%;background:#DDDDDB;text-align:center;padding-top: 20%;">
            <h1>
                <font color="#15428B">
                 	页面初始化中，请等待....
                </font>
            </h1>
        </div>
        <div region="north" split="false"   border="false" style="height:20px;background:#D2E0F2;">
            <span style="float:right; padding-right:20px;" class="head">欢迎 ${adminUser.userName} ,身份：${roleNames}&nbsp;&nbsp;&nbsp;&nbsp;
               &nbsp;&nbsp;当前时间：<span id="nowTime"></span>
               &nbsp;&nbsp;&nbsp;&nbsp;
               <a href="#" id="editpass">修改密码</a> 
               <a href="${ctx}/j_spring_security_logout" id="loginOut">安全退出</a>
            </span>
            <span style="padding-left:10px; font-size: 16px; "><img src="/images/blocks.gif" width="20" height="20" align="absmiddle" />信息管理后台
                
            </span>
        </div>
        <div region="west" split="false" title="功能扩展菜单" style="width:150px;padding:0px;">
            <div id="leftmenu" class="easyui-accordion">
                <c:if test="${adminMenuList!=null }">
                   <c:forEach items="${adminMenuList }" var="menu">
                     <div title="${menu.menuName }"  icon="icon-undo"  style="overflow:auto;">                       
                          <c:if test="${menu.childMenuList!=null }">
                             <c:forEach items="${menu.childMenuList }" var="c">
                                <center><h3 onclick="addTab('${c.menuName }','${c.menuURL }');bgChange(this);">${c.menuName }</h3></center>
                             </c:forEach>
                          </c:if>
                     </div>
                  </c:forEach>
               </c:if>
            </div>
            <script type="text/javascript">
            function bgChange(obj){           
     	         $("h3").each(function(){
         	        $(this).removeAttr("style");
         	     });
     	         $(obj).css("background","#B6DC73"); 
              }
            </script>
        </div>
        <div region="center"  split="true"  style="background:#eee;">
            <div id="main" class="easyui-tabs"  fit="true" border="false" >
               <div title="简绍" style="padding:20px;overflow:hidden;" id="home">	
			      <h1>Welcome to jQuery UI!</h1>
		       </div>
            </div>
        </div>
        <div region="south" split="false" style="height:20px;background:#D2E0F2;">
            <center><div class="footer">By zhangzuolong&nbsp;&nbsp;&nbsp;</div></center>
        </div>
        <script type="text/javascript">
            var bodyh = $(window).height();
            var toph = $('#top').height();
            var mainh = bodyh - toph;
            $('#main').height(mainh);
            function tabCloseEven(){
                //关闭当前
                $('#mm-tabclose').click(function(){
                    var tt = $('#main').data("currtab");
                    $('#main').tabs('select', tt);
                    if (tt != '简绍') {
                        $('#main').tabs('close', tt);
                    }
                })
                //全部关闭
                $('#mm-tabcloseall').click(function(){
                    var tt = $('#main').data("currtab");
                    $('#main').tabs('select', tt);
                    
                    $('.tabs-inner span').each(function(i, n){
                        var t = $(n).text();
                        if (t != '简绍') {
                            $('#main').tabs('close', t);
                        }
                    });
                });
                //关闭除当前之外的TAB
                $('#mm-tabcloseother').click(function(){
                    var tt = $('#main').data("currtab");
                    $('#main').tabs('select', tt);
                    
                    $('.tabs-inner span').each(function(i, n){
                        var t = $(n).text();
                        if (t != tt && t != '简绍') {
                            $('#main').tabs('close', t);
                        }
                    });
                });
                //关闭当前右侧的TAB
                $('#mm-tabcloseright').click(function(){
                    var tt = $('#main').data("currtab");
                    $('#main').tabs('select', tt);
                    
                    var nextall = $('.tabs-selected').nextAll();
                    if (nextall.length == 0) {
                        $.messager.alert('信息提示', "到头了，后边没有啦~~", 'info', function(){
                            return false;
                        });
                    }
                    nextall.each(function(i, n){
                        var t = $('a:eq(0) span', $(n)).text();
                        if (t != '简绍') {
                            $('#main').tabs('close', t);
                        }
                    });
                    return false;
                });
                //关闭当前左侧的TAB
                $('#mm-tabcloseleft').click(function(){
                    var tt = $('#main').data("currtab");
                    $('#main').tabs('select', tt);
                    
                    var prevall = $('.tabs-selected').prevAll();
                    if (prevall.length <= 1) {
                        $.messager.alert('信息提示', "【简绍】不能关了啊~", 'info', function(){
                            return false;
                        });
                    }
                    prevall.each(function(i, n){
                        var t = $('a:eq(0) span', $(n)).text();
                        if (t != '简绍') {
                            $('#main').tabs('close', t);
                        }
                    });
                    return false;
                });
            }
        </script>
        <div id="mm" class="easyui-menu" style="width:150px;">
            <div id="mm-tabclose">
                关闭
            </div>
            <div id="mm-tabcloseall">
                全部关闭
            </div>
            <div id="mm-tabcloseother">
                除此之外全部关闭
            </div>
            <div class="menu-sep">
            </div>
            <div id="mm-tabcloseright">
                当前页右侧全部关闭
            </div>
            <div id="mm-tabcloseleft">
                当前页左侧全部关闭
            </div>
        </div>
		<div id="themeView" iconCls="icon-theme" class="easyui-window" title="主题设置" closed="true" style="height:380px;width:600px;">
		<div class="easyui-layout" style="height:100%;width:100%;">
			<div region="west" title="主题列表" style="width:115px;height:300px;">
				<ul id="themeListTree" class="easyui-tree">
					<li select="true" id="default">默认default</li>
					<li id="gray">灰色gray</li>
					<li id="green">绿色green</li>
					<li id="red">艳红色red</li>		
				</ul>
			</div>
			<div region="center" title="主题预览">
				<img src="${ctx}/widgets/jquery-easyui/extend/images/default.jpg" id="themeimgId" style="height:100%;width:100%;"/>				
			</div>
		</div>
	</div>
    </body>
</html>