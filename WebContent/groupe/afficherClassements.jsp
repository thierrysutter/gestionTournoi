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
			$("#retour").button().click(function(e) {
				e.preventDefault();
				document.location = "tournoi.lister.do";
			});
			
			$("#continuer").button().click(function(e) {
				e.preventDefault();
				document.location = "tournoi.suivant.do?codeTournoi="+$("#codeTournoi").val();
			});
		});
	</script>
  </head>
  
  <body>
  	<jsp:include page="../commun/menu.jsp" flush="true">
	<jsp:param name="onglet"   value=""/>
    <jsp:param name="menu"     value="TOURNOI"/>
    <jsp:param name="info"     value="Classements des groupes"/>
	</jsp:include>
	<div align="center">
	  <!-- Entete du tournoi -->
	  <input type="hidden" id="codeTournoi" name="codeTournoi" value="${tournoi.id}"/>
	  <div id="titre" style="margin-top: 10px;font-size: 40px; font-style: italic; font-weight: bold;">
		${tournoi.libelle}
		<br/>
		<fmt:formatDate value="${tournoi.dateTournoi}" pattern="dd/MM/yyyy" />
	  </div>
	  
	  <!-- Tableau des classements des groupes du tournoi -->
	  <div id="tableauClassements" style="margin-top: 20px;">
		<!-- Maximum 2 par ligne -->
	  <table>
		<tr>
		  <c:forEach items="${tournoi.listeGroupes}" var="groupeTournoi" varStatus="numGroupe">
		  <c:if test="${numGroupe.count % 5 == 0}">
		  </tr><tr>
		  </c:if>
		  <td>
		  <table class="CSSTableGenerator" style="margin-top: 20px; width: 290px; border: 1px; border-color: black; border-style: solid; border-collapse: collapse;">
		  	<tr>
			  <th style="border: 1px; border-color: black; border-style: solid;" colspan="6">${groupeTournoi.libelle}</th>
		  	</tr>
		  	<tr>
			  <th style="border: 1px; border-color: black; border-style: solid;">&nbsp;</th>
			  <th style="border: 1px; border-color: black; border-style: solid;">Equipe</th>
			  <th style="border: 1px; border-color: black; border-style: solid;">Pts</th>
			  <th style="border: 1px; border-color: black; border-style: solid;">Bp</th>
			  <th style="border: 1px; border-color: black; border-style: solid;">Bc</th>
			  <th style="border: 1px; border-color: black; border-style: solid;">Diff</th>
		  	</tr>
		  	<c:choose>
		  	<c:when test="${not empty groupeTournoi.classement}">
		  	<c:forEach items="${groupeTournoi.classement}" var="equipeGroupe" varStatus="compteur">
		  	<tr style="height: 40px;">
		  	  <td>${compteur.count}</td>
		  	  <td style="font-size: 10px;">${equipeGroupe.libelle != '' ? fn:substring(equipeGroupe.libelle, 0, 35) : ''}</td>
		  	  <td>${equipeGroupe.nbPoints}</td>
		  	  <td>${equipeGroupe.nbButsPour}</td>
		  	  <td>${equipeGroupe.nbButsContre}</td>
		  	  <td>${equipeGroupe.nbButsPour - equipeGroupe.nbButsContre}</td>
		  	</tr>
		  	</c:forEach>
		  	</c:when>
		  	<c:otherwise>
		  	<tr><td>aucune équipe</td></tr>
		  	</c:otherwise>
		  	</c:choose>
		  </table>
		  </td>
		  </c:forEach>
		</tr>
	  </table>
	  </div>
	  
	  <div id="tableauBoutons" style="margin-top: 20px;">
		<input type="button" class="button" id="continuer" value="Continuer">
		<input type="button" class="button" id="retour" value="Retour à la liste des tournois">
	  </div>
	</div>
	<br/><br/>
  </body>
</html>