import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

class movies {
    private String title;
    private String genre;
    private String rating;
    private String duration;
    private String synopsis;

    private DatabaseOperation db = new DatabaseOperation();

    public movies(String title, String genre, String rating, String duration, String synopsis) {
        this.title = title;
        this.genre = genre;
        this.rating = rating;
        this.duration = duration;
        this.synopsis = synopsis;
    }

    // insert movies

    public void insertMovie(String title, String genre, String rating, String duration, String synopsis) {
        this.title = title;
        this.genre = genre;
        this.rating = rating;
        this.duration = duration;
        this.synopsis = synopsis;

        String sql = "INSERT INTO movies (title, genre, rating, duration, synopsis) VALUES (?,?,?,?,?)";
        Object[] values = { this.title, this.genre, this.rating, this.duration, this.synopsis };
        int rowsAffected = db.executeUpdate(sql, values);
        if (rowsAffected > 0)
            System.out.println("Movie inserted successfully");
        else
            System.out.println("Something went wrong. Movie not inserted.");
    }

    public void showMovies() {
        String sql = "SELECT * FROM Movies";
        List<Map<String, Object>> movies = db.getRecords(sql);

        for (Map<String, Object> movie : movies) {
            System.out.println("Movie ID: " + movie.get("MovieID"));
            System.out.println("Movie Title: " + movie.get("Title"));
            System.out.println("Genre: " + movie.get("Genre"));
            System.out.println("Rating: " + movie.get("Rating"));
            System.out.println("Duration (in mins): " + movie.get("Duration"));
            System.out.println("Synopsis: " + movie.get("Synopsis"));
            System.out.println("---------------------------------------------------");
        }
    }
}