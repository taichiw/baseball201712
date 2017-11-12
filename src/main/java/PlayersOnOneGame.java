import lombok.Data;

import java.time.LocalDate;
import java.util.List;

/**
 * Created by taichi.watanabe on 2017/08/31.
 */
@Data
public class PlayersOnOneGame {
	String team;
	LocalDate date;
	List<Player> playerList;
}
