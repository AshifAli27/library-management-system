import java.io.Console;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;

public class DatabaseConnection {

    public static Connection connect() throws SQLException {
        Console console = System.console();

        if (console == null) {
            throw new SQLException(
                "Please run the program from the terminal."
            );
        }

        char[] password = console.readPassword(
            "Enter your library_user password: "
        );

        if (password == null) {
            throw new SQLException("Login cancelled.");
        }

        String url =
            "jdbc:mysql://localhost:3306/library_db?sslMode=REQUIRED";

        try {
            return DriverManager.getConnection(
                url,
                "library_user",
                new String(password)
            );
        } finally {
            Arrays.fill(password, '\0');
        }
    }
}