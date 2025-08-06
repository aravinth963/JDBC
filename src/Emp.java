import java.io.ObjectInputStream.GetField;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.*;

public class Emp {

	public static void main(String[] args) throws Exception {
		
		//Readble_records();
		//Insert_Records();
		//Insert_var();
		//Insert_usingPST();
		//Callable();
		//Callable_with_parameter();
		GetEmpNameById();
	}
		public static void Readble_records() throws Exception {
			
			String url = "jdbc:mysql://localhost:3306/testdb";
			String username = "javadev";
			String password = "Java@1234";
			
			
			 Connection con = DriverManager.getConnection(url,username,password);
			 
			 Statement st = con.createStatement();
			 
			 ResultSet rs = st.executeQuery("select * from employee");
			 
			 while(rs.next()) {
				 System.out.print(" id: "+ rs.getInt(1));
				 System.out.print(" name: "+ rs.getString(2));
				 System.out.print(" salary: "+ rs.getInt(3));
				 System.out.println();
			 }
			 st.close();
			 con.close();
		}
		
		//Insert query
public static void Insert_Records() throws Exception {
			
			String url = "jdbc:mysql://localhost:3306/testdb";
			String username = "javadev";
			String password = "Java@1234";
			
			
			 Connection con = DriverManager.getConnection(url,username,password);
			 
			 Statement st = con.createStatement();
			 
			 int rows = st.executeUpdate("insert into employee values (4,'vinoth',3100000)");
			 
			 System.out.println("number of rows affected "+ rows);
			 
			 st.close();
			 con.close();
		}


//Insert with variable


public static void Insert_var() throws Exception {
	
	String url = "jdbc:mysql://localhost:3306/testdb";
	String username = "javadev";
	String password = "Java@1234";
	
	
	 Connection con = DriverManager.getConnection(url,username,password);
	 
	 Statement st = con.createStatement();
	 
	 int id=5;
	 String name="manoj";
	 int salary=1000000;
	 int rows = st.executeUpdate("insert into employee values (" + id + ",'" + name + "'," + salary + ")");
	 
	 System.out.println("number of rows affected "+ rows);
	 
	 st.close();
	 con.close();
}

//using prepared Statement
public static void Insert_usingPST() throws Exception {
	
	String url = "jdbc:mysql://localhost:3306/testdb";
	String username = "javadev";
	String password = "Java@1234";
	
	int id=6;
	String name="tharani";
	int salary=4000000;
	
	 Connection con = DriverManager.getConnection(url,username,password);
	 
	 PreparedStatement pst = con.prepareStatement("insert into employee values(?,?,?)");
	 
	 pst.setInt(1,id);
	 pst.setString(2, name);
	 pst.setInt(3, salary);
	 int rows=pst.executeUpdate();
	 
	 System.out.println("Rows inserted: " + rows);
	 con.close();
	 pst.close();
		 		 
}

//store procedure callable statement

public static void Callable() throws Exception {
	
	String url = "jdbc:mysql://localhost:3306/testdb";
	String username = "javadev";
	String password = "Java@1234";
	
	
	 Connection con = DriverManager.getConnection(url,username,password);
	 CallableStatement cs = con.prepareCall("{call GetEmp()}");
	 ResultSet rs= cs.executeQuery();
	 
	 while(rs.next()) {
		 System.out.println("id "+ rs.getInt(1));
		 System.out.println("id "+ rs.getString(2));
		 System.out.println("id "+ rs.getInt(3));
		 System.out.println();
	 }
	 con.close();
}

//store procedure with parameter

public static void Callable_with_parameter() throws Exception {
	
	String url = "jdbc:mysql://localhost:3306/testdb";
	String username = "javadev";
	String password = "Java@1234";
	int id=3;
	
	 Connection con = DriverManager.getConnection(url,username,password);
	 CallableStatement cs = con.prepareCall("{call GetEmpById(?)}");
	 cs.setInt(1, id);
	 ResultSet rs= cs.executeQuery();
	 
	 
	 while(rs.next()) {
		 System.out.println("id "+ rs.getInt(1));
		 System.out.println("id "+ rs.getString(2));
		 System.out.println("id "+ rs.getInt(3));
		 System.out.println();
	 }
	 con.close();
}

//IN AND OUT CALLABLE STATEMENT

public static void GetEmpNameById() throws Exception {
	
	String url = "jdbc:mysql://localhost:3306/testdb";
	String username = "javadev";
	String password = "Java@1234";
	int id=3;
	
	 Connection con = DriverManager.getConnection(url,username,password);
	 CallableStatement cs = con.prepareCall("{call GetEmpNameById(?,?)}");
	 cs.setInt(1, id);
	 cs.registerOutParameter(2, Types.VARCHAR);
	 cs.executeUpdate();
	 
	 System.out.println(cs.getString(2));
	 
	 con.close();
}
}