import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.apache.commons.lang3.StringUtils;

public class NineBattersInOneTeam {
	private List<Player> nineBattersInOneTeam;

	public NineBattersInOneTeam(List<Player> nineBattersInOneTeam) {
		this.nineBattersInOneTeam = Collections.unmodifiableList(nineBattersInOneTeam);
	}

	public List<String> getValues(Function<Player, Number> valueGetter) {
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
