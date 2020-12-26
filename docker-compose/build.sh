#!/bin/sh

# 记录环境变量
echo 'DB_PASSWORD='`cat ./mysql/ROOT_PASSWORD` > .env
echo "NGINX_PORT=8091" >> .env
echo "JAVA_SERVER_PORT=8092" >> .env

# 构建镜像
sudo docker-compose build --no-cache
