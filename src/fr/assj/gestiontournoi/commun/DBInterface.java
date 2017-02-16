package fr.assj.gestiontournoi.commun;

import java.sql.Blob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

public interface DBInterface {
	public abstract boolean fromDB(boolean flag);

    public abstract byte fromDB(byte byte0);

    public abstract byte[] fromDB(byte abyte0[]);

    public abstract short fromDB(short word0);

    public abstract int fromDB(int i);

    public abstract long fromDB(long l);

    public abstract double fromDB(double d);

    public abstract float fromDB(float f);

    public abstract String fromDB(String s);

    public abstract String fromDB(String s, String s1);

    public abstract Date fromDB(Date date);

    public abstract Date fromDB(Date date, Date date1);

    public abstract Timestamp fromDB(Timestamp timestamp);

    public abstract Timestamp fromDB(Timestamp timestamp, Timestamp timestamp1);

    public abstract byte[] fromDB(Blob blob)
        throws SQLException;

    public abstract String toDB(boolean flag);

    public abstract String toDB(Boolean boolean1);

    public abstract String toDB(int i);

    public abstract String toDB(Integer integer);

    public abstract String toDB(double d);

    public abstract String toDB(Double double1);

    public abstract String toDB(float f);

    public abstract String toDB(Float float1);

    public abstract String toDB(String s);

    public abstract String toDB(java.sql.Date date);

    public abstract String toDB(Date date);

    public abstract String toDB(Timestamp timestamp);

    /**
     * @deprecated Method toDB is deprecated
     */

    public abstract String toDB(Object obj);
}
