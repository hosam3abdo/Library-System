package Library;

class Book {
	private String title;
	private int availablebooks;
	private String author;
	
	private boolean available;

	public Book( String title, String author , int availablebooks) {
		
		this.title = title;
		this.author = author;
		this.availablebooks = availablebooks;
		this.available = true;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public void printBookInfo() {
		System.out.printf("Title: %s\nAuthor: %s\n", title, author);
	}

	@Override
	public String toString() {
		return "Book [title=" + title + ", author=" + author + ", available=" + available + "]";
	}

	public int getAvailableBooks() {
		return availablebooks;
	}

	public void setAvailableBooks(int availablebooks) {
		this.availablebooks = availablebooks;
	}

}