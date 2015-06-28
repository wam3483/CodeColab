package maxfat.spacesurvival.gamesystem;

import com.badlogic.ashley.core.Component;

public class PopulationComponent extends Component {
	public String name;
	public float birthPercentPerTurn;
	public float starveChancePerTurn;
	public float resistienceToNaturalDeath;
	public float foodPerFarmerPerTurn;
	public float foodEatenPerPersonPerTurn;
	public float goldEarnedPerIdlePersonPerTurn;

	public float extrafoodBirthBonus;

	public PopulationComponent() {
		this(.1f, .1f, .1f);
	}

	public PopulationComponent(float birth, float starve, float naturalResist) {
		this.birthPercentPerTurn = birth;
		this.starveChancePerTurn = starve;
		this.resistienceToNaturalDeath = naturalResist;
		this.goldEarnedPerIdlePersonPerTurn = 1;
	}
}