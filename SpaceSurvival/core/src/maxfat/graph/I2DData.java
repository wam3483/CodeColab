package maxfat.graph;

import com.badlogic.gdx.math.Vector2;

public interface I2DData {
	Vector2 getPoint();

	void setPoint(Vector2 point);

	float getMinDistance();

	void setMinDistance(float distance);

	float getSize();

	void setSize(float size);
}
