BEGIN TRANSACTION;
CREATE TABLE IF NOT EXISTS `Questoes` (
	`idQuestao`	integer NOT NULL PRIMARY KEY AUTOINCREMENT,
	`idEleicao`	int NOT NULL,
	`questao`	varchar ( 45 ) NOT NULL,
	`numeroDeAlternativas`	int NOT NULL,
	FOREIGN KEY(`idEleicao`) REFERENCES `Eleicao`
);
INSERT INTO `Questoes` VALUES (1,1,'Os preços são justos?',1);
INSERT INTO `Questoes` VALUES (2,1,'O ambiente é limpo?',1);
INSERT INTO `Questoes` VALUES (3,2,'Um novo curso na área da saúde deve ser criado?',1);
INSERT INTO `Questoes` VALUES (4,2,'Em que período deve ser ofertado?',1);
INSERT INTO `Questoes` VALUES (5,2,'Qual matéria deve fazer parte da grade?',2);
CREATE TABLE IF NOT EXISTS `Pessoa` (
	`login`	varchar ( 45 ) NOT NULL,
	`nome`	varchar ( 45 ) NOT NULL,
	`senha`	varchar ( 45 ) NOT NULL,
	CONSTRAINT `Pessoa_pk` PRIMARY KEY(`login`)
);
INSERT INTO `Pessoa` VALUES ('yan.m','Yan Martins','1234');
INSERT INTO `Pessoa` VALUES ('amanda.s','Amanda Silva','4321');
INSERT INTO `Pessoa` VALUES ('pedro.w','Pedro Winter','1998');
INSERT INTO `Pessoa` VALUES ('fernanda.p','Fernanda Prim','3245');
CREATE TABLE IF NOT EXISTS `Eleitor` (
	`idEleitor`	integer NOT NULL PRIMARY KEY AUTOINCREMENT,
	`situacaoVoto`	boolean NOT NULL,
	`login`	varchar ( 45 ) NOT NULL,
	`idEleicao`	int NOT NULL,
	FOREIGN KEY(`login`) REFERENCES `Pessoa`,
	FOREIGN KEY(`idEleicao`) REFERENCES `Eleicao`
);
CREATE TABLE IF NOT EXISTS `Eleicao` (
	`idEleicao`	integer NOT NULL PRIMARY KEY AUTOINCREMENT,
	`nome`	varchar ( 45 ) NOT NULL,
	`iniciada`	boolean NOT NULL,
	`encerrada`	boolean NOT NULL
);
INSERT INTO `Eleicao` VALUES (1,'Situação da Cantina',0,0);
INSERT INTO `Eleicao` VALUES (2,'Discussão de novo curso para o campus',0,0);
CREATE TABLE IF NOT EXISTS `Alternativa` (
	`idAlternativa`	integer NOT NULL PRIMARY KEY AUTOINCREMENT,
	`idQuestao`	int NOT NULL,
	`alternativa`	int NOT NULL,
	`totalDeVotos`	int NOT NULL,
	CONSTRAINT `Alternativa_Questoes_idQuestao_fk` FOREIGN KEY(`idQuestao`) REFERENCES `Questoes`
);
INSERT INTO `Alternativa` VALUES (1,1,'Sim, é justo',0);
INSERT INTO `Alternativa` VALUES (2,1,'Não, é injusto',0);
INSERT INTO `Alternativa` VALUES (3,2,'Sim, é limpo',0);
INSERT INTO `Alternativa` VALUES (4,2,'Não, é sujo',0);
INSERT INTO `Alternativa` VALUES (5,3,'Sim, deve',0);
INSERT INTO `Alternativa` VALUES (6,1,'Branco',0);
INSERT INTO `Alternativa` VALUES (7,1,'Nulo',0);
INSERT INTO `Alternativa` VALUES (8,3,'Nulo',0);
INSERT INTO `Alternativa` VALUES (9,4,'Branco',0);
INSERT INTO `Alternativa` VALUES (10,4,'Nulo',0);
INSERT INTO `Alternativa` VALUES (11,5,'Branco',0);
INSERT INTO `Alternativa` VALUES (12,5,'Nulo',0);
INSERT INTO `Alternativa` VALUES (13,5,'Matemática',0);
INSERT INTO `Alternativa` VALUES (14,5,'História',0);
INSERT INTO `Alternativa` VALUES (15,5,'Biologia',0);
INSERT INTO `Alternativa` VALUES (16,2,'Branco',0);
INSERT INTO `Alternativa` VALUES (17,2,'Nulo',0);
INSERT INTO `Alternativa` VALUES (18,3,'Não, não deve',0);
INSERT INTO `Alternativa` VALUES (19,3,'Branco',0);
INSERT INTO `Alternativa` VALUES (20,4,'Nulo',0);
INSERT INTO `Alternativa` VALUES (21,4,'Matutino',0);
INSERT INTO `Alternativa` VALUES (22,4,'Noturno',0);
COMMIT;
