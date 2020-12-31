FROM nginx

COPY dist /usr/share/nginx/html/
COPY nginx.conf /etc/nginx/

COPY ./entry-point.sh /
RUN chmod +x entry-point.sh

ENTRYPOINT ["/entry-point.sh"]
CMD ["nginx", "-g", "daemon off;"]
