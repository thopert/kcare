# following commands are required to run the server
# WARNING: If too few memory is provided for vagrant, it randomly kills the server!!!
# INFO: Please give vagrant some time. A virtual machine is confortable but not that fast.
#$ git clone git@git.uibk.ac.at:qepssepmss2017/ps2_team3.git
#$ cd ps2_team3
#$ vagrant up
#$ vagrant ssh
#vagrant:$ cd /vagrant/
#vagrant:$ mvn spring-boot:run

Vagrant.configure("2") do |config|
    config.vm.box = "ubuntu/trusty64"
    config.vm.network "forwarded_port", guest: 8080, host: 8080
    config.vm.provider "virtualbox" do |v|
        v.memory = 4096
        v.cpus = 4
    end
    config.vm.provision "shell", inline: <<-SHELL
        export DEBIAN_FRONTEND=noninteractive
        sudo apt-get update
        sudo apt-get -y upgrade
        sudo timedatectl set-timezone Europe/Vienna
        sudo -E apt-get -q -y install mysql-server
        sudo apt-get install -y subversion maven git
        sudo apt-get install -y software-properties-common
        sudo add-apt-repository -y ppa:webupd8team/java
        sudo apt-get update
        echo debconf shared/accepted-oracle-license-v1-1 select true | sudo debconf-set-selections
        echo debconf shared/accepted-oracle-license-v1-1 seen true |   sudo debconf-set-selections
        sudo apt-get install -y oracle-java8-installer
        echo \"create database kcare\" | mysql -u root
        echo \"create database kcaretest\" | mysql -u root
        mysql -u root kcare < /vagrant/dump.sql
    SHELL
end
