package base;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;

public class DBDictionary {
    private static final String URL =
            "jdbc:mysql://localhost:3306/mintwister";
    private static Connection con = null;

    private void connectToDb() {
        System.out.println("Connecting to database ...");
        try {
            con = DriverManager.getConnection(URL, "mintwister", "");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            System.out.println("Database is connected successfully!");
        }
    }

    public void initialize() throws SQLException {
        // connect to db
        connectToDb();
        // get all the word target
        ArrayList<String> allWordTarget = new ArrayList<>();
        final String query = "select * from dictionary";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            try {
                ResultSet rs = ps.executeQuery();
                try {
                    while (rs.next()) {
                        allWordTarget.add(rs.getString(2));
                    }
                } finally {
                    close(rs);
                }
            } finally {
                close(ps);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Add all the word target to trie data structure
        for (String wordTarget : allWordTarget) {
            Trie.addWord(wordTarget);
        }
    }

    private static void close(Connection con) {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void close(PreparedStatement ps) {
        try {
            if (ps != null) {
                ps.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void close(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String lookupWord(String wordTarget) {
        String query = "select wordExplain from dictionary where wordTarget = ?";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, wordTarget);
            try {
                ResultSet rs = ps.executeQuery();
                try {
                    // check if rs contains any rows
                    if (rs.next()) {
                        return rs.getString("wordExplain");
                    } else {
                        return "N/A";
                    }
                } finally {
                    close(rs);
                }
            } finally {
                close(ps);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "N/A";
    }

    public boolean insertWord(String wordTarget, String wordExplain) {
        String query = "insert into dictionary(wordTarget, wordExplain) values (?, ?)";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, wordTarget);
            ps.setString(2, wordExplain);
            try {
                ps.executeUpdate();
            } catch (SQLIntegrityConstraintViolationException e) {
                System.out.println("The word is already available in the dictionary");
                return false;
            } finally {
                close(ps);
            }
            Trie.addWord(wordTarget);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean removeWord(String wordTarget) {
        String query = "delete from dictionary where wordTarget = ?";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, wordTarget);
            try {
                if (ps.executeUpdate() == 0) {
                    return false;
                }
            } finally {
                close(ps);
            }
            Trie.removeWord(wordTarget);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean modifyWord(String wordTarget, String newWordExplain) {
        String query = "update dictionary set wordExplain = ? where wordTarget = ?";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, newWordExplain);
            ps.setString(2, wordTarget);
            try {
                if (ps.executeUpdate() == 0) {
                    return false;
                }
            } finally {
                close(ps);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    /*public ArrayList<Word> searchWord(String prefix) {
    }*/


    private ArrayList<Word> getWordsFromResultSet (PreparedStatement ps) throws SQLException {
        try {
            ResultSet rs = ps.executeQuery();
            try {
                ArrayList<Word> words = new ArrayList<>();
                while (rs.next()) {
                    words.add(new Word(rs.getString(2), rs.getString(3)));
                }
                return words;
            } finally {
                close(rs);
            }
        } finally {
            close(ps);
        }
    }

    public ArrayList<Word> getAllWords() {
        final String query = "select * from dictionary";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            return getWordsFromResultSet(ps);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public static void main(String []args) {

    }
}
