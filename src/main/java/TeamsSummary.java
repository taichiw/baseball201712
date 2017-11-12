import java.util.Collections;
import java.util.List;
import java.util.Map;

public class TeamsSummary {
	List<Player> teamsSummary;

	public TeamsSummary(List<Player> teamsSummary) {
		this.teamsSummary = Collections.unmodifiableList(teamsSummary);
	}
}
