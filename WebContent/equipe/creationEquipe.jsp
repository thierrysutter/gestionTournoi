<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
    <jsp:param name="menu" value="EQUIPES"/>
    <jsp:param name="info" value="Création d'une équipe"/>
	</jsp:include>
	
	<div>
		<c:if test="${not empty message}">
		<span style="color: green; font-weight: bold;">${message}</span>
		</c:if>
		
		<form id="form1" name="form1" action="equipes.ajouter.do" method="post" style="" >
			<input type="hidden" id="sub" name="sub" value="1"/>
			<table class="tftable">
				<tr>
					<td>Nom</td>
					<td style="text-align: left;"><input type="text" name="nom" id="nom" placeholder="Nom du club" required size="32"/></td>
				</tr>
				<tr>
					<td>Club</td>
					<td style="text-align: left;">
						<select name="club" id="club">
							<c:forEach items="${listeClubs}" var="club">
								<option value="${club.id}">${fn:escapeXml(club.nom)}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td>Catégorie</td>
					<td style="text-align: left;">
						<select name="categorie" id="categorie" >
							<c:forEach items="${listeCategories}" var="categorie">
								<option value="${categorie.id}">${fn:escapeXml(categorie.libelle)}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
			</table>
			<br/>
			<input type="button" class="button" id="btnValider" name="btnValider" value="Valider" size="25"/>
		</form>
		
	</div>
  </body>
</html>