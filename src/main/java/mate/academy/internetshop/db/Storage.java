package mate.academy.internetshop.db;

import java.util.ArrayList;
import java.util.List;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Order;
import mate.academy.internetshop.model.Product;
import mate.academy.internetshop.model.User;

public class Storage {
    public static final List<Product> products = new ArrayList<>();
    public static final List<Bucket> buckets = new ArrayList<>();
    public static final List<Order> orders = new ArrayList<>();
    public static final List<User> users = new ArrayList<>();
    private static Long productIdCounter = 0L;
    private static Long userIdCounter = 0L;
    private static Long orderIdCounter = 0L;
    private static Long bucketIdCounter = 0L;

    public static Product addProduct(Product product) {
        productIdCounter++;
        product.setId(productIdCounter);
        products.add(product);
        return product;
    }
}
