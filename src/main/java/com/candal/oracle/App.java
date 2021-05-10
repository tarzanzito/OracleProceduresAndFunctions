package com.candal.oracle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import oracle.jdbc.OracleCallableStatement;
import oracle.jdbc.OracleTypes;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@SpringBootApplication
public class App implements CommandLineRunner {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		TestResultSet();
		
		jbdctemplateSelectNoParms();
		jbdctemplateSelectWithParms();
		jbdctemplateFunctionTable();
		
		driverManagerSelect();
		driverManagerTableFunctionStatment();
		driverManagerTableFunctionPreparedStatement();
	}

	
	public void jbdctemplateSelectNoParms() throws Exception {

		List<Pessoa> lst = null;

		try {

			String sqlQuery = "SELECT * FROM TEST.PESSOAS";

			lst = jdbcTemplate.query(sqlQuery, new PessoasRowMapper());

		} catch (DataAccessException e) {
			String errLabel = "Error in 'Repository.consultaContratosCliente' (A)";
			throw new Exception(e);
		} catch (Exception e) {
			String errLabel = "Error in 'Repository.consultaContratosCliente' (B)";
			throw new Exception(e);
		}

		System.out.println(lst);
	}

	public void jbdctemplateSelectWithParms() throws Exception {

		List<Pessoa> lst = null;

		try {

			String sqlQuery = "SELECT * FROM TEST.PESSOAS WHERE personid <= ?";
			Object[] parms = new Object[] { 2 };

			lst = jdbcTemplate.query(sqlQuery, parms, new PessoasRowMapper());

		} catch (DataAccessException e) {
			String errLabel = "Error in 'Repository.consultaContratosCliente' (A)";
			throw new Exception(e);
		} catch (Exception e) {
			String errLabel = "Error in 'Repository.consultaContratosCliente' (B)";
			throw new Exception(e);
		}

		System.out.println(lst);
	}

	public void jbdctemplateFunctionTable() throws Exception {

		List<Pessoa> lst = null;

		try {

			// String sqlQuery = "SELECT * FROM TABLE(TEST.get_tab_tf(?))";

			String sqlQuery = "SELECT * FROM TABLE(TEST.Pessoas_FC1(?))";
			Object[] parms = new Object[] { 2 };

			lst = jdbcTemplate.query(sqlQuery, parms, new PessoasRowMapper());

		} catch (DataAccessException e) {
			String errLabel = "Error in 'Repository.consultaContratosCliente' (A)";
			throw new Exception(e);
		} catch (Exception e) {
			String errLabel = "Error in 'Repository.consultaContratosCliente' (B)";
			throw new Exception(e);
		}

		System.out.println(lst);
	}

	public void driverManagerSelect() {

		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;

		try {
			connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/XE", "system", "admin01");

			System.out.println("Connected to database");

			statement = connection.createStatement();

			resultSet = statement.executeQuery("SELECT * FROM TEST.PESSOAS WHERE personid <= 2");

			while (resultSet.next()) {
				System.out.println("Coluna 1: " + resultSet.getString(1));
				System.out.println("Coluna 2: " + resultSet.getString(2));
				System.out.println("Coluna 3: " + resultSet.getString(3));
				System.out.println("Coluna 4: " + resultSet.getString(4));
				System.out.println("Coluna 5: " + resultSet.getString(5));
			}

			System.out.println("Oracle JDBC connect and query test completed.");

		} catch (Exception ex) {
			System.out.println("ERROR:" + ex.getMessage());

		}

	}

	public void driverManagerTableFunctionStatment() {

		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;

		try {
			connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/XE", "system", "admin01");

			System.out.println("Connected to database");

			statement = connection.createStatement();

			resultSet = statement.executeQuery("SELECT * FROM TABLE(TEST.Pessoas_FC1(2))");

			while (resultSet.next()) {
				System.out.println("Coluna 1: " + resultSet.getString(1));
				System.out.println("Coluna 2: " + resultSet.getString(2));
				System.out.println("Coluna 3: " + resultSet.getString(3));
				System.out.println("Coluna 4: " + resultSet.getString(4));
				System.out.println("Coluna 5: " + resultSet.getString(5));
			}

			System.out.println("Oracle JDBC connect and query test completed.");

		} catch (Exception ex) {
			System.out.println("ERROR:" + ex.getMessage());

		}
	}

	public void driverManagerTableFunctionPreparedStatement() {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			// Class.forName("com.mysql.jdbc.Driver");

			connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/XE", "system", "admin01");

			System.out.println("Connected to database");

			preparedStatement = connection.prepareStatement("SELECT * FROM TABLE(TEST.Pessoas_FC1(?))");
			preparedStatement.setInt(1, 3);
			// preparedStatement.setString(1, "3");

			resultSet = preparedStatement.executeQuery();

			while (resultSet.next()) {
				System.out.println("Coluna 1: " + resultSet.getString(1));
				System.out.println("Coluna 2: " + resultSet.getString(2));
				System.out.println("Coluna 3: " + resultSet.getString(3));
				System.out.println("Coluna 4: " + resultSet.getString(4));
				System.out.println("Coluna 5: " + resultSet.getString(5));

			}

			System.out.println("Oracle JDBC connect and query test completed.");

		} catch (Exception ex) {
			System.out.println("ERROR:" + ex.getMessage());

		}
	}

	
	public void TestResultSet() {
	    try {
	    	
	    	DriverManager.registerDriver (new oracle.jdbc.OracleDriver());
	    	Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/XE", "system", "admin01");

			System.out.println("Connected to database");
	    	
	      CallableStatement stmt = connection.prepareCall("BEGIN TEST.PROCEDURE4(?, ?); END;");
	      stmt.setString(1, "'30"); // DEPTNO
	      stmt.registerOutParameter(2, OracleTypes.CURSOR); //REF CURSOR
	      stmt.execute();
	      ResultSet rs = ((OracleCallableStatement)stmt).getCursor(2);
	      while (rs.next()) {
	        System.out.println(rs.getString(1) + ":" + rs.getString(2) + ":" + rs.getString(3)); 
	      }
	      rs.close();
	      rs = null;
	      stmt.close();
	      stmt = null;
	      connection.close();
	      connection = null;
	    }
	    catch (SQLException e) {
	      System.out.println(e.getLocalizedMessage());
	    }
	  }
	
//	CREATE OR REPLACE PROCEDURE PROCEDURE4 
//	(
//	  PARAM1 IN VARCHAR2 ,
//	  p_recordset OUT SYS_REFCURSOR
//	) AS 
//	BEGIN
//	  OPEN p_recordset FOR
//	    SELECT PERSONID,
//	           LASTNAME,
//	           FIRSTNAME,
//	           ADDRESS,
//	           CITY
//	    FROM   PESSOAS
//	    WHERE  FIRSTNAME > PARAM1
//	    ORDER BY PERSONID;
//	END PROCEDURE4;
	
}



//create or replace TYPE      PERSON_OBJECT_TYPE AS OBJECT 
//(
//FIRSTNAME   varchar2(255),
//LASTNAME    varchar2(255),
//CITY        varchar2(255)
//);


//create or replace TYPE           PERSON_TABLE_TYPE 
//AS TABLE OF PERSON_OBJECT_TYPE;








//
//create or replace TYPE      TYPE_PESSOAS_ROW AS OBJECT (
//	    PersonID int,
//	    LastName varchar(255),
//	    FirstName varchar(255),
//	    Address varchar(255),
//	    City varchar(255)
//
//	);
//
//create or replace TYPE      TYPE_PESSOAS_TAB IS TABLE OF TYPE_PESSOAS_ROW;
//
//











//create or replace FUNCTION      Pessoas_FC1 (p_rows IN NUMBER) RETURN  TYPE_PESSOAS_TAB   IS
//v_ret  TYPE_PESSOAS_TAB;
//
//BEGIN
//
//v_ret  := TYPE_PESSOAS_TAB();
//
//   select 
//      TYPE_PESSOAS_ROW(PersonID,LastName,FirstName,Address,City)
//      
//    bulk collect into
//      v_ret
//    from 
//      TEST.PESSOAS
//    where PERSONID <= p_rows;
//  
//    return v_ret;
//
//END;