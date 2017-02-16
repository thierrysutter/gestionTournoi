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
	<script type="text/javascript" src="js/jquery/jquery.ui.datepicker-fr.js"></script>
	
	<script type="text/javascript">
		$(document).ready(function(){
			$( ".datepicker" ).datepicker( {
				showOn: "button",
				buttonImage: "images/date16.gif",
				buttonImageOnly: true
			});
			
			$(".ui-datepicker-trigger").each(function() {
	  			$(this).attr("alt","Calendrier");
	  			$(this).attr("title","");
	  		});

	  		$("img.ui-datepicker-trigger").click(
	  				function(){
	  					$(this).parent().find(".datepicker").blur();
	  					}
	  		);
	  		
	  		$("#choixMode").click(function(e) {
				e.preventDefault();
				if ($("#visu").css('display') =='none') {
					$("#visu").css('display', '');
					$("#modif").css('display', 'none');
					$("#choixMode").text('mode modification');
				} else {
					$("#visu").css('display', 'none');
					$("#modif").css('display', '');
					$("#choixMode").text('mode visualisation');
				}
			});
	  		
	  		if ($("#mode").val() == 'visu') {
				$("#visu").css('display', '');
				$("#modif").css('display', 'none');
	  		} else {
	  			$("#visu").css('display', 'none');
				$("#modif").css('display', '');
	  		}
	  		
	  		$("#btnValider").button().click(function(e) {
				e.preventDefault();
				return valideForm();
			});
	  		
	  		/* $("#typeTournoi").change(function() {
				  //alert( "Changement de type de tournoi." );
				  //$type_tournoi = $( "typeTournoi option:selected" ).val();
				  $type_tournoi = $(this).val();
				  if ($type_tournoi == 1) {
					  // elimination directe
					  // pas de groupe
					  // pas d'équipes qualifiées car pas de phase finale
					  // pas de bareme de points
					  $("#nbGroupes").val('');
					  $("#nbEquipesQualifiees").val('');
					  $("#baremeVictoire").val('');
					  $("#baremeNul").val('');
					  $("#baremeDefaite").val('');
					  
					  $("#nbGroupes").attr('readonly', true);
					  $("#nbEquipesQualifiees").attr('readonly', true);
					  $("#baremeVictoire").attr('readonly', true);
					  $("#baremeNul").attr('readonly', true);
					  $("#baremeDefaite").attr('readonly', true);
					  
					  $("#nbGroupes").attr('disabled', true);
					  $("#nbEquipesQualifiees").attr('disabled', true);
					  $("#baremeVictoire").attr('disabled', true);
					  $("#baremeNul").attr('disabled', true);
					  $("#baremeDefaite").attr('disabled', true);
				  } else if ($type_tournoi == 2) {
					  // championnat
					  // 1 seul groupe
					  // pas d'équipes qualifiées car pas de phase finale
					  $("#nbGroupes").val('1');
					  $("#nbEquipesQualifiees").val('');
					  $("#baremeVictoire").val('');
					  $("#baremeNul").val('');
					  $("#baremeDefaite").val('');
					
					  $("#nbGroupes").attr('readonly', true);
					  $("#nbEquipesQualifiees").attr('readonly', true);
					  $("#baremeVictoire").attr('readonly', false);
					  $("#baremeNul").attr('readonly', false);
					  $("#baremeDefaite").attr('readonly', false);
					  
					  $("#nbGroupes").attr('disabled', true);
					  $("#nbEquipesQualifiees").attr('disabled', true);
					  $("#baremeVictoire").attr('disabled', false);
					  $("#baremeNul").attr('disabled', false);
					  $("#baremeDefaite").attr('disabled', false);
				  } else if ($type_tournoi == 3) {
					  // phases de groupes puis elimination directe
					  $("#nbGroupes").val('');
					  $("#nbEquipesQualifiees").val('');
					  $("#baremeVictoire").val('');
					  $("#baremeNul").val('');
					  $("#baremeDefaite").val('');
					
					  $("#nbGroupes").attr('readonly', false);
					  $("#nbEquipesQualifiees").attr('readonly', false);
					  $("#baremeVictoire").attr('readonly', false);
					  $("#baremeNul").attr('readonly', false);
					  $("#baremeDefaite").attr('readonly', false);
					  
					  $("#nbGroupes").attr('disabled', false);
					  $("#nbEquipesQualifiees").attr('disabled', false);
					  $("#baremeVictoire").attr('disabled', false);
					  $("#baremeNul").attr('disabled', false);
					  $("#baremeDefaite").attr('disabled', false);
				  }
				  
			}); */
		});
		
		function valideForm() {
			/* $("#nbGroupes").attr('disabled', false);
			$("#nbEquipesQualifiees").attr('disabled', false);
			$("#baremeVictoire").attr('disabled', false);
			$("#baremeNul").attr('disabled', false);
			$("#baremeDefaite").attr('disabled', false); */
			  
			// vérification des champs du formulaire
			var valid = true;
			var form = document.form1;
			if(form.libelle == null || form.libelle.value == "") {
				alert("Le nom du tournoi est obligatoire");
				form.libelle.focus();
				valid = false;
				//return false;
			} else if(form.dateTournoi == null || form.dateTournoi.value == "") {
				alert("La date du tournoi est obligatoire");
				form.dateTournoi.focus();
				valid = false;
				//return false;
			} else if(form.categorie == null || form.categorie.value == "") {
				alert("La catégorie du tournoi est obligatoire");
				form.categorie.focus();
				valid = false;
				//return false;
			} else if(form.nbEquipes == null || form.nbEquipes.value == "") {
				alert("Le nombre d'équipes participantes est obligatoire");
				form.nbEquipes.focus();
				valid = false;
				//return false;
			} else if (form.nbEquipes.value < 2) {
				alert("Il faut au moins 2 équipes.");
				form.nbEquipes.focus();
				valid = false;
				//return false;
			} else if(form.nbTerrains == null || form.nbTerrains.value == "") {
				alert("Le nombre de terrains est obligatoire");
				form.nbTerrains.focus();
				valid = false;
				//return false;
			} else if (form.nbTerrains.value < 1) {
				alert("Il faut au moins 1 terrain.");
				form.nbEquipes.focus();
				valid = false;
				//return false;
			} else if(form.dureeRencontre == null || form.dureeRencontre.value == "") {
				alert("La durée des matchs est obligatoire");
				form.dureeRencontre.focus();
				valid = false;
				//return false;
			} else if(form.dureeRencontre.value <= 0) {
				alert("La durée des matchs doit être positive");
				form.dureeRencontre.focus();
				valid = false;
				//return false;
			} else if(form.typeTournoi.value != 1 && (form.baremeVictoire == null || form.baremeVictoire.value == "")) {
				alert("Le nombre de point pour une victoire est obligatoire");
				form.baremeVictoire.focus();
				valid = false;
				//return false;
			} else if(form.typeTournoi.value != 1 && (form.baremeNul == null || form.baremeNul.value == "")) {
				alert("Le nombre de point pour un nul est obligatoire");
				form.baremeNul.focus();
				valid = false;
				//return false;
			} else if(form.typeTournoi.value != 1 && (form.baremeDefaite == null || form.baremeDefaite.value == "")) {
				alert("Le nombre de point pour une défaite est obligatoire");
				form.baremeDefaite.focus();
				valid = false;
				//return false;
			} else if(form.typeTournoi.value != 1 && (form.baremeVictoire.value <= form.baremeNul.value || form.baremeNul.value <= form.baremeDefaite.value || form.baremeNul.value < 0)) {
				alert("Le barême des points n'est pas cohérent (victoire > nul > defaite >= 0)");
				form.baremeVictoire.focus();
				valid = false;
				//return false;
			} else if(form.typeTournoi.value != 1 && (form.nbGroupes == null || form.nbGroupes.value == "")) {
				alert("Le nombre de groupes est obligatoire");
				form.nbGroupes.focus();
				valid = false;
				//return false;
			} else if (form.typeTournoi.value == 3 && form.nbGroupes.value < 1) {
				alert("Il faut au moins 1 groupe.");
				form.nbGroupes.focus();
				valid = false;
				//return false;
			} else if(form.typeTournoi.value == 3 && (form.nbEquipesQualifieesGroupe == null || form.nbEquipesQualifieesGroupe.value == "")) {
				alert("Le nombre d'équipes qualifiées pour la phase finale est obligatoire");
				form.nbEquipesQualifiees.focus();
				valid = false;
				//return false;
			}
			
			if (valid) {
				form.submit();
			} else {
				/* $("#nbGroupes").attr('disabled', true);
				$("#nbEquipesQualifiees").attr('disabled', true);
				$("#baremeVictoire").attr('disabled', true);
				$("#baremeNul").attr('disabled', true);
				$("#baremeDefaite").attr('disabled', true); */
				return false;
			}
		}
		
	</script>
	
  </head>
  <body>
	<c:if test="${tournoi.statut lt 3}">
	<div id="choix" style="text-align: left;">
	  <span id="choixMode" style="text-decoration: underline; cursor: pointer;" onclick="">mode modification</span>
	</div>
	</c:if>
  <div style="text-align: center; margin-top: 25px;">
  <div id="visu">
  <!-- Affichage dans la popup -->
  <c:if test="${tournoi.description != ''}">${tournoi.description}</c:if>
  <table style="margin-left: auto;margin-right: auto;">
	<tr>
	  <td style="text-align: left;">Catégorie:</td>
	  <td style="text-align: left;">${tournoi.libelleCategorie}</td>
	</tr>
	<tr>
	  <td style="text-align: left;">Type:</td>
	  <td style="text-align: left;">${tournoi.libelleTypeTournoi}</td>
	</tr>
	<tr>
	  <td style="text-align: left;">Nombre d'équipes:</td>
	  <td style="text-align: left;">${tournoi.nbEquipes}</td>
	</tr>
	<tr>
	  <td style="text-align: left;">Nombre de terrains:</td>
	  <td style="text-align: left;">${tournoi.nbTerrains}</td>
	</tr>
	<tr>
	  <td style="text-align: left;">Durée des rencontres:</td>
	  <td style="text-align: left;">${tournoi.dureeRencontre} minutes</td>
	</tr>
	<tr>
	  <td style="text-align: left;">Barême de points:</td>
	  <td style="text-align: left;">V=${tournoi.baremeVictoire} pts - N=${tournoi.baremeNul} pts - D=${tournoi.baremeDefaite} pts</td>
	</tr>
	<tr>
	  <td style="text-align: left;">Nombre de groupes:</td>
	  <td style="text-align: left;">${tournoi.nbGroupes}</td>
	</tr>
	<tr>
	  <td style="text-align: left;">Nombre d'équipes qualifiées par groupe:</td>
	  <td style="text-align: left;">${tournoi.nbEquipesQualifieesParGroupe}</td>
	</tr>
	<tr>
	  <td style="text-align: left;">Nombre d'équipes qualifiées:</td>
	  <td style="text-align: left;">${tournoi.nbEquipesQualifiees}</td>
	</tr>
	<tr>
	  <td style="text-align: left;">Consolante:</td>
	  <td style="text-align: left;">${tournoi.consolante ? "Oui" : "Non"}</td>
	</tr>
  </table>
  </div>
  
  <div id="modif">
  <form id="form1" name="form1" action="tournoi.modifier.do" method="get" >
	<input type="hidden" id="mode" name="mode" value="${mode}"/>
	<input type="hidden" id="codeTournoi" name="codeTournoi" value="${tournoi.id}"/>
	
  <table style="margin-left: auto;margin-right: auto;">
	<tr>
	  <td style="text-align: left;">Nom:</td>
	  <td style="text-align: left;"><input type="text" id="libelle" name="libelle" size="50" value="${tournoi.libelle}"/></td>
	</tr>
	<tr>
	  <td style="text-align: left;">Date:</td>
	  <td style="text-align: left;"><input type="text" class="datepicker" id="dateTournoi" name="dateTournoi" size="15" value="<fmt:formatDate value="${tournoi.dateTournoi}" pattern="dd/MM/yyyy" />"/></td>
	</tr>
  
	<tr>
	  <td style="text-align: left;">Catégorie:</td>
	  <td style="text-align: left;">
		<select id="categorie" name="categorie">
		 <c:forEach items="${listeCategories}" var="cat">
		  <option value="${cat.id}" ${tournoi.categorie == cat.id ? 'selected' : ''}>${cat.libelle}</option>
		 </c:forEach>
		</select>
	  </td>
	</tr>
	<tr>
	  <td style="text-align: left;">Type:</td>
	  <td style="text-align: left;">
		<select id="typeTournoi" name="typeTournoi">
		  <option value="1" ${tournoi.typeTournoi == 1 ? 'selected' : ''}>Elimination directe</option>
		  <option value="2" ${tournoi.typeTournoi == 2 ? 'selected' : ''}>Championnat</option>
		  <option value="3" ${tournoi.typeTournoi == 3 ? 'selected' : ''}>Groupe / Elimination directe</option>
		</select>
	  </td>
	</tr>
	<tr>
	  <td style="text-align: left;">Nombre d'équipes:</td>
	  <td style="text-align: left;"><input type="text" id="nbEquipes" name="nbEquipes" size="1" min="1" value="${tournoi.nbEquipes}"/></td>
	</tr>
	<tr>
	  <td style="text-align: left;">Nombre de terrains:</td>
	  <td style="text-align: left;"><input type="text" id="nbTerrains" name="nbTerrains" size="1" min="1" value="${tournoi.nbTerrains}"/></td>
	</tr>
	<tr>
	  <td style="text-align: left;">Durée des rencontres:</td>
	  <td style="text-align: left;"><input type="text" id="dureeRencontre" name="dureeRencontre" size="1" min="0" value="${tournoi.dureeRencontre}"/> minutes</td>
	</tr>
	<tr>
	  <td style="text-align: left;">Barême de points:</td>
	  <td style="text-align: left;">
		<table>
		  <tr>
			<td>Victoire:</td><td><input type="text" id="baremeVictoire" name="baremeVictoire" size="1" min="0" value="${tournoi.baremeVictoire}"/> pts</td>
		  </tr>
		  <tr>
			<td>Nul:</td><td><input type="text" id="baremeNul" name="baremeNul" size="1" min="0" value="${tournoi.baremeNul}"/> pts</td>
		  </tr>
		  <tr>
			<td>Défaite:</td><td><input type="text" id="baremeDefaite" name="baremeDefaite" size="1" min="0" value="${tournoi.baremeDefaite}"/> pts</td>
		  </tr>
		</table>
	  </td>
	</tr>
	<tr>
	  <td style="text-align: left;">Nombre de groupes:</td>
	  <td style="text-align: left;"><input type="text" id="nbGroupes" name="nbGroupes" size="1" min="1" value="${tournoi.nbGroupes}"/></td>
	</tr>
	<tr>
	  <td style="text-align: left;">Nombre d'équipes qualifiées par groupe:</td>
	  <td style="text-align: left;"><input type="text" id="nbEquipesQualifieesGroupe" name="nbEquipesQualifieesGroupe" size="1" min="0" value="${tournoi.nbEquipesQualifieesParGroupe}"/></td>
	</tr>
	<tr>
	  <td style="text-align: left;">Nombre d'équipes qualifiées:</td>
	  <td style="text-align: left;"><input type="text" id="nbEquipesQualifiees" name="nbEquipesQualifiees" size="1" min="0" value="${tournoi.nbEquipesQualifiees}"/></td>
	</tr>
	<tr>
	  <td style="text-align: left;">Consolante:</td>
	  <td style="text-align: left;">
		<input type="radio" id="consolante" name="consolante" value="1" ${tournoi.consolante ? "checked" : ""}/>Oui&nbsp;
		<input type="radio" id="consolante" name="consolante" value="0" ${tournoi.consolante ? "" : "checked"}/>Non
	  </td>
	</tr>
	<tr>
	  <td style="text-align: left;">Description:</td>
	  <td style="text-align: left;">
		<textarea id="description" name="description" rows="10" cols="50">${tournoi.description}</textarea>
	  </td>
	</tr>
  </table>
  </form>
  <input type="button" class="button" id="btnValider" name="btnValider" value="Valider"/>
  </div>
  <br/>  
  </div>
  </body>