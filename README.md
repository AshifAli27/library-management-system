# Library Management System

A Java console application for managing books. This project is being
developed step by step as I learn Java and Git.

## Current Features

- Add books with automatically assigned IDs, titles and authors.
- Display all books and the total book count.
- Search for a book by its ID.
- Reject empty titles and author names.
- Handle invalid menu choices.

## Technologies

- Java
- Java Collections: ArrayList
- Git and GitHub

## How to Run

Install a Java Development Kit (JDK) and open a terminal in the
project folder.

Compile:

```bash
javac Book.java LibraryManagementSystem.java
```

Run:

```bash
java LibraryManagementSystem
```

## Project Files

- Book.java: Represents a book with its ID, title and author.
- LibraryManagementSystem.java: Handles the menu and book operations.

## Current Limitation

Books are stored in memory and are lost when the program closes.

## Planned Features

- Issue and return books.
- Update and delete book records.
- Save books permanently using MySQL and JDBC.

## Author

Ashif Ali