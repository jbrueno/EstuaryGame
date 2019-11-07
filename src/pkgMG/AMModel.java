package pkgMG;

import java.util.*;
import javafx.scene.input.MouseEvent;
import pkgEnum.Game;
import pkgMover.DataNode;
import pkgMover.Mover;
import pkgMover.MatchingAnimal;
import javafx.scene.input.MouseEvent;
import pkgEnum.Game;

public class AMModel extends MinigameModel {
	
	ArrayList<DataNode> animals = new ArrayList<>();
	
	public AMModel() {
		g = Game.ANIMALMATCHING;
		createAnimals();
	}

	public void createAnimals() {
		//dns.clear(); // empty dns ArrayList of horseshoe crabs
		animals.add(new MatchingAnimal(0, 0, 50, 50, "MatchingAnimal", "Turtle", "I have a shell")); // just one for now
		animals.add(new MatchingAnimal(0, 0, 50, 50, "MatchingAnimal", "White-Tail Deer", "I have brown hair")); // just one for now
		animals.add(new MatchingAnimal(0, 0, 50, 50, "MatchingAnimal", "Mussel", "I'm dark and live in the water")); // just one for now
		animals.add(new MatchingAnimal(0, 0, 50, 50, "MatchingAnimal", "Crab", "I have claws")); // just one for now
		animals.add(new MatchingAnimal(0, 0, 50, 50, "MatchingAnimal", "Clam", "happy as a ___")); // just one for now
		//System.out.println(animals);
	}
	

	@Override
	public void update(MouseEvent me) {
	/*
	for(DataNode dn : dns) {
		MatchingAnimal ma = (MatchingAnimal) dn;
	*/	
	}
		
		
}
