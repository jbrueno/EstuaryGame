package pkgMG;

import java.util.*;
import javafx.scene.input.MouseEvent;
import pkgEnum.Game;
import pkgMover.DataNode;
import pkgMover.Mover;
import pkgMover.MatchingAnimal;
import javafx.scene.input.MouseEvent;
<<<<<<< HEAD
import pkgEnum.Game;
=======
import pkgEnum.GameType;
import pkgMover.Mover;

public class AMModel extends MinigameModel{
	
	private final GameType gameType = GameType.ANIMALMATCHING;
	Mover[] animals;
	@Override
	void update(MouseEvent me) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	@Override
	void handleBorderCollision(Mover m) {
		// TODO Auto-generated method stub
		
	}
	
	
>>>>>>> branch 'master' of https://github.com/CISC275-Fall2019/cisc275f19-project-cisc275f19-team-11-0

public class AMModel extends MinigameModel {
	
	ArrayList<Mover> animals = new ArrayList<>(); 
	
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
