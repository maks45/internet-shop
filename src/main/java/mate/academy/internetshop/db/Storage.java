package mate.academy.internetshop.db;

import java.util.ArrayList;
import java.util.List;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Item;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.model.User;

public class Storage {
    public static Long itemIdCounter = 0L;
    public static Long userIdCounter = 0L;
    public static Long orderIdCounter = 0L;
    public static Long bucketIdCounter = 0L;
    public static final List<Item> ITEMS = new ArrayList<>();
    public static final List<Bucket> BUCKETS = new ArrayList<>();
    public static final List<Order> ORDERS = new ArrayList<>();
    public static final List<User> USERS = new ArrayList<>();
}
