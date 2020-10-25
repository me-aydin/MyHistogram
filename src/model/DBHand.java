/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author me-aydin
 */

import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class  DBHand //extends JFrame 
{
    // the database connector details  
    static final String DRIVER = "org.apache.derby.jdbc.ClientDriver"; // Derby drive
    static final String DATABASE_URL = "jdbc:derby://localhost:1527/MyDB"; // the full URL:

    private Connection connection = null;
    private Statement statement = null;
    private PreparedStatement ps = null;
    private ResultSet resultSet = null;
    
    // launch the application
    public DBHand(){ }
    
    /**
     * Returns true if connection could be established, false otherwise
     */
    public boolean setConnection(String user, String password){
        boolean isConnOK = false;
        // connect to database 
        try 
        {
            // load the driver class
            Class.forName(DRIVER);
            // establish connection to database    
            connection =  DriverManager.getConnection(DATABASE_URL, user, password);
            isConnOK = true;  // never reached if exception thrown
        }
        catch (SQLException sqlException)                                
        {       
            System.err.println("Driver loaded, but something went wrong elsewhere!");               
            sqlException.printStackTrace();

        } // end catch                                                     
        catch (ClassNotFoundException classNotFound)                     
        {                        
            System.err.println("Couldn't find driver!");
            classNotFound.printStackTrace();            
        } // end catch                             

        return isConnOK;
    }
    public List getNumbers(String query) {
        List list = new ArrayList();
        
        try {
            // create Statement for querying database
            statement = connection.createStatement();
            // query database                                                   
            resultSet = statement.executeQuery(query);
            // process query results
            ResultSetMetaData metaData = resultSet.getMetaData();
            int numberOfColumns = metaData.getColumnCount();     

            // extract the results
            int i = numberOfColumns;
            while (resultSet.next()) {
                int num = resultSet.getInt(i);
                list.add(num);
            } // end while
        }  // end try
        catch (SQLException sqlException) {                                                                  
            sqlException.printStackTrace();
            System.out.println("ERROR! Something went wrong, please consult stack trace!");
        } // end catch                                                     
        finally // ensure resultSet, statement and connection are closed
        {                                                             
            try                                                        
            {                                                          
                resultSet.close();                                      
                statement.close();                                      
                connection.close();                                     
            } // end try                                               
            catch (Exception exception)                              
            {                                                          
                exception.printStackTrace();                            
            } // end catch                                             
        } // end finally   
            
        return list;
    }    
    public String getAllData(String query){
        String results = null;
        try 
        {
            // create Statement for querying database
            statement = connection.createStatement();

            // query database                                        
            resultSet = statement.executeQuery(query);

            // process query results
            ResultSetMetaData metaData = resultSet.getMetaData();
            int numberOfColumns = metaData.getColumnCount();     
            results = "Authors Table of Books Database:\n";

            // print the column headers
            for (int i = 1; i <=numberOfColumns; i++)
            {
                results += metaData.getColumnName(i) + '\t' ; 
            }
            results += '\n';

            // extract the results
            while (resultSet.next()) 
            {
                for (int i = 1; i <= numberOfColumns; i++)
                {
                    results += resultSet.getObject(i).toString()  + '\t';
                }
                results += '\n';
            } // end while
        }  // end try
        catch (SQLException sqlException)                                
        {                                                                  
            sqlException.printStackTrace();
            // Print an error message instead of results
            results = "ERROR! Something went wrong, please consult stack trace!";
        } // end catch                                                     
        finally // ensure resultSet, statement and connection are closed
        {                                                             
            try                                                        
            {                                                          
                resultSet.close();                                      
                statement.close();                                      
                connection.close();                                     
            } // end try                                               
            catch (Exception exception)                              
            {                                                          
                exception.printStackTrace();                            
            } // end catch                                             
        } // end finally   
            
        return results;
    } 
    
    public int insert(String sql){
        int val = 0;
        try {
            statement = connection.createStatement();
            val = statement.executeUpdate(sql);
            //System.out.println(val+" rows added.");
        } catch (SQLException ex) {
            Logger.getLogger(DBHand.class.getName()).log(Level.SEVERE, null, ex);
        }
         
        return val;
    }
    
    public Connection getConnection(){
        return connection;
    }

}


