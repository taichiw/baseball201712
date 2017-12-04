import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class NineBattersInOneTeam {
	private List<ScoreResultOfEachBattingOrder> nineBattersInOneTeam;

	public NineBattersInOneTeam(List<ScoreResultOfEachBattingOrder> nineBattersInOneTeam) {
		this.nineBattersInOneTeam = Collections.unmodifiableList(nineBattersInOneTeam);
	}

	public List<String> getValues(Function<ScoreResultOfEachBattingOrder, Number> valueGetter) {
		return nineBattersInOneTeam.stream()
				.map(valueGetter).map(numberToStringWithFormatting)
				.collect(Collectors.toList());
	}

	private Function<Number, String> numberToStringWithFormatting = (number -> {
		if (number.getClass().equals(Integer.class)) {
			return String.valueOf(number);
		} else {
			return String.format("%.3f", number);
		}
	});
}
