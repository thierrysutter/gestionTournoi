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
		$(function() {
			
			// initialisation avec type tournoi = 1
			// elimination directe
			// pas de groupe
			// pas d'équipes qualifiées car pas de phase finale
		  	// pas de bareme de points
		  	/* $("#nbGroupes").val('');
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
		  	$("#baremeDefaite").attr('disabled', true); */
			
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
	  		
	  		$("#btnRetour").button().click(function(e) {
				e.preventDefault();
				document.location = "tournoi.lister.do";
			});
			
			$("#btnValider").button().click(function(e) {
				e.preventDefault();
				return valideForm();
			});
			
			$("#btnReset").button().click(function(e) {
				e.preventDefault();
				document.form1.reset();
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
  	<jsp:include page="../commun/menu.jsp" flush="true">
	<jsp:param name="onglet"   value=""/>
    <jsp:param name="menu"     value="TOURNOI"/>
    <jsp:param name="info"     value="Création d'un tournoi"/>
	</jsp:include>
  
	<div >
		<form id="form1" name="form1" action="tournoi.creer.do" method="get" >
			<input type="hidden" id="sub" name="sub" value="1"/>
			<table class="tftable">
				<tr>
					<td>Nom</td>
					<td><input type="text" id="libelle" name="libelle" size="50" /></td>
				</tr>
				<tr>
					<td>Date</td>
					<td><input type="text" class="datepicker" id="dateTournoi" name="dateTournoi" size="10"/></td>
				</tr>
				<tr>
					<td>Heure de début</td>
					<td><input type="time" id="heureDebut" name="heureDebut" /></td>
				</tr>
				<tr>
					<td>Catégorie</td>
					<td>				
					  <select id="categorie" name="categorie">
					  	<c:forEach items="${listeCategories}" var="cat">
					  	<option value="${cat.id}">${cat.libelle}</option>
					  	</c:forEach>
					  </select>
					</td>
				</tr>
				<tr>
					<td>Type de tournoi</td>
					<td>
					  <select id="typeTournoi" name="typeTournoi">
					  	<option value="1">Elimination directe</option>
					  	<option value="2">Championnat</option>
					  	<option value="3">Groupe / Elimination directe</option>
					  </select>
					</td>
				</tr>
				<tr>
					<td>Nombre d'équipes</td>
					<td><input type="text" id="nbEquipes" name="nbEquipes" size="1" min="1"/></td>
				</tr>
				<tr>
					<td>Nombre de terrains</td>
					<td><input type="text" id="nbTerrains" name="nbTerrains" size="1" min="1"/></td>
				</tr>
				<tr>
					<td>Durée des rencontres</td>
					<td><input type="text" id="dureeRencontre" name="dureeRencontre" size="1" min="0"/></td>
				</tr>
				<tr>
					<td>Barême de points</td>
					<td>
					  Victoire: <input type="text" id="baremeVictoire" name="baremeVictoire" size="1" min="0"/><br/>
					  Nul: <input type="text" id="baremeNul" name="baremeNul" size="1" min="0"/><br/>
					  Défaite: <input type="text" id="baremeDefaite" name="baremeDefaite" size="1" min="0"/>
					</td>
				</tr>
				<tr>
					<td>Nombre de groupes</td>
					<td><input type="text" id="nbGroupes" name="nbGroupes" size="1" min="1"/></td>
				</tr>
				<tr>
					<td>Nombre d'équipes qualifiées<br/>par groupe pour la phase finale</td>
					<td><input type="text" id="nbEquipesQualifieesGroupe" name="nbEquipesQualifieesGroupe" size="1" min="0"/></td>
					<!-- <td><input type="text" id="nbEquipesQualifiees" name="nbEquipesQualifiees" size="1" min="0"/></td> -->
				</tr>
				<tr>
					<td>Consolante</td>
					<td>
					  <input type="radio" id="consolante" name="consolante" value="1" />Oui&nbsp;
					  <input type="radio" id="consolante" name="consolante" value="0" checked="checked"/>Non
					</td>
				</tr>
				<tr>
					<td>Description (optionnel)</td>
					<td>
					  <textarea id="description" name="description" rows="10" cols="50"></textarea>
					</td>
				</tr>
			</table>
			<br/>
			<input type="button" class="button" id="btnValider" name="btnValider" value="Valider" size="25"/>
			<input type="button" class="button" id="btnReset" name="btnReset" value="Effacer" size="25"/>
			<input type="button" class="button" id="btnRetour" name="btnRetour" value="Retour" size="25"/>
		</form>
	</div>
   </body>
</html>