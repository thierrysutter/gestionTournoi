<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://struts.apache.org/tags-html" prefix="html" %>
<html>
  <head>
	<link rel="stylesheet" type="text/css" href="css/gestionTournoi.css" />
	<link rel="stylesheet" type="text/css" href="css/menu.css" />
	<link rel="stylesheet" type="text/css" href="css/jquery-ui.css" />
	
	<script type="text/javascript" src="js/jquery/jquery-1.9.1.js"></script>
	<script type="text/javascript" src="js/jquery/jquery-ui.js"></script>
	
	<script type="text/javascript">
		$(document).ready(function(){
			$("#manuel").button().click(function(e) {
				e.preventDefault();
				
			});
			
			$("#automatique").button().click(function(e) {
				e.preventDefault();
				document.form1.submit();
			});
			
			$("#retour").button().click(function(e) {
				e.preventDefault();
				document.location = "tournoi.lister.do";
			});
			
		});
	</script>
  </head>
  <body>
  	<jsp:include page="../commun/menu.jsp" flush="true">
	<jsp:param name="onglet"   value=""/>
    <jsp:param name="menu"     value="TOURNOI"/>
    <jsp:param name="info"     value="Répartition des équipes dans les groupes"/>
	</jsp:include>
	<div align="center">
	
	<form id="form1" name="form1"  action="groupe.repartir.do" method="get">
	  <input type="hidden" id="codeTournoi" name="codeTournoi" value="${tournoi.id}" />
	</form>
	
	<div id="tableauEquipe">
	
	<table style="text-align: center;" >
	  <tr>
		<th style="font-weight: bold;">Equipes inscrites</th>
		<!-- <th style="width: 100px;"></th>
		<th style="font-weight: bold;">Groupes</th> -->
	  </tr>
	  <tr>
		<td>
		  <!-- Affichage de la liste des équipes inscrites -->
		  <c:if test="${not empty equipesTournoi}">
			<c:forEach items="${equipesTournoi}" var="equipeTournoi">
		  	<span>${equipeTournoi.libelle}<br/></span>
		  	</c:forEach>
		  </c:if>
		</td>
		<!--<td>
		  <!-- vide ->
		</td>-->
		<!--
		<td>
		  <!-- Affichage de la liste des groupes disponibles ->
		  <c:if test="${not empty groupesTournoi}">
			<c:forEach items="${groupesTournoi}" var="groupeTournoi">
		  	<span>${groupeTournoi.libelle}<br/></span>
		  	</c:forEach>
		  </c:if>
		</td>-->
	  </table>
	  </div>
	  
	  <div id="tableauBoutons" style="margin-top: 20px;">
	  <input type="button" class="button" id="manuel" value="Tirage au sort manuel">
	  <input type="button" class="button" id="automatique" value="Tirage au sort automatique">
	  <input type="button" class="button" id="retour" value="Annuler">
	  </div>
	  
	  <div id="tableauGroupes" style="margin-top: 20px;">
	  <c:if test="${not empty groupesTournoi}">
		<c:forEach items="${groupesTournoi}" var="groupeTournoi">
		<table>
	  	<tr><th>${groupeTournoi.libelle}</th></tr>
	  	<c:choose>
	  	<c:when test="${not empty groupeTournoi.listeEquipes}">
	  	<c:forEach items="${groupeTournoi.listeEquipes}" var="equipeGroupe">
	  	<tr><td>${equipeGroupe.libelle}</td></tr>
	  	</c:forEach>
	  	</c:when>
	  	<c:otherwise>
	  	<tr><td>aucune équipe</td></tr>
	  	</c:otherwise>
	  	</c:choose>
	  	</table>
	  	</c:forEach>
	  </c:if>
	  </div>
	  
	</div>
  </body>
</html>