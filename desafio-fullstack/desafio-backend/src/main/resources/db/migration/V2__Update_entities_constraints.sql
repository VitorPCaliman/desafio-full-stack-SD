-- Migration V2: Atualizando restrições para as entidades

-- Ajustando a tabela "categories"
ALTER TABLE categories
  ALTER COLUMN name TYPE VARCHAR(100),
  ALTER COLUMN name SET NOT NULL,
  ALTER COLUMN description TYPE VARCHAR(255);

-- Ajustando a tabela "products"
ALTER TABLE products
  ALTER COLUMN name TYPE VARCHAR(100),
  ALTER COLUMN name SET NOT NULL,
  ALTER COLUMN description TYPE VARCHAR(255),
  ALTER COLUMN price SET NOT NULL,
  ADD CONSTRAINT chk_price_positive CHECK (price > 0),
  ALTER COLUMN category_id SET NOT NULL;