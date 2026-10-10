import java.util.ArrayList;
import java.util.Scanner;

public class LibraryManagementSystem {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Book> books = new ArrayList<>();

        int nextBookId = 1;
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

            switch (choice) {
                case "1": {
                    System.out.print("Enter the book title: ");
                    String title = scanner.nextLine().trim();

                    System.out.print("Enter the author's name: ");
                    String author = scanner.nextLine().trim();

                    if (title.isEmpty() || author.isEmpty()) {
                        System.out.println("Title and author cannot be empty.");
                    } else {
                        Book book = new Book(nextBookId, title, author);
                        books.add(book);

                        System.out.println("Book added! Its ID is " + nextBookId);
                        nextBookId++;
                    }
                    break;
                }

                case "2": {
                    if (books.isEmpty()) {
                        System.out.println("No books available. Add a book first.");
                    } else {
                        System.out.println("\nID | Title | Author | Status");
                        System.out.println("--------------------------------");

                        for (Book book : books) {
                            book.displayDetails();
                        }

                        System.out.println("Total books: " + books.size());
                    }
                    break;
                }

                case "3": {
                    System.out.println("Thank you! Goodbye.");
                    running = false;
                    break;
                }

                case "4": {
                    System.out.print("Enter the book ID: ");
                    String searchId = scanner.nextLine().trim();
                    boolean found = false;

                    for (Book book : books) {
                        if (String.valueOf(book.getId()).equals(searchId)) {
                            System.out.println("\nID | Title | Author | Status");
                            book.displayDetails();
                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        System.out.println("No book found with that ID.");
                    }
                    break;
                }

                case "5": {
                    System.out.print("Enter the book ID to issue: ");
                    String issueId = scanner.nextLine().trim();
                    boolean found = false;

                    for (Book book : books) {
                        if (String.valueOf(book.getId()).equals(issueId)) {
                            book.issueBook();
                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        System.out.println("No book found with that ID.");
                    }
                    break;
                }

                case "6": {
                    System.out.print("Enter the book ID to return: ");
                    String returnId = scanner.nextLine().trim();
                    boolean found = false;

                    for (Book book : books) {
                        if (String.valueOf(book.getId()).equals(returnId)) {
                            book.returnBook();
                            found = true;
                            break;
                        }
                    }

                    if (!found) {
                        System.out.println("No book found with that ID.");
                    }
                    break;
                }

                default:
                    System.out.println("Invalid choice. Please enter 1 to 6.");
            }
        }

        scanner.close();
    }
}