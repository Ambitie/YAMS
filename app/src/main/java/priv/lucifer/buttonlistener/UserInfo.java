package priv.lucifer.buttonlistener;

import android.content.pm.PackageInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Lucifer Wong on 2017/2/22.
 */

public class UserInfo {

    public interface Ignore {
        boolean ignoreKey();

        boolean ignoreValue();
    }

    public static String current_app = "";
    private static HashMap<Integer, PackageInfo> app_maps = new HashMap<>();
    private static ArrayList<PackageInfo> app_maps_core = new ArrayList<>();

    public static int getKeyFromIndex(int index) {
        Iterator<Integer> iterator = app_maps.keySet().iterator();
        PackageInfo app = app_maps_core.get(index);
        while (iterator.hasNext()) {
            int it = iterator.next();
            if (app_maps.get(it) == app)
                return it;
        }
        return 0;
    }

    public static PackageInfo getAppByIndex(int i) {
        return app_maps_core.get(i);
    }

    public static PackageInfo getAppByKey(int i) {
        return app_maps.get(i);
    }

    public static int getAppMappingSize() {
        return app_maps_core.size();
    }

    public static void addMapping(Integer key, PackageInfo app, Ignore ignore) {
        if (app_maps.containsKey(key)) {
            if (ignore.ignoreKey()) {
                app_maps_core.remove(app_maps.get(key));
                app_maps.remove(key);
            } else {
                System.out.println("不应该来的");
                return;
            }
        }
        if (app_maps.containsValue(app)) {
            if (ignore.ignoreValue()) {
                app_maps_core.remove(app);
                app_maps.remove(app);
            } else
                return;
        }
        app_maps.put(key, app);
        app_maps_core.add(app);
    }

    public static void removeMapping(Integer key) {
        if (app_maps.containsKey(key)) {
            app_maps_core.remove(app_maps.get(key));
            app_maps.remove(key);

        }
    }


}
