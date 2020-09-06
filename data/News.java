import java.net.URL;
import java.util.List;

public class News {
    public String content;
    public String date;
    public String source;
    public String time;
    public String title;
    public List<URL> urls;

    @Override
    public String toString() {
        return "News{\n" +
                "\ncontent='" + content + '\'' +
                ", \ndate='" + date + '\'' +
                ", \nsource='" + source + '\'' +
                ", \ntime='" + time + '\'' +
                ", \ntitle='" + title + '\'' +
                ", \nurls=" + urls +
                "\n}";
    }
}
