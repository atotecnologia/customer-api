package br.com.composable.customerapi.infra.config;

import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;

public class DataSource {

	public static Jdbi getJdbi() {
    	return Jdbi
    			.create(ConnectionFactory.getConnection())
    			.installPlugin(new SqlObjectPlugin());

    }
}
