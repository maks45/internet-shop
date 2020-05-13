CREATE SCHEMA `internet_shop` DEFAULT CHARACTER SET utf8;

CREATE TABLE `internet_shop`.`products`
(
    `product_id` BIGINT(11)     NOT NULL AUTO_INCREMENT,
    `name`       VARCHAR(225)   NOT NULL,
    `price`      DECIMAL(10, 2) NOT NULL,
    PRIMARY KEY ('product_id')
);

CREATE TABLE `internet_shop`.`shopping_carts`
(
    `shopping_cart_id`      BIGINT(11) NOT NULL AUTO_INCREMENT,
    `shopping_cart_user_id` BIGINT(11) NOT NULL,
    PRIMARY KEY (`shopping_cart_id`)
);

CREATE TABLE `internet_shop`.`roles`
(
    `role_id`   BIGINT(11)   NOT NULL AUTO_INCREMENT,
    `role_name` VARCHAR(225) NOT NULL,
    PRIMARY KEY (`role_id`),
    UNIQUE INDEX `role_name_UNIQUE` (`role_name` ASC) VISIBLE
);

CREATE TABLE `internet_shop`.`orders`
(
    `order_id`      BIGINT(11) NOT NULL AUTO_INCREMENT,
    `order_user_id` BIGINT(11) NOT NULL,
    PRIMARY KEY (`order_id`),
    UNIQUE INDEX `order_id_UNIQUE` (`order_id` ASC) VISIBLE
);

ALTER TABLE `internet_shop`.`orders`
    ADD CONSTRAINT `orders_users_fk`
        FOREIGN KEY (`order_user_id`)
            REFERENCES `internet_shop`.`users` (`user_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION;

ALTER TABLE `internet_shop`.`orders`
    ADD INDEX `orders_users_fk_idx` (`order_user_id` ASC) VISIBLE;

ALTER TABLE `internet_shop`.`shopping_carts`
    ADD CONSTRAINT `fk_shopping_carts_1`
        FOREIGN KEY (`shopping_cart_user_id`)
            REFERENCES `internet_shop`.`users` (`user_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION;

ALTER TABLE `internet_shop`.`shopping_carts`
    ADD INDEX `fk_shopping_carts_1_idx` (`shopping_cart_user_id` ASC) VISIBLE;

CREATE TABLE `internet_shop`.`orders_products`
(
    `order_id`   BIGINT(11) NOT NULL,
    `product_id` BIGINT(11) NOT NULL
);

ALTER TABLE `internet_shop`.`orders_products`
    ADD INDEX `order_products_id_fk_idx` (`order_id` ASC) VISIBLE,
    ADD INDEX `order_products_id_fk_idx1` (`product_id` ASC) VISIBLE;

ALTER TABLE `internet_shop`.`orders_products`
    ADD CONSTRAINT `order_id_fk`
        FOREIGN KEY (`order_id`)
            REFERENCES `internet_shop`.`orders` (`order_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    ADD CONSTRAINT `order_products_id_fk`
        FOREIGN KEY (`product_id`)
            REFERENCES `internet_shop`.`products` (`product_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION;

CREATE TABLE `internet_shop`.`shopping_cart_products`
(
    `shopping_cart_id` BIGINT(11) NOT NULL,
    `product_id`       BIGINT(11) NOT NULL
);

ALTER TABLE `internet_shop`.`shopping_cart_products`
    ADD INDEX `shopping_cart_id_fk_idx` (`shopping_cart_id` ASC) VISIBLE,
    ADD INDEX `product_id_fk_idx` (`product_id` ASC) VISIBLE;
;
ALTER TABLE `internet_shop`.`shopping_cart_products`
    ADD CONSTRAINT `shopping_cart_id_fk`
        FOREIGN KEY (`shopping_cart_id`)
            REFERENCES `internet_shop`.`shopping_carts` (`shopping_cart_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    ADD CONSTRAINT `product_id_fk`
        FOREIGN KEY (`product_id`)
            REFERENCES `internet_shop`.`products` (`product_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION;

CREATE TABLE `internet_shop`.`users_roles`
(
    `user_id` BIGINT(11) NOT NULL,
    `role_id` BIGINT(11) NOT NULL,
    INDEX `user_id_fk_idx` (`user_id` ASC) VISIBLE,
    INDEX `role_id_fk_idx` (`role_id` ASC) VISIBLE,
    CONSTRAINT `user_id_fk`
        FOREIGN KEY (`user_id`)
            REFERENCES `internet_shop`.`users` (`user_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `role_id_fk`
        FOREIGN KEY (`role_id`)
            REFERENCES `internet_shop`.`roles` (`role_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);