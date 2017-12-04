import org.jsoup.select.Elements;

/**
 * Created by taichi.watanabe on 2017/08/28.
 */

@lombok.Data
public class Player extends PlayersScoreFormat {

	@Override
	public String toString() {
		return super.toString();
	}

	static Player playerBuilderFromElements(Elements tds) {
		Player player = new Player();
		player.setName(tds.get(1).text());
		player.setBats(Integer.valueOf(tds.get(3).text()));
		player.setRun(Integer.valueOf(tds.get(4).text()));
		player.setHit(Integer.valueOf(tds.get(5).text()));
		player.setRbi(Integer.valueOf(tds.get(6).text()));
		player.setBb(Integer.valueOf(tds.get(8).text()));

		player.setSingleHit((int)(tds.stream().filter(element -> element.text().indexOf("安") > 0).count()));
		player.setTwoBaseHit((int)(tds.stream().filter(element -> element.text().indexOf("２") > 0).count()));
		player.setThreeBaseHit((int)(tds.stream().filter(element -> element.text().indexOf("３") > 0).count()));
		player.setHomeRun(Integer.valueOf(tds.get(12).text()));

		return player;
	}
}
