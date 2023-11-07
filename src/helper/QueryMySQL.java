package helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

// Warning!: JDCB API provided by instructors is vulnerable to a SQL injection attack
// (use PreparedStatements in production)
// Also not considering scale, connection is kept on the whole application, not accounting for
// dropped connections etc.
// a helper function that uses prepared statements would use variable arguments then iterate throuhg it
// to add the parameters to the prepared statements.
public class QueryMySQL {
    public static ResultSet query(String query) {
        ResultSet results = null;
        try {
            JDBC.makeConnection();
            JDBC.makePreparedStatement(query, JDBC.getConnection());
            results = JDBC.getPreparedStatement().executeQuery();
        } catch (Exception e) {
            System.out.println(e);
            return results;
        }
        return results;
    }
}
