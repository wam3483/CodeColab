package maxfat.spacesurvival.gamesystem;

import maxfat.util.random.IRandom;

import com.badlogic.ashley.core.Component;

public class BattleComponent extends Component {
	public float health;
	public int attack;
	public int defense;

	double chanceForFail = .05;
	double chanceForCritical = .05;

	public IRandom random;

	public BattleComponent(float health, int attack, int defense) {
		this.health = health;
		this.attack = attack;
		this.defense = defense;
	}

	public float rollForAttack() {
		float result = attack;

		double chance = random.nextDouble();
		if (chance < chanceForFail)
			result /= 2;
		else if (chance > (1 - chanceForCritical))
			result *= 2;

		return result;
	}
}
