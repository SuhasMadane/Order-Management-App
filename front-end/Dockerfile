FROM node:17-alpine
WORKDIR /frontapp
COPY package.json package-lock.json ./
RUN npm install
COPY . .
EXPOSE 3000
CMD ["npm","start"]