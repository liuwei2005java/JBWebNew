<%@page contentType="text/html; charset=GBK"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@include file="/common/common.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<HEAD>
		<TITLE>用户登录</TITLE>
		<LINK href="${ctx}/images/Default.css" type=text/css rel=stylesheet>
		<LINK href="${ctx}/images/xtree.css" type=text/css rel=stylesheet>
		<LINK href="${ctx}/images/User_Login.css" type=text/css rel=stylesheet>
		<META http-equiv=Content-Type content="text/html; charset=gb2312"/>
		<META content="MSHTML 6.00.6000.16674" name=GENERATOR/>
	</HEAD>
	
	<script type="text/javascript">
		function login(){
			fm.submit();
		}
		
		//因其他库使用了$，因此使用j代替
		var j = jQuery.noConflict(true);
		
		j(document).ready(function(){
			var msg = "${msg}";
			if(msg=="error"){
				j.messager.alert('错误提示','用户名或密码错误，并确认用户是否可用','warning');
			}
		});
	</script>
	
	<BODY id=userlogin_body>
	<form id="fm" method="post" action="login_login.action">
		<DIV></DIV>
		<DIV id="user_login">
			<DL>
				<DD id="user_top">
					<UL>
						<LI class="user_top_l"></LI>
						<LI class="user_top_c"></LI>
						<LI class="user_top_r"></LI>
					</UL>
				<DD id="user_main">
					<UL>
						<LI class="user_main_l"></LI>
						<LI class="user_main_c">
							<DIV class="user_main_box">
								<UL>
									<LI class="user_main_text">
										用户名：
									</LI>
									<LI class="user_main_input">
										<INPUT class="TxtUserNameCssClass" id="username" maxLength=20 name="username"/>
									</LI>
								</UL>
								<UL>
									<LI class="user_main_text">
										密 码：
									</LI>
									<LI class="user_main_input">
										<INPUT class="TxtPasswordCssClass" id="passwd" type="password" name="passwd"/>
									</LI>
								</UL>
								<UL>
							</DIV>
						</LI>
						<LI class="user_main_r">
							<INPUT class="IbtnEnterCssClass" id="IbtnEnter"
								style="BORDER-TOP-WIDTH: 0px; BORDER-LEFT-WIDTH: 0px; BORDER-BOTTOM-WIDTH: 0px; BORDER-RIGHT-WIDTH: 0px"
								onclick='login()'
								type="image" src="${ctx}/images/user_botton.gif"/>
						</LI>
					</UL>
				<DD id="user_bottom">
					<UL>
						<LI class="user_bottom_l"></LI>
						<LI class="user_bottom_c"></LI>
						<LI class="user_bottom_r"></LI>
					</UL>
				</DD>
			</DL>
		</DIV>
		<SPAN id="ValrUserName" style="DISPLAY: none; COLOR: red"></SPAN>
		<SPAN id="ValrPassword" style="DISPLAY: none; COLOR: red"></SPAN>
		<SPAN id="ValrValidateCode" style="DISPLAY: none; COLOR: red"></SPAN>
		<DIV id="ValidationSummary1" style="DISPLAY: none; COLOR: red"></DIV>
		<DIV></DIV>
		</FORM>
	</BODY>
</HTML>
