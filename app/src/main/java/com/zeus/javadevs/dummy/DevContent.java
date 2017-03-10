package com.zeus.javadevs.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample username for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DevContent {

    /**
     * An array of sample (dummy) items.
     */
    public static final List<DummyItem> ITEMS = new ArrayList<DummyItem>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static final Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();


    static {

    }

    /*private static void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.username, item);
    }

    private static DummyItem createDummyItem(int position) {
        return new DummyItem(String.valueOf(position), "Item " + position, makeDetails(position), "position");
    }*/

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore profileURL information here.");

        }
        return builder.toString();
    }

    /**
     * A dummy item representing a piece of username.
     */
    public static class DummyItem {
        public final String id;
        public final String username;
        public final String profileURL;
        public final String profilePix;
        //public final Bitmap profileDP;


        public DummyItem(String id, String username, String profileURL, String pixUrl) {
            this.id = id;
            this.username = username;
            this.profileURL = profileURL;
            this.profilePix = pixUrl;
            /*this.profileDP = null;
            new DownloadImageTask(profileDP).execute(profilePix);*/
        }

        @Override
        public String toString() {
            return username;
        }
    }


}
