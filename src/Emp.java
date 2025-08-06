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
		//GetEmpNameById();
		//commit_demo1();
		//commit_demo2();
		//batch_demo();
		//rollback_demo();
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

//commit vs autocommit

public static void commit_demo1() throws Exception {
	
	String url = "jdbc:mysql://localhost:3306/testdb";
	String username = "javadev";
	String password = "Java@1234";
	
	String query1 = "update employee set salary = 10 where id = 1";
	String query2 = "update employee set salary = 10 where id = 2";
	 
	Connection con = DriverManager.getConnection(url,username,password);
	Statement st = con.createStatement();
	int row1 = st.executeUpdate(query1);
	int row2 = st.executeUpdate(query2);
	 
	System.out.println("rows affected "+ row1);
	System.out.println("rows affected "+ row2);
	 con.close();
}

public static void commit_demo2() throws Exception {
	
	String url = "jdbc:mysql://localhost:3306/testdb";
	String username = "javadev";
	String password = "Java@1234";
	
	String query1 = "update employee set salary = 0 where id = 1";
	String query2 = "updat employee set salary = 0 where id = 2"; //update is wrong here
	 
	Connection con = DriverManager.getConnection(url,username,password);
	con.setAutoCommit(false);
	Statement st = con.createStatement();
	
	int row1 = st.executeUpdate(query1);
	System.out.println("rows affected "+ row1);
	
	int row2 = st.executeUpdate(query2);
	System.out.println("rows affected "+ row2);
	
	if(row1 > 0 && row2 > 0) {
		con.commit();
	}
	 con.close();
}

//batch 

public static void batch_demo() throws Exception {
	
	String url = "jdbc:mysql://localhost:3306/testdb";
	String username = "javadev";
	String password = "Java@1234";
	
	String query1 = "update employee set salary = 1 where id = 1";
	String query2 = "update employee set salary = 1 where id = 2"; 
	String query3 = "update employee set salary = 1 where id = 3";
	String query4 = "update employee set salary = 1 where id = 4";
	 
	Connection con = DriverManager.getConnection(url,username,password);
	Statement st = con.createStatement();
	
	st.addBatch(query1);
	st.addBatch(query2);
	st.addBatch(query3);
	st.addBatch(query4);
	
	int[] result = st.executeBatch();
	
	for(int i:result) {
		System.out.println("rows affected " + i);
	}
	 con.close();
}

//rollback

public static void rollback_demo() throws Exception {
	
	String url = "jdbc:mysql://localhost:3306/testdb";
	String username = "javadev";
	String password = "Java@1234";
	
	String query1 = "update employee set salary = 2 where id = 1";
	String query2 = "update employee set salary = 2 where id = 2"; 
	String query3 = "update employee set salary = 2 where id = 3";
	String query4 = "update employee set salary = 2 where id = 4"; //if one wrong all back to before value
	 
	Connection con = DriverManager.getConnection(url,username,password);
	con.setAutoCommit(false);
	Statement st = con.createStatement();
	
	st.addBatch(query1);
	st.addBatch(query2);
	st.addBatch(query3);
	st.addBatch(query4);
	
	int[] result = st.executeBatch();
	
	for(int i:result) {
		if(i>0)
			continue;
		else
			con.rollback();
	}
	con.commit();
	 con.close();
}
}