package examples2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UseStatement {
	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		Class.forName("org.postgresql.Driver");		// <1>

		try(final Connection 	conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres","sasa21");	// <2>
			final Statement		stmt = conn.createStatement();		// <3>
			final ResultSet		rs = stmt.executeQuery("select 1 as field1, 'text 1' as field2 union all select 2, 'text 2'")) {	// <4>
			
			while (rs.next()) {	// <5>
				System.err.println("Int="+rs.getInt("field1")+", text="+rs.getString(2)); // <6>
			}
		}
	}
}
