import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class BookDeleter {

    public static void delete(
        Connection connection, int id, Scanner scanner
    ) throws SQLException {

        String findSql =
            "SELECT title, author, issued FROM books WHERE id = ?";

        try (PreparedStatement statement =
                connection.prepareStatement(findSql)) {

            statement.setInt(1, id);

            try (ResultSet result = statement.executeQuery()) {
                if (!result.next()) {
                    System.out.println("No book found with that ID.");
                    return;
                }

                if (result.getBoolean("issued")) {
                    System.out.println(
                        "Return this book before deleting it."
                    );
                    return;
                }

                System.out.println("Title: " + result.getString("title"));
                System.out.println("Author: " + result.getString("author"));
            }
        }

        System.out.print("Type YES to permanently delete this book: ");
        String confirmation = scanner.nextLine().trim();

        if (!confirmation.equals("YES")) {
            System.out.println("Deletion cancelled.");
            return;
        }

        String deleteSql =
            "DELETE FROM books WHERE id = ? AND issued = FALSE";

        try (PreparedStatement statement =
                connection.prepareStatement(deleteSql)) {

            statement.setInt(1, id);
            int rows = statement.executeUpdate();

            if (rows > 0) {
                System.out.println("Book deleted successfully.");
            } else {
                System.out.println(
                    "Book was not deleted. It may be missing or issued."
                );
            }
        }
    }
}