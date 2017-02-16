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
		  modifierNbEquipesInscrites();		  
		  
		  $("#ajoutSelect").button().click(function(e) {
			  e.preventDefault();
			  $.each($('#equipesDispo option:selected'), function(key, value) {
				  $('#equipesSelectionnes').append($(value));
				  var nbEquipes = $('#nbEquipesInscrites').html();
		    	  nbEquipes++; 
		    	  $('#nbEquipesInscrites').html(nbEquipes);
		    	  modifierNbEquipesInscrites();
			  });
		  });
		  
		  $("#ajoutTout").button().click(function(e) {
			  e.preventDefault();
			  $.each($('#equipesDispo option'), function(key, value) {
				     $('#equipesSelectionnes').append($(value));
				     var nbEquipes = $('#nbEquipesInscrites').html();
		    		 nbEquipes++; 
		    		 $('#nbEquipesInscrites').html(nbEquipes);
		    		 modifierNbEquipesInscrites();
				});
		  });
		  
		  $("#supprTout").button().click(function(e) {
			  e.preventDefault();
			  $.each($('#equipesSelectionnes option'), function(key, value) {
				  $('#equipesDispo').append($(value));
				  //$('#equipesSelectionnes option[value='+value.value+']').remove();
				  var nbEquipes = $('#nbEquipesInscrites').html();
				  nbEquipes--; 
				  $('#nbEquipesInscrites').html(nbEquipes);
				  modifierNbEquipesInscrites();
				});
		  });
		  
		  $("#supprSelect").button().click(function(e) {
			  e.preventDefault();
			  $.each($('#equipesSelectionnes option:selected'), function(key, value) {
				  $('#equipesDispo').append($(value));
				  //$('#equipesSelectionnes option[value='+value.value+']').remove();
				  var nbEquipes = $('#nbEquipesInscrites').html();
				  nbEquipes--; 
				  $('#nbEquipesInscrites').html(nbEquipes);
				  modifierNbEquipesInscrites();
				});
		  });
		  
		  $("#enregistrer").button().click(function(e) {
			  e.preventDefault();
			  enregistrer();
		  });
		  
		  $("#finaliser").button().click(function(e) {
			  e.preventDefault();
			  
			  if (confirm("Vous ne pourrez plus modifier les équipes après cette action. Voulez vous vraiment cloturer les inscriptions à ce tournoi ?")) {
				  $('#clotureInscription').val("1");
				  enregistrer();
			  }
		  });
		  
		  $("#retour").button().click(function(e) {
			  e.preventDefault();
			  document.location = "tournoi.lister.do";
		  });
		  
	  });
	  
	  function enregistrer() {
		  var form = document.form1;
		  $.each($('#equipesSelectionnes>option'), function(key, value) {
			  // on force la sélection de chacune des équipes de la liste
			  $(this).attr('selected', 'selected');
			});
		  
		  form.submit();
	  }
	  
	  function modifierNbEquipesInscrites() {
		  if ($('#nbEquipesInscrites').html() === $('#nbEquipesTournoi').html()) {
			  $('#finaliser').css('display', '');
			  //$('#creerEquipe').css('display', 'none');
		  } else {
			  $('#finaliser').css('display', 'none');
			  //$('#creerEquipe').css('display', '');
		  }
	  }
	</script>
  </head>
  <body>
  	<jsp:include page="../commun/menu.jsp" flush="true">
	<jsp:param name="onglet"   value=""/>
    <jsp:param name="menu"     value="TOURNOI"/>
    <jsp:param name="info"     value="Saisie des équipes"/>
	</jsp:include>
	<!---->
	<div align="center">
		<c:if test="${not empty message}">
		<span style="color: green; font-weight: bold;">${message}</span>
		</c:if>
	
	  <table style="text-align: center;" >
	  <tr>
		<td>
		  Equipes disponibles<br/>
		  <select id="equipesDispo" multiple size="20" style="width: 200px;">
			<c:if test="${not empty listeEquipesDispo}">
			<c:forEach items="${listeEquipesDispo}" var="equipe">
		  	<option value="${equipe.id}_${equipe.libelle}">${equipe.libelle}</option>
		  	</c:forEach>
		  	</c:if>
		  </select>
		</td>
		<td>
		  <input id="ajoutSelect" type="button" class="button" style="width: 25px;" value="-->" title="Ajouter la sélection"/><br/>
		  <input id="ajoutTout" type="button" class="button" style="width: 25px;" value="-->>" title="Ajouter tout"/><br/>
		  <input id="supprTout" type="button" class="button" style="width: 25px;" value="<<--" title="Supprimer tout"/><br/>
		  <input id="supprSelect" type="button" class="button" style="width: 25px;" value="<--" title="Supprimer la sélection"/>
		</td>
		<td>
		  Equipes inscrites (<span id="nbEquipesInscrites">${fn:length(equipesTournoi)}</span>/ <span id="nbEquipesTournoi">${tournoi.nbEquipes}</span>)<br/>
		  <form id="form1" name="form1"  action="equipe.enregistrer.do" method="get">
		  <input type="hidden" id="codeTournoi" name="codeTournoi" value="${tournoi.id}" />
		  <%-- <input type="hidden" id="nbEquipesTournoiInit" name="nbEquipesTournoiInit" value="${fn:length(equipesTournoi)}" /> --%>
		  <input type="hidden" id="clotureInscription" name="clotureInscription" value="0" />
		  
		  <select id="equipesSelectionnes" name="equipesSelectionnes" multiple="multiple" size="20" style="width: 200px;">
		    <c:if test="${not empty equipesTournoi}">
		    <c:forEach items="${equipesTournoi}" var="equipeTournoi">
		  	<option value="${equipeTournoi.id}_${equipeTournoi.libelle}">${equipeTournoi.libelle}</option>
		  	</c:forEach>
		  	</c:if>
		  </select>
		  </form>
		</td>
	  </table>
	  <!-- <input type="button" class="button" id="creerEquipe" value="Créer une équipe"> -->
	  <input type="button" class="button" id="enregistrer" value="Enregistrer">
	  <input type="button" class="button" id="finaliser" value="Finaliser">
	  <input type="button" class="button" id="retour" value="Annuler">
	</div>
	
	<!-- 
	<div id="dialog-modale" style="display:none;">
	  <table class="tftable">
		<tr>
		  <td><input type="text" size="50" name="nomEquipeACreer" id="nomEquipeACreer"/></td>
		</tr>
	  </table>
	</div>
	-->
  </body>
</html>