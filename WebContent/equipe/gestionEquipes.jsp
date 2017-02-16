<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html>
  <head>
	<link rel="stylesheet" type="text/css" href="css/gestionTournoi.css" />
	<link rel="stylesheet" type="text/css" href="css/menu.css" />
	<link rel="stylesheet" type="text/css" href="css/jquery-ui.css" />
	
	<script type="text/javascript" src="js/jquery/jquery-1.9.1.js"></script>
	<script type="text/javascript" src="js/jquery/jquery.dataTables.min.js"></script>
	<script type="text/javascript" src="js/jquery/jquery-ui.js"></script>
	
	<script type="text/javascript">
	
		$(document).ready(function(){
			
			var theHREF = "";
			
			$('#tableATrier').dataTable({
			  	"bPaginate": false,
				"bLengthChange": false,
				"bFilter": false,
				"bSort": true,
				"bInfo": false,
				"bAutoWidth": false,
				"oLanguage": {
					"sSearch": "Filtrer ",
					"sZeroRecords": "Aucun enregistrement disponible."
				},
				"aaSorting": [ [1,'asc'] ],	
				"aoColumns": [{ "bSortable": false }, null, null, null, { "bSortable": false }],
				"bJQueryUI": false
			});
			
			$("#rechercher").button().click(function(e) {
				e.preventDefault();
				document.location = "equipes.do?libelleClub="+$("#nomClub").val()+"&libelleEquipe="+$("#nomEquipe").val();
			});
			
			$("#ajouterClub").button().click(function(e) {
				e.preventDefault();
				document.location = "clubs.creer.do";
				
			});
			
			$("#ajouterEquipe").button().click(function(e) {
				e.preventDefault();
				document.location = "equipes.ajouter.do";
			});
		});
	</script>
  </head>
  <body>
  	<jsp:include page="../commun/menu.jsp" flush="true">
	<jsp:param name="onglet"   value=""/>
    <jsp:param name="menu"     value="EQUIPES"/>
    <jsp:param name="info"     value="Gestion des équipes"/>
	</jsp:include>
	<!---->
	<div style="text-align: center;">
		<c:if test="${not empty message}">
		<span style="color: green; font-weight: bold;">${message}</span>
		</c:if>
		
		
		<div id="moteurDeRecherche" style="margin-bottom: 10px;">
			<input class="form-control" id="nomClub" name="nomClub" placeholder="Nom du club" required="" type="text" value="${libelleClub}"> et/ou 
			<input class="form-control" id="nomEquipe" name="nomEquipe" placeholder="Nom de l'équipe" required="" type="text" value="${libelleEquipe}">
			<input type="button" class="button" id="rechercher" value="Rechercher">
		</div>
		
		<div class="CSSTableGenerator" style="margin-bottom: 10px;">
			<table id="tableATrier">
				<thead>
				<tr height="35px">
					<th style="width: 10px;">#</th>
					<th style="width: 120px;"><b>Equipe</b></th>
					<th style="width: 120px;"><b>Club</b></th>
					<th style="width: 40px;"><b>Catégorie</b></th>
					<th style="width: 40px;"><b>Pays</b></th>
					<th style="width: 100px;"><b>Action</b></th>
				</tr>
				</thead>
				<c:if test="${empty listeEquipes}">
					<tr><td colspan="6">Aucune donnée dispobible</td></tr>
				</c:if>
				<c:if test="${not empty listeEquipes}">
				<c:forEach var="equipe" items="${listeEquipes}">
				<tr>
					<td>
						&nbsp;
					</td>
					<td style="text-align: center;" title="Date du tournoi">
						${fn:escapeXml(equipe.libelle)}
					</td>
					<td>
						${fn:escapeXml(equipe.libelleClub)}
					</td>
					<td style="text-align: center;" title="Catégorie">
						${fn:escapeXml(equipe.categorie.libelle)}
					</td>
					<td style="text-align: center;" title="Pays">
						${fn:escapeXml(equipe.pays)}
					</td>
					
					<td style="text-align: center;">
						<img class="saisie" id="saisie_${equipe.id}" src="images/zoom32.png" style="border-width: 0px; " title="Afficher la fiche équipe"/>
						<img class="copie" id="copie_${equipe.id}" src="images/copy32.png" style="border-width: 0px; " title="Duppliquer l'équipe"/>
						<img class="delete" id="delete_${equipe.id}" src="images/trash32.png" style="border-width: 0px;" title="Supprimer l'équipe"/>
					</td>
				</tr>
				</c:forEach>
				</c:if>
			</table>
		</div>
		
		<input type="button" class="button" id="ajouterClub" value="Ajouter un club">
		<input type="button" class="button" id="ajouterEquipe" value="Ajouter une équipe">
	</div>
  </body>
</html>