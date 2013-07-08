package linux.android.club.fsoss.notes;

public class Note {
	private long id;
	private String title;
	private String note;
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getId() {
		return this.id;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public void setNote(String note) {
		this.note = note;
	}
	
	public String getNote() {
		return this.note;
	}

	@Override
	public String toString() {
		return this.title;
	}
}
