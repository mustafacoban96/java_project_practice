import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.mysql.cj.protocol.Resultset;

public class SirketCalisanlari {
	private java.sql.Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private Connection con = null;


    public SirketCalisanlari() {
	 
        String url = "jdbc:mysql://" + DataBase.host + ":" + DataBase.port + "/" + DataBase.db_ismi;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            //throw new RuntimeException(e);
            System.out.println("Driver bulunamadi...");
        }

        try {
            con = DriverManager.getConnection(url , DataBase.kullanici_adi, DataBase.parola);
            System.out.println("Baglanti basarili");
        } catch (SQLException e) {
            //throw new RuntimeException(e);
            System.out.println("Baglanti basarisiz");
        }
    }
    
    ////////////////////////////////////////////////////////////////////////////
    public boolean girisYap(String user_name,String sifre) {
    	
    	String query = "Select * from adminler where username = ? and password = ?";
    	
    	try {
			preparedStatement = con.prepareStatement(query);
			
			preparedStatement.setString(1, user_name);
			preparedStatement.setString(2, sifre);
			
			ResultSet rs = preparedStatement.executeQuery();
			return rs.next();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
    }
    ////////////////////////////////////////////////////////////////////////////////////////
    public ArrayList<Calisan> calisanlariGetir(){
    	ArrayList<Calisan> cikti = new ArrayList<>();
    	
    	try {
			statement = con.createStatement();
			String query = "Select * From calisanlar";
			ResultSet rs = statement.executeQuery(query);
			while(rs.next()) {
				int id = rs.getInt("id");
				String ad = rs.getString("ad");
				String soyad = rs.getString("soyad");
				String depart = rs.getString("departman");
				String maas = rs.getString("maas");
				// ResultSet in strcuture araştır.!!!!!!!
				cikti.add(new Calisan(id, ad, soyad, depart, maas));
				
			}
			return cikti; //burada dönen liste calisanEkrani classındaki calisanlariGoruntule methodundaki tabloya
			//geliyor.
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
    }
    
    public void calisanEkle(String ad, String soyad, String departman,String maas) {
    	String query = "Insert Into calisanlar (ad,soyad,departman,maas) VALUES(?, ?, ?, ?)";
    	try {
			preparedStatement = con.prepareStatement(query);
			
			preparedStatement.setString(1, ad);
			preparedStatement.setString(2, soyad);
			preparedStatement.setString(3, departman);
			preparedStatement.setString(4, maas);
			
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    ///////////////////////////////////////////
    public void calisanGuncelle(int id,String yeni_ad,String yeni_soyad,String yeni_departman,String yeni_maas) {
    	String query = "Update calisanlar Set ad = ?,soyad = ?,departman = ?,maas = ? where id = ?";
    	
    	try {
			preparedStatement = con.prepareStatement(query);
			
			preparedStatement.setString(1,yeni_ad);
			preparedStatement.setString(2,yeni_soyad);
			preparedStatement.setString(3,yeni_departman);
			preparedStatement.setString(4,yeni_maas);
			preparedStatement.setInt(5, id);
			
			preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	
    }
    public void calisanSil(int id) {
    	String queryString = "Delete from calisanlar where id = ?";
    	
    	try {
			preparedStatement = con.prepareStatement(queryString);
			preparedStatement.setInt(1,id);
			
			preparedStatement.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
    
}

