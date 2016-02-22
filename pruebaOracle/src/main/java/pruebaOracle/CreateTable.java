package pruebaOracle;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateTable {

	private static String dropIEQuery ="BEGIN EXECUTE IMMEDIATE 'DROP TABLE EXTERNALSYSTEM';  EXCEPTION  WHEN OTHERS THEN  IF SQLCODE != -942 THEN RAISE; END IF; END;";
	private static String createQuery = "CREATE TABLE PRUEBA.EXTERNALSYSTEM (SERVICEID VARCHAR2(20 BYTE) NOT NULL ENABLE, SYNCHRONIZEDSTATE VARCHAR2(20 BYTE) NOT NULL ENABLE, CREATIONDATE DATE NOT NULL ENABLE, UPDATEDATE DATE, EXTERNALSYSTEM VARCHAR2(20 BYTE) NOT NULL ENABLE, QUEUETYPE VARCHAR2(20 BYTE), CONSTRAINT EXTERNALSYSTEM_PK PRIMARY KEY (SERVICEID))";
	private static String insertQuery1 = "INSERT INTO externalsystem (SERVICEID, synchronizedstate, CREATIONDATE,  updatedate, ExternalSystem,  queuetype) VALUES ('Customer//myService2', 'In-sync', SYSDATE, to_date(NULL), 'Assurance', '')";
	private static String noHaceNada="";
	
	public static void main(String[] args){			
		Connection conn=null;
		Statement stmt=null;
		
		try {			
			Class.forName("oracle.jdbc.driver.OracleDriver");
			conn=DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521/xe", "prueba", "prueba");
			stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
			
			conn.setAutoCommit(false);
			
			stmt.execute(dropIEQuery);
			stmt.execute(createQuery);
			stmt.execute(insertQuery1);
			
			conn.commit();
			
		} catch (SQLException | ClassNotFoundException e) {
				System.out.println("Unable to complete statement");
				try {
					conn.rollback();
				} catch (SQLException e1) {
				}
			}finally{
				try {
					stmt.close();
				} catch (SQLException e) {
				}
				
				try {
					conn.close();
				} catch (SQLException e) {
				}
			}

	}

}
