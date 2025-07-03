# 1단계: 빌드
FROM node:18 AS builder
WORKDIR /app
COPY . .
RUN npm install && npm run build

# 2단계: nginx로 서빙
FROM nginx:alpine
COPY --from=builder /app/dist /usr/share/nginx/html
COPY nginx.conf /etc/nginx/conf.d/default.conf
EXPOSE 8080
CMD ["nginx", "-g", "daemon off;"]
