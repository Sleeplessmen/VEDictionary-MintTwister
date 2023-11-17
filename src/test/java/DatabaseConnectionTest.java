import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnectionTest {
    public static void main(String []args) {
        // connect to database test
        String url = "jdbc:mysql://localhost:3306/mintwister";
        String username = "mintwister";
        String password = "mintwister";

        System.out.println("Connecting database...");

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            System.out.println("Database connected!");
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }

        // manipulate data in database test

    }
}
