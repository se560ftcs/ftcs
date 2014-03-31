package edu.metu.se560.stemming;

import zemberek.morphology.apps.TurkishMorphParser;
import zemberek.morphology.parser.MorphParse;

import java.io.IOException;
import java.util.List;

public class Stemming {
    private TurkishMorphParser parser;

    public Stemming() {
        try {
			this.parser = TurkishMorphParser.createWithDefaults();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

    public String parse(String word) {
        List<MorphParse> parses = parser.parse(word);
        String root = word;
        if(parses.size() > 0){
        	MorphParse morphParse = parses.get(0);
        	root = morphParse.getLemma();
        }
    	return root;
    }
    
}
