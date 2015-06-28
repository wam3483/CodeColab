package maxfat.spacesurvival.gamesystem;

import java.util.HashSet;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;

public class SystemBattle extends IteratingSystem {
	private final HashSet<Integer> idSet = new HashSet<Integer>();
	public IBattleCompleteListener battleListener;

	private ComponentMapper<BattleComponent> battleMapper = ComponentMapper
			.getFor(BattleComponent.class);
	private ComponentMapper<BattleIdComponent> idMapper = ComponentMapper
			.getFor(BattleIdComponent.class);

	@SuppressWarnings("unchecked")
	public SystemBattle() {
		super(Family.all(BattleComponent.class, BattleIdComponent.class).get());
	}

	private Entity getEntityWithBattleId(int id) {
		for (Entity e : this.getEntities()) {
			BattleIdComponent c = this.idMapper.get(e);
			if (id == c.battleId)
				return e;
		}
		return null;
	}

	private void fight(Entity e1, Entity e2) {
		BattleComponent b1 = battleMapper.get(e1);
		BattleComponent b2 = battleMapper.get(e1);
		b2.health -= b1.rollForAttack();
		b1.health -= b2.rollForAttack();
		if (b1.health <= 0 || b2.health <= 0) {
			if (this.battleListener != null) {
				this.battleListener.completedBattle(b1, b2);
			}
		}
	}

	public void update(float time) {
		idSet.clear();
		super.update(time);
	}

	@Override
	protected void processEntity(Entity entity, float deltaTime) {
		BattleIdComponent id1 = this.idMapper.get(entity);
		if (!idSet.contains(id1.battleId)) {
			Entity e2 = getEntityWithBattleId(id1.battleId);
			fight(entity, e2);
		}
	}

	public interface IBattleCompleteListener {
		void completedBattle(BattleComponent b1, BattleComponent b2);
	}
}