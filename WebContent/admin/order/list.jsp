<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<HTML>
	<HEAD>
		<meta http-equiv="Content-Language" content="zh-cn">
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<link href="${pageContext.request.contextPath}/css/Style1.css" rel="stylesheet" type="text/css" />
		
	</HEAD>
		<!-- 先导JQ -->
		<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.11.3.min.js"></script>
		<!-- 再导layer.jsp -->
		<script type="text/javascript" src="${pageContext.request.contextPath }/layer/layer.js"></script>
	<script type="text/javascript">
	function getDetail(oid) {
		//方式Ajax请求
		$.getJSON("${pageContext.request.contextPath}/adminOrderServlet", {
			"method" : "findByOid",
			"oid" : oid
		}, function(result) {

			//console.log("result="+result);
		 	var s = "<table border='1px' width='100%' height='100%'><tr><td>商品图片</td><td>商品名称</td><td>购买数量</td></tr>";
			$(result).each(function(i,obj) {
				s += "<tr><td><img width='100px' height='90px' src='${ pageContext.request.contextPath }/"+obj.product.pimage+"'/></td><td>"+obj.product.pname+"</td><td>"+obj.count+"</td></tr>";
			});
			s += "</table>"
			layer.open({
				type : 1,//0:信息框; 1:页面; 2:iframe层;	3:加载层;	4:tip层
				title : "商品详情!",//标题
				area : [ '600px', '400px' ],//大小
				shadeClose : true, //点击弹层外区域 遮罩关闭
				content : s//内容
			});
 
		});
	}
</script>
	
	<body>
		<br>
		<form id="Form1" name="Form1" action="${pageContext.request.contextPath}/user/list.jsp" method="post">
			<table cellSpacing="1" cellPadding="0" width="100%" align="center" bgColor="#f5fafe" border="0">
				<TBODY>
					<tr>
						<td class="ta_01" align="center" bgColor="#afd1f3">
							<strong>订单列表</strong>
						</TD>
					</tr>
					
					<tr>
						<td class="ta_01" align="center" bgColor="#f5fafe">
							<table cellspacing="0" cellpadding="1" rules="all"
								bordercolor="gray" border="1" id="DataGrid1"
								style="BORDER-RIGHT: gray 1px solid; BORDER-TOP: gray 1px solid; BORDER-LEFT: gray 1px solid; WIDTH: 100%; WORD-BREAK: break-all; BORDER-BOTTOM: gray 1px solid; BORDER-COLLAPSE: collapse; BACKGROUND-COLOR: #f5fafe; WORD-WRAP: break-word">
								<tr
									style="FONT-WEIGHT: bold; FONT-SIZE: 12pt; HEIGHT: 25px; BACKGROUND-COLOR: #afd1f3">

									<td align="center" width="10%">
										序号
									</td>
									<td align="center" width="10%">
										订单编号
									</td>
									<td align="center" width="10%">
										订单金额
									</td>
									<td align="center" width="10%">
										收货人
									</td>
									<td align="center" width="10%">
										订单状态
									</td>
									<td align="center" width="50%">
										订单详情
									</td>
								</tr>
								<c:forEach items="${list }" var="o" varStatus="vs">
										<tr>
											<td style="CURSOR: hand; HEIGHT: 22px" align="center"
												width="18%">
												${vs.count }
											</td>
											<td style="CURSOR: hand; HEIGHT: 22px" align="center"
												width="17%">
												${o.oid }
											</td>
											<td style="CURSOR: hand; HEIGHT: 22px" align="center"
												width="17%">
													${o.total }
											</td>
											<td style="CURSOR: hand; HEIGHT: 22px" align="center"
												width="17%">
												${o.name }
											</td>
											<td style="CURSOR: hand; HEIGHT: 22px" align="center"
												width="17%">
												<c:if test="${o.state == 0}">
													未付款
												</c:if>
												<c:if test="${o.state == 1 }">
													<a href="${pageContext.request.contextPath }/adminOrderServlet?method=updateState&oid=${o.oid}">去发货</a>
												</c:if>
												<c:if test="${o.state == 2 }">
													等待确认收货
												</c:if>
												<c:if test="${o.state== 3 }">
													订单完成
												</c:if>
											
											</td>
											<td align="center" style="HEIGHT: 22px">
												<input type="button" value="订单详情"  onclick="getDetail('${o.oid}')"/>
												<div id="divId">
													
												</div>
											</td>
							
										</tr>
									</c:forEach>
							</table>
						</td>
					</tr>
					<tr align="center">
						<td colspan="7">
							第<s:property value="pageBean.page"/>/<s:property value="pageBean.totalPage"/>页 
							<s:if test="pageBean.page != 1">
								<a href="${ pageContext.request.contextPath }/adminOrder_findAll.action?page=1">首页</a>|
								<a href="${ pageContext.request.contextPath }/adminOrder_findAll.action?page=<s:property value="pageBean.page-1"/>">上一页</a>|
							</s:if>
							<s:if test="pageBean.page != pageBean.totalPage">
								<a href="${ pageContext.request.contextPath }/adminOrder_findAll.action?page=<s:property value="pageBean.page+1"/>">下一页</a>|
								<a href="${ pageContext.request.contextPath }/adminOrder_findAll.action?page=<s:property value="pageBean.totalPage"/>">尾页</a>|
							</s:if>
						</td>
					</tr>
				</TBODY>
			</table>
		</form>
	</body>
</HTML>

