-- ========================================================
-- PET SHOP MANAGEMENT SYSTEM - SAMPLE DATA SEEDING SCRIPT
-- ========================================================

-- 1. Insert Employee accounts (1 Manager, 1 Sales Staff)
INSERT INTO employees (name, phone, email, address, username, password, role)
VALUES 
('Alice Smith', '0901234567', 'alice.manager@petshop.com', 'Thu Dau Mot, Binh Duong', 'manager1', 'pass123', 'MANAGER'),
('Bob Johnson', '0907654321', 'bob.staff@petshop.com', 'Ben Cat, Binh Duong', 'staff1', 'pass123', 'SALES_STAFF')
ON CONFLICT DO NOTHING;

-- 2. Insert sample Customers for POS checkout
INSERT INTO customers (name, phone, email, address)
VALUES 
('John Doe', '0911223344', 'john.doe@gmail.com', 'District 1, Ho Chi Minh City'),
('Jane Mary', '0988776655', 'jane.mary@gmail.com', 'Thu Duc City, Ho Chi Minh City'),
('David Tran', '0933445566', 'david.tran@gmail.com', 'Thu Dau Mot, Binh Duong')
ON CONFLICT DO NOTHING;

-- 3. Insert sample Pets (includes available and sold pets for filter testing)
INSERT INTO pets (name, price, species, breed, age_in_months, is_available, photo_path)
VALUES 
('Milo', 3500000, 'Dog', 'Poodle', 3, true, '/images/poodle.png'),
('Luna', 5000000, 'Cat', 'British Shorthair', 4, true, '/images/bsh.png'),
('Max', 4200000, 'Dog', 'Corgi', 2, true, '/images/corgi.png'),
('Bella', 2800000, 'Cat', 'Persian', 5, false, '/images/persian.png') -- Sold pet to test canSell rules
ON CONFLICT DO NOTHING;

-- 4. Insert sample retail Products (includes items with stock <= 5 for low-stock alerts)
INSERT INTO products (name, price, category, stock)
VALUES 
('Royal Canin Mini Adult 2kg', 320000, 'Food', 15),
('Whiskas Tuna Cat Food 1.2kg', 145000, 'Food', 25),
('Leather Dog Collar', 85000, 'Accessory', 4),  -- Stock 4 (Low-stock alert test)
('Cat Scratching Post', 210000, 'Accessory', 3), -- Stock 3 (Low-stock alert test)
('Pet Shampoo 500ml', 120000, 'Care', 10)
ON CONFLICT DO NOTHING;

-- 5. Insert historical sample Orders (to test daily revenue chart aggregations)
INSERT INTO orders (customer_id, total_amount, order_date)
VALUES 
(1, 3820000, CURRENT_DATE - INTERVAL '2 days'),
(2, 5145000, CURRENT_DATE - INTERVAL '1 day'),
(3, 210000, CURRENT_DATE)
ON CONFLICT DO NOTHING;

SELECT * FROM employees;
SELECT * FROM pets;
SELECT * FROM products;
SELECT * FROM customers;
SELECT * FROM orders;
DELETE FROM pets WHERE id = 1;

