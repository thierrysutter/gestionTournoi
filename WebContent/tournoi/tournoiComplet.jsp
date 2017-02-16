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
			
			if ($("#statutTournoi").val() == 2) {
				$("#tableauGroupes").css('display', '');
				$("#tableauMatchs").css('display', 'none');
				$("#tableauClassements").css('display', 'none');
				
				$("#groupeAutomatique").css('display', '');
				$("#planning").css('display', '');
				$("#classement").css('display', 'none');
				$("#saisie").css('display', '');
				$("#suivant").css('display', 'none');
			} else if ($("#statutTournoi").val() == 3) {
				$("#tableauGroupes").css('display', '');
				$("#tableauMatchs").css('display', '');
				$("#tableauClassements").css('display', 'none');
				
				$("#groupeAutomatique").css('display', 'none');
				$("#planning").css('display', 'none');
				$("#classement").css('display', '');
				$("#saisie").css('display', 'none');
				
				// si tous les résultats ont été saisis, on peut paser à l'étape suivante
				$saisieResultatTermine = true;
				$(".statutMatch").each(function(){
					if($(this).val() < 1) {
						$saisieResultatTermine = false;
					} 
				});
				if($saisieResultatTermine) {
					$("#suivant").css('display', '');
				} else {
					$("#suivant").css('display', 'none');
				}
			}
			
			
			/* $("#groupeManuel").button().click(function(e) {
				e.preventDefault();
			}); */
			
			 $("#groupeAutomatique").button().click(function(e) {
				e.preventDefault();
				var submit = true;
				if ($("#statutTournoi").val() == 2) {
					if (!confirm("Voulez vous vraiment relancer un tirage au sort complet ?")) {
						submit = false;
					}
				}
				if (submit) {
					document.form1.action="groupe.repartir.do";
					document.form1.submit();
				}
			});
			
			$("#planning").button().click(function(e) {
				e.preventDefault();
				if ($("#tableauMatchs").css('display') =='none') {
					$("#tableauMatchs").css('display', '');
					$("#planning").val('Masquer les plannings');
				}
				else {
					$("#tableauMatchs").css('display', 'none');
					$("#planning").val('Afficher les plannings');
				}
			});
			
			$("#classement").button().click(function(e) {
				e.preventDefault();
				if ($("#tableauClassements").css('display') =='none') {
					$("#tableauClassements").css('display', '');
					$("#classement").val('Masquer les classements');
				}
				else {
					$("#tableauClassements").css('display', 'none');
					$("#classement").val('Afficher les classements');
				}
			});
			
			$("#retour").button().click(function(e) {
				e.preventDefault();
				document.location = "tournoi.lister.do";
			});
			
			$("#saisie").button().click(function(e) {
				e.preventDefault();
				//confirm("Vous ne pourrez plus modifier le tournoi après cette action. Voulez vous vraiment continuer ?");
				$("#dialog-confirm").find("p").html($("#dialog-confirm").find("p").html()+"Vous ne pourrez plus modifier le tournoi après cette action. Voulez-vous vraiment continuer ?");
				$("#dialog-confirm").find("#url").val("tournoi.ouvrir.do?codeTournoi="+$("#codeTournoi").val());
		        $("#dialog-confirm").dialog("open");
				//document.location = "tournoi.lister.do";
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
			
			$("#suivant").button().click(function(e) {
				e.preventDefault();
				//confirm("Vous ne pourrez plus modifier le tournoi après cette action. Voulez vous vraiment continuer ?");
				$("#dialog-confirm").find("p").html($("#dialog-confirm").find("p").html()+"Vous ne pourrez plus modifier les résultats des matchs après cette action. Voulez-vous vraiment continuer ?");
				$("#dialog-confirm").find("#url").val("groupe.afficherClassements.do?codeTournoi="+$("#codeTournoi").val());
		        $("#dialog-confirm").dialog("open");
			});
			
			$("#horaire").button().click(function(e) {
				e.preventDefault();
				// enregistrement des modifications
				document.formMatchs.action="planning.enregistrer.do";
				document.formMatchs.submit();
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
	
	<!-- Tableau des groupes du tournoi -->
	<div id="tableauGroupes" style="margin-top: 20px;">
	<!-- Maximum 4 par ligne -->
	<div style="font-size: 20px; font-weight: bold;">Groupes</div>
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
	
	<!-- Tableau des matchs du tournoi -->
	<div id="tableauMatchs" style="margin-top: 20px;">
	<!-- Maximum 2 par ligne -->
	<form id="formMatchs" name="formMatchs" action="match.enregistrer.do" method="get">
	  <input type="hidden" id="codeTournoi" name="codeTournoi" value="${tournoi.id}" />
	  <input type="hidden" id="saveAll" name="saveAll" value="" />
	  <input type="hidden" id="idMatch" name="idMatch" value="" />
	  <!-- <input type="hidden" id="statutMatchs" name="statutMatchs" value="${statutMatchs}" /> -->
	<div style="font-size: 20px; font-weight: bold;">Plannings</div>
	<table>
	<tr>
	<c:forEach items="${tournoi.listeGroupes}" var="groupeTournoi" varStatus="numGroupe">
	  <c:if test="${not empty groupeTournoi.listeMatchs}">
	  <c:if test="${numGroupe.count % 3 == 0}">
	  </tr><tr>
	  </c:if>
	  <td>
	  <table class="CSSTableGenerator" style="width: 585px; border: 1px; border-color: black; border-style: solid; border-collapse: collapse;">
		<tr style="border-color: black; border: 1px; border-style: solid;">
		  <th colspan="9">${groupeTournoi.libelle}</th>
		</tr>
		<tr style="border-color: black; border: 1px; border-style: solid;">
		  <td colspan="2" style="font-weight: bold; text-align:center;">Heure</td>
		  <td colspan="3" style="font-weight: bold; text-align:center;">Rencontre</td>
		  <td colspan="3" style="font-weight: bold; text-align:center;">Score</td>
		  <td style="font-weight: bold; text-align:center;">
		  <!-- Boutons pour enregistrer tous les résultats -->
		  <img class="saveAll" id="imgSaveAll" src="images/save32x32.png" style="border-width: 0px; " title="Sauvegarder tous les résultats"/>
		  </td>
		</tr>
		<c:forEach items="${groupeTournoi.listeMatchs}" var="matchTournoi">
		<tr>
		  <td style="width:50px; text-align:center; padding-left:5px; padding-right: 5px; ">
		  	<c:choose>
			  	<c:when test="${tournoi.statut lt 3}">
			  		<input type="time" name="horaire_${matchTournoi.id}" id="horaire_${matchTournoi.id}" value="${matchTournoi.horaire}"/>
			  	</c:when>
			  	<c:otherwise>
			  		${matchTournoi.horaire}
			  	</c:otherwise>
		  	</c:choose>
		  </td>
		  <td style="width:25px; text-align:center; padding-left:5px; padding-right: 5px; ">${matchTournoi.tour}</td>
		  <td style="width:125px; padding-left:5px;" nowrap>${matchTournoi.equipeLocale.libelle}</td>
		  <td style="width:3px; text-align:center;">-</td>
		  <td style="width:125px; padding-right: 5px;" nowrap>${matchTournoi.equipeVisiteur.libelle}</td>
		  <td style="width:25px; padding-left:5px;">
		  <input type="hidden" class="statutMatch" name="statutMatch_${matchTournoi.id}" id="statutMatch_${matchTournoi.id}" value="${(matchTournoi.statut)}"/>
		  <c:choose>
		  <c:when test="${tournoi.statut lt 3}">
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
		  <c:when test="${tournoi.statut lt 3}">
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
	  </td>
	  </c:if>
	  </c:forEach>
	  </tr>
	  </table>
	  </form>
	  </div>
	  
	  <!-- Tableau des classements des groupes du tournoi -->
	  <div id="tableauClassements" style="margin-top: 20px;">
		<!-- Maximum 2 par ligne -->
		<div style="font-size: 20px; font-weight: bold;">Classements</div>
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
	  
	  
	  <!-- Tableau des différents boutons de la page -->
	  <div id="tableauBoutons" style="margin-top: 20px;">
	  <input type="button" class="button" id="groupeAutomatique" value="Tirage au sort des groupes">
	  <input type="button" class="button" id="planning" value="Afficher les plannings">
	  <input type="button" class="button" id="classement" value="Afficher les classements">
	  <br/>
	  <input type="button" class="button" id="saisie" value="Valider">
	  <input type="button" class="button" id="suivant" value="Passer à la phase suivante">
	  <input type="button" class="button" id="retour" value="Retour à la liste des tournois">
	  <br/>
	  <input type="button" class="button" id="horaire" value="Enregistrer les modifications">
	  
	  </div>
	</div>
	<br/><br/>
	
	<div id="dialog-confirm" title="Confirmation" style="display:none;">
	  <input type="hidden" name="url" id="url" value=""/>
	  <p>
		<span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>
	  </p>
	</div>
  </body>
</html>