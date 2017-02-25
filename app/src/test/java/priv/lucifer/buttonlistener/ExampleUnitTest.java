package priv.lucifer.buttonlistener;

import android.content.pm.PackageInfo;

import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public  void hashmaptest(){
        HashMap<Integer,File> map = new HashMap<>();
        map.put(1,new File(""));
        System.out.println(map.get(2));

    }
    @Test
    public void reflect()  {
        try {
            System.out.print(GlobeVer.KeyVal.reflect("_SOFT_LEFT"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void reflect_()  {
        try {
            System.out.print(GlobeVer.KeyVal.reflect(0));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static HashMap<Integer, File> app_maps = new HashMap<>();
    private static ArrayList<File> app_maps_core = new ArrayList<>();

    public static int getKeyFromIndex(int index){
        Iterator<Integer> iterator = app_maps.keySet().iterator();
        File app = app_maps_core.get(index);
        while (iterator.hasNext()){
            int it = iterator.next();
            if(app_maps.get(it)==app)
                return it;
        }
        return  0;
    }
    public static File getAppByIndex(int i){
        return app_maps_core.get(i);
    }
    public static File getAppByKey(int i){
        return app_maps.get(i);
    }

    public static int getAppMappingSize(){
        return  app_maps_core.size();
    }
    public static void addMapping(Integer i, File app) {
        if (app_maps.containsKey(i)){
            app_maps.remove(i);
            app_maps_core.remove(app);
        }
        app_maps.put(i,app);
        app_maps_core.add(app);
    }

    public static void removeMapping(Integer key) {
        if (app_maps.containsKey(key)){
            app_maps_core.remove(app_maps.get(key));
            app_maps.remove(key);

        }
    }

    @Test
    public void comp(){
        addMapping(2,new File("2"));
        addMapping(1,new File("1"));
        addMapping(3,new File("3"));

        System.out.println(getKeyFromIndex(2));
        removeMapping(2);
        System.out.println(getKeyFromIndex(1));
    }
}