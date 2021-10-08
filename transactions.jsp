<html>
<head>
	<title>Transactions</title>
	<script type="text/javascript">
	function submitTransactionForm(formId){
		
		var acct_num	= document.getElementById("acct_num");
		var acct_amt	= document.getElementById("acct_amt");

		var anum_val	= acct_num.value;
		var amnt_val	= acct_amt.value;

		if (anum_val.length == 0 || anum_val.length < 10 || anum_val.length > 10 || isNaN(anum_val)) {

			alert("Account Number should not be empty / size should be 10 digits only!!");  
			acct_num.focus();  
			return false;

		} else if (isNaN(amnt_val) || amnt_val == 0) {

			alert("Amount should be numeric / should be greater than zero!!");
			acct_amt.focus();  
			return false;

		} else {

			document.getElementById("actn_type").value = formId;
			document.getElementById("transactionForm").action = "transaction";
			document.getElementById("transactionForm").submit();
		}
	}
	</script>
</head>
<body bgcolor="33FFB2">
<%=session.getAttribute("user_id")%>&nbsp;&nbsp;&nbsp;<%=session.getAttribute("acct_uid")%>
	<div id="container" align="center"><br/>
	<h2>Perform Debit or Credit Operation</h2>
		<form name="transactionForm" id="transactionForm" method="post">
			<input type="hidden" name="actn_type" id="actn_type" value="" />
			<%
			String message	= (String)request.getAttribute("message");
			if (message != "") {
			%>
				<p style="color:red;font-size:20px;text-align:left"><%=message%></p><br/>
			<% } %>
			<table border="0" align="left">
				<tr><td><b>Account&nbsp;Number</b></td><td>:</td><td><input type="text" name="acct_num" id="acct_num" align="left" maxlength="10" />*</td></tr>
				<tr><td><b>Amount</b></td><td>:</td><td><input type="text" name="acct_amt" id="acct_amt" align="left" maxlength="7" />*</td></tr>
				<tr><td valign="top"><b>Comments</b></td><td valign="top">:</td><td><textarea name="acct_cmts" id="acct_cmts" align="left" rows="7" cols="40"></textarea></td></tr>
				<tr><td colspan="3">&nbsp;</td></tr>
				<tr><td colspan="2">&nbsp;</td><td align="left">
					<input type="button" id="debit" value="Debit From" align="left" onClick="javascript:submitTransactionForm(this.id);"/>&nbsp;&nbsp;&nbsp;
					<input type="button" id="credit" value="Credit To" align="left" onClick="javascript:submitTransactionForm(this.id);"/>
				</td></tr>
			</table>
		</form>
	</div>
</body>
</html>