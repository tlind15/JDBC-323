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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.HashMap;

public class DbFunctions {
    
      
      
    DbFunctions() {} //default empty constructor
    
  //function allows user to add a new book to the database  
   public void addBook(Connection conn) {
       /*Add a book to the database written by an existing writing group
         and published by an existing publisher*/
       
       
       Scanner in = new Scanner(System.in);
       String stmt = "";
       ArrayList<String> groups = new ArrayList<String> ();
       PreparedStatement pstmt = null;
       ResultSet rs; 
       
       System.out.println("\nAdd a Book \n");
       
       //***Get Title***
       System.out.print("What is the title of the book? ");
       String Title = in.nextLine();
       //******
       
       //***Get Page count***
       System.out.print("How many pages is the book? ");
       int pages = Integer.valueOf(in.nextLine()); //in.nextLine returns a string a we want pages as an integer
       //******
       
       try {
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
       
       //display group name choices as options 1, 2, 3, etc.
       for (int i=0; i < groups.size(); i++) {
           System.out.println("   " + String.valueOf(i+1) + ") " + groups.get(i));
       }  
       
       System.out.print("Enter the number corresponding to an option above: ");
       int choice = Integer.valueOf(in.next());
       //eventually need to account for invalid choice
       
       String GroupName = groups.get(choice - 1); //set groupname based on user choice   
       
       System.out.println(GroupName);
       //******
       
       //***Get publisher name*** 
       //must pick a publisher that already exists in the database
       
       //Querying for the list of publishers for the user to choose from
       stmt = "Select publishername FROM publishers";
       pstmt = conn.prepareStatement(stmt);     
       rs = pstmt.executeQuery();
       
       //put the groupnames in a list
       groups.clear();
       while (rs.next())
           groups.add(rs.getString("publishername"));
      
       System.out.println("Who is the publisher?");
       
       //display group name choices as options 1, 2, 3, etc.
       for (int i=0; i < groups.size(); i++) {
           System.out.println("   " + String.valueOf(i+1) + ") " + groups.get(i));
       }  
       
       System.out.print("Enter the number corresponding to an option above: ");
       choice = Integer.valueOf(in.next());
       //eventually need to account for invalid choice
       
       String pubName = groups.get(choice - 1); //set publishername based on user choice
       
       System.out.print("What year was the book published? ");
       int pubYear = Integer.valueOf(in.next());
       
       System.out.println(pubName);
       //******
       
       //***Add book data to database***
       stmt = "INSERT INTO books (groupname, booktitle, publishername, yearpublished, numberpages) VALUES (?, ?, ?, ?, ?)";
       pstmt = conn.prepareStatement(stmt);
       pstmt.setString(1, GroupName);
       pstmt.setString(2, Title);
       pstmt.setString(3, pubName);
       pstmt.setInt(4, pubYear);
       pstmt.setInt(5, pages);
       
       //***If user violates primary key or any uniqueness constraint***
       try {
           pstmt.executeUpdate();
       }
       
       catch (java.sql.SQLIntegrityConstraintViolationException e) { 
           System.out.println("Sorry it looks like that book is already stored!");
           System.out.println("Please input another book.");
           addBook(conn);
           return;
       }
       
       System.out.println("Book added!");
       System.out.print("Would you like to add another book? Enter 'y' or 'n'. ");
       String decision = in.next();
       
       if (decision.equalsIgnoreCase("y")){
           addBook(conn);
       }
       //******
       
       rs.close();
       pstmt.close();
       conn.close();
       
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
            return;
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
            return;
        } finally {
            //finally block used to close resources
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException se2) {}// nothing we can do
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }
   }
   
   public void removeBook(Connection conn) {
       
       Scanner in = new Scanner(System.in);
       String stmt = "";
       ArrayList<String> groups = new ArrayList<String> ();
       PreparedStatement pstmt = null;
       ResultSet rs;  
       
       System.out.println("\nRemove a Book \n");
       
       //***Get Title***
       System.out.print("What is the title of the book? ");
       String Title = in.nextLine();
       //******
       
       try {
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
       
       //display group name choices as options 1, 2, 3, etc.
       for (int i=0; i < groups.size(); i++) {
           System.out.println("   " + String.valueOf(i+1) + ") " + groups.get(i));
       }  
       
       System.out.print("Enter the number corresponding to an option above: ");
       int choice = Integer.valueOf(in.next());
       //eventually need to account for invalid choice
       
       String GroupName = groups.get(choice - 1); //set groupname based on user choice
       
       //***Delete Book from Database***
       stmt = "DELETE FROM books WHERE groupname = ? AND booktitle = ?";
       pstmt = conn.prepareStatement(stmt);
       pstmt.setString(1, GroupName);
       pstmt.setString(2, Title);
       
       pstmt.executeUpdate();
        //******
       
       //***Allow user to repeat action if they would like
       System.out.println("Deleted!");
       System.out.print("Would you like to remove another book? Enter 'y' or 'n'. ");
       String decision = in.next();
       
       if (decision.equalsIgnoreCase("y")){
           removeBook(conn);
           
       } 
       
       rs.close();
       pstmt.close();
       conn.close();
       
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
            return;
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
            return;
        } finally {
            //finally block used to close resources
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException se2) {}// nothing we can do
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }
   } 
   
   public void publisherChange(Connection conn) {
       Scanner in = new Scanner(System.in);
       String stmt = "";
       ArrayList<String> groups = new ArrayList<String> ();
       PreparedStatement pstmt = null;
       ResultSet rs = null;  
       
       System.out.println("\nPublisher Update\n");
       
       try {
           //***Get new publisher info
           System.out.print("What is the new publisher's name? ");
           String newPub = in.nextLine();
           
           System.out.print("What is the new publisher's address? ");
           String pubAddress = in.nextLine();
           
           System.out.print("What is the new publisher's phone? Enter as XXX-XXX-XXXX: ");
           String pubPhone = in.nextLine();
           
           System.out.print("What is the new publisher's email? ");
           String pubEmail = in.nextLine();
          
           
            //***Get old publisher name*** 
       
            //Querying for the list of publishers for the user to choose from
            stmt = "Select publishername FROM publishers";
            pstmt = conn.prepareStatement(stmt);     
            rs = pstmt.executeQuery();
       
            //put the groupnames in a list
            groups.clear();
            while (rs.next())
                groups.add(rs.getString("publishername"));

            System.out.println("Who is the old publisher?");

            //display group name choices as options 1, 2, 3, etc.
            for (int i=0; i < groups.size(); i++) {
                System.out.println("   " + String.valueOf(i+1) + ") " + groups.get(i));
            }  
       
       System.out.print("Enter the number corresponding to an option above: ");
       int choice = Integer.valueOf(in.next());
       //eventually need to account for invalid choice
       
       String oldPub = groups.get(choice - 1); //set publishername based on user choice
       //******
       
       //***Create the new publisher using all of the same info from the old publisher but just using the new name***
            stmt = "INSERT INTO publishers (publishername, publisheraddress, publisherphone, publisheremail)"
                    + " VALUES (?, ?, ?, ?)";

            pstmt = conn.prepareStatement(stmt);
            pstmt.setString(1, newPub);
            pstmt.setString(2, pubAddress);
            pstmt.setString(3, pubPhone);
            pstmt.setString(4, pubEmail);

            pstmt.executeUpdate();
            //******
      
       
       //***Change books by oldPub to be published by newPub
       stmt = "UPDATE books SET publishername = ? WHERE publishername = ?";
       pstmt = conn.prepareStatement(stmt);
       pstmt.setString(1, newPub);
       pstmt.setString(2, oldPub);
       
       pstmt.executeUpdate();
       //******  
       
       rs.close();
       pstmt.close();
       conn.close();
       
        } catch (SQLException se) {
            //Handle errors for JDBC
            se.printStackTrace();
            return;
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
            return;
        } finally {
            //finally block used to close resources
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (SQLException se2) {}// nothing we can do
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }
   }
}
