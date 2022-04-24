package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

	private static DBConnection instance = new DBConnection();
	
	public static DBConnection getinstance() {
		return instance;
	}
	
	private DBConnection() {}
	
	public Connection getconnection()
	{
		Connection conn = null;
		try 
		{
			String user = "ddris";
			String password = "ddris";
			String url = "jdbc:oracle:thin:@10.10.10.11:1521:DDSDB";
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, user, password);
			System.out.println("연결완료");
			
		}catch(ClassNotFoundException e){
			System.out.println("db드라이버 로딩 실패" + e.toString());
		}catch(SQLException e) {
			System.out.println("db접속 실패" + e.toString());
		}catch(Exception e) {
			System.out.println("에러" + e.toString());
		}
		
		return conn;
	}
	
}
