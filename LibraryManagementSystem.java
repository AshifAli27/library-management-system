import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class LibraryManagementSystem {

    public static void main(String[] args) {
        try (
            Connection connection = DatabaseConnection.connect();
            Scanner scanner = new Scanner(System.in)
        ) {
            System.out.println("Connected to the library database!");
            boolean running = true;

            while (running) {
                System.out.println("\n===== Library Management System =====");
                System.out.println("1. Add a book");
                System.out.println("2. View all books");
                System.out.println("3. Exit");
                System.out.println("4. Search by book ID");
                System.out.println("5. Issue a book");
                System.out.println("6. Return a book");
                System.out.print("Enter your choice: ");

                String choice = scanner.nextLine().trim();

                try {
                    switch (choice) {
                        case "1":
                            addBook(connection, scanner);
                            break;

                        case "2":
                            viewBooks(connection);
                            break;

                        case "3":
                            running = false;
                            System.out.println("Thank you! Goodbye.");
                            break;

                        case "4":
                            searchBook(connection, readBookId(scanner));
                            break;

                        case "5":
                            changeBookStatus(
                                connection, readBookId(scanner), true
                            );
                            break;

                        case "6":
                            changeBookStatus(
                                connection, readBookId(scanner), false
                            );
                            break;

                        default:
                            System.out.println(
                                "Invalid choice. Please enter 1 to 6."
                            );
                    }
                } catch (NumberFormatException error) {
                    System.out.println(
                        "Please enter a positive whole number for the ID."
                    );
                } catch (SQLException error) {
                    System.out.println(
                        "Database operation failed: " + error.getMessage()
                    );
                }
            }
        } catch (SQLException error) {
            System.out.println("Database error: " + error.getMessage());
        }
    }

    private static int readBookId(Scanner scanner) {
        System.out.print("Enter the book ID: ");
        int id = Integer.parseInt(scanner.nextLine().trim());

        if (id <= 0) {
            throw new NumberFormatException();
        }

        return id;
    }

    private static void addBook(
        Connection connection, Scanner scanner
    ) throws SQLException {
        System.out.print("Enter the book title: ");
        String title = scanner.nextLine().trim();

        System.out.print("Enter the author's name: ");
        String author = scanner.nextLine().trim();

        if (title.isEmpty() || author.isEmpty()) {
            System.out.println("Title and author cannot be empty.");
            return;
        }

        if (title.length() > 255 || author.length() > 255) {
            System.out.println(
                "Title and author must each be 255 characters or fewer."
            );
            return;
        }

        String sql = "INSERT INTO books (title, author) VALUES (?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(
                sql, java.sql.Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, title);
            statement.setString(2, author);
            statement.executeUpdate();

            try (ResultSet keys = statement.getGeneratedKeys()) {
                if (keys.next()) {
                    System.out.println(
                        "Book saved! Its ID is " + keys.getInt(1)
                    );
                }
            }
        }
    }

    private static void viewBooks(
        Connection connection
    ) throws SQLException {
        String sql = "SELECT id, title, author, issued FROM books ORDER BY id";

        try (
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet results = statement.executeQuery()
        ) {
            int count = 0;

            System.out.println("\nID | Title | Author | Status");
            System.out.println("--------------------------------");

            while (results.next()) {
                displayBook(results);
                count++;
            }

            if (count == 0) {
                System.out.println("No books saved yet.");
            }

            System.out.println("Total books: " + count);
        }
    }

    private static void searchBook(
        Connection connection, int id
    ) throws SQLException {
        String sql = "SELECT id, title, author, issued FROM books WHERE id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);

            try (ResultSet results = statement.executeQuery()) {
                if (results.next()) {
                    System.out.println("\nID | Title | Author | Status");
                    displayBook(results);
                } else {
                    System.out.println("No book found with that ID.");
                }
            }
        }
    }

    private static void changeBookStatus(
        Connection connection, int id, boolean issue
    ) throws SQLException {
        String sql =
            "UPDATE books SET issued = ? WHERE id = ? AND issued = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setBoolean(1, issue);
            statement.setInt(2, id);
            statement.setBoolean(3, !issue);

            int changedRows = statement.executeUpdate();

            if (changedRows == 1) {
                System.out.println(
                    issue
                        ? "Book issued successfully."
                        : "Book returned successfully."
                );
            } else {
                System.out.println(
                    issue
                        ? "Book not found or already issued."
                        : "Book not found or already available."
                );
            }
        }
    }

    private static void displayBook(
        ResultSet results
    ) throws SQLException {
        String status =
            results.getBoolean("issued") ? "Issued" : "Available";

        System.out.println(
            results.getInt("id") + " | "
            + results.getString("title") + " | "
            + results.getString("author") + " | "
            + status
        );
    }
}