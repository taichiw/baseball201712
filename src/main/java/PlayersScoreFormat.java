import org.jsoup.select.Elements;

/**
 * Created by taichi.watanabe on 2017/08/28.
 */

@lombok.Data
public abstract class PlayersScoreFormat {
	String name;
	int bats;	//打数（Not 打席数）
	int run;	//得点
	int hit;	//安打
	int rbi;	//打点
	int bb;		//四死球

	int singleHit;
	int twoBaseHit;
	int threeBaseHit;
	int homeRun;

	//TPA : Total Plate Appearance (=打席数)
	//得点 per 打席数
	public double getRunPerTPA() {
		return Double.valueOf(run) / Double.valueOf(bats + bb);
	}

	//生還率
	public double getSurviveRate() {
		return Double.valueOf(run) / Double.valueOf(hit + bb);
	}

	//長打率 : Slugging percentage / SLG
	public double getSLG() {
		return Double.valueOf(singleHit + 2 * twoBaseHit + 3 * threeBaseHit + 4 * homeRun) / Double.valueOf(bats);
	}

	//出塁率 : On base percentabge / OBP
	//犠飛を本当は引くんだけどメンドウなので省略
	public double getOBP() {
		return Double.valueOf(hit + bb) / (bats + bb);
	}

	//OPS
	public double getOPS() {
		return getOBP() + getSLG();
	}
}
