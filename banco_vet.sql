CREATE DATABASE clinicavet;

\c clinicavet;

CREATE TABLE veterinario (
	registro INT PRIMARY KEY,
	cpf VARCHAR(15) NOT NULL,
	nome VARCHAR(60) NOT NULL,
	datadenasc DATE,
	logradouro VARCHAR(100)
);

CREATE TABLE tutor(
	cpf VARCHAR(15) PRIMARY KEY,
	nome VARCHAR(60) NOT NULL,
	datadenasc DATE, 
	logradouro VARCHAR(60)
);

CREATE TABLE animal(
	cpftutor VARCHAR(15) NOT NULL,
	codigo SERIAL PRIMARY KEY,
	nomeanimal VARCHAR(60) NOT NULL,
	raca VARCHAR (30),
	dataDeNasc DATE
);

CREATE TABLE agendamento(
	codigoagendamento SERIAL PRIMARY KEY,
	dataagendamento DATE NOT NULL,
	horaagendamento TIME NOT NULL,
	veterinario INT,
	animal INT
);

ALTER TABLE animal
ADD FOREIGN KEY (cpfTutor) REFERENCES tutor(cpf)
ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE agendamento
ADD FOREIGN KEY (veterinario) REFERENCES veterinario(registro)
ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE agendamento
ADD FOREIGN KEY (animal) REFERENCES animal(codigo)
ON UPDATE CASCADE ON DELETE CASCADE;
