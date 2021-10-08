<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core" %>
<%@page import="java.util.*"%>
<%@page import="com.banking.bean.AccountTransactions"%>
<html>
<head>
<title>Display Statement</title>
<script type="text/javascript">
	function validateDisplayForm(){

		var start_date	= document.getElementById("start_date");
		var end_date	= document.getElementById("end_date");

		var start_ln	= start_date.value.length;
		var end_ln		= end_date.value.length;
		var pattern		= /^([0-9]{2})\/([0-9]{2})\/([0-9]{2})$/;

		if (start_ln == 0) {

			alert("From Date should not be empty!!");
			start_date.focus();
			return false;

		} else if (end_ln == 0) {

			alert("To Date should not be empty!!");
			end_date.focus();
			return false;
		}

		if (!pattern.test(start_date.value)) {

			alert("Input a Valid From Date [mm/dd/yy format]");
			start_date.focus();
			return false;

		} else if (!pattern.test(end_date.value)) {

			alert("Input a Valid To Date [mm/dd/yy format]");
			end_date.focus();
			return false;
		}

		return true;
	}
	</script>
</head>
<body bgcolor="33FFB2">
	<div id="container" align="center"><br/>
		<h2>Display Statement</h2>
	</div>
	<form name="displayForm" action="display" onsubmit="javascript:return validateDisplayForm();" method="post">
		<table border="0">
			<tr><td colspan="5"><b>Date&nbsp;Range:</b></td></tr>
			<tr>
				<td><b>From&nbsp;:&nbsp;</b><input type="text" name="start_date" id="start_date" align="left" size="10" maxlength="8" />*</td>
				<td>&nbsp;</td>
				<td><b>To&nbsp;:&nbsp;</b><input type="text" name="end_date" id="end_date" align="left" size="10" maxlength="8" />*</td>
				<td>&nbsp;</td>
				<td><input type="submit" value="Display" align="left"/></td>
			</tr>
			<tr><td colspan="5">&nbsp;</td></tr>
		</table>
	</form>
	<%
	String emptylist = (String)request.getAttribute("emptylist");

	if (emptylist == "yes") {
	%>
		<p style="color:red;font-size:20px;text-align:left">There are no transactions for the selected date range!!</p><br/>

	<% } else if (emptylist == "no") { %>
	<div id="txn_container" align="left">
		<table border="1" align="left">
			<tr>
				<td align="center"><b>S.No</b></td>
				<td align="center"><b>Date</b></td>
				<td align="center"><b>Description</b></td>
				<td align="center"><b>Cheque No.</b></td>
				<td align="center"><b>Withdraw</b></td>
				<td align="center"><b>Deposit</b></td>
				<td align="center"><b>Available Balance</b></td>
			</tr>
			<c:forEach items="${transactions}" var="transaction" varStatus="loop">
				<tr>
					<td align="center"><c:out value="${loop.count}" /></td>
					<td align="center"><c:out value="${transaction.getTransactionDate()}" /></td>
					<td align="center"><c:out value="${transaction.getTransactionComments()}" /></td>
					<c:choose>
						<c:when test="${transaction.getChequeNo() ne 0}">
							<td align="center"><c:out value="${transaction.getChequeNo()}" /></td>
						</c:when>
						<c:otherwise>
							<td align="center">&nbsp;</td>
						</c:otherwise>
					</c:choose>
					<c:choose>
						<c:when test="${transaction.getTransactionType() ne 'credit'}">
							<td align="center"><c:out value="${transaction.getTransactionAmount()}" /></td>
						</c:when>
						<c:otherwise>
							<td align="center">&nbsp;</td>
						</c:otherwise>
					</c:choose>
					<c:choose>
						<c:when test="${transaction.getTransactionType() ne 'debit'}">
							<td align="center"><c:out value="${transaction.getTransactionAmount()}" /></td>
						</c:when>
						<c:otherwise>
							<td align="center">&nbsp;</td>
						</c:otherwise>
					</c:choose>
					<td align="center"><c:out value="${transaction.getTransactionBalanceAmount()}" /></td>
				</tr>
			</c:forEach>
		</table>
	</div>
	<% } %>
</body>
</html>