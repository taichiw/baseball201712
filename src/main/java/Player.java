import org.jsoup.select.Elements;

/**
 * Created by taichi.watanabe on 2017/08/28.
 */

@lombok.Data
public class Player {
	String name;
	int bats;	//打数（Not 打席数）
	int run;	//得点
	int hit;	//安打
	int rbi;	//打点
	int bb;		//四死球

	//TPA : Total Plate Appearance (=打席数)
	//得点 per 打席数
	public double getRunPerTPA() {
		return Double.valueOf(run) / Double.valueOf(bats + bb);
	}

	//生還率
	public double getSurviveRate() {
		return Double.valueOf(run) / Double.valueOf(hit + bb);
	}

	static Player playerBuilderFromElements(Elements tds) {
		Player player = new Player();
		player.setName(tds.get(1).text());
		player.setBats(Integer.valueOf(tds.get(3).text()));
		player.setRun(Integer.valueOf(tds.get(4).text()));
		player.setHit(Integer.valueOf(tds.get(5).text()));
		player.setRbi(Integer.valueOf(tds.get(6).text()));
		player.setBb(Integer.valueOf(tds.get(8).text()));

		return player;
	}
}
