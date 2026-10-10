public class Book {
    private int id;
    private String title;
    private String author;
    private boolean issued;

    public Book(int id, String title, String author) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.issued = false;
    }

    public int getId() {
        return id;
    }

    public void issueBook() {
        if (issued) {
            System.out.println("This book is already issued.");
        } else {
            issued = true;
            System.out.println("Book issued successfully.");
        }
    }

    public void returnBook() {
        if (issued) {
            issued = false;
            System.out.println("Book returned successfully.");
        } else {
            System.out.println("This book is already available.");
        }
    }

    public void displayDetails() {
        String status;

        if (issued) {
            status = "Issued";
        } else {
            status = "Available";
        }

        System.out.println(
            id + " | " + title + " | " + author + " | " + status
        );
    }
}