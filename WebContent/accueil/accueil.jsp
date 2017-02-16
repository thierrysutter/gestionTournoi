<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
  <head>
	<meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Page d'accueil</title>
    
  	<!-- Bootstrap Core CSS -->
    <link href="bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <!-- Custom CSS -->
    <link href="bootstrap/css/business-frontpage.css" rel="stylesheet">
  </head>
  <body>
  	<header class="business-header">
        <div class="container">
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="tagline">Gestion de tournois de football</h1>
                </div>
            </div>
        </div>
    </header>
    
    <!-- Page Content -->
    <div class="container">

        <hr>

        <div class="row">
            <div class="col-sm-6">
                <!--<img class="img-circle img-responsive img-center" src="http://placehold.it/300x300" alt="Club">-->
                <img class="img-circle img-responsive img-center pointer" src="images/ballonherbe.png" alt="Club" id="clubs" style="width: 150px; height: 150px;">
                <h2>Les clubs</h2>
                <p>Créez et gérez les clubs de football référencés.</p>
            </div>
            <div class="col-sm-6">
                <!--<img class="img-circle img-responsive img-center" src="http://placehold.it/300x300" alt="Tournoi">-->
                <img class="img-circle img-responsive img-center pointer" src="images/ballonherbe.png" alt="Tournoi" id="tournois" style="width: 150px; height: 150px;">
                <h2>Les tournois</h2>
                <p>Créez, visualisez et gérez les tournois de football.</p>
            </div>
        </div>
        <!-- /.row -->

        <hr>

        <!-- Footer -->
        <footer>
            <div class="row">
                <div class="col-lg-12">
                    <p>Copyright &copy; GestionTournoi 2015</p>
                </div>
            </div>
            <!-- /.row -->
        </footer>

    </div>
    <!-- /.container -->

    <!-- jQuery -->
    <script src="js/jquery/jquery-1.9.1.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="bootstrap/js/bootstrap.min.js"></script>
    
    <script type="text/javascript">
	    $(document).ready(function(){
	        $("#clubs").click(function(){
	            document.location = "clubs.do";
	        });
	        
			$("#tournois").click(function(){
				document.location = "tournoi.lister.do";
	        });
	    });
    </script>
   </body>
</html>