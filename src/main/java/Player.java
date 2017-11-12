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

}
