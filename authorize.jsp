<html>
<head>
	<title>Authorize Credit Card Transactions</title>
	<script type="text/javascript">
	function validateAuthorizeForm(){

		var card_number	= document.getElementById("card_number");
		var card_cvv	= document.getElementById("card_cvv");
		var txn_amount	= document.getElementById("txn_amount");

		var num_ln = card_number.value.length;
		var cvv_ln = card_cvv.value.length;
		var txn_ln = txn_amount.value.length;

		if (num_ln == 0) {

			alert("Card Number should not be empty!!");  
			card_number.focus();  
			return false;

		} else if (isNaN(card_number.value) || num_ln != 16) {

			alert("Card Number should be 16 digit number!!");  
			card_number.focus();  
			return false;
		}

		if (cvv_ln == 0) {

			alert("CVV should not be empty!!");  
			card_cvv.focus();  
			return false;

		} else if (isNaN(card_cvv.value) || cvv_ln != 3) {

			alert("CVV should be 3 digit number!!");  
			card_cvv.focus();  
			return false;
		}

		if (txn_ln == 0) {

			alert("Amount should not be empty!!");  
			txn_amount.focus();  
			return false;

		} else if (isNaN(txn_amount.value)) {

			alert("Amount should be numeric!!");  
			txn_amount.focus();  
			return false;
		}

		return true;
	}
	</script>
</head>
<body bgcolor="33FFB2">
	<div id="container" align="center"><br/>
	<h2>Authorize Credit Card Transactions</h2>
		<form name="authorizeForm" action="authorize" onsubmit="javascript:return validateAuthorizeForm();" method="post">
			<%
			String action  = (String)request.getAttribute("action");
			String message = (String)request.getAttribute("message");

			if (action == "yes") {
			%>
				<p style="color:red;font-size:20px;text-align:left"><%=message%></p><br/>
			<% } %>

			<table border="0" align="left">
				<tr><td><b>Name</b></td><td>:</td><td><input type="text" name="card_name" id="card_name" align="left" /></td></tr>
				<tr><td><b>Card Number</b></td><td>:</td><td><input type="text" name="card_number" id="card_number" align="left" />*</td></tr>
				<tr><td><b>CVV</b></td><td>:</td><td><input type="text" name="card_cvv" id="card_cvv" align="left" />*</td></tr>
				<tr><td><b>Amount</b></td><td>:</td><td><input type="text" name="txn_amount" id="txn_amount" align="left" />*</td></tr>
				<tr><td colspan="3">&nbsp;</td></tr>
				<tr><td colspan="3" align="left"><input type="submit" value="Authorize" align="left"/></td></tr>
			</table>
		</form>
	</div>
</body>
</html>