package tutoriels.core.swing.views;

public class NodeInfo {

	private String title;
	private long id;
	
	public NodeInfo(long id) {
		this.id = id;
	}
	
	public String toString() {
		return title;
	}
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public long getId() {
		return id;
	}

}
