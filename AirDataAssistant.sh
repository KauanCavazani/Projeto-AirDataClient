#!/bin/bash

PURPLE='0;35'
NC='\033[0m' 
VERSAO=11

clear
echo  "$(tput setaf 10)[AirData assistant]: Olá $USER, sou seu assistente. !;"
echo  "$(tput setaf 10)[AirData assistant]: Verificando se você possui o Java instalado...;"
sleep 2

which java
if [ $? -eq 0 ]
	then
		echo "$(tput setaf 10)[AirData assistant]:: Você já tem o java instalado!!!"
		sleep 2 
		clear
	else
		echo "$(tput setaf 10)[AirData assistant]: Nenhuma versão do java encontrada!"
		echo "$(tput setaf 10)[AirData assistant]: Você deseja instalar o Java (S/N)?"
		read inst
		clear
		if [ \"$inst\" == \"S\" ]
			then
				echo "$(tput setaf 10)[AirData assistant]: Ok, Iniciando a instalação do Java!"
				echo "$(tput setaf 10)[AirData assistant]: Adicionando o repositório!"
				sleep 2
				sudo add-apt-repository ppa:webupd8team/java -y
				clear
				echo "$(tput setaf 10)[AirData assistant]: Atualizando! Quase lá."
				sleep 2
				sudo apt update -y
				clear
				
				if [ $VERSAO -eq 11 ]
					then
						echo "$(tput setaf 10)[AirData assistant]:Preparando para instalar a versão 11 do Java."
						sudo apt install default-jre ; apt install openjdk-11-jre-headless; -y
						clear
						echo "$(tput setaf 10)[AirData assistant]:Java instalado com sucesso!"
				fi
			else 	
				echo "$(tput setaf 10)[AirData assistant]: Você optou por não instalar o Java por enquanto."
				sleep 2
				clear
			
		fi
fi

	echo "$(tput setaf 10)[AirData assistant]: Clonando o repositório..."
	sleep 2
	cd ~/
	git clone https://github.com/KauanCavazani/Projeto-AirDataClient.git
	clear
	echo "$(tput setaf 10)[AirData assistant]: Repositório criado!"
	sleep 2
	clear
	cd ~/Projeto-AirDataClient/AirDataClient
	echo "$(tput setaf 10)[AirData assistant]: Instalando a aplicação..."
	sleep 2
	mvn install
	cd ~/Projeto-AirDataClient/AirDataClient/target
	clear
	echo "$(tput setaf 10)[AirData assistant]: Criando atalho na desktop."
	sleep 2
	cp ~/Projeto-AirDataClient/AirDataClient/target/AirDataClient-1.0-SNAPSHOT-jar-with-dependencies.jar ~/Desktop
	clear
	cd ~/Desktop
	echo "$(tput setaf 10)[AirData assistant]: Iniciando aplicação!"
	java -jar AirDataClient-1.0-SNAPSHOT-jar-with-dependencies.jar
			
echo "$(tput setaf 10)[AirData assistant]: Encerrando, até mais!"

# ===================================================================
# Todos direitos reservados para o autor: Dra. Profa. Marise Miranda.
# Sob licença Creative Commons @2020
# Podera modificar e reproduzir para uso pessoal.
# Proibida a comercialização e a exclusão da autoria.
# ===================================================================
