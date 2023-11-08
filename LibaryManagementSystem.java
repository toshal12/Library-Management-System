import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Book {
    private int bookId;
    private String title;
    private String author;
    private boolean available;

    public Book(int bookId, String title, String author) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.available = true;
    }

    public int getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}

class LibraryMember {
    private int memberId;
    private String name;

    public LibraryMember(int memberId, String name) {
        this.memberId = memberId;
        this.name = name;
    }

    public int getMemberId() {
        return memberId;
    }

    public String getName() {
        return name;
    }
}

class LibraryStaff {
    private String staffId;
    private String name;

    public LibraryStaff(String staffId, String name) {
        this.staffId = staffId;
        this.name = name;
    }

    public String getStaffId() {
        return staffId;
    }

    public String getName() {
        return name;
    }

    public void addBook(List<Book> library, Book book) {
        library.add(book);
    }

    public List<Book> searchBooksByTitle(List<Book> library, String title) {
        List<Book> foundBooks = new ArrayList<>();
        for (Book book : library) {
            if (book.getTitle().equalsIgnoreCase(title) && book.isAvailable()) {
                foundBooks.add(book);
            }
        }
        return foundBooks;
    }

    public boolean borrowBook(LibraryMember member, Book book) {
        if (book.isAvailable()) {
            book.setAvailable(false);
            return true;
        }
        return false;
    }

    public void returnBook(Book book) {
        book.setAvailable(true);
    }
}

public class Main {
    public static void LibaryManagementSystem(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LibraryStaff staff = new LibraryStaff("L001", "Staff Name");
        List<Book> library = new ArrayList<>();
        List<LibraryMember> members = new ArrayList<>();

        while (true) {
            System.out.println("Library Management System");
            System.out.println("1. Add Book");
            System.out.println("2. Search Books by Title");
            System.out.println("3. Register Member");
            System.out.println("4. Borrow Book");
            System.out.println("5. Return Book");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    System.out.print("Enter book title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter author: ");
                    String author = scanner.nextLine();
                    int bookId = library.size() + 1;
                    Book newBook = new Book(bookId, title, author);
                    staff.addBook(library, newBook);
                    System.out.println("Book added successfully.");
                    break;

                case 2:
                    System.out.print("Enter title to search: ");
                    String searchTitle = scanner.nextLine();
                    List<Book> foundBooks = staff.searchBooksByTitle(library, searchTitle);
                    if (foundBooks.isEmpty()) {
                        System.out.println("No matching books found.");
                    } else {
                        System.out.println("Matching books:");
                        for (Book book : foundBooks) {
                            System.out.println(book.getTitle() + " by " + book.getAuthor());
                        }
                    }
                    break;

                case 3:
                    System.out.print("Enter member name: ");
                    String memberName = scanner.nextLine();
                    int memberId = members.size() + 1;
                    LibraryMember newMember = new LibraryMember(memberId, memberName);
                    members.add(newMember);
                    System.out.println("Member registered successfully.");
                    break;

                case 4:
                    System.out.print("Enter member ID: ");
                    int memberIdToBorrow = scanner.nextInt();
                    System.out.print("Enter book ID to borrow: ");
                    int bookIdToBorrow = scanner.nextInt();
                    LibraryMember memberToBorrow = members.stream()
                            .filter(member -> member.getMemberId() == memberIdToBorrow)
                            .findFirst()
                            .orElse(null);

                    if (memberToBorrow == null) {
                        System.out.println("Member not found.");
                    } else {
                        Book bookToBorrow = library.stream()
                                .filter(book -> book.getBookId() == bookIdToBorrow)
                                .findFirst()
                                .orElse(null);

                        if (bookToBorrow == null) {
                            System.out.println("Book not found.");
                        } else {
                            if (staff.borrowBook(memberToBorrow, bookToBorrow)) {
                                System.out.println(memberToBorrow.getName() + " borrowed " + bookToBorrow.getTitle());
                            } else {
                                System.out.println("Book is not available for " + memberToBorrow.getName() + " to borrow.");
                            }
                        }
                    }
                    break;

                case 5:
                    System.out.print("Enter book ID to return: ");
                    int bookIdToReturn = scanner.nextInt();
                    Book bookToReturn = library.stream()
                            .filter(book -> book.getBookId() == bookIdToReturn)
                            .findFirst()
                            .orElse(null);

                    if (bookToReturn == null) {
                        System.out.println("Book not found.");
                    } else {
                        staff.returnBook(bookToReturn);
                        System.out.println("Book returned: " + bookToReturn.getTitle());
                    }
                    break;

                case 6:
                    System.out.println("Exiting Library Management System.");
                    scanner.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}


