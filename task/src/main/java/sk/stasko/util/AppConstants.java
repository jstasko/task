package sk.stasko.util;

public class AppConstants {
    public static final String SQL_INSERT_USER = "INSERT INTO SUSERS (USER_ID, USER_GUID, USER_NAME) VALUES (?, ?, ?)";
    public static final String SQL_SELECT_ALL = "SELECT * FROM SUSERS";
    public static final String SQL_DELETE_ALL = "DELETE FROM SUSERS";
    public static final String SQL_INIT_SCRIPT_PROPERTY = "db.init.script";
    public static final String JDBC_URL_PROPERTY = "jdbc.url";
}
