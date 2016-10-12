/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author tlindblom
 */
public class DbFunctions {
    
      
      
    DbFunctions() {} //default empty constructor
    
/*****Functions for Database Connectivity*****/

    
   public void connect() {
       
   }
 //**********
   
   public void addBook(Connection conn) throws SQLException {
       Scanner in = new Scanner(System.in);
       String stmt = "";
       PreparedStatement pstmt;
       ResultSet rs;       
       
       System.out.println("What is the title of the book? ");
       String Title = in.nextLine();
       
       System.out.println("How many pages is the book? ");
       String pages = in.nextLine();
       
       System.out.println("What is the name of the writing group who wrote the book? ");
       String GroupName = in.nextLine();
       
       stmt = "Select * FROM writinggroups WHERE groupname = ?";
       pstmt = conn.prepareStatement(stmt);
       pstmt.setString(1, GroupName);     
       rs = pstmt.executeQuery();
        
       if(rs.getString("groupname") == null || !rs.getString("groupname").equalsIgnoreCase(GroupName)) 
       System.out.println("Who is the head writer? ");
       String HeadWriter = in.nextLine();
       
       System.out.println("What year was the writing group formed? ");
       String YearFormed = in.nextLine();
       
       System.out.println("What kinds of books does this group write? ");
       String Subject = in.nextLine();
       
       
   }

   
    
}
