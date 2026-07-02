package containers;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Random;

import models.Word;
import validators.StringValidator;
import exceptions.ValidatorException;

public class WordContainer {
	private ArrayList<Word> wordsList;
	private Random random;
	
	public WordContainer() {
		this.wordsList = new ArrayList<Word>();
		this.random = new Random();
		//loadDefaultWords();
	}
	
	// esto lo voy a poner en otro lado, main seguro
	/*
	private void loadDefaultWords() {
        wordsList.add(new Word("MATE", "Cultura", "Bebida típica que se toma con bombilla"));
        wordsList.add(new Word("EMPANADA", "Gastronomía", "Masa rellena horneada o frita"));
        wordsList.add(new Word("ASADO", "Gastronomía", "Carne cocinada a las brasas"));
        wordsList.add(new Word("CHIMICHURRI", "Gastronomía", "Salsa verde para la carne"));
        wordsList.add(new Word("FUTBOL", "Deporte", "El deporte más popular del mundo"));
        wordsList.add(new Word("QUILOMBO", "Lunfardo", "Gran desorden o lío"));
        wordsList.add(new Word("FIACA", "Lunfardo", "Pereza, ganas de no hacer nada"));
        wordsList.add(new Word("PIBE", "Lunfardo", "Chico joven"));
        wordsList.add(new Word("BONDI", "Lunfardo", "El colectivo porteño"));
        wordsList.add(new Word("CHAMUYO", "Lunfardo", "Palabrería para convencer"));
        wordsList.add(new Word("BANDONEON", "Música", "Instrumento del tango"));
        wordsList.add(new Word("PERONISMO", "Historia", "Movimiento político de los años 40"));
        wordsList.add(new Word("PATAGONIA", "Geografía", "Región del sur del país"));
        wordsList.add(new Word("DULCEDELECHE", "Gastronomía", "El manjar más amado"));
        wordsList.add(new Word("LOCRO", "Gastronomía", "Guiso patrio del norte"));
        wordsList.add(new Word("CUMBIA", "Música", "Género musical bailable"));
        wordsList.add(new Word("MILANESA", "Gastronomía", "Carne empanizada y frita"));
        wordsList.add(new Word("TANGO", "Música", "Baile emblemático de Buenos Aires"));
        wordsList.add(new Word("PELOTA", "Deporte", "Objeto esférico usado en deportes"));
        wordsList.add(new Word("YERBA", "Cultura", "Planta base de la bebida nacional"));
    }
	*/
	
	public Word getRandomWord() {
		if (wordsList.isEmpty()) {
			//throw exception no hay palabras para poder jugar comuniquese con el admin?
		}
		int index = random.nextInt(wordsList.size());
		return wordsList.get(index);
	}
	
	
	public Word getWordByName(String name) {
        Optional<Word> word = wordsList.stream()
                .filter(w -> w.getName().equalsIgnoreCase(name))
                .findFirst();
        return word.orElse(null);
    }
	
	public ArrayList<Word> getWordsByCategory(String categoryId){
		ArrayList<Word> filtered = new ArrayList<>();
		
		for(Word word: wordsList) {
			if(word.getCategory().equals(categoryId)) {
				filtered.add(word);
			}
		}
		return filtered;
	}
	
	//@admin //dejamos el throw o un trycatch?
	public void addWord(Word newWord) throws ValidatorException{
		StringValidator.from(newWord.getName())
			.notBlankValidate()
			.minLengthValidate(2)
			.maxLengthValidate(30);
    
		StringValidator.from(newWord.getHint())
	        .notBlankValidate()
	        .minLengthValidate(5);
		
		wordsList.add(newWord);
	}
	
	public ArrayList<Word> getAllWords() {
		return new ArrayList<>(wordsList);
	}

	//@admin
	public boolean removeWord(String name){
		Word word = getWordByName(name);
		if(word != null) {
			wordsList.remove(word);
			return true;
		}
		return false;
	}
	
	
}
