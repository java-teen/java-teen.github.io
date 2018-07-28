package examples2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UsePreparedStatement {
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		try(final Connection 	conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres","sasa21")) {
			
			try(final Statement		stmt = conn.createStatement()) {	// <1>
				stmt.executeUpdate("create temporary table t(f1 integer,f2 text)");
				
				try(final PreparedStatement	pstmt = conn.prepareStatement("insert into t(f1,f2) values (?,?)")) {	// <2>
					for (int index = 0; index < 10; index++) {
						pstmt.setInt(1,index);			// <3>
						pstmt.setString(2,"text "+index);
						pstmt.executeUpdate();			// <4>
					}
				}
				
				try(final ResultSet		rs = stmt.executeQuery("select count(*) from t")) {	// <5>
					while (rs.next()) {	// <6>
						System.err.println("Count="+rs.getInt(1)); // <7>
					}
				}
			};		
		}
	}
}
