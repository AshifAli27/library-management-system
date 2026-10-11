# Library Management System

A Java console application that uses MySQL to store book records
and track whether books are available or issued.

## Features

- Add books with automatically generated IDs, titles and authors.
- View all books and their availability.
- Search for books by ID.
- Issue and return books.
- Prevent issuing an already issued book.
- Validate empty fields and invalid book IDs.
- Preserve book records and issue status after restarting.

## Technologies

- Java
- JDBC
- MySQL
- Git and GitHub

## Requirements

- Java Development Kit (tested locally with JDK 26)
- MySQL Server 8.4
- MySQL Connector/J (tested locally with version 26.7.0)

## Setup

### 1. Download the JDBC driver

Download MySQL Connector/J from:
https://dev.mysql.com/downloads/connector/j/

Choose Platform Independent, download the ZIP and extract it.
Create a lib folder in this project and copy the driver JAR into it.

The JAR is excluded from this repository.

### 2. Create the database

Log in to MySQL as an administrator.

Run the SQL statements from schemas.sql to create library_db
and its books table.

### 3. Create the application account

Run these statements as a MySQL administrator.
Replace the password placeholder inside the quotes before executing:

```sql
CREATE USER 'library_user'@'localhost'
IDENTIFIED BY 'REPLACE_WITH_YOUR_OWN_PASSWORD';

GRANT SELECT, INSERT, UPDATE, DELETE
ON library_db.*
TO 'library_user'@'localhost';
```

If the account already exists, use the existing account and ensure
it has these permissions.

Keep passwords private. Do not store them in source code or Git.

## Compile and Run on Windows

Open PowerShell in the project folder.

Compile:

```powershell
javac -cp ".;lib/*" DatabaseConnection.java LibraryManagementSystem.java
```

Run:

```powershell
java -cp ".;lib/*" LibraryManagementSystem
```

Enter the library_user password when prompted.
Password input is hidden.

The application connects to localhost on port 3306 and requires
an encrypted MySQL connection. MySQL must be running.

## Project Files

- LibraryManagementSystem.java: Menu, validation and database operations.
- DatabaseConnection.java: Opens the database connection.
- DatabaseTest.java: Standalone connection check.
- schemas.sql: Creates the database and books table.
- Book.java: Book model from the earlier in-memory version;
  not used by the current JDBC application.

## Manual Checks Performed

- Added a book and confirmed it remained after restarting.
- Searched for existing and missing book IDs.
- Issued a book and confirmed its status remained after restarting.
- Returned a book and checked that it became available.

## Current Scope

This is a single-user console learning project.
It tracks book availability but does not yet record borrower
details, due dates or loan history.

## Planned Improvements

- Update and delete book records.
- Track borrowers and loan dates.

## Author

Ashif Ali

https://github.com/AshifAli27