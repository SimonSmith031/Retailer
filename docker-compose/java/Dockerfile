FROM openjdk:8-jdk-alpine
COPY retailer.jar /java/
# COPY会把文件夹中的内容拷贝过去
# COPY真的非常奇怪，各种BUG
COPY assets /java/assets/
# 用tar也不行……不知道为什么，总之就是各种问题，真的垃圾
