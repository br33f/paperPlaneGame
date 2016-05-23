package adapters;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;
import java.io.IOException;
import java.sql.*;

/**
 * MySql basic adapter.
 * Instructions: call class constructor and enjoy.
 * Created by br33 on 10.05.2016.
 */
public class MysqlAdapter {
    //attributes
    protected String host, dbName, user, password, port;
    protected Connection dbConnection;
    private boolean throwErrors;

    /**
     * MysqlAdapter constructor, takes 2 parameters:
     * @param file - name of properties file (main project catalog). For example: "config.properties"
     * @param throwErrors - boolean value, decides whether connection with database is mandatory and it throws errors or not.
     */
    //methods
    public MysqlAdapter(String file, boolean throwErrors)
    {
        this.throwErrors = throwErrors;
        this.readConfig(file);
        this.connect();
    }

    /**
     * Getter for dbConnection
     * @return this.dbConnection - current connection established based on configuration file
     */
    public Connection getDbConnection()
    {
        return this.dbConnection;
    }

    /**
     * Function returns string made from Adapter's attributes (read from configuration file)
     * @return String - host, dbName, user, password
     */
    public String toString()
    {
        return "host: " + this.host + "\ndbName: " + this.dbName + "\nuser: " + this.user + "\npassword: " + this.password;
    }

    /**
     * Function returns ResultSet from mysql query string
     * @param str - mysql query string
     * @return ResultSet - result from mysql query
     */
    public ResultSet getResultSet(String str)
    {
        ResultSet returnedValue = null;

        if(this.dbConnection != null) {
            try {
                Statement s = this.dbConnection.createStatement();
                returnedValue = s.executeQuery(str);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return returnedValue;
    }

    /**
     * Function reads file and returns Properties object to be further processed.
     * @param file - name of a configuration file
     * @return Properties - Properties object read from file
     */
    protected Properties loadConfig(String file)
    {
        Properties p = new Properties();
        try {

            InputStream in =  this.getClass().getClassLoader().getResourceAsStream(file);
            p.load(in);
            in.close();
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e){
            e.printStackTrace();
        }
        return p;
    }

    /**
     * Function executes loadConfig function and then sets Adapters attributes according to Properties.
     * @param file - name of a configuration file
     */
    private void readConfig(String file)
    {
        Properties p = this.loadConfig(file);

        this.host = (p.getProperty("host") != null) ? p.getProperty("host") : "127.0.0.1";
        this.dbName = (p.getProperty("dbName") != null) ? p.getProperty("dbName") : "defaultDbName";
        this.user = (p.getProperty("user") != null) ? p.getProperty("user") : "defaultUser";
        this.password = (p.getProperty("password") != null) ? p.getProperty("password") : "defaultPassword";
        this.port = (p.getProperty("port") != null) ? p.getProperty("port") : "3306";
    }

    /**
     * Function establishes a connection based on data previously read from configuration file.
     * If connection was not established with success it throws an Expection becouse connections with databases are
     * vital for this application.
     */
    private void connect()
    {
        String url = "jdbc:mysql://" + this.host + ":" + this.port + "/" + this.dbName;
        try {
            this.dbConnection = DriverManager.getConnection(url, this.user, this.password);
            System.out.println("Connected to: " + url);
        } catch (SQLException e) {
            if(this.throwErrors)
                throw new IllegalStateException("\nCouldn't connect to: " + url + "\n(user: " + this.user + " password:" + this.password + ")");
            else
                System.out.println("\nCouldn't connect to: " + url + "\n(user: " + this.user + " password:" + this.password + ")");
        }
    }
}
