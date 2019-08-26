BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS `Questoes` (
	`idQuestao`	integer NOT NULL PRIMARY KEY AUTOINCREMENT,
	`idEleicao`	int NOT NULL,
	`questao`	varchar ( 45 ) NOT NULL,
	`numeroDeAlternativas`	int NOT NULL,
	FOREIGN KEY(`idEleicao`) REFERENCES `Eleicao`
);
INSERT INTO `Questoes` (idQuestao,idEleicao,questao,numeroDeAlternativas) VALUES (1,1,'Os preços são justos?',1),
 (2,1,'O ambiente é limpo?',1),
 (3,2,'Um novo curso na área da saúde deve ser criado?',1),
 (4,2,'Em que período deve ser ofertado?',1),
 (5,2,'Qual matéria deve fazer parte da grade?',2),
 (8,1,'Qual deve ser o horário de funcionamento?',1);
CREATE TABLE IF NOT EXISTS `Pessoa` (
	`login`	varchar ( 45 ) NOT NULL,
	`nome`	varchar ( 45 ) NOT NULL,
	`senha`	varchar ( 45 ) NOT NULL,
	CONSTRAINT `Pessoa_pk` PRIMARY KEY(`login`)
);
INSERT INTO `Pessoa` (login,nome,senha) VALUES ('yan.m','Yan Martins','pbkdf2:sha256:150000$vSlwcCZ3$d7cdef5bc4e37502faf3ef4c24dfee15e5c4ae0ee8f2316da50242a7013cb801'),
 ('amanda.s','Amanda Silva','pbkdf2:sha256:150000$itoOlW1e$e8053bb96726e5c1ee508cddfc8dc864a597483a72d105fc07137b71090b325b'),
 ('pedro.w','Pedro Winter','pbkdf2:sha256:150000$cNsDxGpI$7ff3f578bca15923ac816118fae06128ba787b1ad11239976f6ea15b07a49be5'),
 ('fernanda.p','Fernanda Prim','pbkdf2:sha256:150000$u5TAxrlA$664d3b8865e60b80e3f4b94042adf40d0e6bc52e82b7985ae1a434e5ba46679b'),
 ('sarom.t','Sarom Torres','pbkdf2:sha256:150000$OdKnZRPY$7e161c737757f8a66fd13e397f171f72605b14c4450d62aafbe5394832673e79'),
CREATE TABLE IF NOT EXISTS `Eleitor` (
	`idEleitor`	integer NOT NULL PRIMARY KEY AUTOINCREMENT,
	`situacaoVoto`	boolean NOT NULL,
	`login`	varchar ( 45 ) NOT NULL,
	`idEleicao`	int NOT NULL,
	FOREIGN KEY(`login`) REFERENCES `Pessoa`,
	FOREIGN KEY(`idEleicao`) REFERENCES `Eleicao`
);
INSERT INTO `Eleitor` (idEleitor,situacaoVoto,login,idEleicao) VALUES (1,1,'yan.m',1),
 (2,1,'yan.m',2),
 (3,1,'pedro.w',1),
 (4,0,'amanda.s',1),
 (7,0,'sarom.t',1),
 (8,1,'sarom.t',2);
CREATE TABLE IF NOT EXISTS `Eleicao` (
	`idEleicao`	integer NOT NULL PRIMARY KEY AUTOINCREMENT,
	`nome`	varchar ( 45 ) NOT NULL,
	`iniciada`	boolean NOT NULL,
	`encerrada`	boolean NOT NULL,
	`idAdministrador`	integer NOT NULL,
	FOREIGN KEY(`idAdministrador`) REFERENCES `Administrador`
);
INSERT INTO `Eleicao` (idEleicao,nome,iniciada,encerrada,idAdministrador) VALUES (1,'Situação da Cantina',1,1,1),
 (2,'Discussão de novo curso para o campus',1,0,1);
CREATE TABLE IF NOT EXISTS `Alternativa` (
	`idAlternativa`	integer NOT NULL PRIMARY KEY AUTOINCREMENT,
	`idQuestao`	int NOT NULL,
	`alternativa`	int NOT NULL,
	`totalDeVotos`	int NOT NULL,
	CONSTRAINT `Alternativa_Questoes_idQuestao_fk` FOREIGN KEY(`idQuestao`) REFERENCES `Questoes`
);
INSERT INTO `Alternativa` (idAlternativa,idQuestao,alternativa,totalDeVotos) VALUES (1,1,'Sim, é justo',3),
 (2,1,'Não, é injusto',0),
 (3,2,'Sim, é limpo',0),
 (4,2,'Não, é sujo',0),
 (5,3,'Sim, deve',0),
 (6,1,'Branco',0),
 (7,1,'Nulo',0),
 (8,3,'Nulo',0),
 (9,4,'Branco',0),
 (10,4,'Nulo',0),
 (11,5,'Branco',0),
 (12,5,'Nulo',0),
 (13,5,'Matemática',0),
 (14,5,'História',0),
 (15,5,'Biologia',0),
 (16,2,'Branco',0),
 (17,2,'Nulo',0),
 (18,3,'Não, não deve',0),
 (19,3,'Branco',0),
 (21,4,'Matutino',0),
 (22,4,'Noturno',0),
 (24,8,'Branco',0),
 (25,8,'Nulo',0);
 
CREATE TABLE IF NOT EXISTS `Administrador` (
	`idAdministrador`	integer NOT NULL PRIMARY KEY AUTOINCREMENT,
	`login`	varchar ( 45 ) NOT NULL,
	CONSTRAINT `Administrador_Pessoa_login_fk` FOREIGN KEY(`login`) REFERENCES `Pessoa`
);
INSERT INTO `Administrador` (idAdministrador,login) VALUES (1,'yan.m');
COMMIT;
