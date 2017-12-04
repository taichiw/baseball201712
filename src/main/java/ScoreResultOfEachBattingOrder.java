import java.util.ArrayList;
import java.util.List;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;

public class ScoreResultOfEachBattingOrder extends PlayersScoreFormat {

	int battingOrder;
	List<PlayersOnOneGame> playeresOnOneGames;

	private ScoreResultOfEachBattingOrder(int battingOrder, List<PlayersOnOneGame> playeresOnOneGames) {

		this.battingOrder = battingOrder;
		this.playeresOnOneGames = new ArrayList<>(playeresOnOneGames);

		setName(battingOrder + 1 + "番");

		setBats(getSumOfSpecifiedOrderAndSpecifiedValue(playeresOnOneGames, battingOrder, Player::getBats));
		setBb(getSumOfSpecifiedOrderAndSpecifiedValue(playeresOnOneGames, battingOrder, Player::getBb));
		setHit(getSumOfSpecifiedOrderAndSpecifiedValue(playeresOnOneGames, battingOrder, Player::getHit));
		setRbi(getSumOfSpecifiedOrderAndSpecifiedValue(playeresOnOneGames, battingOrder, Player::getRbi));
		setRun(getSumOfSpecifiedOrderAndSpecifiedValue(playeresOnOneGames, battingOrder, Player::getRun));

		setSingleHit(getSumOfSpecifiedOrderAndSpecifiedValue(playeresOnOneGames, battingOrder, Player::getSingleHit));
		setTwoBaseHit(getSumOfSpecifiedOrderAndSpecifiedValue(playeresOnOneGames, battingOrder, Player::getTwoBaseHit));
		setThreeBaseHit(getSumOfSpecifiedOrderAndSpecifiedValue(playeresOnOneGames, battingOrder, Player::getThreeBaseHit));
		setHomeRun(getSumOfSpecifiedOrderAndSpecifiedValue(playeresOnOneGames, battingOrder, Player::getHomeRun));

		System.out.println(this);
	}

	public static ScoreResultOfEachBattingOrder buildFromPlayersOnOneGames(List<PlayersOnOneGame> playeresOnOneGames, int battingOrder) {
		return new ScoreResultOfEachBattingOrder(battingOrder, playeresOnOneGames);
	}

	private static int getSumOfSpecifiedOrderAndSpecifiedValue(List<PlayersOnOneGame> playeresOnOneGames, Integer currentOrder, ToIntFunction<Player> getValueFunction) {
		return playeresOnOneGames.parallelStream()
				.filter(playersOnOneGame -> playersOnOneGame != null && playersOnOneGame.getPlayerList() != null && !playersOnOneGame.getPlayerList().isEmpty())
				.map(PlayersOnOneGame::getPlayerList)
				.map(players -> players.get(currentOrder))
				.mapToInt(getValueFunction)
				.sum();
	}
}
