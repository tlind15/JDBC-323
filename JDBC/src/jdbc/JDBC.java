/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jdbc_final;

/**
 *
 * @author tlindblom
 */

import java.sql.*;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.ArrayList;

public class JDBC_final {//  Database credentials
    static String USER = "username";
    static String PASS = "password";
    static String DBNAME = "database";

    static final String displayFormat="%-25s%-25s%-25s%-25s\n";
    static final String displayFormat2 = "%-25s%-25s%-25s%-25s%-25s\n";
    static final String JDBC_DRIVER = "org.apache.derby.jdbc.ClientDriver";
    static String DB_URL = "jdbc:derby://localhost:1527/";
    static Connection conn;

    /**
     * Takes the input string and outputs"N/A" if the string is empty or null
     * @param input the String to be mapped
     * @return either the String or "N/A" as appropriate.
     */
    public static String dispNull (String input)
    {
        //because of short circuiting, if it's null, it never checks the length.
        if (input == null || input.length() == 0)
            return "N/A";
        else
            return input;
    }

    public static void main(String[] args)
    {

        Statement statement = null;
        String sqlCode ;
        ResultSet result = null;
        Scanner input = new Scanner(System.in);
        boolean end = false;

        Scanner in = new Scanner(System.in);
        System.out.print("Name of the database (not the user account): ");
        DBNAME = in.nextLine();
        System.out.print("Database user name: ");
        USER = in.nextLine();
        System.out.print("Database password: ");
        PASS = in.nextLine();
        //Constructing the database URL connection string
        DB_URL = DB_URL + DBNAME + ";user="+ USER + ";password=" + PASS;

        try {
            //STEP 2: Register JDBC driver
            Class.forName("org.apache.derby.jdbc.ClientDriver");

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL);



            int choice;


            do
            {
                System.out.println("\t\t\t*Main Menu*");
                System.out.println("what do you want to do? \n1\\WritingGroups Menu.\n2\\Publisher Menu." +
                        "\n3\\Book Menu.\n0\\Exit.");
                choice = getInt();
                switch (choice)
                {
                    case 1:
                        // WorkGroup case.
                        do
                        {
                            System.out.println("what do you want to do? \n1\\List all Writing groups." +
                                    "\n2\\List all the data for a writing group specified by the user." +
                                    "\n0\\Back to Main Menu");
                            choice = getInt();
                            switch (choice)
                            {
                                case 1:
                                    //List All WritingGroups
                                    listWritingGroups();
                                    break;

                                case 2:
                                    //List All Info of one WritingGroup
                                    showSpecificWritingGroup();
                                    break;

                                case 0:
                                    // Go back to Main Menu.
                                    end = true;
                                    break;

                                default:
                                    System.out.println("Invalid Input please try again!!\n\n");
                            }

                        }

                        while(!end);

                        end = false;
                        break;

                    case 2:
                        // Publisher case
                        do
                        {
                            System.out.println("what do you want to do? \n1\\List all publishers." +
                                    "\n2\\List all the data for a publisher specified by the user." +
                                    "\n3\\Insert A new publisher\n4\\Insert and Replace Existing publisher\n" +
                                    "0\\Back to Main Menu.");
                            choice = getInt();
                            switch (choice)
                            {
                                case 1:
                                    // List All Publisher
                                    listPublishers();
                                    break;

                                case 2:
                                    //List All Info of one publisher.
                                    showSpecificPublisher();
                                    break;
                                case 3:
                                    //Insert a publisher
                                    insertPublisher();
                                    break;

                                case 4:
                                    //Insert and Update publisher
                                    insertNUpdatePublisher();
                                    break;

                                case 0:
                                    // Go back to Main Menu.
                                    end = true;
                                    break;

                                default:
                                    System.out.println("Invalid Input please try again!!\n\n");
                            }
                        }
                        while (!end);

                        end = false;
                        break;

                    case 3:
                        //Book case
                        do
                        {
                            System.out.println("what do you want to do? \n1\\List all books." +
                                    "\n2\\List all the data for a book specified by the user." +
                                    "\n3\\Insert A new book\n4\\Remove a book\n" +
                                    "0\\Back to main Menu.");
                            choice = getInt();
                            switch (choice)
                            {
                                case 1:
                                    // List All Books
                                    listBooks();
                                    break;

                                case 2:
                                    // List All Info for one Book
                                    showSpecificBook();
                                    break;

                                case 3:
                                    // Add a new book
                                    addBook();
                                    break;

                                case 4:
                                    // Remove an Existing book
                                    removeBook();
                                    break;

                                case 0:
                                    // Go back to Main Menu.
                                    end = true;
                                    break;

                                default:
                                    System.out.println("Invalid Input please try again!!\n\n");

                            }
                        }
                        while (!end);
                        end = false;
                        break;


                    case 0:
                        end = true;
                        break;

                    default:
                        System.out.println("Invalid Input Please Try again!! \n\n");

                }
            }
            while (!end);

            statement.close();
            conn.close();
        } catch (SQLException se) {
            System.out.println("Sorry those are not the correct Database Credentials!");
            //Handle errors for JDBC
            //se.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //finally block used to close resources
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException se2) {
            }// nothing we can do
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try
        }//end try
        System.out.println("Goodbye!");
    }//end main


    public static int getInt()
    {
        Scanner in = new Scanner(System.in);
        boolean valid = false;
        int validNum = 0;
        while( !valid )
        {

            if( in.hasNextInt() )
            {
                validNum = in.nextInt();
                valid = true;
            }
            else
            {
                in.next();
                System.out.println("Invalid Input please try again!!\n\n");
            }
        }
        return validNum;
    }

    public static boolean getCharYN()
    {
        Scanner in = new Scanner(System.in);
        boolean valid = false;
        boolean end=true;
        String validChar="";

        while( !valid )
        {
            validChar = in.nextLine();
            if(validChar.equalsIgnoreCase("Y"))
            {
                valid = true;
                end = true;
            }
            else if (validChar.equalsIgnoreCase("N"))
            {
                valid = true;
                end = false;
            }
            else
            {
                System.out.println("Invalid Input please try again!!\n\n");
            }

        }
        return end;
    }

    //List All WritingGroups 1 1
    public static void listWritingGroups()
    {
        Statement statement;
        ResultSet resultSet;

        try
        {
            // Create statement
            statement = conn.createStatement();

            // Execute writing group query
            String sqlSelect = "SELECT groupname FROM WritingGroups";
            resultSet = statement.executeQuery(sqlSelect);

            System.out.println("WriterGroups Names:");
            // Extract resultSet from query and display all writing groups
            while (resultSet.next())
            {
                // Display the resultSet
                System.out.println(resultSet.getString("groupname"));
            }
        }
        catch(SQLException se)
        {
            // If writing group does not exist, print out the error
            System.err.println("Error: WritingGroups DataBase dose not exist.");
        }
    }

    //List information of a Specific Writing Group 1 2
    public static void showSpecificWritingGroup()
    {
        String sqlSpecify = "SELECT * FROM WritingGroups WHERE " + "GroupName=?";

        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try
        {
            statement = conn.prepareStatement(sqlSpecify);

            String specifier = null;

            //opens scanner
            Scanner in = new Scanner(System.in);

            //ask user for input
            System.out.print("Enter Group Name: ");
            specifier = in.nextLine();
            statement.setString(1, specifier.toUpperCase());

            resultSet = statement.executeQuery();

            resultSet.next();


            // Grabs the string from spcified row/column
            String GroupName = resultSet.getString("GroupName");
            String HeadWriter = resultSet.getString("HeadWriter");
            String YearFormed = resultSet.getString("YearFormed");
            String Subject = resultSet.getString("Subject");

            // prints out data for group name
            System.out.println( "GroupName: " + GroupName+"\nHead Writer: " + HeadWriter+
                    "\nYear Formed: " + YearFormed+
                    "\nSubject: " + Subject);
        }
        catch(SQLException se)
        {
            // If the specified writing group does not exist, print out the error
            System.err.println("Error: The Writing Group does not exist.");
        }
        finally
        {
            // Close statement
            try
            {
                if (statement != null)
                    statement.close();
            }
            catch(SQLException se)
            {
                // If the statement cannot be closed, print out the error
                System.err.println("Error: Cannot close statement.");
            }

            // Close results
            try
            {
                if (resultSet != null)
                    resultSet.close();
            }
            catch(SQLException se)
            {
                // If the results cannot be closed, print out the error
                System.err.println("Error: Cannot close results.");
            }
        }
    }

    //List All Publishers case 2 1
    public static void listPublishers()
    {
        try
        {
            Statement statement;
            ResultSet result;
            // Create statement
            System.out.println("Creating statement...");
            statement = conn.createStatement();

            // Execute publisher query
            String sqlCode = "SELECT PublisherName FROM publishers";
            result = statement.executeQuery(sqlCode);

            // Extract results from query and display all publishers
            System.out.println("Publishers Name:");
            while (result.next())
            {
                System.out.println(result.getString("publishername"));

            }
            result.close();
        }
        catch (SQLException sqlE)
        {
            //System.err.println("Connection to the DB failed!!\n Please Try Again!!");
            sqlE.printStackTrace();
        }

    }

    //List information of a specific publisher 2 2
    public static void showSpecificPublisher ()
    {
        String selectstatment = "SELECT * FROM publishers WHERE publishername = ?";
        PreparedStatement statement = null;
        ResultSet result = null;

        try
        {
            statement = conn.prepareStatement(selectstatment);

            //Creating Scanner
            Scanner input =new Scanner(System.in);

            System.out.println("Enter the Name of the publisher to find");
            String publisherChosen = input.nextLine();
            statement.setString(1,publisherChosen.toUpperCase());

            result = statement.executeQuery();

            result.next();


            //Grabbing from specified Column
            String publisher_Name = result.getString("publishername");
            String publisher_Address = result.getString("publisheraddress");
            String publisher_Phone = result.getString("publisherphone");
            String publisher_Email = result.getString("publisheremail");

            System.out.println(  "PublisherName: "+ dispNull(publisher_Name)+
                    "\nPublisherAddress: " +dispNull(publisher_Address)+
                    "\nPublisherPhone: "+ dispNull(publisher_Phone)+
                    "\nPublisherEmail: "+dispNull(publisher_Email));



        }
        catch (SQLException sqlE)
        {
            System.err.println("Error:PublisherName is invalid.");
        }
        finally
        {
            // Close statement
            try
            {
                if (statement != null)
                    statement.close();
            }
            catch (SQLException se)
            {
                // If the statement cannot be closed, print out the error
                System.err.println("Error: Cannot close statement.");
            }

            // Close results
            try
            {
                if (result != null)
                    result.close();
            }
            catch (SQLException se)
            {
                // If the results cannot be closed, print out the error
                System.err.println("Error: Cannot close results.");
            }
        }
    }

    //List information of a specific publisher 2 3
    public static void insertPublisher()
    {
        String sqlInsert =  "INSERT INTO publishers (publishername, publisheraddress, publisherphone, publisheremail) VALUES " +
                "(?, ?, ?, ?)";
        PreparedStatement statement = null;
        boolean end = false;

        try
        {
            statement = conn.prepareStatement(sqlInsert);
            //Creating Scanner
            do
            {
                Scanner input = new Scanner(System.in);

                System.out.println("Enter Name of Publisher :");
                String publisher_Name = input.nextLine();
                statement.setString(1,publisher_Name.toUpperCase());

                System.out.println("Enter Publisher's Address");
                String publisher_Address = input.nextLine();
                statement.setString(2, publisher_Address.toUpperCase());

                System.out.println("Enter Publisher's Phone");
                String publisher_Phone = input.nextLine();
                statement.setString(3, publisher_Phone.toUpperCase());

                System.out.println("Enter Publisher's E-Mail");
                String publisher_Email = input.nextLine();
                statement.setString(4, publisher_Email.toUpperCase());

                statement.executeUpdate();

                System.out.println("Do you want to enter another publisher");
                end = getCharYN();

            }
            while (end);


        }
        catch(SQLException sqlE)
        {
            System.err.println("Error: the New Publisher wasn't inserted. Please make sure the publisher Database" +
                    " exists and the information is not duplicated.");

        }
        finally
        {
            // Close statement
            try
            {
                if (statement != null)
                    statement.close();
            }
            catch(SQLException se)
            {
                // If the statement cannot be closed, print out error
                System.err.println("Error: Cannot close statement.");
            }
        }

    }

    //List information of a specific publisher 2 4
    public static void insertNUpdatePublisher()
    {
        String sqlInsert =  "INSERT INTO publishers (publishername, publisheraddress, publisherphone, publisheremail) VALUES " +
                "(?, ?, ?, ?)";
        PreparedStatement statement = null;
        boolean end = false;
        String publisher_Name = null;

        try
        {
            statement = conn.prepareStatement(sqlInsert);
            //Creating Scanner

            Scanner input = new Scanner(System.in);

            System.out.println("Enter Name of Publisher :");
            publisher_Name = input.nextLine();
            statement.setString(1,publisher_Name.toUpperCase());

            System.out.println("Enter Publisher's Address");
            String publisher_Address = input.nextLine();
            statement.setString(2, publisher_Address.toUpperCase());

            System.out.println("Enter Publisher's Phone");
            String publisher_Phone = input.nextLine();
            statement.setString(3, publisher_Phone.toUpperCase());

            System.out.println("Enter Publisher's E-Mail");
            String publisher_Email = input.nextLine();
            statement.setString(4, publisher_Email.toUpperCase());

            statement.executeUpdate();

        }
        catch(SQLException sqlE)
        {
            //System.out.println("Error: the New Publisher wasn't inserted. Please make sure the publisher Database" +
            //" exists and the information is not duplicated.");
            sqlE.printStackTrace();
        }
        finally
        {
            // Close statement
            try
            {
                if (statement != null)
                    statement.close();
            }
            catch(SQLException se)
            {
                // If the statement cannot be closed, print out error
                System.err.println("Error: Cannot close statement.");
            }
        }
        //Update all the books
        String sqlUpdate = "UPDATE books SET publishername = ? WHERE publishername = ?";
        statement = null;
        try
        {
            statement = conn.prepareStatement(sqlUpdate);
            String updater = null;

            Scanner in = new Scanner(System.in);

            // Set the Prepared Statement
            statement.setString(1, publisher_Name.toUpperCase());

            // Get publisher to replace from user
            System.out.print("Enter Publisher to Replace: ");
            updater = in.nextLine();
            statement.setString(2, updater.toUpperCase());

            System.out.print("Updating publishers...");
            statement.executeUpdate();
            System.out.println("done!");
        }
        catch (SQLException sqlE) {
            System.err.println("Error: Operation not complete.\n" +
                    "Make sure both Publisher and NewPublisher exist");
        }
        finally
        {
            // Close statement
            try
            {
                if (statement != null)
                    statement.close();
            }
            catch(SQLException se)
            {
                // If the statement cannot be closed, print out error
                System.err.println("Error: Cannot close statement.");
            }
        }

    }

    //List All Books 3 1
    public static void listBooks()
    {
        try
        {
            Statement statement;
            ResultSet result;
            // Create statement

            System.out.println("Creating statement...");

            statement = conn.createStatement();

            // Execute publisher query
            String sqlCode = "SELECT booktitle FROM books";
            result = statement.executeQuery(sqlCode);

            // Extract results from query and display all publishers
            System.out.println("Book Titles:");
            while (result.next())
            {
                System.out.println(result.getString("booktitle"));

            }
            result.close();
        }
        catch (SQLException sqlE)
        {
            System.err.println("Connection to the DB failed!!\n Please Try Again!!");
        }
    }

    //List information of a specific book 3 2
    public static void showSpecificBook()
    {
        //select statement to specify the publisher's name
        String sqlSpecify = "SELECT * FROM writinggroups INNER JOIN books USING(groupname) INNER JOIN "
                + "publishers USING(publishername) WHERE "
                + "GroupName=?" + " AND "
                + "BookTitle=?" /*+ " AND "
                + "PublisherName=?"*/;

        PreparedStatement statement = null;
        ResultSet result = null;
        try
        {
            statement = conn.prepareStatement(sqlSpecify);

            String specifier;

            //opens scanner
            Scanner in = new Scanner(System.in);

            //ask user for input
            System.out.print("Enter Group Name: ");
            specifier = in.nextLine();
            statement.setString(1, specifier.toUpperCase());

            System.out.print("Enter Book Title: ");
            specifier = in.nextLine();
            statement.setString(2, specifier.toUpperCase());

//            System.out.print("Enter Publisher Name: ");
//            specifier = in.nextLine();
//            statement.setString(3, specifier.toUpperCase());


            result = statement.executeQuery();

            result.next();
            String[] book = {result.getString("GroupName"),result.getString("headwriter"),result.getString("yearformed"),
                    result.getString("subject"),result.getString("BookTitle"),result.getString("YearPublished"),
                    result.getString("NumberPages"),result.getString("PublisherName"),result.getString("publisheraddress"),
                    result.getString("publisherphone"),result.getString("publisheremail")};
            String[] header = {"GroupName","HeadWriter","YearFormed","Subject","BookTitle ","YearPublished",
            "NumberPages","PublisherName","PublisherAddress","PublisherPhone","PublisherEmail"};

            for (int i = 0; i < book.length; i++) {
                System.out.println(header[i] +" : "+ book[i] );
            }
        }
        catch(SQLException se)
        {
            System.err.println("Error: Book does not exist. Please make sure that "
                    + "Writing Group, Publisher, and Book Title are correct.");

        }
        finally
        {
            // Close statement
            try
            {
                if (statement != null)
                    statement.close();
            }
            catch(SQLException se)
            {
                System.err.println("Error: Cannot close statement.");
            }

            // Close results
            try
            {
                if (result != null)
                    result.close();
            }
            catch(SQLException se)
            {
                System.err.println("Error: Cannot close results.");
            }
        }
    }

    //Add a Book 3 3
    public static void addBook()
    {
       /*Add a book to the database written by an existing writing group
         and published by an existing publisher*/


        Scanner in = new Scanner(System.in);
        String stmt = "";
        ArrayList<String> groups = new ArrayList<String> ();
        PreparedStatement statement = null;
        ResultSet resultSet;

        System.out.println("\nAdd a Book \n");

        //***Get Title***
        System.out.print("What is the title of the book? ");
        String Title = in.nextLine().toUpperCase();
        //******

        //***Get Page count***
        System.out.print("How many pages is the book? ");
        int pages = 0;
        do
        { //make sure that number of pages inserted is valid
            try
            {
                pages = in.nextInt();

            }
            catch (InputMismatchException e)
            {
                System.out.print("That is not a valid number of pages. how many pages does the book have? ");
            }
            in.nextLine();
        }
        while (pages <= 0);
        //******

        try
        {
            //***Get groupname***
            //must pick a name that already exists in the database

            //Querying for the list of writing groups for the user to choose from
            stmt = "Select groupname FROM writinggroups";
            statement = conn.prepareStatement(stmt);
            resultSet = statement.executeQuery();

            //put the groupnames in a list
            while (resultSet.next())
                groups.add(resultSet.getString("groupname"));

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

                } catch (IndexOutOfBoundsException e) {
                    System.out.print("That is not a valid selection. Please choose a writing group from the choices above? ");
                    choice = 0;
                } catch (InputMismatchException x) {
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
            statement = conn.prepareStatement(stmt);
            resultSet = statement.executeQuery();

            //put the publishers in a list
            groups.clear();
            while (resultSet.next()) //may throw SQL excpetion (if resultSet is empty)
                groups.add(resultSet.getString("publishername"));

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

                } catch (IndexOutOfBoundsException e) {
                    System.out.print("That is not a valid selection. Please choose a publisher from the choices above? ");
                    choice = 0;
                } catch (InputMismatchException x) {
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

                } catch (InputMismatchException e) {
                    System.out.print("That is not a valid year. What year was the book published? ");
                }
                in.nextLine();
            } while (pubYear <= 0);

            //******
            //return;
            //***Add book data to database***
            stmt = "INSERT INTO books (groupname, booktitle, publishername, yearpublished, numberpages) VALUES (?, ?, ?, ?, ?)";
            statement = conn.prepareStatement(stmt);
            statement.setString(1, GroupName.toUpperCase());
            statement.setString(2, Title.toUpperCase());
            statement.setString(3, pubName.toUpperCase());
            statement.setInt(4, pubYear);
            statement.setInt(5, pages);

            //***If user violates primary key or any uniqueness constraint***
            try {
                statement.executeUpdate();
                System.out.println("Book added!");
            }

            catch (SQLIntegrityConstraintViolationException e) {
                System.out.println("Sorry it looks like that book is already stored!");
                System.out.println("Please input another book.");
                addBook();
                return;
            }


            System.out.print("Would you like to add another book? Enter 'y' or 'n'. ");
            String decision = in.next();

            if (decision.equalsIgnoreCase("y")){
                addBook();
            }
            //******

            resultSet.close();
            statement.close();
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
                if (statement != null) {
                    statement.close();
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

    //Remove a book 3 4
    public static void removeBook()
    {
        Scanner in = new Scanner(System.in);
        String stmt = "";
        ArrayList<String> groups = new ArrayList<String> ();
        PreparedStatement pstmt = null;
        ResultSet rs;

        System.out.println("\nRemove a Book \n");
        try
        {

            //***Get Title***
            //Querying for the list of book titles for the user to choose from
            stmt = "Select booktitle FROM books";
            pstmt = conn.prepareStatement(stmt);
            rs = pstmt.executeQuery();

            //put the titles in a list
            groups.clear();
            while (rs.next())
                groups.add(rs.getString("booktitle"));

            System.out.println("What is the title of the book?");

            //display group name choices as options 1, 2, 3, etc.
            for (int i=0; i < groups.size(); i++)
            {
                System.out.println("   " + String.valueOf(i+1) + ") " + groups.get(i));
            }

            System.out.print("Enter the number corresponding to an option above: ");
            int choice = 0;
            String Title = "";
            do
            { //make sure that title choice input is valid
                try
                {
                    choice = in.nextInt();
                    Title = groups.get(choice - 1);

                } catch (IndexOutOfBoundsException e)
                {
                    System.out.print("That is not a valid selection. Please choose a writing group from the choices above? ");
                    choice = 0;
                } catch (InputMismatchException x)
                {
                    System.out.print("That is not a valid selection. Please choose a writing group from the choices above? ");
                    choice = 0;
                }

                in.nextLine();
            } while (choice <= 0);


            //***Get groupname***
            //Querying for the list of writing groups for the user to choose from
            stmt = "Select groupname FROM writinggroups";
            pstmt = conn.prepareStatement(stmt);
            rs = pstmt.executeQuery();

            //put the groupnames in a list
            groups.clear();
            while (rs.next())
                groups.add(rs.getString("groupname"));

            System.out.println("Which writing group wrote the book?");

            //display group name choices as options 1, 2, 3, etc.
            for (int i=0; i < groups.size(); i++) {
                System.out.println("   " + String.valueOf(i+1) + ") " + groups.get(i));
            }

            System.out.print("Enter the number corresponding to an option above: ");
            choice = 0;
            String GroupName = "";
            do { //make sure that group choice input is valid
                try {
                    choice = in.nextInt();
                    GroupName = groups.get(choice - 1);

                } catch (IndexOutOfBoundsException e) {
                    System.out.print("That is not a valid selection. Please choose a writing group from the choices above? ");
                    choice = 0;
                } catch (InputMismatchException x) {
                    System.out.print("That is not a valid selection. Please choose a writing group from the choices above? ");
                    choice = 0;
                }

                in.nextLine();
            } while (choice <= 0);

            //***Delete Book from Database***
            stmt = "DELETE FROM books WHERE groupname = ? AND booktitle = ?";
            pstmt = conn.prepareStatement(stmt);
            pstmt.setString(1, GroupName.toUpperCase());
            pstmt.setString(2, Title.toUpperCase());

            pstmt.executeUpdate();
            //******

            //***Allow user to repeat action if they would like
            System.out.println("Deleted!");
            System.out.print("Would you like to remove another book? Enter 'y' or 'n'. ");
            String decision = in.next();

            if (decision.equalsIgnoreCase("y")){
                removeBook();

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
            //e.printStackTrace();
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
