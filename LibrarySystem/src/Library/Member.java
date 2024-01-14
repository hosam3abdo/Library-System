package Library;

class Member {
	private String name;
	private String info;
	private int memberId;

	public Member(String name, String info) {
		this.name = name;
		this.info = info;
		
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getinfo() {
		return info;
	}

	public void setMemberId(String info) {
		this.info = info;
	}

	public void printBookInfo() {
		System.out.printf("Name: %s\nInfo: %s\n", name, info);
	}

	public int getMemberId() {
		return memberId;
	}

	public void setMemberId(int memberId) {
		this.memberId = memberId;
	}
}
