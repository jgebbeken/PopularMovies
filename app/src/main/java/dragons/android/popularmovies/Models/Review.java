package dragons.android.popularmovies.Models;

/**
 *  Review Model
 */

public class Review {

    private String Author;
    private String content;


    public String getAuthor() {
        return Author;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
