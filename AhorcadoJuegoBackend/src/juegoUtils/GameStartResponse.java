package juegoUtils;

public class GameStartResponse {
	private String maskedWord;
	private int lives;
	private String hint;
	private String category;

	public GameStartResponse(String maskedWord, int lives, String hint, String category) {
		this.maskedWord = maskedWord;
		this.lives = lives;
		this.hint = hint;
		this.category = category;
	}

	public String getMaskedWord() { return maskedWord; }
	public int getLives() { return lives; }
	public String getHint() { return hint; }
	public String getCategory() { return category; }
}
