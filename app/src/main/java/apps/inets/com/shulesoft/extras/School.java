package apps.inets.com.shulesoft.extras;
/**
 * Created by admin on 20 Jun 2018.
 */

public class School {

    private String ImageUrl;

    private String mName;

    public School(String name, String url){
        ImageUrl = url;
        mName = name;
    }

    public String getName() {
        return mName;
    }

    public String getImageUrl() {
        return ImageUrl;
    }
}
