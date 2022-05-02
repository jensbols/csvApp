import java.sql.SQLException;

import cli.MainCli;
import domain.DomainController;

public class StartUp {
	public static void main(String[] args) throws SQLException {
		DomainController dc = new DomainController();
		MainCli mainCli = new MainCli(dc);
		mainCli.run();
	}
}
