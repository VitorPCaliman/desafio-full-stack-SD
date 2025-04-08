-- V3__alter_code_to_integer_simple.sql
-- 1. Adiciona uma nova coluna temporária "code_int" do tipo INTEGER
-- 2. Atualiza "code_int" removendo o prefixo "PROD-" da coluna "code"
-- 3. Remove a coluna antiga "code"
-- 4. Renomeia "code_int" para "code"
ALTER TABLE products ADD COLUMN code_int INTEGER;

UPDATE products
SET code_int = CASE
    WHEN code IS NOT NULL AND code LIKE 'PROD-%'
         THEN CAST(SUBSTRING(code FROM 6) AS INTEGER)
    ELSE NULL
END;

ALTER TABLE products DROP COLUMN code;
ALTER TABLE products RENAME COLUMN code_int TO code;
