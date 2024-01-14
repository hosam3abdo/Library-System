package Library;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.*;
import java.util.*;

class Library {
	String connectionUrl = "jdbc:sqlserver://DESKTOP-SSNOV57:1433;" + "database=LibrarySystem;" + "user=library;"
			+ "password=1234;" + "encrypt=true;" + "trustServerCertificate=true;" + "loginTimeout=30;";
	private List<Book> books;
	private List<Member> members;
	private Map<Member, List<Book>> borrowedBooks;
	String ask;

	public Library() {
		books = new ArrayList<>();
		members = new ArrayList<>();
		borrowedBooks = new HashMap<>();

	}

	public void addBook(Book book) {
		try (Connection connection = DriverManager.getConnection(connectionUrl)) {
			String insertBook = "INSERT INTO Books (bookname, authorname , avialble) VALUES (?, ?,?)";
			PreparedStatement insertBookStatement = connection.prepareStatement(insertBook);

			insertBookStatement.setString(1, book.getTitle());
			insertBookStatement.setString(2, book.getAuthor());
			insertBookStatement.setInt(3, book.getAvailableBooks());
			insertBookStatement.executeUpdate();
		} catch (SQLException e) {

			System.err.println("Error inserting book: " + e.getMessage());
		}

	}

	public void addInput() {
		try {
			Scanner scanner = new Scanner(System.in);
			String bookname;
			String authorname;
			do {

				System.out.println("please enter books name :");
				bookname = scanner.nextLine();

				System.out.println("please enter author name :");
				authorname = scanner.nextLine();
				System.out.println("please enter books numbers :");
				int numbers = scanner.nextInt();
				addBook(new Book(bookname, authorname , numbers));

				System.out.println("Do you need another book ");
				ask = scanner.nextLine();
			} while (ask.equalsIgnoreCase("yes"));
		}

		catch (Exception e) {
			System.out.println("An error occurred while adding the book");
		}
	}

	public List<Book> selectBooks(String query) {

		try (Connection connection = DriverManager.getConnection(connectionUrl)) {
			String selectQuery = "SELECT * FROM Books WHERE " + query;
			;
			try (PreparedStatement selectStatement = connection.prepareStatement(selectQuery)) {

				ResultSet resultSet = selectStatement.executeQuery();

				while (resultSet.next()) {

					String title = resultSet.getString("bookname");
					String author = resultSet.getString("AuthorName");
					int availble = resultSet.getInt("avialble");
					Book book = new Book(title, author,availble);
					books.add(book);
				}
			}
		} catch (SQLException e) {
			System.err.println("Error selecting books: " + e.getMessage());
		}

		return books;
	}

	public void removeBook(int bookId) {
		try (Connection connection = DriverManager.getConnection(connectionUrl)) {
			String deleteBook = "DELETE FROM Books WHERE bookId = ?";
			PreparedStatement deleteBookStatement = connection.prepareStatement(deleteBook);
			deleteBookStatement.setInt(1, bookId);
			deleteBookStatement.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Error deleting book: " + e.getMessage());
		}
	}

	public void updateBook(String newName, String newAuthor, int selectid) {
		try (Connection connection = DriverManager.getConnection(connectionUrl)) {
			String updateBook = "UPDATE Books SET name = ?, authorName = ? WHERE bookId = ?";
			PreparedStatement updateBookStatement = connection.prepareStatement(updateBook);
			updateBookStatement.setString(1, newName);
			updateBookStatement.setString(2, newAuthor);
			updateBookStatement.setInt(3, selectid);
			updateBookStatement.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Error updating book: " + e.getMessage());
		}
	}

	public void addMember(Member member) {
		try (Connection connection = DriverManager.getConnection(connectionUrl)) {
			String insertMember = "INSERT INTO Member (membername, info) VALUES (?, ?)";
			PreparedStatement insertMemberStatement = connection.prepareStatement(insertMember);

			insertMemberStatement.setString(1, member.getName());
			insertMemberStatement.setString(2, member.getinfo());

			insertMemberStatement.executeUpdate();
		} catch (SQLException e) {
			// Handle SQL exceptions appropriately
			System.err.println("Error inserting book: " + e.getMessage());
		}

	}

	public void addUser() {
		try {
			Scanner scanner = new Scanner(System.in);
			String membername;
			do {
				System.out.println("please enter member name :");
				membername = scanner.nextLine();

				System.out.println("please enter member info :");
				String memberinfo = scanner.nextLine();
				addMember(new Member(membername, memberinfo));

				System.out.println("Do you need another user ");
				ask = scanner.nextLine();
			} while (ask.equalsIgnoreCase("yes"));

		} catch (Exception e) {
			System.out.println("An error occurred while adding the member ");
		}

	}

	public List<Member> selectMembers(String query) {

		try (Connection connection = DriverManager.getConnection(connectionUrl)) {
			String selectQuery = "SELECT * FROM Member WHERE  " + query;
			;
			try (PreparedStatement selectStatement = connection.prepareStatement(selectQuery)) {

				ResultSet resultSet = selectStatement.executeQuery();

				while (resultSet.next()) {

					String name = resultSet.getString("memberName");
					String info = resultSet.getString("info");
					Member member = new Member(name, info);
					members.add(member);
				}
			}
		} catch (SQLException e) {
			System.err.println("Error selecting books: " + e.getMessage());
		}

		return members;
	}

	public void updateMember(String newName, String newInfo, int selectid) {
		try (Connection connection = DriverManager.getConnection(connectionUrl)) {
			String updateMember = "UPDATE Member SET membername = ?, info = ? WHERE memberId = ?";
			PreparedStatement updateMemberStatement = connection.prepareStatement(updateMember);
			updateMemberStatement.setString(1, newName);
			updateMemberStatement.setString(2, newInfo);
			updateMemberStatement.setInt(3, selectid);
			updateMemberStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Error updating book: " + e.getMessage());
		}
	}

	public void deleteMember(int memberId) {
		try (Connection connection = DriverManager.getConnection(connectionUrl)) {
			String deleteMember = "DELETE FROM Members WHERE memberId = ?";
			PreparedStatement deleteMemberStatement = connection.prepareStatement(deleteMember);
			deleteMemberStatement.setInt(1, memberId);
			deleteMemberStatement.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Error deleting book: " + e.getMessage());
		}
	}

	public void borrowBook(int bookId, int memberId) {
		String bookAvailabilityQuery = "SELECT avialble FROM Books WHERE bookId = ?";
		String insertQuery = "INSERT INTO Borrowings (bookId, memberId, borrowingDate) VALUES (?, ?, ?)";
		String updateBookQuery = "UPDATE Books SET avialble = avialble-1 WHERE bookId = ?";

		try (Connection connection = DriverManager.getConnection(connectionUrl);
				PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
				PreparedStatement bookAvailabilityStatement = connection.prepareStatement(bookAvailabilityQuery);
				PreparedStatement updateStatement = connection.prepareStatement(updateBookQuery);)

		{
			bookAvailabilityStatement.setInt(1, bookId);
			ResultSet bookAvailabilityResult = bookAvailabilityStatement.executeQuery();

			if (!bookAvailabilityResult.next() || bookAvailabilityResult.getInt("avialble") == 0) {
				System.out.println("The book is not available.");
				return;
			}

			insertStatement.setInt(1, bookId);
			insertStatement.setInt(2, memberId);
			insertStatement.setDate(3, new java.sql.Date(System.currentTimeMillis()));

			insertStatement.executeUpdate();

			updateStatement.setInt(1, bookId);

			updateStatement.executeUpdate();
		} catch (SQLException e) {
			System.err.println("Error borrowing book: " + e.getMessage());
		}
	}

	public void returnBook(int bookId, int memberId) {
		String updateBorrowingQuery = "UPDATE Borrowings SET returnningDate = ? WHERE bookId = ? AND memberId = ?";
		String updateBookQuery = "UPDATE Books SET avialble = avialble+1 WHERE bookId = ?";

		try (Connection connection = DriverManager.getConnection(connectionUrl);
				PreparedStatement updateBorrowingStatement = connection.prepareStatement(updateBorrowingQuery);
				PreparedStatement updateBookStatement = connection.prepareStatement(updateBookQuery)) {

			updateBorrowingStatement.setDate(1, new java.sql.Date(System.currentTimeMillis()));
			updateBorrowingStatement.setInt(2, bookId);
			updateBorrowingStatement.setInt(3, memberId);

			int rowsUpdated = updateBorrowingStatement.executeUpdate();

			if (rowsUpdated == 0) {
				System.out.println("No borrowing record found for the specified book and member.");
				return;
			}

			updateBookStatement.setInt(1, bookId);

			updateBookStatement.executeUpdate();

			System.out.println("Book returned successfully.");
		} catch (SQLException e) {
			System.err.println("Error returning book: " + e.getMessage());
		}
	}

	public void printBorrowedBooks() {
	    String query = "Select * from ActiveBorrowings where memberId is not null  " ;
	    
	    
	    try (Connection connection = DriverManager.getConnection(connectionUrl);
	         PreparedStatement statement = connection.prepareStatement(query)) {
	        

	        ResultSet resultSet = statement.executeQuery();
	      
	        System.out.println("Borrowed Books:");
	        System.out.println("-----------------------");
	        

	        while (resultSet.next()) {
	            
	            String title = resultSet.getString("bookname");
	            String membername = resultSet.getString("membername");
	            Date borrowDate = resultSet.getDate("borrowingDate");
	            
	            
	            
	            System.out.println("Title: " + title);
	            System.out.println("Member: " + membername);
	            System.out.println("Borrow Date: " + borrowDate);
	            
	            System.out.println("-----------------------");
	        }
	        
	    } catch (SQLException e) {
	        System.err.println("Error retrieving borrowed books: " + e.getMessage());
	    }
	}
}
