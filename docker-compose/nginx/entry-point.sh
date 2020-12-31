#!/bin/bash

# 替换js中的一些信息，sed一次只能够替换一个文件
files=`ls /usr/share/nginx/html/js/*.*`
# 注意：用到的两个环境变量不要包含引号
for file in $files; do
    sed -i "s/ENV_WRAPPER.JAVA_HOSTNAME/'$JAVA_HOSTNAME'/g" $file
    sed -i "s/ENV_WRAPPER.JAVA_PORT/'$JAVA_PORT'/g" $file
done;

exec "$@"
