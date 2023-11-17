package base;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;

public class DBDictionary {
    private static final String DATABASE_URL =
            "jdbc:mysql://localhost:3306/mintwister";
    private static final String USERNAME = "mintwister";
    private static final String PASSWORD = "mintwister";
    private static final String EXPORTTOFILEPATH = "src/main/resources/DBDExportFile.txt";
    private static Connection con = null;

    private void connect() {
        System.out.println("Connecting database ...");
        try {
            con = DriverManager.getConnection(DATABASE_URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            throw new IllegalStateException("Cannot connect the database!", e);
        }
    }

    public void initialize() {
        // connect to db
        connect();
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

    /**
     * Reference:<a href=" https://coderanch.com/t/300886/databases/Proper-close-Connection-Statement-ResultSe">...</a>t
     */
    public static void close(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Database disconnected!");
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

    public void exportToFileTxt() {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(EXPORTTOFILEPATH), StandardCharsets.UTF_8))) {
            ArrayList<Word> listWord = getAllWords();
            StringBuilder res = new StringBuilder();
            for (Word word : listWord) {
                res.append(word.getWordTarget()).append("\t").
                        append(word.getWordExplain()).append("\n");
            }
            bufferedWriter.write(res.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String []args) {
        DBDictionary dbDictionary = new DBDictionary();
        dbDictionary.initialize();
        dbDictionary.exportToFileTxt();
    }
}
