/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package group_writing;

/**
 *
 * @author tlindblom
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;

public class DbFunctions {
    
      
      
    DbFunctions() {} //default empty constructor
    
  //function allows user to add a new book to the database  
   public void addBook(Connection conn) throws SQLException {
       Scanner in = new Scanner(System.in);
       String stmt = "";
       ArrayList<String> groups = new ArrayList<String> ();
       PreparedStatement pstmt;
       ResultSet rs;       
       
       //***Get Title***
       System.out.print("What is the title of the book? ");
       String Title = in.nextLine();
       //******
       
       //***Get Page count***
       System.out.print("How many pages is the book? ");
       int pages = Integer.valueOf(in.nextLine()); //in.nextLine returns a string a we want pages as an integer
       //******
       
       //***Get groupname*** 
       //must pick a name that already exists in the database
       
       //Querying for the list of writing groups for the user to choose from
       stmt = "Select groupname FROM writinggroups";
       pstmt = conn.prepareStatement(stmt);     
       rs = pstmt.executeQuery();
       
       //put the groupnames in a list
       while (rs.next())
           groups.add(rs.getString("groupname"));
      
       System.out.println("Which writing group wrote the book?");
       
       //display group name choices as options 1, 2, and 3
       for (int i=0; i < groups.size(); i++) {
           System.out.println("   " + String.valueOf(i+1) + ") " + groups.get(i));
       }  
       
       System.out.print("Enter 1, 2, or 3 to pick an option: ");
       int choice = Integer.valueOf(in.next());
       //eventually need to account for invalid choice
       
       String GroupName = groups.get(choice - 1); //set groupname based on user choice   
       
       System.out.println(GroupName);
       //******
       
       
  }
}