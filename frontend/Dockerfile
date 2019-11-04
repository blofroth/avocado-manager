FROM nginx:1.15.2
COPY ./build /var/www
COPY nginx.conf /etc/nginx/nginx.conf
EXPOSE 80 
ENV BACKEND_ROOT=http://localhost:8080/
CMD /bin/bash -c "envsubst < /var/www/env-config.js.template > /var/www/env-config.js && exec nginx -g 'daemon off;'"
