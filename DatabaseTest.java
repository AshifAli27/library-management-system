import java.io.Console;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;

public class DatabaseTest {
    public static void main(String[] args) {
        Console console = System.console();

        if (console == null) {
            System.out.println("Run this program from the VS Code terminal.");
            return;
        }

        char[] password = console.readPassword(
            "Enter your library_user password: "
        );

        if (password == null) {
            return;
        }

        String url = "jdbc:mysql://localhost:3306/library_db?sslMode=REQUIRED";

        try (Connection connection = DriverManager.getConnection(
                url, "library_user", new String(password))) {

            System.out.println("Connected to MySQL successfully!");
            System.out.println("Database: " + connection.getCatalog());

        } catch (SQLException error) {
            System.out.println("Connection failed: " + error.getMessage());
        } finally {
            Arrays.fill(password, '\0');
        }
    }
}