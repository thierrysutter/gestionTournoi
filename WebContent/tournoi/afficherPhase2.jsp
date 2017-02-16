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
				//document.location = "tournoi.suivant.do?codeTournoi="+$("#codeTournoi").val();
			});
			
			$(".saveAll").click(function(e) {
				e.preventDefault();
				document.formMatchs.saveAll.value = 1;
				document.formMatchs.submit();
			});
			
			$(".save").click(function(e) {
				e.preventDefault();
				document.formMatchs.saveAll.value = 0;
				document.formMatchs.idMatch.value = $(this).prop('id').split('_')[1];
				document.formMatchs.submit();
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
	  
	  <!-- Tableau des matchs du tournoi -->
	<div id="tableauMatchs" style="margin-top: 20px;">
	<!-- Maximum 2 par ligne -->
	<form id="formMatchs" name="formMatchs" action="match.enregistrer.do" method="get">
	  <input type="hidden" id="codeTournoi" name="codeTournoi" value="${tournoi.id}" />
	  <input type="hidden" id="saveAll" name="saveAll" value="" />
	  <input type="hidden" id="idMatch" name="idMatch" value="" />
	  <!-- <input type="hidden" id="statutMatchs" name="statutMatchs" value="${statutMatchs}" /> -->
	
	  <c:if test="${not empty rencontresFinales}">
	  <table>
	  <tr>
		  <th style="font-weight: bold;">1/${fn:length(rencontresFinales)} de finale tournoi</th>
		</tr>
	  <tr>
	  <td>
	  <table class="CSSTableGenerator" style="width: 451px; border: 1px; border-color: black; border-style: solid; border-collapse: collapse;">
		<tr style="border-color: black; border: 1px; border-style: solid;">
		  <td colspan="2" style="font-weight: bold; text-align:center;">Heure</td>
		  <td colspan="3" style="font-weight: bold; text-align:center;">Rencontre</td>
		  <td colspan="3" style="font-weight: bold; text-align:center;">Score</td>
		  <td style="font-weight: bold; text-align:center;">
		  <!-- Boutons pour enregistrer tous les résultats -->
		  <img class="saveAll" id="imgSaveAll" src="images/save32x32.png" style="border-width: 0px; " title="Sauvegarder tous les résultats"/>
		  </td>
		</tr>
		<c:forEach items="${rencontresFinales}" var="matchTournoi">
		<tr>
		  <td style="width:50px; text-align:center; padding-left:5px; padding-right: 5px; ">${matchTournoi.horaire}</td>
		  <td style="width:25px; text-align:center; padding-left:5px; padding-right: 5px; "><!--${matchTournoi.tour}-->1/${fn:length(rencontresFinales)}</td>
		  <td style="width:125px; padding-left:5px;" nowrap>${matchTournoi.equipeLocale.libelle}</td>
		  <td style="width:3px; text-align:center;">-</td>
		  <td style="width:125px; padding-right: 5px;" nowrap>${matchTournoi.equipeVisiteur.libelle}</td>
		  <td style="width:25px; padding-left:5px;">
		  <input type="hidden" class="statutMatch" name="statutMatch_${matchTournoi.id}" id="statutMatch_${matchTournoi.id}" value="${(matchTournoi.statut)}"/>
		  <c:choose>
		  <c:when test="${tournoi.statut lt 4}">
		  ${(matchTournoi.statut gt 1) ? matchTournoi.scoreLocal : ''}
		  </c:when>
		  <c:otherwise>
		  <input type="text" size="1" maxlength="2" min="0" class="match" name="match_${matchTournoi.id}_loc" id="match_${matchTournoi.id}_loc" value="${(matchTournoi.statut gt 1) ? matchTournoi.scoreLocal : ''}"/>
		  <br/>
		  <input type="text" size="1" maxlength="2" min="0" class="match" name="tab_match_${matchTournoi.id}_loc" id="tab_match_${matchTournoi.id}_loc" value=""/>
		  </c:otherwise>
		  </c:choose>
		  </td>
		  <td style="width:3px; text-align:center;">-</td>
		  <td style="width:25px; padding-right: 5px;">
		  <c:choose>
		  <c:when test="${tournoi.statut lt 4}">
		  ${(matchTournoi.statut gt 1) ? matchTournoi.scoreVisiteur : ''}
		  </c:when>
		  <c:otherwise>
		  <input type="text" size="1" maxlength="2" min="0" class="match" name="match_${matchTournoi.id}_vis" id="match_${matchTournoi.id}_vis" value="${(matchTournoi.statut gt 1) ? matchTournoi.scoreVisiteur : ''}"/>
		  <br/>
		  <input type="text" size="1" maxlength="2" min="0" class="match" name="tab_match_${matchTournoi.id}_vis" id="tab_match_${matchTournoi.id}_vis" value=""/>
		  </c:otherwise>
		  </c:choose>
		  </td>
		  
		  <!-- icone pour enregistrement du résultat du match associé -->
		  <td><img class="save" id="imgSave_${matchTournoi.id}" src="images/save32.png" style="border-width: 0px; " title="Sauvegarder le résultat"/></td>
		  
		</tr>
		</c:forEach>
	  </table>
	  </td></tr>
	  </table>
	  </c:if>
	  
	  
	  <c:if test="${not empty rencontresConsolante}">
	  <div style="margin-top: 20px;">
	  <table>
	  <tr>
		  <th style="font-weight: bold;">1/${fn:length(rencontresConsolante)} de finale consolante</th>
		</tr>
	  <tr>
	  <td>
	  <table class="CSSTableGenerator" style="width: 451px; border: 1px; border-color: black; border-style: solid; border-collapse: collapse;">
		<tr style="border-color: black; border: 1px; border-style: solid;">
		  <td colspan="2" style="font-weight: bold; text-align:center;">Heure</td>
		  <td colspan="3" style="font-weight: bold; text-align:center;">Rencontre</td>
		  <td colspan="3" style="font-weight: bold; text-align:center;">Score</td>
		  <td style="font-weight: bold; text-align:center;">
		  <!-- Boutons pour enregistrer tous les résultats -->
		  <img class="saveAll" id="imgSaveAll" src="images/save32x32.png" style="border-width: 0px; " title="Sauvegarder tous les résultats"/>
		  </td>
		</tr>
		<c:forEach items="${rencontresConsolante}" var="matchTournoi">
		<tr>
		  <td style="width:50px; text-align:center; padding-left:5px; padding-right: 5px; ">${matchTournoi.horaire}</td>
		  <td style="width:25px; text-align:center; padding-left:5px; padding-right: 5px; "><!--${matchTournoi.tour}-->1/${fn:length(rencontresConsolante)}</td>
		  <td style="width:125px; padding-left:5px;" nowrap>${matchTournoi.equipeLocale.libelle}</td>
		  <td style="width:3px; text-align:center;">-</td>
		  <td style="width:125px; padding-right: 5px;" nowrap>${matchTournoi.equipeVisiteur.libelle}</td>
		  <td style="width:25px; padding-left:5px;">
		  <input type="hidden" class="statutMatch" name="statutMatch_${matchTournoi.id}" id="statutMatch_${matchTournoi.id}" value="${(matchTournoi.statut)}"/>
		  <c:choose>
		  <c:when test="${tournoi.statut lt 4}">
		  ${(matchTournoi.statut gt 1) ? matchTournoi.scoreLocal : ''}
		  </c:when>
		  <c:otherwise>
		  <input type="text" size="1" maxlength="2" min="0" class="match" name="match_${matchTournoi.id}_loc" id="match_${matchTournoi.id}_loc" value="${(matchTournoi.statut gt 1) ? matchTournoi.scoreLocal : ''}"/>
		  </c:otherwise>
		  </c:choose>
		  </td>
		  <td style="width:3px; text-align:center;">-</td>
		  <td style="width:25px; padding-right: 5px;">
		  <c:choose>
		  <c:when test="${tournoi.statut lt 4}">
		  ${(matchTournoi.statut gt 1) ? matchTournoi.scoreVisiteur : ''}
		  </c:when>
		  <c:otherwise>
		  <input type="text" size="1" maxlength="2" min="0" class="match" name="match_${matchTournoi.id}_vis" id="match_${matchTournoi.id}_vis" value="${(matchTournoi.statut gt 1) ? matchTournoi.scoreVisiteur : ''}"/>
		  </c:otherwise>
		  </c:choose>
		  </td>
		  
		  <!-- icone pour enregistrement du résultat du match associé -->
		  <td><img class="save" id="imgSave_${matchTournoi.id}" src="images/save32.png" style="border-width: 0px; " title="Sauvegarder le résultat"/></td>
		  
		</tr>
		</c:forEach>
	  </table>
	  </td></tr>
	  </table>
	  </div>
	  </c:if>
	  </form>
	  </div>
	  
	  
	  <div id="tableauBoutons" style="margin-top: 20px;">
		<input type="button" class="button" id="continuer" value="Continuer">
		<input type="button" class="button" id="retour" value="Retour à la liste des tournois">
	  </div>
	</div>
	<br/><br/>
  </body>
</html>