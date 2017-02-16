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
				"aoColumns": [{ "bSortable": false }, null, null, { "bSortable": false }],
				"bJQueryUI": false
			});
			
			$( "#dialog-confirm" ).dialog({
		        resizable: false,
		        height:160,
		        width:500,
		        autoOpen: false,
		        modal: true,
		        buttons: {
		            "Oui": function() {
		                $(this).dialog("close");
		                window.location.href = theHREF;
		            },
		            "Non": function() {
		                $( this ).dialog( "close" );
		            }
		         }
		    });
		    
			$("#rechercher").button().click(function(e) {
				e.preventDefault();
				document.location = "equipes.do?libelleClub="+$("#nomClub").val();
			});
			
			$("#ajouterClub").button().click(function(e) {
				e.preventDefault();
				document.location = "clubs.creer.do";
				
			});
			
			$("#ajouterEquipe").button().click(function(e) {
				e.preventDefault();
				document.location = "equipes.ajouter.do";
			});
			
			$(".toggleClub").click(function(e) {
				e.preventDefault();
				$idClub = $(this).attr("id").split("_")[1];
				
				if ($(this).attr("src").indexOf("moins")>0) {
					$(this).attr({src:"images/tri_plus48.png"});
				} else if ($(this).attr("src").indexOf("plus")>0) {
					$(this).attr({src:"images/tri_moins48.png"});
				} else {
					$(this).attr({src:"images/tri_point.gif"});
				}
				
				$(".equipe"+$idClub).toggle();
			});
			
			$(".saisie").click(function(e) {
				e.preventDefault();
				//$idClub = $(this).attr("id").split("_")[1];
				theHREF = "clubs.afficher.do?idClub="+$(this).attr("id").split("_")[1];
		        document.location = theHREF;
			});
			
			/*$(".copie").click(function(e) {
				e.preventDefault();
				$idClub = $(this).attr("id").split("_")[1];
				
			});*/
			
			$(".delete").click(function(e) {
				e.preventDefault();
				//$idClub = $(this).attr("id").split("_")[1];
				theHREF = "clubs.supprimer.do?idClub="+$(this).attr("id").split("_")[1];
		        $("#dialog-confirm").find("p").html($("#dialog-confirm").find("p").html()+"Etes-vous sûr de vouloir supprimer ce club ?");
		        $("#dialog-confirm").dialog("open");
			});
		});
	</script>
  </head>
  <body>
  	<jsp:include page="../commun/menu.jsp" flush="true">
	<jsp:param name="onglet"   value=""/>
    <jsp:param name="menu"     value="CLUBS"/>
    <jsp:param name="info"     value="Gestion des clubs"/>
	</jsp:include>
	<!---->
	<div style="text-align: center; margin-bottom: 40px;">
		<c:if test="${not empty message}">
		<span style="color: green; font-weight: bold;">${message}</span>
		</c:if>
		
		
		<div id="moteurDeRecherche" style="margin-bottom: 10px;">
			<input class="form-control" id="nomClub" name="nomClub" placeholder="Nom du club" required type="text" value="${libelleClub}"> 
			<input type="button" class="button" id="rechercher" value="Rechercher">
		</div>
		
		<div class="CSSTableGenerator" style="margin-bottom: 25px;">
			<table id="tableATrier">
				<thead>
				<tr height="35px">
					<th style="width: 10px;">#</th>
					<th style="width: 80px;"><b>Club</b></th>
					<th style="width: 40px;"><b>Pays</b></th>
					<th style="width: 40px;"><b>Ligue</b></th>
					<th style="width: 40px;"><b>District</b></th>
					<th style="width: 100px;"><b>Action</b></th>
				</tr>
				</thead>
				<c:if test="${empty listeClubs}">
					<tr><td colspan="4">Aucune donnée dispobible</td></tr>
				</c:if>
				<c:if test="${not empty listeClubs}">
				<c:forEach var="club" items="${listeClubs}">
				<tr>
					<td style="text-align: center;">
						<c:if test="${fn:length(club.listeEquipes) > 0}">
							<img id="plus_${club.id}" class="toggleClub" src="images/tri_plus48.png" style="width: 16px; height: 16px;" />
						</c:if>
					</td>
					<td style="text-align: center;" title="Date du tournoi">
						${fn:escapeXml(club.nom)}
					</td>
					<td style="text-align: center;" title="Pays">
						${fn:escapeXml(club.pays)}
					</td>
					<td style="text-align: center;" title="Pays">
						${fn:escapeXml(club.ligue)}
					</td>
					<td style="text-align: center;" title="Pays">
						${fn:escapeXml(club.district)}
					</td>
					
					<td style="text-align: center;">
						<img class="saisie" id="saisie_${club.id}" src="images/zoom32.png" style="border-width: 0px; " title="Afficher la fiche club"/>
						<!-- <img class="copie" id="copie_${club.id}" src="images/copy32.png" style="border-width: 0px; " title="Duppliquer le club"/>-->
						<img class="delete" id="delete_${club.id}" src="images/trash32.png" style="border-width: 0px;" title="Supprimer le club"/>
					</td>
				</tr>
				<!-- 
				<c:if test="${fn:length(club.listeEquipes) > 0}">
				
				<c:forEach var="equipe" items="${club.listeEquipes}">
				<tr class="equipe${club.id}" id="equipe${equipe.id}" style="display: none;">
					<td>&nbsp;</td>
					<td style="text-align: center;">${equipe.libelle}</td>
					<td style="text-align: center;">${equipe.categorie.libelle}</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
					<td>&nbsp;</td>
				</tr>
				</c:forEach>
				</c:if>
				 -->
				</c:forEach>
				</c:if>
			</table>
		</div>
		
		<input type="button" class="button" id="ajouterClub" value="Ajouter un club">
		<input type="button" class="button" id="ajouterEquipe" value="Ajouter une équipe">
		
		<div id="dialog-confirm" title="Confirmation de la suppression" style="display:none;">
		    <p><span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span></p>
		</div>
	</div>
  </body>
</html>