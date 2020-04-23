package mate.academy.internetshop.db;

import java.util.ArrayList;
import java.util.List;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Product;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.model.User;

public class Storage {
    private static Long productIdCounter = 0L;
    private static Long userIdCounter = 0L;
    private static Long orderIdCounter = 0L;
    private static Long bucketIdCounter = 0L;
    public static final List<Product> PRODUCTS = new ArrayList<>();
    public static final List<Bucket> BUCKETS = new ArrayList<>();
    public static final List<Order> ORDERS = new ArrayList<>();
    public static final List<User> USERS = new ArrayList<>();

    public static Long getItemId(){
        productIdCounter++;
        return productIdCounter;
    }
}
