CREATE USER IF NOT EXISTS 'airdata_client'@'localhost' IDENTIFIED BY '#AirData2022';
GRANT SELECT, UPDATE, INSERT, DELETE, EXECUTE, SHOW VIEW ON airData.* TO 'airdata_client'@'localhost';

DROP DATABASE IF EXISTS airData;
CREATE DATABASE airData;
USE airData;

CREATE TABLE empresa (
    idEmpresa INT PRIMARY KEY AUTO_INCREMENT,
    cnpjEmpresa CHAR(18) NOT NULL,
    nomeEmpresa VARCHAR(45) NOT NULL,
    telefoneEmpresa CHAR(14)
);

CREATE TABLE aeroporto (
    idAeroporto INT PRIMARY KEY AUTO_INCREMENT,
    fkEmpresa INT NOT NULL,
    nomeAeroporto VARCHAR(45) NOT NULL,
    cepAeroporto CHAR(9) NOT NULL,
    numeroAeroporto INT NOT NULL,
    ufAeroporto CHAR(2) NULL,
    cidadeAeroporto VARCHAR(45) NOT NULL,
    bairroAeroporto VARCHAR(45) NOT NULL,
    ruaAeroporto VARCHAR(45) NOT NULL,
    FOREIGN KEY(fkEmpresa) REFERENCES empresa(idEmpresa)
);

CREATE TABLE usuario (
	idUsuario INT PRIMARY KEY AUTO_INCREMENT,
	nomeUsuario VARCHAR(45) NOT NULL,
	emailUsuario VARCHAR(45) NOT NULL,
	senhaUsuario CHAR(128) NOT NULL,
	cpfUsuario CHAR(14) NOT NULL,
    tipoUsuario CHAR(1) NOT NULL CHECK (tipoUsuario IN('F', 'G', 'S')),
	fkSupervisor INT NULL,
	fkAeroporto INT NOT NULL,
	fkGestor INT NULL,
	FOREIGN KEY(fkAeroporto) REFERENCES aeroporto(idAeroporto),
    FOREIGN KEY(fkSupervisor) REFERENCES usuario(idUsuario),
    FOREIGN KEY(fkGestor) REFERENCES usuario(idUsuario)
);

CREATE TABLE torre (
    idTorre INT PRIMARY KEY AUTO_INCREMENT,
    fkAeroporto INT NOT NULL,
    FOREIGN KEY(fkAeroporto) REFERENCES aeroporto(idAeroporto)
);

CREATE TABLE servidor (
	idServidor VARCHAR(17) PRIMARY KEY,
    fkTorre INT NOT NULL,
    FOREIGN KEY(fkTorre) REFERENCES torre(idTorre)
);

CREATE TABLE componente (
	idComponente INT AUTO_INCREMENT,
    fkServidor VARCHAR(17) NOT NULL,
    tipoComponente VARCHAR(45) NOT NULL,
    nomeComponente VARCHAR(50) NOT NULL,
    memoria DECIMAL(5,2),
    tipoMemoria VARCHAR(30),
    FOREIGN KEY (fkServidor) REFERENCES servidor(idServidor),
    PRIMARY KEY(idComponente, fkServidor)
);

INSERT INTO componente (idComponente, fkServidor, tipoComponente, nomeComponente, memoria, tipoMemoria) VALUES
(60, '10:5B:AD:FA:55:AF', 'CPU', 'CPU', '12', 'Memoria');
INSERT INTO componente (idComponente, fkServidor, tipoComponente, nomeComponente, memoria, tipoMemoria) VALUES
(61, '10:5B:AD:FA:55:AF', 'DISCO', 'DISCO', '500', 'Memoria');
INSERT INTO componente (idComponente, fkServidor, tipoComponente, nomeComponente, memoria, tipoMemoria) VALUES
(62, '10:5B:AD:FA:55:AF', 'RAM', 'RAM', '12', 'Memoria');
INSERT INTO componente (idComponente, fkServidor, tipoComponente, nomeComponente, memoria, tipoMemoria) VALUES
(64, '10:5B:AD:FA:55:AF', 'CPU', 'CPU', '12', 'Memoria');

INSERT INTO servidor (idServidor, fkTorre) VALUES ('10:5B:AD:FA:55:AF', 2);

CREATE TABLE alerta(
	idAlerta INT PRIMARY KEY AUTO_INCREMENT,
	statusAlerta VARCHAR(45) NOT NULL,
	momentoAlerta DATETIME NOT NULL,
	fkComponente INT NOT NULL,
	FOREIGN KEY (fkComponente) references componente(idComponente)
);

CREATE TABLE metrica (
	idMetrica INT PRIMARY KEY,
    nomeComponente VARCHAR(40) NOT NULL,
    nomeMetrica VARCHAR(40) NOT NULL,
    nomeView VARCHAR(40) NOT NULL,
    comando VARCHAR(50) NOT NULL,
    unidadeMedida VARCHAR(10) NOT NULL,
    isTupla TINYINT NOT NULL
);

CREATE TABLE leitura (
    fkMetrica INT NOT NULL,
    horario DATETIME NOT NULL,
    valorLido DECIMAL(5,2) NOT NULL,
	fkComponente_idComponente INT NOT NULL,
    fkComponente_fkServidor VARCHAR(17) NOT NULL,
    FOREIGN KEY(fkComponente_idComponente, fkComponente_fkServidor) REFERENCES componente(idComponente, fkServidor)
);

CREATE TABLE parametro (
	fkMetrica INT NOT NULL,
	fkComponente_idComponente INT NOT NULL,
    fkComponente_fkServidor VARCHAR(17) NOT NULL,
    FOREIGN KEY(fkMetrica) REFERENCES metrica(idMetrica),
    FOREIGN KEY(fkComponente_idComponente, fkComponente_fkServidor) REFERENCES componente(idComponente, fkServidor)
);

-- Views
CREATE VIEW vw_iniciarSessao AS
SELECT idUsuario, nomeUsuario, emailUsuario, senhaUsuario, tipoUsuario, idTorre, usuario.fkAeroporto, fkGestor, fkSupervisor
FROM usuario
JOIN aeroporto ON idAeroporto = usuario.fkAeroporto
JOIN torre ON torre.fkAeroporto = idAeroporto;

CREATE VIEW vw_cpuPercent AS
SELECT idComponente, fkServidor AS idServidor, leitura.horario, valorLido, unidadeMedida 
FROM leitura
JOIN componente ON fkComponente_idComponente = idComponente
AND fkComponente_fkServidor = fkServidor
JOIN metrica ON fkMetrica = idMetrica
WHERE metrica.nomeComponente = 'CPU'
AND metrica.nomeMetrica = 'Porcentagem de uso'
ORDER BY horario DESC;

CREATE VIEW vw_ramPercent AS
SELECT idComponente, fkServidor AS idServidor, leitura.horario, valorLido, unidadeMedida 
FROM leitura
JOIN componente ON fkComponente_idComponente = idComponente
AND fkComponente_fkServidor = fkServidor
JOIN metrica ON fkMetrica = idMetrica
WHERE metrica.nomeComponente = 'RAM'
AND metrica.nomeMetrica = 'Porcentagem de uso'
ORDER BY horario DESC;

CREATE VIEW vw_diskPercent AS
SELECT idComponente, fkServidor AS idServidor, leitura.horario, valorLido, unidadeMedida 
FROM leitura
JOIN componente ON fkComponente_idComponente = idComponente
AND fkComponente_fkServidor = fkServidor
JOIN metrica ON fkMetrica = idMetrica
WHERE metrica.nomeComponente = 'DISCO'
AND metrica.nomeMetrica = 'Porcentagem de uso'
ORDER BY horario DESC;

CREATE VIEW vw_cpuTemp AS
SELECT idComponente, fkServidor AS idServidor, leitura.horario, valorLido, unidadeMedida 
FROM leitura
JOIN componente ON fkComponente_idComponente = idComponente
AND fkComponente_fkServidor = fkServidor
JOIN metrica ON fkMetrica = idMetrica
WHERE metrica.nomeComponente = 'CPU'
AND metrica.nomeMetrica = 'Temperatura'
ORDER BY horario DESC;

CREATE VIEW vw_alertas as
SELECT idAlerta, statusAlerta, momentoAlerta, fkTorre, tipoComponente, idServidor
FROM alerta
JOIN componente ON fkComponente = idComponente
JOIN servidor ON fkServidor = idServidor
JOIN torre ON fkTorre = idTorre
ORDER BY momentoAlerta DESC;

CREATE VIEW vw_onlineServers AS
	SELECT servidor.fkTorre, fkComponente_fkServidor AS idServidor, MAX(horario) AS ultimaLeitura, TIMESTAMPDIFF(MINUTE, MAX(horario), NOW()) AS minutosDesdeUltimaLeitura, 
		CASE WHEN TIMESTAMPDIFF(MINUTE, MAX(horario), NOW()) > 1 THEN 'OFFLINE'
		ELSE 'ONLINE'
		END AS estado
	FROM leitura
    INNER JOIN servidor ON servidor.idServidor = leitura.fkComponente_fkServidor
	GROUP BY fkComponente_fkServidor;

CREATE VIEW vw_componenteMetrica AS
SELECT idComponente, fkServidor, tipoComponente, componente.nomeComponente, tipoMemoria, nomeMetrica, unidadeMedida, nomeView 
FROM componente 
JOIN parametro ON fkComponente_idComponente = idComponente 
AND fkComponente_fkServidor = fkServidor
JOIN metrica ON fkMetrica = idMetrica
ORDER BY idComponente, fkServidor;

CREATE VIEW vw_maquinasMaiorUsoCpu AS 
	SELECT idComponente, fkServidor, MAX(horario) AS ultimoHorario, valorLido, nomeComponente, idServidor, fkTorre FROM leitura 
    INNER JOIN componente ON leitura.fkComponente_idComponente = componente.idComponente AND leitura.fkComponente_fkServidor = componente.fkServidor 
    INNER JOIN servidor ON componente.fkServidor = servidor.idServidor 
    WHERE tipoComponente = 'CPU' AND valorLido != 0.0 AND fkMetrica = 1
    GROUP BY idComponente, fkServidor
    ORDER BY valorLido DESC
    LIMIT 3;
        
CREATE VIEW vw_alertasRecentes AS 
	SELECT * FROM alerta INNER JOIN componente ON alerta.fkComponente = componente.idComponente 
    INNER JOIN servidor ON componente.fkServidor = servidor.idServidor 
    WHERE TIMESTAMPDIFF(MINUTE, momentoAlerta, NOW()) <= 30;

-- cardápio
INSERT INTO metrica (idMetrica, nomeComponente, nomeMetrica, nomeView, comando, unidadeMedida, isTupla) VALUES 
(1, 'CPU', 'Porcentagem de uso', 'cpuPercent', 'psutil.cpu_percent(interval=0.1)', '%', FALSE);
INSERT INTO metrica (idMetrica, nomeComponente, nomeMetrica, nomeView, comando, unidadeMedida, isTupla) VALUES 
(2, 'RAM', 'Porcentagem de uso', 'ramPercent', 'psutil.virtual_memory().percent', '%', FALSE);
INSERT INTO metrica (idMetrica, nomeComponente, nomeMetrica, nomeView, comando, unidadeMedida, isTupla) VALUES 
(3, 'DISCO', 'Porcentagem de uso', 'diskPercent', 'psutil.disk_usage("/").percent', '%', FALSE);
INSERT INTO metrica (idMetrica, nomeComponente, nomeMetrica, nomeView, comando, unidadeMedida, isTupla) VALUES 
(4, 'CPU', 'Temperatura', 'cpuTemp', '', '°C', FALSE);

INSERT INTO empresa (idEmpresa,nomeEmpresa,cnpjEmpresa,telefoneEmpresa) VALUES 
(2, 'AirData', '00.000.000/0000-00', '(00) 0000-0000');
INSERT INTO aeroporto (idAeroporto, fkEmpresa,nomeAeroporto,cepAeroporto,numeroAeroporto,ufAeroporto,cidadeAeroporto,bairroAeroporto,ruaAeroporto) VALUES 
(2,'2', 'AirData', '01414-000', '123', 'SP', 'São Paulo', 'Cerqueira César', 'Rua Haddock Lobo');
INSERT INTO torre (idTorre, fkAeroporto) VALUES 
(2,'2');
INSERT INTO usuario (idUsuario, nomeUsuario,emailUsuario,senhaUsuario,cpfUsuario,tipoUsuario,fkAeroporto) VALUES 
(2, 'Kauan Cavazani', 'kauan@email.com', 'afec94183c6234a1f4a9432892c5cb8dcf60a541f5559caa46e8a448618954355116b1028506107e198bd015a30f478059f15bfef17405ecb4657bc3c1f24217','000.000.000-00','G','2');


CREATE VIEW vw_getServidor AS
SELECT idServidor, idComponente
FROM servidor
JOIN componente ON fkServidor = idServidor;