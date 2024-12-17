import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseOperation {
    static final String url = "jdbc:mysql://localhost:3306/MTM";
    static final String username = "root";
    static final String password = "1234";

    public Connection connectToDatabase() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            System.out.println(e);
        }
        return conn;
    }

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/MTM";
            String username = "root";
            String password = "1234";
            Connection con = DriverManager.getConnection(url, username, password);
            if (con.isClosed()) {
                System.out.println("Connection is closed");
            } else {
                System.out.println("Connection created ....");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public int executeUpdate(String sql, Object[] values) {
        int rowsAffected = 0;
        try (Connection conn = connectToDatabase();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            for (int i = 0; i < values.length; i++) {
                ps.setObject(i + 1, values[i]);
            }
            rowsAffected = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    public List<Map<String, Object>> getRecords(String sql) {
        List<Map<String, Object>> records = new ArrayList<>();
        try (Connection conn = connectToDatabase();
                PreparedStatement pstmt = conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    row.put(rsmd.getColumnName(i), rs.getObject(i));
                }
                records.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return records;
    }

    public int getSeatingCapacity(String sql, int parameter) {
        int record = 0;
        try (Connection conn = connectToDatabase();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, parameter);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                record = rs.getInt("SeatingCapacity");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return record;
    }

    public ArrayList<Integer> getBookedSeats(int showtimeID) {
        String sql = "SELECT SelectedSeats from bookings where ShowtimeID = ?";
        ArrayList<Integer> bookedSeats = new ArrayList<>();

        try (Connection conn = connectToDatabase();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, showtimeID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                bookedSeats.add(rs.getInt("SelectedSeats"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookedSeats;
    }

    public ArrayList<Integer> getShowtimeDetailsForusername(int usernameID, int showtimeID) {
        String sql = "SELECT SelectedSeats from bookings where ShowtimeID = ?";
        ArrayList<Integer> bookedSeats = new ArrayList<>();

        try (Connection conn = connectToDatabase();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, showtimeID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                bookedSeats.add(rs.getInt("SelectedSeats"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookedSeats;
    }

    public List<Map<String, Object>> getAllBookingsForUser(int userID) {
        List<Map<String, Object>> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE UserID = ?";
        Object[] values = { userID };
        try (Connection conn = connectToDatabase();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, userID);

            ResultSet rs = ps.executeQuery();

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (rs.next()) {
                Map<String, Object> booking = new HashMap<>();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    Object columnValue = rs.getObject(i);
                    booking.put(columnName, columnValue);
                }
                bookings.add(booking);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bookings;
    }

    public void getShowtimeDetails(String sql, int showtimeID) {
        try (Connection conn = connectToDatabase();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, showtimeID);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                int showtimeID_sql = rs.getInt("ShowtimeID");
                String title = rs.getString("Title");
                int duration = rs.getInt("Duration");
                Time showtime = rs.getTime("Showtime");
                System.out.println("ShowtimeID: " + showtimeID_sql + ", Title: " + title + ", Duration: " + duration
                        + ", Showtime: " + showtime);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public int removeBooking(int bookingID) {
        // enter any booking id and works
        // future scope : to verify if booking is usernames or not
        String sql = "DELETE from bookings where BookingID =?";
        int rowsAffected = 0;
        try (Connection conn = connectToDatabase();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, bookingID);
            rowsAffected = ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    public String validatePass(String sql, String username) {
        String password = null;
        try (Connection conn = connectToDatabase();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                password = rs.getString("Password");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return password;
    }

    public int fetchUserID(String sql, String username) {
        int userID = -1;
        try (Connection conn = connectToDatabase();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                userID = rs.getInt("UserID");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userID;
    }
}
