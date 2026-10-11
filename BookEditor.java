import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class BookEditor {

    public static void update(
        Connection connection, int id, Scanner scanner
    ) throws SQLException {

        String findSql = "SELECT title, author FROM books WHERE id = ?";

        try (PreparedStatement statement =
                connection.prepareStatement(findSql)) {

            statement.setInt(1, id);

            try (ResultSet result = statement.executeQuery()) {
                if (!result.next()) {
                    System.out.println("No book found with that ID.");
                    return;
                }

                System.out.println("Current title: " + result.getString("title"));
                System.out.println("Current author: " + result.getString("author"));
            }
        }

        System.out.print("Enter the new title: ");
        String title = scanner.nextLine().trim();

        System.out.print("Enter the new author: ");
        String author = scanner.nextLine().trim();

        if (title.isEmpty() || author.isEmpty()) {
            System.out.println("Title and author cannot be empty. Nothing changed.");
            return;
        }

        if (title.length() > 255 || author.length() > 255) {
            System.out.println(
                "Title and author must each be 255 characters or fewer."
            );
            return;
        }

        String updateSql =
            "UPDATE books SET title = ?, author = ? WHERE id = ?";

        try (PreparedStatement statement =
                connection.prepareStatement(updateSql)) {

            statement.setString(1, title);
            statement.setString(2, author);
            statement.setInt(3, id);

            int rows = statement.executeUpdate();

            if (rows > 0) {
                System.out.println("Book details saved successfully.");
            } else {
                System.out.println("No book record was updated.");
            }
        }
    }
}