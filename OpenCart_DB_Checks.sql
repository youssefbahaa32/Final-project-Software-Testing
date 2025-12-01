-- OpenCart Database Validation SQL
-- And replace placeholders like <email>, <customer_id>, <order_id>, <session_id>, <product_id>.

-- 1) Verify new customer after registration
SELECT customer_id, email, status, date_added FROM oc_customer WHERE email = 'ahmedhegazysad@gmail.com';

-- 2) Address created for customer
SELECT address_id, customer_id, firstname, lastname, city FROM oc_address WHERE customer_id = <customer_id>;

-- 3) Activity log for login
SELECT * FROM oc_customer_activity WHERE customer_id = <customer_id> ORDER BY date_added DESC LIMIT 5;

-- 4) Cart item linked to customer/session
SELECT * FROM oc_cart WHERE (customer_id = <customer_id> OR session_id = '<session_id>') ORDER BY date_added DESC;

-- 5) Latest order and dependent rows
SELECT * FROM oc_order WHERE email = '<email>' ORDER BY date_added DESC LIMIT 1;
-- set @order_id := <order_id>;
-- Order products
SELECT * FROM oc_order_product WHERE order_id = <order_id>;
-- Order totals (expect codes like sub_total, shipping, tax, total)
SELECT code, title, value FROM oc_order_total WHERE order_id = <order_id>;

-- 6) Order history (after status update in Admin)
SELECT * FROM oc_order_history WHERE order_id = <order_id> ORDER BY date_added DESC;

-- 7) Wishlist
SELECT * FROM oc_customer_wishlist WHERE customer_id = <customer_id>;

-- 8) Newsletter
SELECT newsletter FROM oc_customer WHERE customer_id = <customer_id>;
