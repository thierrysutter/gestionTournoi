<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
  <head>
  	<link rel="stylesheet" href="css/gestionTournoi.css" type="text/css" />
	<link rel="stylesheet" href="css/menu.css" type="text/css" />
	
  </head>
  <body>
  	<jsp:include page="../commun/menu.jsp" flush="true">
	<jsp:param name="onglet"   value=""/>
    <jsp:param name="menu"     value="ACCUEIL"/>
    <jsp:param name="info"     value="Bienvenue sur votre site de gestion de tournoi"/>
	</jsp:include>
  
	<div style="text-align: center;">
		Bienvenue
	</div>
   </body>
</html>