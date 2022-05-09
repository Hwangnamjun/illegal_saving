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
			String user = "ADMIN";
			String password = "mihwum-gojby3-hetgYf";
			String url = "jdbc:oracle:thin:@hwangnamjun_medium?TNS_ADMIN=/Users/hwangnamjun/Oracle_backup/Wallet_Hwangnamjun/";
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn = DriverManager.getConnection(url, user, password);
			
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
