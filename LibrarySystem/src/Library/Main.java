package Library;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws SQLException {

		Library library = new Library();
		String ask;
		String select;
		Scanner scanner = new Scanner(System.in);
		do {
			
			System.out.println("please select your option");
			System.out.println("1: Adding books");
			System.out.println("2: Adding member");
			System.out.println("3: searching for books");
			System.out.println("4: Searching for members");
			System.out.println("5: borrowing books");
			System.out.println("6: Returning books");
			System.out.println("7: Editing books");
			System.out.println("8: Deleting books");
			System.out.println("9: Editing members");
			System.out.println("10: Deleting members");
			System.out.println("11: Printing books");
			System.out.println("12: Printing members");
			System.out.println("13: Printing Borrowed Books");
			select = scanner.nextLine();
			int selectoption = Integer.parseInt(select);
			switch (selectoption) {
			case 1: {

				// Add books

				library.addInput();
				
				break;
			}
			case 2: {
				// Add members
				library.addUser();
				
				break;
			}

			case 3: {
				// find book by name

				System.out.println("Please enter the book Id that you want to find : ");
				String query = scanner.nextLine();
				List<Book> selectedBooks = library.selectBooks("bookId =" +query); 

				for (Book book : selectedBooks) {
				    System.out.println("Title: " + book.getTitle());
				    System.out.println("Author: " + book.getAuthor());
				    System.out.println("--------------------");
				
				    break;	}break;			
			}

			case 4: {
				// Find a member by name
				System.out.println("Please enter the member Id : ");
				String query = scanner.nextLine();
				List<Member> selectedMembers = library.selectMembers("memberId =" +query); 

				for (Member member : selectedMembers) {
				    System.out.println("Name: " + member.getName());
				    System.out.println("Info: " + member.getinfo());
				    System.out.println("--------------------");
				
				    break;}	
				break;
			}	

			
			case 5: {
				// Borrowing Book
				
				System.out.println("please enter books id :");
				int bookname = scanner.nextInt();
			    System.out.println("Enter member id: ");
			    int memberName = scanner.nextInt();
			    library.borrowBook(bookname, memberName );
			    

				break;
			}
			case 6: {
				// Returning Book
				System.out.println("please enter books id :");
				int bookname = scanner.nextInt();
			    System.out.println("Enter member id: ");
			    int memberName = scanner.nextInt();
			    library.returnBook(bookname, memberName );
				break;
			}
			case 7: {
				// Edit Book
				System.out.println("please enter books id that you want to delete :");
				String bookid = scanner.nextLine();
				int selectid = Integer.parseInt(bookid);
				System.out.println("please enter new book name :");
				String newName = scanner.nextLine();
				System.out.println("please enter new author name :");
				String newAuthor = scanner.nextLine();
				//Book bookUpdate = new Book( newName, newAuthor); 
			    library.updateBook(newName,newAuthor,selectid);
				break;
			}
			case 8: {
				// Remove Book
				System.out.println("please enter books id that you want to delete :");
				String bookid = scanner.nextLine();
				int selectid = Integer.parseInt(bookid);
				library.removeBook(selectid);
				break;
			}

			case 9: {
				// Edit Member
				System.out.println("please enter member id that you want to update :");
				String memberid = scanner.nextLine();
				int selectid = Integer.parseInt(memberid);
				System.out.println("please enter new member name :");
				String newName = scanner.nextLine();
				System.out.println("please enter new info :");
				String newInfo = scanner.nextLine();
				library.updateMember(newName, newInfo,selectid);
				break;
			}
			case 10: {
				// Remove Member
				System.out.println("please enter member id that you want to delete :");
				String memberid = scanner.nextLine();
				int selectid = Integer.parseInt(memberid);
				library.deleteMember(selectid);
				break;
			}
		
			
			case 11:{
				
				List<Book> selectedBooks = library.selectBooks("BookId is not null"); 

				for (Book book : selectedBooks) {
				    System.out.println("Title: " + book.getTitle());
				    System.out.println("Author: " + book.getAuthor());
				    System.out.println("--------------------");
				
				}break;			
			}	
			case 12:{
				List<Member> selectedMembers = library.selectMembers("Memberid is not null"); 

				for (Member member : selectedMembers) {
				    System.out.println("Title: " + member.getName());
				    System.out.println("Info: " + member.getinfo());
				    System.out.println("--------------------");
				
				}break;
			}
						
				
			case 13:{
				
				 
				library.printBorrowedBooks();
				
				break;		
			
			}}
			System.out.println("Do you need anything else ");
			ask = scanner.nextLine();
		} while (ask.equalsIgnoreCase("yes"));

	scanner.close();

	}
}
