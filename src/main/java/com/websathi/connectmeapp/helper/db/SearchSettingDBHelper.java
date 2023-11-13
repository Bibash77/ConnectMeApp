package com.websathi.connectmeapp.helper.db;

public class SearchSettingDBHelper {

    private static final String DATABASE_NAME = "busines";

    private static final int DATABASE_VERSION = 1;

    private static final String SEARCH_SETTING_TABLE = "search_setting";

    private static final String DROP_TABLE = "DROP TABLE IF EXISTS search_setting";

    private static final String SEARCH_TABLE_CREATE_QUERY = "CREATE TABLE search_setting( id INTEGER PRIMARY KEY AUTOINCREMENT , " +
            " VARCHAR, street VARCHAR, description VARCHAR, image varchar)";


}
