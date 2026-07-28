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

create user 'leticia'@'%' identified by 'valentim123';

grant all privileges on ERP_Financeiro.* to 'leticia'@'%' ;

create user 'matheus'@'%' identified by 'valentim123';

grant select, insert, update, create on ERP_Financeiro.* to 'matheus'@'%' ;

create user 'ryan'@'%' identified by 'valentim123';

grant select on ERP_Financeiro.* to 'ryan'@'%' ;

create user 'kaue'@'%' identified by 'valentim123';

grant select on ERP_Financeiro.* to 'kaue'@'%' ;

create user 'yago'@'%' identified by 'valentim123';

grant select on ERP_Financeiro.* to 'yago'@'%' ;

flush privileges;