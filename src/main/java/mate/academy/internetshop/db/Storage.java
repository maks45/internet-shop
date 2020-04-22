package mate.academy.internetshop.db;

import java.util.ArrayList;
import java.util.List;
import mate.academy.internetshop.model.Item;

public class Storage {
    public static Long itemIdCounter = 0L;
    public static Long userIdCounter = 0L;
    public static Long orderIdCounter = 0L;
    public static Long bucketIdCounter = 0L;

    public static final List<Item> items = new ArrayList<>();

}
