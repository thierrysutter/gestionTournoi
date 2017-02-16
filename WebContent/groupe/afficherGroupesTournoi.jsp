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
			
			$( "#dialog-confirm" ).dialog({
		        resizable: false,
		        height:160,
		        width:500,
		        autoOpen: false,
		        modal: true,
		        buttons: {
		            "Oui": function() {
		                $(this).dialog("close");
		                window.location.href = $(this).find("#url").val();
		            },
		            "Non": function() {
		                $( this ).dialog( "close" );
		            }
		         }
		    });
			
			$("#groupeAutomatique").button().click(function(e) {
				e.preventDefault();
				var submit = true;
				if ($("#statutTournoi").val() >= 3) {
					if (!confirm("Voulez vous vraiment relancer un tirage au sort des groupes ?")) {
						submit = false;
					}
				}
				if (submit) {
					document.form1.action="groupe.repartir.do";
					document.form1.submit();
				}
			});
			
			$("#matchAutomatique").button().click(function(e) {
				e.preventDefault();
				document.form1.action="match.generer.do";
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
    <jsp:param name="info"     value="Affichage du tournoi"/>
	</jsp:include>
	<div align="center">
	
		<div id="messageOK" style="width: 95%; color: green; font-weight: bold; text-align: center; margin-top: 10px; display: ${not empty messageOK ? '' : 'none'}">
		  ${messageOK}
		</div>
		
		<div id="messageKO" style="width: 95%; color: red; font-weight: bold; text-align: center; margin-top: 10px; display: ${not empty messageKO ? '' : 'none'}">
		  ${messageKO}
		</div>
		
		<form id="form1" name="form1" action="groupe.repartir.do" method="get">
		  <input type="hidden" id="codeTournoi" name="codeTournoi" value="${tournoi.id}" />
		  <input type="hidden" id="statutTournoi" name="statutTournoi" value="${tournoi.statut}" />
		</form>
		
		<!-- Entete du tournoi -->
		<div id="titre" style="margin-top: 10px;font-size: 40px; font-style: italic; font-weight: bold;">
			${tournoi.libelle}
			<br/>
			<fmt:formatDate value="${tournoi.dateTournoi}" pattern="dd/MM/yyyy" />
		</div>
		
		<!-- Tableau des équipes inscrites au tournoi -->
		<div id="tableauEquipes" style="margin-top: 20px;">
		  <table style="text-align: center;" >
			<tr>
			  <th style="font-weight: bold;">Equipes inscrites</th>
			</tr>
			<tr>
			  <td>
			  <!-- Affichage de la liste des équipes inscrites -->
			  <c:if test="${not empty tournoi.listeEquipesInscrites}">
				<c:forEach items="${tournoi.listeEquipesInscrites}" var="equipeTournoi">
			  	<span>${equipeTournoi.libelle}<br/></span>
			  	</c:forEach>
			  </c:if>
			  </td>
			</tr>
		  </table>
		</div>
		
		<!-- Tableau des groupes du tournoi -->
		<div id="tableauGroupes" style="margin-top: 20px;">
		<!-- Maximum 4 par ligne -->
		<div style="font-weight: bold;">Groupes</div>
		<c:if test="${not empty tournoi.listeGroupes}">
		<table>
		 <tr>
		  <c:forEach items="${tournoi.listeGroupes}" var="groupeTournoi" varStatus="numGroupe">
		  <c:if test="${numGroupe.count % 5 == 0}">
		  </tr><tr>
		  </c:if>
		  <td>
		  <table class="CSSTableGenerator" style="margin-top: 20px; width: 290px; border: 1px; border-color: black; border-style: solid; border-collapse: collapse;">
		  	<tr><th style="border: 1px; border-color: black; border-style: solid;">${groupeTournoi.libelle}</th></tr>
		  	<c:choose>
		  	<c:when test="${not empty groupeTournoi.listeEquipes}">
		  	<c:forEach items="${groupeTournoi.listeEquipes}" var="equipeGroupe">
		  	<tr><td>${equipeGroupe.libelle != '' ? fn:substring(equipeGroupe.libelle, 0, 35) : ''}</td></tr>
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
		</c:if>
		</div>
		
		<!-- Tableau pour la génération des matchs de chaque groupe du tournoi -->
		<div id="tableauBoutonsMatchs" style="margin-top: 20px;">
		  <!-- <input type="button" class="button" id="matchManuel"  value="Génération manuelle du calendrier"> -->
		  <input type="button" class="button" id="groupeAutomatique" value="Tirage au sort automatique des groupes">
		  <input type="button" class="button" id="matchAutomatique" value="Génération automatique du calendrier">
		</div>
	
	</div>
	
	<div id="dialog-confirm" title="Confirmation" style="display:none;">
	  <input type="hidden" name="url" id="url" value=""/>
	  <p>
		<span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>
	  </p>
	</div>
  </body>
</html>