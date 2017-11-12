import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;

public class EachTeamsSummary {

	Map<String, List<Player>> map;

	EachTeamsSummary(Map<String, List<Player>> map) {
		this.map = map;
	}

	void printResult() {
		System.out.println("得点");
		System.out.println(StringUtils.join(getValueList(Player::getRun), "\n"));

		System.out.println("得点 / 打席数");
		System.out.println(StringUtils.join(getValueList(Player::getRunPerTPA), "\n"));

		System.out.println("打点");
		System.out.println(StringUtils.join(getValueList(Player::getRbi), "\n"));

		System.out.println("生還率");
		System.out.println(StringUtils.join(getValueList(Player::getSurviveRate), "\n"));
	}

	private List<String> getValueList(Function<Player, Number> valueGetter) {
		List<String> values = new ArrayList<>();
		for (String team : map.keySet()) {
			List<Player> list = map.get(team);
			NineBattersInOneTeam nineBattersInOneTeam = new NineBattersInOneTeam(list);
			values.add(team + "," + StringUtils.join(nineBattersInOneTeam.getValues(valueGetter), ","));
		}
		return values;
	}
}
