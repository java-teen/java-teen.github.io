package examples2;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

public class UseCallableStatement {
	private static final String	SQL = "CREATE OR REPLACE FUNCTION multiply(x integer, y integer) RETURNS integer AS "
									 +"$BODY$ "
									 +"begin "
									 +"	return x * y; "
									 +"end; "
									 +"$BODY$ "
									 +"LANGUAGE plpgsql";

	public static void main(String[] args) throws ClassNotFoundException, SQLException {
		try(final Connection 	conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres","postgres","sasa21")) {
			
			try(final Statement		stmt = conn.createStatement()) {	// <1>
				stmt.executeUpdate(SQL);
				
				try(final CallableStatement	cstmt = conn.prepareCall("{?= call multiply(?,?)}")) {	// <2>
					cstmt.registerOutParameter(1,Types.INTEGER);	// <3>
					cstmt.setInt(2,12);		// <4>
					cstmt.setInt(3,43);
					cstmt.executeUpdate();	// <5>
					
					System.err.println("Result="+cstmt.getInt(1));	// <6>
				}				
			};		
		}
	}
}
