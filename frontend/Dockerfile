
FROM node:18-alpine AS ui-builder

WORKDIR /app
COPY package.json yarn.lock ./
RUN yarn install;

COPY . ./
RUN yarn build;

FROM nginx:alpine
RUN rm -rf /usr/share/nginx/html/*
COPY --from=ui-builder /app/dist /usr/share/nginx/html

COPY nginx.conf /etc/nginx/conf.d/default.conf

EXPOSE 80

CMD ["nginx", "-g", "daemon off;"]