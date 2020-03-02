package coffee.management.connection;
import java.sql.*;

public class DbAdapter
{
    private final String jdbUrl = "jdbc:mysql://localhost:3306/coffee_shop";
    private final String userName = "root";
    private final String password = "";

    private Connection connection = null;
    private final Statement statement = null;
    private final ResultSet resultSet = null;

    public DbAdapter() { }

    public void connect()
    {
        try
        {
            connection = DriverManager.getConnection(jdbUrl, userName, password);
            System.out.println("Database connection established!");
        }
        catch(SQLException e) { e.printStackTrace(); }
    }

    public void disConnect()
    {
        try
        {
            if(statement != null) statement.close();
            if(resultSet != null)  resultSet.close();
            if(connection != null) connection.close();
            System.out.println("Database connection disconnect!");
        }
        catch (SQLException e) { e.printStackTrace(); }
    }

    public Connection getConnection() { return connection; }
}
