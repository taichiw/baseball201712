import com.sun.deploy.util.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static java.time.format.DateTimeFormatter.BASIC_ISO_DATE;

/**
 * Created by taichi.watanabe on 2017/08/28.
 */
public class Main {
	public static void main(String[] args) throws IOException {

		final LocalDate OPENING_GAME_DAY = LocalDate.of(2017, 3, 31);
		final LocalDate END_DAY = LocalDate.of(2017, 4, 1);
//		 final LocalDate END_DAY = LocalDate.of(2017, 10, 13);

		final List<String> ORDERS = Arrays.asList("1番", "2番", "3番", "4番", "5番", "6番", "7番", "8番", "9番");

		List<PlayersOnOneGame> playersListList = getPlayersOnOneGames(OPENING_GAME_DAY, END_DAY);
		printScore(playersListList);

		Map<String, List<PlayersOnOneGame>> eachTeamMap = playersListList.parallelStream()
				.collect(Collectors.groupingBy(PlayersOnOneGame::getTeam));

		Map<String, List<Player>> eachTeamsSummary = new HashMap<>();
		for (String team : eachTeamMap.keySet()) {
			eachTeamsSummary.put(team, makeSummaryForEachOrder(eachTeamMap.get(team)));
		}

		System.out.println("得点");
		List<String> runs = new ArrayList<>();
		for (String team : eachTeamsSummary.keySet()) {
			runs.add(team + "," + eachTeamsSummary.get(team).stream()
					.map(player -> String.valueOf(player.getRun()))
					.collect(Collectors.joining(",")));
		}
		System.out.println(StringUtils.join(runs, "\n"));

		System.out.println("得点 / 打席数");
		List<String> runPerTPAs = new ArrayList<>();
		for (String team : eachTeamsSummary.keySet()) {
			runPerTPAs.add(team + "," + eachTeamsSummary.get(team).stream()
					.map(Player::getRunPerTPA).map(aDouble -> String.format("%.3f", aDouble))
					.collect(Collectors.joining(",")));
		}
		System.out.println(StringUtils.join(runPerTPAs, "\n"));

		System.out.println("打点");
		List<String> rbis = new ArrayList<>();
		for (String team : eachTeamsSummary.keySet()) {
			rbis.add(team + "," + eachTeamsSummary.get(team).stream()
					.map(Player::getRbi).map(String::valueOf)
					.collect(Collectors.joining(",")));
		}
		System.out.println(StringUtils.join(rbis, "\n"));

		System.out.println("生還率");
		List<String> surviveRates = new ArrayList<>();
		for (String team : eachTeamsSummary.keySet()) {
			surviveRates.add(team + "," + eachTeamsSummary.get(team).stream()
					.map(Player::getSurviveRate).map(aDouble -> String.format("%.3f", aDouble))
					.collect(Collectors.joining(",")));
		}
		System.out.println(StringUtils.join(surviveRates, "\n"));
	}

	private static List<PlayersOnOneGame> getPlayersOnOneGames(LocalDate startDate, LocalDate endDate) {
		List<LocalDate> dateList = new ArrayList<>();
		for (LocalDate localDate = startDate; localDate.isBefore(endDate); localDate = localDate.plusDays(1)) {
			dateList.add(localDate);
		}
		final List<Integer> GAME_NUMBERS = Arrays.asList(1, 2, 3, 4, 5, 6);
		return dateList.parallelStream()
					.map(
							localDate -> {
								List<PlayersOnOneGame> topList = GAME_NUMBERS.parallelStream()
										.map(i -> {
													return parsePage(localDate, i, B_T.TOP);
												}
										).collect(Collectors.toList());

								List<PlayersOnOneGame> bottomList = GAME_NUMBERS.parallelStream()
										.map(i -> {
													return parsePage(localDate, i, B_T.BOTTOM);
												}
										).collect(Collectors.toList());

								topList.addAll(bottomList);

								return topList;
							})
					.filter(list -> !(list.isEmpty()))
					.flatMap(
							list ->  list.parallelStream()
					)
					.filter(playersOnOneGame -> playersOnOneGame != null)
//					.filter(playersOnOneGame -> playersOnOneGame.team.equals("F"))
					.collect(Collectors.toList());
	}

	public enum B_T {
		TOP("tableTop"), BOTTOM("tableBottom");

		private String value;

		B_T(String tableClass) {
			this.value = tableClass;
		}

		String getTabeleClass() {
			return this.value;
		}
	}

	private static void printScore(List<PlayersOnOneGame> playerListList) {
		Map<String, List<PlayersOnOneGame>> eachTeamMap = playerListList.parallelStream()
				.collect(Collectors.groupingBy(PlayersOnOneGame::getTeam));

		eachTeamMap.entrySet().stream().forEach(
				e -> {
					System.out.println(e.getKey());
					printScoreCorePart(e.getValue());
				}
		);
	}

	private static void printScoreCorePart(List<PlayersOnOneGame> playerListList) {
		List<Player> summaryForEachOrderList = makeSummaryForEachOrder(playerListList);
		System.out.println("打順,打数,得点,打点,四死球,安打");
		summaryForEachOrderList.stream().forEach(
				player -> System.out.println(player.getName()
											 + "," + player.getBats()
											 + "," + player.getRun()
											 + "," + player.getRbi()
											 + "," + player.getBb()
											 + "," + player.getHit())
		);

	}

	private static List<Player> makeSummaryForEachOrder(List<PlayersOnOneGame> playerListList) {
		final List<Integer> ORDERS = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8);
		return ORDERS.stream().map(
				currentOrder -> {
					int bats = playerListList.parallelStream()
							.filter(playersOnOneGame -> playersOnOneGame != null && playersOnOneGame.getPlayerList() != null && !playersOnOneGame.getPlayerList().isEmpty())
							.mapToInt(players -> {
								return players.getPlayerList().get(currentOrder).getBats();
							})
							.sum();
					int run = playerListList.parallelStream()
							.filter(playersOnOneGame -> playersOnOneGame != null && playersOnOneGame.getPlayerList() != null && !playersOnOneGame.getPlayerList().isEmpty())
							.mapToInt(players -> {
								return players.getPlayerList().get(currentOrder).getRun();
							})
							.sum();
					int rbi = playerListList.parallelStream()
							.filter(playersOnOneGame -> playersOnOneGame != null && playersOnOneGame.getPlayerList() != null && !playersOnOneGame.getPlayerList().isEmpty())
							.mapToInt(players -> {
								return players.getPlayerList().get(currentOrder).getRbi();
							})
							.sum();
					int hit = playerListList.parallelStream()
							.filter(playersOnOneGame -> playersOnOneGame != null && playersOnOneGame.getPlayerList() != null && !playersOnOneGame.getPlayerList().isEmpty())
							.mapToInt(players -> {
								return players.getPlayerList().get(currentOrder).getHit();
							})
							.sum();
					int bb = playerListList.parallelStream()
							.filter(playersOnOneGame -> playersOnOneGame != null && playersOnOneGame.getPlayerList() != null && !playersOnOneGame.getPlayerList().isEmpty())
							.mapToInt(players -> {
								return players.getPlayerList().get(currentOrder).getBb();
							})
							.sum();
//					System.out.println(String.valueOf(currentOrder + 1)
//							+ "," + String.valueOf(run)
//							+ "," + String.valueOf(rbi)
//							+ "," + String.valueOf(hit + bb)
//							+ "," + String.valueOf(hit)    //本当は長打にしたい
//					);
					Player summaryForTheOrder = new Player();
					summaryForTheOrder.setName(currentOrder + 1 + "番");
					summaryForTheOrder.setBats(bats);
					summaryForTheOrder.setBb(bb);
					summaryForTheOrder.setHit(hit);
					summaryForTheOrder.setRbi(rbi);
					summaryForTheOrder.setRun(run);

					return summaryForTheOrder;
				}
		).collect(Collectors.toList());
	}

	static PlayersOnOneGame parsePage(LocalDate localDate, int index, B_T tableClass) {
		System.out.println(localDate.format(BASIC_ISO_DATE) + ":" + index);

		String dateStr = localDate.format(BASIC_ISO_DATE);
		try {
			Document document = Jsoup.connect("https://baseball.yahoo.co.jp/npb/game/" + dateStr + "0" + String.valueOf(index) + "/stats").get();
			Element div = document.select("div ." + tableClass.getTabeleClass()).first();

			if (div == null) {
				return null;
			}


			Elements trs = div.select("tr");
			String team = "";
			try {
				team = div.classNames().parallelStream().filter(className -> !className.equals(tableClass.getTabeleClass())).findFirst().get();
			} catch (Exception e) {
				return null;
			}

			List<Player> playerList = trs.parallelStream()
					.map(tr -> {
						return tr.select("td");
					})
					.filter(tds -> tds.size() > 0)
					.filter(tds -> {
						String position = tds.get(0).text();
						return position.length() > 0 && position.substring(0, 1).equals("(");
					})
					.map(tds -> {
						return Player.playerBuilderFromElements(tds);
					})
					.collect(Collectors.toList());

			PlayersOnOneGame playersOnOneGame = new PlayersOnOneGame();
			playersOnOneGame.setTeam(team);
			playersOnOneGame.setDate(localDate);
			playersOnOneGame.setPlayerList(playerList);

			System.out.println(playersOnOneGame);
			return playersOnOneGame;
		} catch (IOException e) {
			return null;
		}
	}

}