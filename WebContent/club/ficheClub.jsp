<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<html>
  <head>
  	<link rel="stylesheet" href="css/gestionTournoi.css" type="text/css" />
	<link rel="stylesheet" href="css/menu.css" type="text/css" />
	<link rel="stylesheet" href="css/jquery-ui.css" type="text/css" />
	
	<style type="text/css">
		div#entete {
			width: 800px;
			max-with:960px;
			margin:5em auto;
			height: 101px;
		}
		
		div#container {
			width: 800px;
			max-with:960px;
			margin:5em auto;
		}
		
		div#colonneG {
		    float:left;	
			width: 400px;
			margin-bottom: 25px;
		}
		
		div#colonneD {
		    float: right;
		    width: 400px;
		    margin-bottom: 25px;
		
			
		}
		
		div#boutons {
			/*margin: 5em auto;
			width: 75%;
			padding: 1em;*/
			
			/*padding:1px 0;*/
			
			text-align:center;
			clear:both;
		}
	</style>
	
	<script type="text/javascript" src="js/jquery/jquery-1.9.1.js"></script>
	<script type="text/javascript" src="js/jquery/jquery-ui.js"></script>
	<script type="text/javascript" src="js/jquery/jquery.ui.datepicker-fr.js"></script>
	
	<script type="text/javascript">
		$(document).ready(function(){
			
			var theHREF = "";
			
			$("#btnRetour").button().click(function(e) {
				e.preventDefault();
				window.location.href = "clubs.do";
			});
			
			$("#enteteContact").click(function(e) {
				e.preventDefault();
				if ($("#imgContact").attr("src").indexOf("moins")>0) {
					$("#imgContact").attr({src:"images/tri_plus.gif"});
				} else if ($("#imgContact").attr("src").indexOf("plus")>0) {
					$("#imgContact").attr({src:"images/tri_moins.gif"});
				} else {
					$("#imgContact").attr({src:"images/tri_point.gif"});
				}
				$("#bodyContact").toggle();
			});

			$("#enteteTerrain").click(function(e) {
				e.preventDefault();
				if ($("#imgTerrain").attr("src").indexOf("moins")>0) {
					$("#imgTerrain").attr({src:"images/tri_plus.gif"});
				} else if ($("#imgTerrain").attr("src").indexOf("plus")>0) {
					$("#imgTerrain").attr({src:"images/tri_moins.gif"});
				} else {
					$("#imgTerrain").attr({src:"images/tri_point.gif"});
				}
				$("#bodyTerrain").toggle();
			});

			$("#enteteEquipe").click(function(e) {
				e.preventDefault();
				if ($("#imgEquipe").attr("src").indexOf("moins")>0) {
					$("#imgEquipe").attr({src:"images/tri_plus.gif"});
				} else if ($("#imgEquipe").attr("src").indexOf("plus")>0) {
					$("#imgEquipe").attr({src:"images/tri_moins.gif"});
				} else {
					$("#imgEquipe").attr({src:"images/tri_point.gif"});
				}
				$("#bodyEquipe").toggle();
			});
		});
	</script>
  </head>
  <body>
  	<jsp:include page="../commun/menu.jsp" flush="true">
	<jsp:param name="onglet" value=""/>
    <jsp:param name="menu" value="CLUBS"/>
    <jsp:param name="info" value="Fiche d'un club"/>
	</jsp:include>
	
	<div id="entete" style="margin: auto;">
	    <table>
	    <tr>
	    <td><img src="images/logos/${club.logo}" style="width: 125px; height: 150px; border: 1px; border-style: solid; border-color: black; float: left;" /></td>
	    <td style="vertical-align: top; text-align: left;">
	    <span style="font-size: 30px; font-weight: bold; ">${club.nom}</span><br>
	    <span style="text-align: left;font-size: 15px; font-weight: bold; ">${club.numeroAffiliation}</span><br>
	    <span style="font-size: 10px; font-weight: bold; ">
	    Fédération: ${club.pays}<br>
	    Ligue: ${club.ligue}<br>
	    District: ${club.district}<br>
	    Site web: ${club.siteWeb}
	    </span>
	    </td>
	    </tr>
	    </table>
	</div>
	
	<div id="container">

	  <div id="colonneG">
		<table class="tftable" style="width: 98%;">
		  <thead>
		  	<tr>
		  	  <th colspan="2">Coordonnées principales</th>
		  	</tr>
		  </thead>
		  <tbody>
			<tr>
	    	  <td align="right">Adresse</td>
	    	  <td align="left" style="width: 80%;">${club.adresse}</td>
			</tr>
			
			<tr>
			  <td align="right" >Tel 1</td>
			  <td align="left" style="width: 80%;">${club.tel1}<br/></td>
			</tr>
			<tr>
			  <td align="right" >Tel 2</td>
			  <td align="left">${club.tel2}<br/></td>
			</tr>
			<tr>
			  <td align="right" >Fax 1</td>
	    	  <td align="left">${club.fax1}<br/></td>
	    	</tr>
	    	<tr>
			  <td align="right" >Fax 2</td>
	    	  <td align="left">${club.fax2}<br/></td>
	    	</tr>
	    	<tr>
	    	  <td align="right" >Email 1</td>
	    	  <td align="left">${club.email1}<br/></td>
	    	</tr>
	    	<tr>
	    	  <td align="right" >Email 2</td>
	    	  <td align="left">${club.email2}<br/></td>
	    	</tr>

		    <tr>
			  <td align="right" >Stade</td>
			  <td align="left">${club.stade}<br/></td>
			</tr>
			<tr>
			  <td align="right" >Couleur 1</td>
			  <td align="left">${club.couleur1}<br/></td>
			</tr>
			<tr>
			  <td align="right" >Couleur 2</td>
			  <td align="left">${club.couleur2}<br/></td>
			</tr>
		  </tbody>
		</table>
	  </div>
	  <div id="colonneG">
		<table class="tftable" style="width: 98%;margin-bottom: 10px;">
		  <thead>
		  	<tr>
		  	  <th colspan="2" id="enteteContact">
		  	  <img id="imgContact"  src="images/tri_plus.gif" style="height: 12px; width: 12px;cursor: pointer;float: left;"/>Contacts
		  	  </th>
		  	</tr>
		  </thead>
		  <tbody id="bodyContact" style="display: none;">
		    <c:if test="${fn:length(club.listeContacts) <= 0}">
			<tr>
			  <td colspan="2">Aucun contact</td>
			</tr>
			</c:if>
			<c:if test="${fn:length(club.listeContacts) > 0}">
			<c:forEach items="${club.listeContacts}" var="contact">
			<tr>
			  <td align="left" style="width: 85%;">
			    ${contact.fonction}<br>
			    ${contact.nom} ${contact.prenom}<br>
			    ${contact.tel}<br>
			    ${contact.email}
			  </td>
			  <td align="right">
			  <img class="ficheContact" id="ficheContact_${contact.nom}" src="images/zoom16.gif" style="cursor: pointer;background: white;border-width: 0px;"/>
			  <img class="deleteContact" id="deleteContact_${contact.nom}" src="images/trash16.gif" style="cursor: pointer;background: white;border-width: 0px;"/>
			  </td>
			</tr>
			</c:forEach>
			</c:if>
			<tr><td colspan="2"><a href="#" class="ajoutContact" id="ajoutContact" style="font-size:10px; cursor: pointer;float: right;">Ajouter un contact</a></td></tr>
		  </tbody>
		</table>

		<table class="tftable" style="width: 98%;margin-bottom: 10px;"">
		  <thead>
		  	<tr>
		  	  <th colspan="2" id="enteteTerrain"><img id="imgTerrain" src="images/tri_plus.gif" style="height: 12px; width: 12px;cursor: pointer;float: left;"/>Terrains</th>
		  	</tr>
		  </thead>
		  <tbody id="bodyTerrain" style="display: none;">
			<c:if test="${fn:length(club.listeTerrains) <= 0}">
			<tr>
			  <td colspan="2">Aucun terrain</td>
			</tr>
			</c:if>
			<c:if test="${fn:length(club.listeTerrains) > 0}">
			<c:forEach items="${club.listeTerrains}" var="terrain">
			<tr>
			  <td align="left" style="width: 85%;">
			  	${terrain.libelle}<br>
			  	${terrain.type}
			  </td>
			  <td align="center">
			  <img class="ficheTerrain" id="ficheTerrain_${terrain.libelle}" src="images/zoom16.gif" style="cursor: pointer;background: white;border-width: 0px;"/>
			  <img class="deleteTerrain" id="deleteTerrain_${terrain.libelle}" src="images/trash16.gif" style="cursor: pointer;background: white;border-width: 0px;"/>
			  </td>
			</tr>
			</c:forEach>
			</c:if>
			<tr><td colspan="2"><a href="#" class="ajoutTerrain" id="ajoutTerrain" style="font-size:10px; cursor: pointer;float: right;">Ajouter un terrain</a></td></tr>
		  </tbody>
		</table>

		<table class="tftable" style="width: 98%;margin-bottom: 10px;"">
		  <thead>
		  	<tr>
		  	  <th colspan="3" id="enteteEquipe"><img id="imgEquipe" src="images/tri_plus.gif" style="height: 12px; width: 12px;cursor: pointer;float: left;"/>Equipes</th>
		  	</tr>
		  </thead>
		  <tbody id="bodyEquipe" style="display: none;">
			<c:if test="${fn:length(club.listeEquipes) <= 0}">
			<tr>
			  <td colspan="3">Aucune équipe</td>
			</tr>
			</c:if>
			<c:if test="${fn:length(club.listeEquipes) > 0}">
			<c:forEach items="${club.listeEquipes}" var="equipe">
			<tr>
			  <td align="left" style="width: 55%;">
			  	${equipe.libelle}
			  </td>
			  <td align="left" style="width: 30%;">
			  	${equipe.categorie.libelle}
			  </td>
			  <td align="center">
			  <img class="ficheEquipe" id="ficheEquipe_${equipe.id}" src="images/zoom16.gif" style="cursor: pointer;background: white;border-width: 0px;"/>
			  <img class="deleteEquipe" id="deleteEquipe_${equipe.id}" src="images/trash16.gif" style="cursor: pointer;background: white;border-width: 0px;"/>
			  </td>
			</tr>
			</c:forEach>
			</c:if>
			<tr><td colspan="3"><a href="#" class="ajoutEquipe" id="ajoutEquipe" style="font-size:10px; cursor: pointer;float: right;">Ajouter une équipe</a></td></tr>
		  </tbody>
		</table>
	  </div>
	</div>

	<div id="boutons">
		<input type="button" class="button" id="btnRetour" value="Retour">
	</div>
	
  </body>
</html>