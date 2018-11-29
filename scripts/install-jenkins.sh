#!/usr/bin/env bash
# install java 8 and maven
sudo apt update -y
sudo apt-get install openjdk-8-jre -y
sudo apt-get install openjdk-8-jdk -y
sudo apt-get install maven -y

# install jenkins repo
wget -q -O - https://pkg.jenkins.io/debian/jenkins.io.key | sudo apt-key add -

# add jenkins to source list
echo "installing jenkins"
sudo sh -c 'echo deb http://pkg.jenkins.io/debian-stable binary/ > /etc/apt/sources.list.d/jenkins.list'

sudo apt update -y

sudo apt install jenkins -y
systemctl status jenkins
# enable jenkins
systemctl start jenkins

# enable uncomplicated firewall
echo "Enabling firewalls"
sudo ufw enable
sudo ufw allow 8080
sudo ufw allow ssh
sudo ufw allow 22
sudo ufw status

# get jenkins password
IP=ifconfig | grep -Eo 'inet (addr:)?([0-9]*\.){3}[0-9]*' | grep -Eo '([0-9]*\.){3}[0-9]*' | grep -v '127.0.0.1'
echo "Visit ${IP}:8080/jenkins"
echo "Jenkins password:"
cat /var/lib/jenkins/secrets/initialAdminPassword

# Install Docker
echo "Installing Docker"
sudo apt-get install docker.io
sudo usermod -aG jenkins docker