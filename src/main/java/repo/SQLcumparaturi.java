package repo;

import domain.cumparaturi;
import org.sqlite.SQLiteDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SQLcumparaturi extends MemoryRepository<cumparaturi> implements AutoCloseable {
    private Connection connection;
    private static final String DB_URL = "jdbc:sqlite:C:/Users/coman/IdeaProjects/lab4/cumparaturi/cumparaturi";

    public SQLcumparaturi() throws RepositoryException, DuplicateIDException {
        openConnection();
        createTable();
        loadData();
        if (entities.isEmpty()) {
            predefined();
        }
    }

    private void predefined() throws DuplicateIDException, RepositoryException {
        cumparaturi c1=new cumparaturi(102,"Ariston","WSL",2400,2);
        cumparaturi c2=new cumparaturi(100,"Lenovo","ThinkPad",9500,14);
        cumparaturi c3=new cumparaturi(101,"Asus","Strix",7700,4);
        cumparaturi c4=new cumparaturi(104,"Whirlpool","Super",3200,10);
        cumparaturi c5=new cumparaturi(103,"Bosh","Series",1900,11);
        add(c1);
        add(c2);
        add(c3);
        add(c4);
        add(c5);
    }

    private void createTable() {
        String sql = "CREATE TABLE IF NOT EXISTS cumparaturi (" +
                "id INTEGER PRIMARY KEY, " +
                "marca VARCHAR(50), " +
                "nume VARCHAR(50), " +
                "pret INT, " +
                "cantitate INT)";
        try (Statement start = connection.createStatement()) {
            start.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println("Error creating table: " + e.getMessage());
        }
    }

    private void openConnection() {
        try {
            SQLiteDataSource dataSource = new SQLiteDataSource();
            dataSource.setUrl(DB_URL);
            if (connection == null || connection.isClosed()) {
                connection = dataSource.getConnection();
            }
        } catch (SQLException e) {
            System.out.println("Error opening connection: " + e.getMessage());
        }
    }

    private void loadData() {
        String sql = "SELECT * FROM cumparaturi"; // Corrected table name
        try (PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                cumparaturi c = new cumparaturi(
                        resultSet.getInt("id"),
                        resultSet.getString("marca"),
                        resultSet.getString("nume"),
                        resultSet.getInt("pret"),
                        resultSet.getInt("cantitate")
                );
                entities.add(c);
            }
        } catch (SQLException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
    }

    public void add(cumparaturi c) throws DuplicateIDException, RepositoryException {
        super.add(c);
        String sql = "INSERT INTO cumparaturi VALUES (?,?, ?, ?, ?)";
        try (PreparedStatement addStatement = connection.prepareStatement(sql)) {
            addStatement.setInt(1, c.getId());
            addStatement.setString(2, c.getMarca());
            addStatement.setString(3, c.getNume());
            addStatement.setInt(4, c.getPret());
            addStatement.setInt(5, c.getCantitate());
            addStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    public void remove(int id) throws RepositoryException {
        super.remove(id);
        String sql = "DELETE FROM cumparaturi WHERE id = ?";
        try (PreparedStatement removeStatement = connection.prepareStatement(sql)) {
            removeStatement.setInt(1, id);
            removeStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    public cumparaturi find(int id) {
        String sql = "SELECT * FROM cumparaturi WHERE id = ?";
        try (PreparedStatement findStatement = connection.prepareStatement(sql)) {
            findStatement.setInt(1, id);
            ResultSet resultSet = findStatement.executeQuery();
            if (resultSet.next()) {
                return new cumparaturi(
                        resultSet.getInt("id"),
                        resultSet.getString("marca"),
                        resultSet.getString("nume"),
                        resultSet.getInt("pret"),
                        resultSet.getInt("cantitate")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error finding record: " + e.getMessage());
        }
        return null;
    }

    public void update(cumparaturi p) {
        String sql = "UPDATE cumparaturi SET marca = ?, nume = ?, pret = ?, cantitate = ? WHERE id = ?";
        try (PreparedStatement updateStatement = connection.prepareStatement(sql)) {
            updateStatement.setString(1, p.getMarca());
            updateStatement.setString(2, p.getNume());
            updateStatement.setInt(3, p.getPret());
            updateStatement.setInt(4, p.getCantitate());
            updateStatement.setInt(5, p.getId());
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error updating record: " + e.getMessage());
        }
    }

    public Collection<cumparaturi> getAll() throws RepositoryException {
        List<cumparaturi> results = new ArrayList<>();
        String sql = "SELECT * FROM cumparaturi";
        try (PreparedStatement getAllStatement = connection.prepareStatement(sql);
             ResultSet resultSet = getAllStatement.executeQuery()) {
            while (resultSet.next()) {
                cumparaturi c = new cumparaturi(
                        resultSet.getInt("id"),
                        resultSet.getString("marca"),
                        resultSet.getString("nume"),
                        resultSet.getInt("pret"),
                        resultSet.getInt("cantitate")
                );
                results.add(c);
            }
            return results;
        } catch (SQLException e) {
            throw new RepositoryException(e.getMessage());
        }
    }

    @Override
    public void close() throws Exception {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}

