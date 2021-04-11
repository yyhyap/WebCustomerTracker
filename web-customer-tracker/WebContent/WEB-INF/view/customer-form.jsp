<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
<head>
<title>Save Customer</title>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css"/>
<link type="text/css" rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/add-customer-style.css"/>
<style>.error {color:red}</style>
</head>

<body>
	<div id="wrapper">
		<div id="header">
			<h2>CRM - Customer Relationship Manager</h2>
		</div>
	</div>
	
	<div id="container">
		<h3>Save Customer</h3>
		
		<!-- modelAttribute="customer" is mapping with model.addAttribute("customer", theCustomer); -->
		<!-- in showFormForAdd method in CustomerConttroller -->
		<!-- OR in showFormForUpdate method in CustomerConttroller -->
		<form:form action="saveCustomer" modelAttribute="customer" method="POST">
		
			<!-- associate this data with customer id -->
			<!-- since GETTER methods are called when form loaded -->
			<!-- and SETTER methods are called when form submitted -->
			<!-- this LIINE is important to TRACK the customer -->
			<!-- so back end KNOWS which CUSTOMER to UPDATE -->
			<form:hidden path="id" />
			
			<table>
				<tbody>
					<tr>
						<td><label>First Name: </label></td>
						<td><form:input path="firstName"/></td>
						<td><form:errors path="firstName" cssClass="error"/></td>
					</tr>
					<tr>
						<td><label>Last Name: </label></td>
						<td><form:input path="lastName"/></td>
						<td><form:errors path="lastName" cssClass="error"/></td>
					</tr>
					<tr>
						<td><label>Email: </label></td>
						<td><form:input path="email"/></td>
						<td><form:errors path="email" cssClass="error"/></td>
					</tr>
					<tr>
						<td><label></label></td>
						<td><input type="submit" value="Save" class="save"/></td>
					</tr>
				</tbody>
			</table>
		
		</form:form>
		
		<div style="clear; both;"></div>
		
		<p>
			<a href="${pageContext.request.contextPath}/customer/list">Back to Customer List</a>
		</p>
		
	</div>
</body>

</html>