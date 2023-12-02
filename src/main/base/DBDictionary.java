package base;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;

public class DBDictionary {
    public static Connection con = null;
    private final String url = "jdbc:mysql://localhost:3306/dict2";
    private final String username = "root";
    private final String password = "minttwister";

    public DBDictionary() {
    }
    private void connectToDb() {
        System.out.println("Connecting to database ...");
        try {
            con = DriverManager.getConnection(url, username, password);
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
        String query = "SELECT Pronunciation, Explanation FROM dictionary WHERE Word = ?";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, wordTarget);
            try {
                ResultSet rs = ps.executeQuery();
                try {
                    if (rs.next()) {
                        String pronunciation = rs.getString("Pronunciation");
                        String explanation = rs.getString("Explanation");
                        return "Pronunciation: " + pronunciation + "\nExplanation: " + explanation;
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

    public boolean insertWord(String wordTarget, String pronunciation, String explanation) {
        String query = "INSERT INTO dictionary(Word, Pronunciation, Explanation) VALUES (?, ?, ?)";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, wordTarget);
            ps.setString(2, pronunciation);
            ps.setString(3, explanation);
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
        String query = "delete from dictionary where Word = ?";
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

    public boolean modifyWord(String wordTarget, String newPronunciation, String newExplanation) {
        String query = "UPDATE dictionary SET Pronunciation = ?, Explanation = ? WHERE Word = ?";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, newPronunciation);
            ps.setString(2, newExplanation);
            ps.setString(3, wordTarget);
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


    private ArrayList<Word> getWordsFromResultSet(PreparedStatement ps) throws SQLException {
        try {
            ResultSet rs = ps.executeQuery();
            try {
                ArrayList<Word> words = new ArrayList<>();
                while (rs.next()) {
                    String wordTarget = rs.getString("Word");
                    String pronunciation = rs.getString("Pronunciation");
                    String explanation = rs.getString("Explanation");

                    // Skip records with no wordTarget
                    if (wordTarget != null && !wordTarget.isEmpty()) {
                        words.add(new Word(wordTarget, explanation, pronunciation ));
                    }
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
        final String query = "SELECT * FROM dictionary";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            return getWordsFromResultSet(ps);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public ArrayList<Word> getAllWordsFromDb() {
        final String query = "SELECT * FROM dictionary";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            return getWordsFromResultSet(ps);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public ObservableList<Word> getAllWordsFromDB() {
        final String query = "select Word, Pronunciation, Explanation from dictionary";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            return FXCollections.observableArrayList(getWordsFromResultSet(ps));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return FXCollections.observableArrayList(); // Return an empty list in case of an error
    }

    public void closeConnection() {
        try {
            if (con != null && !con.isClosed()) {
                con.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String []args) throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        DBDictionary dict = new DBDictionary();
        dict.initialize();
        dict.insertWord("osu!", "/os/", "game bam vong");
        String wordTarget = "osu!";
        String wordExplain = dict.lookupWord(wordTarget);
        System.out.println("Explanation for '" + wordTarget + "': " + wordExplain);
    }
}
