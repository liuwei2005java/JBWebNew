import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestTTdb {

	public static void main(String[] args) {
		String URL = "jdbc:timesten:client:DSN=jbweb";
		Connection conn;
		try {
			conn = DriverManager.getConnection(URL);
			conn.setAutoCommit(true);
	        String sql = "insert into test1 ( a, b ) values ( ?, ?);";

	      PreparedStatement pstmt = conn.prepareStatement(sql);
	      pstmt.setInt(1, 1);
	      pstmt.setString(2, "cccc");
	      pstmt.executeUpdate();
	      conn.commit();
	      conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
//	public static void main(String[] args) {
//		String URL = "jdbc:timesten:client:DSN=jbweb";
//		Connection con;
//		ResultSet rs;
//		int tblSize = 0;
//		try {
//			con = DriverManager.getConnection(URL);
//			Statement stmt = con.createStatement();
//			stmt.execute("select count(*) from test");
//			rs = stmt.getResultSet();
//			if (rs.next()) {
//				tblSize = rs.getInt(1);
//				System.out.println(tblSize);
//			}
//			rs.close();
//			stmt.close();
//			con.close();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
}