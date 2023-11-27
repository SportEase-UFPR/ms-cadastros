INSERT INTO cadastros.tb_administradores (id, cpf, email, id_usuario, nome)
SELECT 1, '00000000000', 'adm@gmail.com', 1, 'adm'
FROM dual
WHERE NOT EXISTS (
    SELECT 1
    FROM cadastros.tb_administradores
    WHERE id = 1
);
