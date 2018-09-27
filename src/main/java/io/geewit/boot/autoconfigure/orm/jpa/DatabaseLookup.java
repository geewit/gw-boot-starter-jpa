package io.geewit.boot.autoconfigure.orm.jpa;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.jdbc.DatabaseDriver;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.jdbc.support.MetaDataAccessException;
import org.springframework.orm.jpa.vendor.Database;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Utility to lookup well known {@link Database Databases} from a {@link DataSource}.
 *
 * @author Eddú Meléndez
 * @author Phillip Webb
 */
final class DatabaseLookup {
	private transient final static Logger logger = LoggerFactory.getLogger(DatabaseLookup.class);

	private static final Map<DatabaseDriver, Database> LOOKUP;

	static {
		Map<DatabaseDriver, Database> map = new HashMap<>();
		map.put(DatabaseDriver.DERBY, Database.DERBY);
		map.put(DatabaseDriver.H2, Database.H2);
		map.put(DatabaseDriver.HSQLDB, Database.HSQL);
		map.put(DatabaseDriver.MYSQL, Database.MYSQL);
		map.put(DatabaseDriver.MARIADB, Database.MYSQL);
		map.put(DatabaseDriver.ORACLE, Database.ORACLE);
		map.put(DatabaseDriver.POSTGRESQL, Database.POSTGRESQL);
		map.put(DatabaseDriver.SQLSERVER, Database.SQL_SERVER);
		map.put(DatabaseDriver.DB2, Database.DB2);
		map.put(DatabaseDriver.INFORMIX, Database.INFORMIX);
		LOOKUP = Collections.unmodifiableMap(map);
	}

	private DatabaseLookup() {
	}

	/**
	 * Return the most suitable {@link Database} for the given {@link DataSource}.
	 * @param dataSource the source {@link DataSource}
	 * @return the most suitable {@link Database}
	 */
	public static Database getDatabase(DataSource dataSource) {
		if (dataSource == null) {
			return Database.DEFAULT;
		}
		try {
			String url = JdbcUtils.extractDatabaseMetaData(dataSource, "getURL");
			DatabaseDriver driver = DatabaseDriver.fromJdbcUrl(url);
			Database database = LOOKUP.get(driver);
			if (database != null) {
				return database;
			}
		}
		catch (MetaDataAccessException ex) {
			logger.warn("Unable to determine jdbc url from datasource", ex);
		}
		return Database.DEFAULT;
	}

}
