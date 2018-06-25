
package apps.inets.com.shulesoft;
/**
 * Created by admin on 20 Jun 2018.
 */

public class School {

    private String mUrl;

    private String mName;

    public School(String name, String url){
        mUrl = url;
        mName = name;
    }

    public String getName() {
        return mName;
    }

    public String getUrl() {
        return mUrl;
    }
}
