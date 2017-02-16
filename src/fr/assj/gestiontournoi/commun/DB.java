package fr.assj.gestiontournoi.commun;

import java.sql.Blob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Date;

public class DB implements DBInterface {
	
	public DB() {
		
	}
	
	protected static String doubleQuote(String chaine) {
		if(chaine == null)
            return null;
        if(chaine.indexOf("'") == -1)
            return chaine;
        StringBuffer st = new StringBuffer();
        for(int i = 0; i < chaine.length(); i++)
            if(chaine.charAt(i) == '\'')
                st.append("''");
            else
                st.append(chaine.charAt(i));

        return st.toString();
	}

	@Override
	public boolean fromDB(boolean valeur) {
		// TODO Auto-generated method stub
		return valeur;
	}

	@Override
	public byte fromDB(byte valeur) {
		// TODO Auto-generated method stub
		return valeur;
	}

	@Override
	public byte[] fromDB(byte[] valeur) {
		// TODO Auto-generated method stub
		return valeur;
	}

	@Override
	public short fromDB(short valeur) {
		// TODO Auto-generated method stub
		return valeur;
	}

	@Override
	public int fromDB(int valeur) {
		// TODO Auto-generated method stub
		return valeur;
	}

	@Override
	public long fromDB(long valeur) {
		// TODO Auto-generated method stub
		return valeur;
	}

	@Override
	public double fromDB(double valeur) {
		// TODO Auto-generated method stub
		return valeur;
	}

	@Override
	public float fromDB(float valeur) {
		// TODO Auto-generated method stub
		return valeur;
	}

	@Override
	public String fromDB(String valeur) {
		// TODO Auto-generated method stub
		return valeur;
	}

	@Override
	public String fromDB(String valeur, String defaut) {
		// TODO Auto-generated method stub
		if (valeur == null) {
			return defaut;
		} else {
			return valeur;
		}
	}

	@Override
	public java.util.Date fromDB(java.util.Date valeur) {
		// TODO Auto-generated method stub
		return valeur;
	}

	@Override
	public java.util.Date fromDB(java.util.Date valeur, java.util.Date defaut) {
		// TODO Auto-generated method stub
		if (valeur == null) {
			return defaut;
		} else {
			return valeur;
		}
	}

	@Override
	public Timestamp fromDB(Timestamp valeur) {
		// TODO Auto-generated method stub
		return valeur;
	}

	@Override
	public Timestamp fromDB(Timestamp valeur, Timestamp defaut) {
		// TODO Auto-generated method stub
		if(valeur == null)
            return defaut;
        else
            return valeur;
	}

	@Override
	public byte[] fromDB(Blob valeur) throws SQLException {
		// TODO Auto-generated method stub
		if(valeur == null)
            return new byte[0];
        else
            return valeur.getBytes(1L, (int)valeur.length());
	}

	@Override
	public String toDB(boolean valeur) {
		// TODO Auto-generated method stub
		if(valeur) {
            return "1";
		} else {
            return "0";
		}
	}

	@Override
	public String toDB(Boolean valeur) {
		// TODO Auto-generated method stub
		if(valeur == null)
            return "null";
        if(valeur.booleanValue())
            return "1";
        else
            return "0";
	}

	@Override
	public String toDB(int valeur) {
		// TODO Auto-generated method stub
		return String.valueOf(valeur);
	}

	@Override
	public String toDB(Integer valeur) {
		// TODO Auto-generated method stub
		if(valeur == null)
            return "null";
        else
            return valeur.toString();
	}

	@Override
	public String toDB(double valeur) {
		// TODO Auto-generated method stub
		return String.valueOf(valeur);
	}

	@Override
	public String toDB(Double valeur) {
		// TODO Auto-generated method stub
		if(valeur == null)
            return "null";
        else
            return valeur.toString();
	}

	@Override
	public String toDB(float valeur) {
		// TODO Auto-generated method stub
		return String.valueOf(valeur);
	}

	@Override
	public String toDB(Float valeur) {
		// TODO Auto-generated method stub
		if(valeur == null)
            return "null";
        else
            return valeur.toString();
	}

	@Override
	public String toDB(String valeur) {
		// TODO Auto-generated method stub
		if(valeur == null)
            return "null";
        else
            return "'" + doubleQuote(valeur) + "'";
	}

	@Override
	public String toDB(Date valeur) {
		// TODO Auto-generated method stub
		if(valeur == null)
            return "null";
        else
            return "'" + valeur.toString() + "'";
	}

	@Override
	public String toDB(java.util.Date valeur) {
		// TODO Auto-generated method stub
		if(valeur == null)
        {
            return "null";
        } else
        {
            Date sqlDate = new Date(valeur.getTime());
            return "'" + sqlDate.toString() + "'";
        }
	}

	@Override
	public String toDB(Timestamp valeur) {
		// TODO Auto-generated method stub
		if(valeur == null)
            return "null";
        else
            return "'" + valeur.toString() + "'";
	}

	@Override
	public String toDB(Object valeur) {
		// TODO Auto-generated method stub
		if(valeur == null)
            return null;
        if(valeur.getClass().getName().equals("java.lang.Double"))
            return toDB((Double)valeur);
        if(valeur.getClass().getName().equals("java.lang.Float"))
            return toDB((Float)valeur);
        if(valeur.getClass().getName().equals("java.lang.Integer"))
            return toDB((Integer)valeur);
        if(valeur.getClass().getName().equals("java.lang.String"))
            return toDB((String)valeur);
        if(valeur.getClass().getName().equals("java.lang.Boolean"))
            return toDB((Boolean)valeur);
        if(valeur.getClass().getName().equals("java.sql.Date"))
            return toDB((Date)valeur);
        if(valeur.getClass().getName().equals("java.util.Date"))
            return toDB((java.util.Date)valeur);
        else
            return valeur.toString();
	}

}
