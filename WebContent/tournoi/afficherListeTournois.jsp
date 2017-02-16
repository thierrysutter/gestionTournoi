<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page pageEncoding="UTF-8"%>
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
	<script type="text/javascript" src="js/jquery/jquery.dataTables.min.js"></script>
	<script type="text/javascript" src="js/jquery/jquery-ui.js"></script>
	 
	<script type="text/javascript">
	
		/* méthode tri pour les colonnes contenant des dates */
		jQuery.fn.dataTableExt.oSort['date-asc']  = function(a,b) {
		    var datea = a.split('/');
		    var dateb = b.split('/');
		
		    var x = (datea[2] + datea[1] + datea[0]) * 1;
		    var y = (dateb[2] + dateb[1] + dateb[0]) * 1;
		
		    return ((x < y) ? -1 : ((x > y) ?  1 : 0));
		};
		
		jQuery.fn.dataTableExt.oSort['date-desc'] = function(a,b) {
		    var datea = a.split('/');
		    var dateb = b.split('/');
		
		    var x = (datea[2] + datea[1] + datea[0]) * 1;
		    var y = (dateb[2] + dateb[1] + dateb[0]) * 1;
		
		    return ((x < y) ? 1 : ((x > y) ?  -1 : 0));
		};
		
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
				"aoColumns": [{ "bSortable": false }, null,{ "sType": 'date' }, null, null, null, null, { "bSortable": false }],
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
		    
		    $(".delete").click(function(e) {
		        e.preventDefault();
		        theHREF = "tournoi.supprimer.do?codeTournoi="+$(this).attr("id").split("_")[1];
		        $("#dialog-confirm").find("p").html($("#dialog-confirm").find("p").html()+"Etes-vous sûr de vouloir supprimer le tournoi : "+$(this).attr("id").split("_")[2]+" ?");
		        $("#dialog-confirm").dialog("open");
		    });
		    
		    $(".parametres").click(function(e) {
		        e.preventDefault();
		        theHREF = "tournoi.parametrer.do?codeTournoi="+$(this).attr("id").split("_")[1];
				popup(600, 400, $(this).attr("id").split("_")[2]+" - "+$(this).attr("id").split("_")[3], theHREF);
		    });
		    
		    $(".saisie").click(function(e) {
		        e.preventDefault();
		        theHREF = "tournoi.afficher.do?codeTournoi="+$(this).attr("id").split("_")[1]+"&statut="+$(this).attr("id").split("_")[2];
		        document.location = theHREF;
		    });
		    
		    $(".copie").click(function(e) {
		        e.preventDefault();
		        theHREF = "tournoi.duppliquer.do?codeTournoi="+$(this).attr("id").split("_")[1];
		        document.location = theHREF;
		    });
		    
		    $("#ajouterTournoi").button().click(function(e) {
				  e.preventDefault();
				  //theHREF = "equipe.creer.do";
				  document.location = "tournoi.creer.do";
			  });
		});
		
		function popup(dialogWidth, dialogHeight, dialogTitle, url) {
			$frame = $('#dialog-modale');
			$frame.dialog({
		        modal: true,
		        width: dialogWidth,
		        height: dialogHeight,
		        resizable: true,
		        position: "center",
		        buttons: {
		        	"Valider": function(){
		        		$( "#libelle" ).val();
		        		$( "#dateTournoi" ).val();
		        		$( "#categorie" ).val();
		        		$( "#typeTournoi" ).val();
		        		$( "#nbEquipes" ).val();
		        		$( "#nbTerrains" ).val();
		        		$( "#dureeRencontre" ).val();
		        		$( "#baremeVictoire" ).val();
		        		$( "#baremeNul" ).val();
		        		$( "#baremeDefaite" ).val();
		        		$( "#nbGroupes" ).val();
		        		$( "#nbEquipesQualifiees" ).val();
		        		$( "#consolante" ).val();
		        		$( "#description" ).val();
		        		
		        		// appel asynchrone pour enregistrer les modifications du tournoi
		        		
		        	},
		        	"Fermer": function(){
		        		$frame.dialog("close");
		        	}
		        },
		        title: dialogTitle,
		        /*show: {//l'affichage se fait avec l'effet blind
		        	effect: "blind",
		        	duration: 500
		        },
		        hide: {//la fermeture se fait avec l'effet explode
		        	effect: "explode",
		        	duration: 500
		        },*/
		        close: function(){
		        	// si on veut effectuer des opérations dans la page appelante à la fermeture de la popup modale c'est ici
		        	//alert("qdsdqsdqsfqsf");
		        	// appel de la fonction qui sera exécuté sur la page appelante à la fermeture de la popup
		        	// fonctionRetour();
		        	// ou ecrire le script directement ici
		        }
		    });
			$frame.load(url);
			$frame.css("width", "100%");
			$frame.dialog("open");
		}
	</script>
  </head>
  <body>
  	<jsp:include page="../commun/menu.jsp" flush="true">
	<jsp:param name="onglet"   value=""/>
    <jsp:param name="menu"     value="TOURNOI"/>
    <jsp:param name="info"     value="Liste des tournois"/>
	</jsp:include>
  
  
	<div style="text-align: center;">
		<div class="CSSTableGenerator">
		<table id="tableATrier">
		<thead>
		<tr height="35px">
			<th style="width: 10px;">#</th>
			<th style="width: 150px;"><b>Libelle</b></th>
			<th style="width: 30px;"><b>Date</b></th>
			<th style="width: 50px;"><b>Catégorie</b></th>
			<th style="width: 150px;"><b>Description</b></th>
			<th style="width: 50px;"><b>Inscription</b></th>
			<th style="width: 50px;"><b>Etat</b></th>
			<th style="width: 130px;"><b>Action</b></th>
			<!-- <br><br><img src="images/tableaux/fleche_up_off.gif" /><img src="images/tableaux/fleche_down_off.gif" /> -->
		</tr>
		</thead>
		<c:if test="${empty listeTournois }">
			
		</c:if>
		<c:if test="${not empty listeTournois }">
		<c:forEach var="tournoi" items="${listeTournois}">
		<tr>
			<td>
				&nbsp;
			</td>
			<td>
				${fn:escapeXml(tournoi.libelle)}
			</td>
			<td style="text-align: center;" title="Date du tournoi">
				<fmt:formatDate value="${tournoi.dateTournoi}" pattern="dd/MM/yyyy" />
			</td>
			<td style="text-align: center;" title="Catégorie">
				${tournoi.libelleCategorie}
			</td>
			<td title="Description du tournoi">
				${fn:escapeXml(tournoi.description)}
			</td>
			<td style="text-align: center;" title="Nb d'équipes inscrites / Nb équipes max">
				${empty tournoi.listeEquipesInscrites ? 0 : fn:length(tournoi.listeEquipesInscrites)} / ${tournoi.nbEquipes}
			</td>
			<td style="text-align: center;" title="Statut du tournoi">
				${tournoi.libelleStatut}
			</td>
			<td style="text-align: center;">
				<img class="saisie" id="saisie_${tournoi.id}_${tournoi.statut}" src="images/zoom32.png" style="border-width: 0px; " title="Afficher le tournoi"/>
				<img class="copie" id="copie_${tournoi.id}_${tournoi.statut}" src="images/copy32.png" style="border-width: 0px; " title="Duppliquer le tournoi"/>
				<img class="parametres" id="parametres_${tournoi.id}_${tournoi.libelle}_${tournoi.dateTournoi}" src="images/param32.png" style="border-width: 0px; " title="Paramètrer le tournoi"/>
				<img class="delete" id="delete_${tournoi.id}_${tournoi.libelle}" src="images/trash32.png" style="border-width: 0px;" title="Supprimer le tournoi"/>
			</td>
		</tr>
		</c:forEach>
		</c:if>
		</table>
		</div>
		<br/>
		<br/>
		<input type="button" class="button" id="ajouterTournoi" value="Ajouter un tournoi">

	    <div id="dialog-modale" style="display:none;"></div>
		<div id="dialog-confirm" title="Confirmation de la suppression" style="display:none;">
		    <p>
		        <span class="ui-icon ui-icon-alert" style="float:left; margin:0 7px 20px 0;"></span>
		    </p>
		</div>
	</div>
  </body>
</html>