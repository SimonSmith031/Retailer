#!/bin/sh

# 记录环境变量
echo 'Setting up environments...'
echo '--------------------'
echo 'DB_PASSWORD='`cat ./mysql/ROOT_PASSWORD` > .env
echo "NGINX_PORT=8091" >> .env
echo "JAVA_PORT=8092" >> .env

# 如果环境变量未设置，则获取本机的ip
if [ -z $JAVA_HOSTNAME ]; then
    #JAVA_HOSTNAME=`ifconfig -a|grep inet|grep -v 127.0.0.1|grep -v inet6|awk '{print $2}'|tr -d "addr:"`
    JAVA_HOSTNAME=`dig +short myip.opendns.com @resolver1.opendns.com`
    echo 'No $JAVA_HOSTNAME variable found. Using ip of this machine.'
    echo 'IP: '$JAVA_HOSTNAME
fi

echo "JAVA_HOSTNAME="$JAVA_HOSTNAME >> .env

# 构建镜像
echo -e "\nBuilding..."
echo '--------------------'
sudo docker-compose build --no-cache
