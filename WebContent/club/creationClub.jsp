<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
  <head>
  	<link rel="stylesheet" href="css/gestionTournoi.css" type="text/css" />
	<link rel="stylesheet" href="css/menu.css" type="text/css" />
	<link rel="stylesheet" href="css/jquery-ui.css" type="text/css" />
	
	<script type="text/javascript" src="js/jquery/jquery-1.9.1.js"></script>
	<script type="text/javascript" src="js/jquery/jquery-ui.js"></script>
	<script type="text/javascript" src="js/jquery/jquery.ui.datepicker-fr.js"></script>
	
	<script type="text/javascript">
		$(document).ready(function(){
			
			var theHREF = "";
			
			$("#btnValider").button().click(function(e) {
				e.preventDefault();
				$("#form1").submit();
			});
		});
	</script>
  </head>
  <body>
  	<jsp:include page="../commun/menu.jsp" flush="true">
	<jsp:param name="onglet" value=""/>
    <jsp:param name="menu" value="CLUBS"/>
    <jsp:param name="info" value="Création d'un club"/>
	</jsp:include>
	
	<div>
		<c:if test="${not empty message}">
		<span style="color: green; font-weight: bold;">${message}</span>
		</c:if>
		
		<form id="form1" name="form1" action="clubs.creer.do" method="post" style="" >
			<input type="hidden" id="sub" name="sub" value="1"/>
			<table class="tftable">
				<tr>
					<td>Nom</td>
					<td style="text-align: left;"><input type="text" name="nom" id="nom" placeholder="Nom du club" required size="20"/></td>
				</tr>
				<tr>
					<td>Ligue</td>
					<td style="text-align: left;"><input type="text" name="ligue" id="ligue" placeholder="Ligue" size="20"/></td>
				</tr>
				<tr>
					<td>District</td>
					<td style="text-align: left;"><input type="text" name="district" id="district" placeholder="District" size="20"/></td>
				</tr>
				<tr>
					<td>N° d'affiliation</td>
					<td style="text-align: left;"><input type="text" name="numAffiliation" id="numAffiliation" placeholder="N° d'affiliation" maxlength="6" size="8"/></td>
				</tr>
				<tr>
					<td>Adresse</td>
					<td style="text-align: left;"><input type="text" name="adresse" id="adresse" placeholder="Adresse" size="40"/></td>
				</tr>
				<tr>
					<td>Code postal</td>
					<td style="text-align: left;"><input type="text" name="codePostal" id="codePostal" placeholder="Code postal" size="8"/></td>
				</tr>
				<tr>
					<td>Ville</td>
					<td style="text-align: left;"><input type="text" name="ville" id="ville" placeholder="Ville" size="20"/></td>
				</tr>
				<tr>
					<td>Pays</td>
					<td style="text-align: left;"><input type="text" name="pays" id="pays" placeholder="Pays" required size="20"/></td>
				</tr>
				<tr>
					<td>N° téléphone</td>
					<td style="text-align: left;"><input type="text" name="tel1" id="tel1" placeholder="N° téléphone" size="10"/></td>
				</tr>
				<tr>
					<td>N° fax</td>
					<td style="text-align: left;"><input type="text" name="fax1" id="fax1" placeholder="N° fax" size="10"/></td>
				</tr>
				<tr>
					<td>Email</td>
					<td style="text-align: left;"><input type="text" name="email1" id="email1" placeholder="Email" size="40"/></td>
				</tr>
				<tr>
					<td>Site web</td>
					<td style="text-align: left;"><input type="text" name="siteWeb" id="siteWeb" placeholder="Site web" size="40"/></td>
				</tr>
			</table>
			<br/>
			<input type="button" class="button" id="btnValider" name="btnValider" value="Valider" size="25"/>
		</form>
		
	</div>
  </body>
</html>