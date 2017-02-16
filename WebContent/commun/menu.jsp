<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
 
<div id="menu">
  <div id="menucontainer">
	  <div id="menunav">
	  <ul>
		  <li><a href="accueil.do" title="Accueil" class="${param.menu == 'ACCUEIL' ? 'current' : ''}" ><span>Accueil</span></a></li>
		  <li><a href="clubs.do" title="Clubs" class="${param.menu == 'CLUBS' ? 'current' : ''}" ><span>Clubs</span></a></li>
		  <li><a href="equipes.do" title="Equipes" class="${param.menu == 'EQUIPES' ? 'current' : ''}" ><span>Equipes</span></a></li>
		  <li><a href="tournoi.lister.do" title="Tournoi" class="${param.menu == 'TOURNOI' ? 'current' : ''}" ><span>Tournoi</span></a></li>
	  </ul>
	  <div id="menu-page">${param.info}</div>
	  <div id="menu-user">
	  <!--
	  <c:choose>
		<c:when test="${global_user.admin}">
		  <a href="choixFournisseur.do" style="color: #fff;">Fournisseur ${global_user.fournisseur.nom}</a>
		</c:when>
		<c:otherwise>
		  Fournisseur ${global_user.fournisseur.nom}
		</c:otherwise>
	  </c:choose>-->
	  Administrateur test
	  <!--
	  <a href="afficherChangementMotDePasse.do"><img src=images/picto_psw.gif style="border-width: 0px" title="Changer de mot de passe" /></a>
	  &nbsp;
	  <a href="afficherAide.do"><img src=images/picto_aide.gif style="border-width: 0px" title="Aide" /></a>
	  &nbsp;
	  <img src=images/picto_msg.gif style="border-width: 0px; cursor: pointer;" title="Historique des messages" onclick="afficherNotification('1');" />
	  &nbsp;-->
	  &nbsp;&nbsp;<a href="login.do?close=1"><img src=images/picto_stop.gif style="border-width: 0px; cursor: pointer;" title="Déconnexion" /></a>
	  &nbsp;
	  </div>
	</div>
	<!--
	<div id="menu-logo">
	  <img src="images/logo_pdm.png" />
	</div>-->
  </div>
</div>
<!-- 
<ul id="css3menu1" class="topmenu">
	<li class="topfirst"><a class="pressed" href="accueil.do" style="height:18px;line-height:18px;">Accueil</a></li>
	<li class="topmenu"><a href="#" style="height:18px;line-height:18px;">Tournois</a></li>
	<li class="toplast"><a href="#" style="height:18px;line-height:18px;">Contact</a></li>
</ul><p class="_css3m"><a href="http://css3menu.com/">CSS Vertical Drop Down Menu</a> by Css3Menu.com</p>
-->