FROM nginx:1.17.7-alpine
 
WORKDIR /usr/src/app
 
COPY nginx.conf /etc/nginx/conf.d/default.conf
 
COPY ./dist/out /usr/share/nginx/html
 
EXPOSE 80