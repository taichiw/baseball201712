import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.LocalDate;

import static org.junit.Assert.*;

/**
 * Created by taichi.watanabe on 2017/08/31.
 */
public class MainTest {
	@Test
	public void parsePage() throws Exception {
		PlayersOnOneGame playersOnOneGame = Main.parsePage(LocalDate.of(2017, 3, 31), 5, Main.B_T.TOP);
		System.out.println(playersOnOneGame);

	}
}
