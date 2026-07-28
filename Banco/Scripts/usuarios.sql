use ERP_Financeiro;

CREATE TABLE Usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    senha VARCHAR(64) NOT NULL,
    permissao ENUM('R', 'RW', 'RWD') NOT NULL
);
insert into Usuarios(nome, senha, permissao) values ('', sha2('', 256), 3);

create user 'erp-financeiro'@'%' identified by 'valentim123';

grant select, insert, update, delete, create on ERP_Financeiro.* to 'erp-financeiro'@'%' ;

flush privileges;