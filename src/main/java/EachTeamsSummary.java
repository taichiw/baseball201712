import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import org.apache.commons.lang3.StringUtils;

public class EachTeamsSummary {

	Map<String, List<ScoreResultOfEachBattingOrder>> map;

	EachTeamsSummary(Map<String, List<ScoreResultOfEachBattingOrder>> map) {
		this.map = map;
	}

	void printResult() {
		System.out.println("得点");
		System.out.println(StringUtils.join(getValueList(ScoreResultOfEachBattingOrder::getRun), "\n"));

		System.out.println("得点 / 打席数");
		System.out.println(StringUtils.join(getValueList(ScoreResultOfEachBattingOrder::getRunPerTPA), "\n"));

		System.out.println("打点");
		System.out.println(StringUtils.join(getValueList(ScoreResultOfEachBattingOrder::getRbi), "\n"));

		System.out.println("生還率");
		System.out.println(StringUtils.join(getValueList(ScoreResultOfEachBattingOrder::getSurviveRate), "\n"));

		System.out.println("長打率");
		System.out.println(StringUtils.join(getValueList(ScoreResultOfEachBattingOrder::getSLG), "\n"));

		System.out.println("OPS");
		System.out.println(StringUtils.join(getValueList(ScoreResultOfEachBattingOrder::getOPS), "\n"));
	}

	private List<String> getValueList(Function<ScoreResultOfEachBattingOrder, Number> valueGetter) {
		List<String> values = new ArrayList<>();
		for (String team : map.keySet()) {
			List<ScoreResultOfEachBattingOrder> list = map.get(team);
			NineBattersInOneTeam nineBattersInOneTeam = new NineBattersInOneTeam(list);
			values.add(team + "," + StringUtils.join(nineBattersInOneTeam.getValues(valueGetter), ","));
		}
		return values;
	}
}
