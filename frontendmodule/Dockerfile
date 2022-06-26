FROM node:16 as builder

RUN mkdir -p /app

WORKDIR /app

COPY . .

RUN npm install
RUN npm run build --prod

EXPOSE 4200

CMD ["npm", "start"]

#FROM nginx:alpine
#
#COPY src/nginx/etc/conf.d/default.conf /etc/nginx/conf.d/default.conf
#COPY --from=builder app/dist/frontend usr/share/nginx/html
#
#EXPOSE 80
#
#CMD nginx -g "daemon off;"
