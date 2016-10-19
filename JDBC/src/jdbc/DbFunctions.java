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
       int pages = 0;
       do { //make sure that number of pages inserted is valid
           try {
               pages = in.nextInt();
          
           } catch (java.util.InputMismatchException e) {
               System.out.print("That is not a valid number of pages. how many pages does the book have? ");
           }
           in.nextLine();
       } while (pages <= 0);
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
       int choice = 0;
       String GroupName = "";
       do { //make sure that group choice input is valid
           try {
               choice = in.nextInt();
               GroupName = groups.get(choice - 1);
          
           } catch (java.lang.IndexOutOfBoundsException e) {
               System.out.print("That is not a valid selection. Please choose a writing group from the choices above? ");
               choice = 0;
           } catch (java.util.InputMismatchException x) {
               System.out.print("That is not a valid selection. Please choose a writing group from the choices above? ");
               choice = 0; 
           } 
           
           in.nextLine();
       } while (choice <= 0);
       //******
       
       //***Get publisher name*** 
       //must pick a publisher that already exists in the database
       
       //Querying for the list of publishers for the user to choose from
       stmt = "Select publishername FROM publishers";
       pstmt = conn.prepareStatement(stmt);     
       rs = pstmt.executeQuery();
       
       //put the publishers in a list
       groups.clear();
       while (rs.next()) //may throw SQL excpetion (if rs is empty)
           groups.add(rs.getString("publishername"));
      
       System.out.println("Who is the publisher?");
       
       //display group name choices as options 1, 2, 3, etc.
       for (int i=0; i < groups.size(); i++) {
           System.out.println("   " + String.valueOf(i+1) + ") " + groups.get(i));
       }  
       
       System.out.print("Enter the number corresponding to an option above: ");
       choice = 0;
       String pubName = "";
       do { //make sure that choice of publisher is valid
           try {
               choice = in.nextInt();
               pubName = groups.get(choice - 1); //set publishername based on user choice
          
           } catch (java.lang.IndexOutOfBoundsException e) {
               System.out.print("That is not a valid selection. Please choose a publisher from the choices above? ");
               choice = 0;
           } catch (java.util.InputMismatchException x) {
               System.out.print("That is not a valid selection. Please choose a publisher from the choices above? ");
               choice = 0; 
           } 
           
           in.nextLine();
       } while (choice <= 0);
       
       System.out.print("What year was the book published? ");
       int pubYear = 0;
       do { //make sure that the entered year is valid
           try {
               pubYear = in.nextInt();
          
           } catch (java.util.InputMismatchException e) {
               System.out.print("That is not a valid year. What year was the book published? ");
           }
           in.nextLine();
       } while (pubYear <= 0);
       
       //******
       //return;
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
           System.out.println("Book added!");
       }
       
       catch (java.sql.SQLIntegrityConstraintViolationException e) { 
           System.out.println("Sorry it looks like that book is already stored!");
           System.out.println("Please input another book.");
           addBook(conn);
           return;
       }
       
       
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
            
        } catch (Exception e) {
            //Handle errors for Class.forName
            e.printStackTrace();
            
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
       int choice = 0;
       String GroupName = "";
       do { //make sure that group choice input is valid
           try {
               choice = in.nextInt();
               GroupName = groups.get(choice - 1);
          
           } catch (java.lang.IndexOutOfBoundsException e) {
               System.out.print("That is not a valid selection. Please choose a writing group from the choices above? ");
               choice = 0;
           } catch (java.util.InputMismatchException x) {
               System.out.print("That is not a valid selection. Please choose a writing group from the choices above? ");
               choice = 0; 
           } 
           
           in.nextLine();
       } while (choice <= 0);
       
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
           
           //Make sure phone is valid
           String pubPhone = "";
           do {
           System.out.print("What is the new publisher's phone? Enter as XXX-XXX-XXXX: ");
           pubPhone = in.nextLine(); 
           
           } while (pubPhone.length() < 12 && !pubPhone.contains("-"));
           
           //Make sure email is valid
           String pubEmail = "";
           do { 
               System.out.print("What is the new publisher's email? ");
               pubEmail = in.nextLine();
           
           } while (!pubEmail.contains("@"));
          
           
            //***Get old publisher name*** 
       
            //Querying for the list of publishers for the user to choose from
            stmt = "Select publishername FROM publishers";
            pstmt = conn.prepareStatement(stmt);     
            rs = pstmt.executeQuery();
       
            //put the publishers in a list
            while (rs.next())
                groups.add(rs.getString("publishername"));

            System.out.println("Who is the old publisher?");

            //display group name choices as options 1, 2, 3, etc.
            for (int i=0; i < groups.size(); i++) {
                System.out.println("   " + String.valueOf(i+1) + ") " + groups.get(i));
            }  
       
       System.out.print("Enter the number corresponding to an option above: ");
      
       //******
       int choice = 0;
       String oldPub = "";
       do { //make sure that choice of publisher is valid
           try {
               choice = in.nextInt();
               oldPub = groups.get(choice - 1); //set publishername based on user choice
          
           } catch (java.lang.IndexOutOfBoundsException e) {
               System.out.print("That is not a valid selection. Please choose a publisher from the choices above? ");
               choice = 0;
           } catch (java.util.InputMismatchException x) {
               System.out.print("That is not a valid selection. Please choose a publisher from the choices above? ");
               choice = 0; 
           } 
           
           in.nextLine();
       } while (choice <= 0);
       
       //***Insert new publisher into the DB
            stmt = "INSERT INTO publishers (publishername, publisheraddress, publisherphone, publisheremail)"
                    + " VALUES (?, ?, ?, ?)";

            pstmt = conn.prepareStatement(stmt);
            pstmt.setString(1, newPub);
            pstmt.setString(2, pubAddress);
            pstmt.setString(3, pubPhone);
            pstmt.setString(4, pubEmail);

        try {
          pstmt.executeUpdate();
                
        } catch (java.sql.SQLIntegrityConstraintViolationException e) {
           System.out.println("Sorry it looks like that publisher is already stored!");
           System.out.println("Please add a new publisher.");
           publisherChange(conn);
       }
            //******
      
       
       //***Change books by oldPub to be published by newPub
       stmt = "UPDATE books SET publishername = ? WHERE publishername = ?";
       pstmt = conn.prepareStatement(stmt);
       pstmt.setString(1, newPub);
       pstmt.setString(2, oldPub);
       
       
   
        pstmt.executeUpdate();
        System.out.println("Updated!");   
       //******  
       
       //***Allow user to repeat action if they would like
       
       System.out.print("Would you like to update another publisher? Enter 'y' or 'n'. ");
       String decision = in.next();
       
       if (decision.equalsIgnoreCase("y")){
           publisherChange(conn);
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
}
