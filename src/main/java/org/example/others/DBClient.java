package org.example.others;

public class DBClient {
    private static DBClient dbClient;

    private DBClient() {}

    public DBClient getDbClient() {
        if (dbClient == null) {
            dbClient = new DBClient();
        }
        return dbClient;
    }
}
