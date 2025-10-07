package JavierIraheta_OscarPerez_B_G1.FINALBOSS;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class ServletInitializer extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(FinalbossApplication.class);
	}

}
