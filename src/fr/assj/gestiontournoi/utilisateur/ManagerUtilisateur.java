package fr.assj.gestiontournoi.utilisateur;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;

import org.apache.log4j.Logger;

public class ManagerUtilisateur {
	/**
	 * Logger spécifique à cette classe.
	 */
	protected static Logger logger = Logger.getLogger(ManagerUtilisateur.class);
	
	public static String verifierUtilisateur(Connection connection, Utilisateur user, String password) throws Exception {
		try {
			logger.info("Vérification des identifiants de connexion...");
			
			// controles des statuts de refus de connexion (verrouillé, fermé, ...) mais sans vérifier le mot de passe 
			String messageRejet = controlerRejetCompte(user);
			
			if (messageRejet != null && !messageRejet.equals("")) {
				return messageRejet;
			} else {
				// vérification du password
				if (!user.getPassword().equalsIgnoreCase(hachageMotDePasse(user.getId(), password))) {
					// le mot de passe saisi ne correspond pas à celui en base
					// incrémenter le nombre d'échec d(authentification
					new DaoUtilisateur(connection).tracerEchecConnexion(user.getId());
					user.setNbEchec(user.getNbEchec()+1);
					
					// si 3 echecs consécutifs de connexion alors le compte est verrouillé 
					if (user.isVerrouille()) {
						messageRejet = "Compte verrouillé";
					} else {
						messageRejet = "Mot de passe incorrect (" + (Utilisateur.nbEssaiConnexion - user.getNbEchec()) + " essais restants)";
					}
				}
				
				// si authentification réussie 
				if (messageRejet != null && !messageRejet.equals("")) {
					logger.info("erreur authentificaton de " + user.getLogin() + " ("+messageRejet+")");
				} else {
					// alors on trace la connexion en base et dans le fichier de log
					new DaoUtilisateur(connection).tracerConnexion(user.getId());
					logger.info("connexion réussie de " + user.getLogin());
				}
				
				return messageRejet;
			}
			
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}
	
	public static Utilisateur trouverCompteUtilisateurParLogin(Connection connection, String login) throws Exception {
		try {
			logger.info("Recherche du compte de l'utilisateur.");
			return new DaoUtilisateur(connection).trouverCompteUtilisateurParLogin(login);
		} catch (Exception e) {
			logger.error(e);
			return null;
		}
	}
	
	public static String controlerRejetCompte(Utilisateur user) {
		// controles des cas de refus de connexion 
		if (user == null) {
			// utilisateur inconnu
			return "Compte inconnu";
		} else if (!user.isActif()) {
			return "Compte supprimé";
		} else if (user.isVerrouille()) {
			return "Compte verrouillé";
		} else if (!user.isCguApprouve()) {
			return "Conditions Générales d'Utilisation du site non approuvée";
		}
		
		return null;
	}
	
	/**
	 * Hachage d'un mot de passe
	 * 
	 * @param idCompteFournisseur
	 * @param motDePasse
	 * @return le mot de passe 'haché'
	 */
	public static String hachageMotDePasse(int idUtilisateur, String password) {
		MessageDigest md;
		String algorithm="SHA-256";
		String hash;
 
		//Password encryption
		try {
			md=MessageDigest.getInstance(algorithm);
			hash=byteToHex(md.digest((idUtilisateur + password).getBytes("UTF-8")));
			return hash;
		} 
		catch (NoSuchAlgorithmException e) {
			System.out.println("L'algo n'existe pas !!");
			e.printStackTrace();
			return null;
		}
		catch (UnsupportedEncodingException e) {
			System.out.println("L'encodage n'est pas bon");
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * @pdmAchat
	 * 
     * Convertit des octets en leur representation hexadecimale (base 16),
     * chacun se retrouvant finalement 'non signe' et sur 2 caracteres.
     * 
	 * @param bits 
	 * @return le mot de passe en hexadecimal
     */
    public static String byteToHex(byte[] bits) {
        if (bits == null) {
            return null;
        }
        StringBuffer hex = new StringBuffer(bits.length * 2); // encod(1_bit) => 2 digits
        for (int i = 0; i < bits.length; i++) {
            if ((bits[i] & 0xff) < 0x10) { // 0 < .. < 9
                hex.append("0");
            }
            hex.append(Integer.toString(bits[i] & 0xff, 16)); // [(bit+256)%256]^16
        }
        return hex.toString();
    }
}
