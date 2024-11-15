ALTER TABLE products
ADD status VARCHAR(8);

ALTER TABLE products
DROP INDEX code;