package IFCfile;


public class Commentaire {
	String commentaire;
	Double positionX;
	Double positionY;
	public String getCommentaire() {
		return commentaire;
	}
	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}
	public Double getPositionX() {
		return positionX;
	}
	public void setPositionX(Double positionX) {
		this.positionX = positionX;
	}
	public Double getPositionY() {
		return positionY;
	}
	public void setPositionY(Double positionY) {
		this.positionY = positionY;
	}
	public Commentaire(String commentaire, double positionX, double positionY) {
		super();
		this.commentaire = commentaire;
		this.positionX = positionX;
		this.positionY = positionY;
	}
	
}