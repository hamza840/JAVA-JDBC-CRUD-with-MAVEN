# JAVA-JDBC-CRUD-with-MAVEN
This JDBC tutorial is going to help you learning how to do basic database operations (CRUD - Create, Retrieve, Update and Delete) using 
JDBC (Java Database Connectivity) API using maven as a build tool.

<h2>Understand the main JDBC interfaces and classes</h2>
DriverManager: this class is used to register driver for a specific database type (e.g. MySQL in this tutorial) and to establish
a database connection with the server via its getConnection() method.



<b>Connection</b>: this interface represents an established database connection from which we can create statements to execute queries
and retrieve results, get metadata about the database, close connection, etc.


<b>Statement and PreparedStatement</b>: these interfaces are used to execute static SQL query and parameterized SQL query, respectively. 
Statement is the super interface of the PreparedStatement interface.



<b>boolean execute(String sql)</b>: executes a general SQL statement. It returns true if the query returns a ResultSet, false if the
query returns an update count or returns nothing. This method can be used with a Statement only.


<b>int executeUpdate(String sql)</b>: executes an INSERT, UPDATE or DELETE statement and returns an update account indicating number
of rows affected (e.g. 1 row inserted, or 2 rows updated, or 0 rows affected).


<b>ResultSet executeQuery(String sql)</b>: executes a SELECT statement and returns a ResultSet object which contains results returned
by the query.


<b>ResultSet</b>: contains table data returned by a SELECT query. Use this object to iterate over rows in the result set using next() 
method, and get value of a column in the current row using get() methods (e.g. getString(), getInt(), getFloat() and so on). 
The column value can be retrieved either by index number (1-based) or by column name.


<h2>Adding JDBC Dependecy in Maven POM.xml file:</h2>
The following code snippet will add the dependency in Maven:

```XML
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.17</version>
</dependency>
```

## Connecting to a Database

Supposing the MySQL database server is listening on the default port 3306 at localhost. The following code snippet connects to
the database name crud by the user root and password empty by default:

```java
  /*connect method will create connection with database using Connection Interface and    
  Driver manager for registering driver for specific database*/    
  public void connect(String dbUrl,String userName,String password){
        try {
            connection= DriverManager.getConnection(dbUrl,userName,password);
            if(connection!=null){
                logger.debug("Database Connection Successful");
                logger.info("Entering application.");

            }
        }catch (SQLException ex){
            //showing exception in log           
            logger.error("Exception in connection: "+ ex.toString());

        }
    }
```
    
## Closing Database Connection after all operations

To close the database connection simply call the connection object method close() if it is not null:

```java
//closing the connection with database    public void closeConnection(){
        try {
            if(connection!=null) {
                connection.close();
            }
        }catch (Exception e){
            logger.error("Exception in connection: "+ e.toString());

        }
    }
``` 
