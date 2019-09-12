


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * @author hamza.ahmed 11/09/19
 * Class for database connection and operations
 */
public class DatabaseConn {
    Connection connection;

    //logger object for saving logs
    private static final Logger logger = LogManager.getLogger();

    /*connect method will create connection with database using Connection Interface and
     Driver manager for registering driver for specific database*/
    public void connect(String dbUrl,String userName,String password){
        try {
//            DOMConfigurator.configure("log4j2.xml");
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

    /*
    insert record will take the input from user and create a sql statement through the connection and execute the query
     */
    public void insertRecord(){

        //sql statement for inserting record
        String sql = "INSERT INTO employee (first_name, last_name, salary) VALUES (?, ?,?)";
        //getting input from user
        Scanner input=new Scanner(System.in);
        System.out.println("Enter First name");
        String fName=input.nextLine();
        System.out.println("Enter Last name");
        String lName=input.nextLine();
        System.out.println("Enter salary");
        String salary=input.nextLine();

        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            //setting parameter values
            statement.setString(1, fName);
            statement.setString(2, lName);
            statement.setString(3, salary);
            //executing query which will return an integer value
            int rowsInserted = statement.executeUpdate();
            //if rowInserted is greater then 0 mean rows are inserted
            if (rowsInserted > 0) {
                logger.debug("A new user was inserted successfully!");
            }
        }catch (Exception e){
            logger.error("Exception in connection: "+ e.toString());

        }
    }

    public void getAllRecords(){
        //sql statement for inserting record
        String sql = "SELECT * FROM employee";
        //Creating a collection form employee list for storing all employee record
        ArrayList<Employee> employeeList=new ArrayList<Employee>();

        try {

            //creating and executing our statement
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            //iterating over the rows in the result
            while (result.next()) {

                //storing single result in employee object
                Employee employee=new Employee();
                employee.setId(Integer.parseInt(result.getString(1)));
                employee.setFirstName(result.getString(2));
                employee.setLastName(result.getString(3));
                employee.setSalary(Integer.parseInt(result.getString(4)));

                //adding employee in employee list
                employeeList.add(employee);
            }
            //caalling function to display all record
            displayRecord(employeeList);
        }catch (Exception e){
            logger.error("Exception in connection: "+ e.toString());

        }
    }

    public void displayRecord(ArrayList<Employee> employees){
        //iterating over employee list and displaying all employees data
        for(int i=0;i<employees.size();i++){
            System.out.println("Id: "+employees.get(i).getId());
            System.out.println("First Name: "+employees.get(i).getFirstName());
            System.out.println("Last Name: "+employees.get(i).getLastName());
            System.out.println("Salary: "+employees.get(i).getSalary());

        }
    }

    public void updateRecord(){
        //sql statement for inserting record
        String sql = "UPDATE employee SET first_name=?, last_name=?, salary=? WHERE id=?";
        //getting input from user
        Scanner input=new Scanner(System.in);
        System.out.println("Enter id of employee to update");
        String id=input.nextLine();
        System.out.println("Enter First name");
        String fName=input.nextLine();
        System.out.println("Enter Last name");
        String lName=input.nextLine();
        System.out.println("Enter salary");
        String salary=input.nextLine();

        try {
            //creating and executing our statement
            PreparedStatement statement = connection.prepareStatement(sql);
            //setting parameter values
            statement.setString(1, fName);
            statement.setString(2, lName);
            statement.setString(3, salary);
            statement.setString(4, id);

            int rowsUpdated = statement.executeUpdate();
            //if rowInserted is greater then 0 mean rows are inserted
            if (rowsUpdated > 0) {
                logger.debug("An existing user was updated successfully!");
            }
        }catch (Exception e){
            logger.error("Exception in connection: "+ e.toString());

        }
    }

    public void deleteRecord(){
        //sql statement for inserting record
        String sql = "DELETE FROM employee WHERE id=?";

        //getting input from user
        Scanner input=new Scanner(System.in);
        System.out.println("Enter id of employee to delete");
        String id=input.nextLine();

        try {
            //creating and executing our statement
            PreparedStatement statement = connection.prepareStatement(sql);
            //setting parameter values
            statement.setString(1, id);

            int rowsDeleted = statement.executeUpdate();
            //if rowInserted is greater then 0 mean rows are inserted
            if (rowsDeleted > 0) {
                logger.debug("Employee was deleted successfully!");
            }else {
                logger.debug("Employee not found");

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
//closing the connection with database
    public void closeConnection(){
        try {
            if(connection!=null) {
                connection.close();
            }
        }catch (Exception e){
            logger.error("Exception in connection: "+ e.toString());

        }
    }
//creating a menu for user to select a operation
    public void crudMenu(){
        int choice;
        Scanner input=new Scanner(System.in);
        do{
            //printing menu
            System.out.println("press 1 to insert record\npress 2 to display all record\npress 3 to delete a record\npress 4 to update a record\npress 0 for exit");
            choice=input.nextInt();
            //checking user choice
            switch (choice){
                case 1:
                    insertRecord();
                    break;
                case 2:
                    getAllRecords();
                    break;
                case 3:
                    deleteRecord();
                    break;
                case 4:
                    updateRecord();
                    break;
                default:
                    System.out.println("Enter a right command");
                    break;
            }
        }while (choice!=0);
    }
}
