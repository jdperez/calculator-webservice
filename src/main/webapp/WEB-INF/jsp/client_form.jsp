<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
        "http://www.w3.org/TR/html4/loose.dtd">
<html>
<% %>
<head>
<style type="text/css">
label {width:150px;float:left;}
</style>
<title>Calculator</title>
</head>

<body>
<H2>Simple Calculator</H2>

<form action="http://localhost:8080/project1-1.0/foo/" method="post">
<div class=”field_container”><label>First operand</label><input type=”text” name="firstOperand"></div>
<div class=”field_container”><label>Second operand</label><input type="text" name="secondOperand"></div> <br />
<input type="submit" name="addButton" value="ADD">
<input type="submit" name="subtractButton" value="SUBTRACT">
<input type="submit" name="multiplyButton"  value="MULTIPLY">
<input type="submit" name="divideButton" value="DIVIDE">
</form>

</body>
</html>