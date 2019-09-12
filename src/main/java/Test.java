import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author hamza.ahmed 11/09/19
 * Class for Testing with main
 */
public class Test {
    private static final Logger logger = LogManager.getLogger(Test.class.getName());
    public static void main(String[] args) {

        //creating DatabaseConn object
        DatabaseConn databaseConn=new DatabaseConn();
        //calling connect method and giving credentials for establishing database connection
        databaseConn.connect("jdbc:mysql://localhost:3306/crud?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root","");
        //calling method for displaying menu
        databaseConn.crudMenu();
        //closing database connection
        databaseConn.closeConnection();

    }
}
